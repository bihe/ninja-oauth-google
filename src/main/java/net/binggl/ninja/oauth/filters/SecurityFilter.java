package net.binggl.ninja.oauth.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.utils.NinjaProperties;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.binggl.ninja.oauth.SecurityService;
import net.binggl.ninja.oauth.models.Token;
import net.binggl.ninja.oauth.util.CommonResults;
import net.binggl.ninja.oauth.util.InternationalizationHelper;

/**
 * check the loged-in user
 * @author henrik
 */
public class SecurityFilter implements Filter {

	// members
	private static final Logger logger = LoggerFactory
			.getLogger(SecurityFilter.class);
	
	@Inject
	CommonResults common;

	@Inject
	SecurityService securityService;

	@Inject
	InternationalizationHelper i18n;
	
	@Inject
	NinjaProperties ninjaProperties;

	@Override
	public Result filter(FilterChain chain, Context context) {

		logger.debug("start: security check.");

		try {

			// check if the session is available
			if (context.getSession() == null) {
				logger.warn("No session available, show view 403");
				return common.getNoAccessResult(context, "No session available!");
			}

			// check for the login-data in the session
			Token t = securityService.getToken(context);
			if (t == null) {
				logger.warn("No token in session, show view 403");
				return common.getNoAccessResult(context, "No token in session!");
			}

			// check the token TTL
			DateTime created = new DateTime(t.getTimeStamp());
			long diff = DateTime.now().getMillis() - created.getMillis();
			long ttl = ninjaProperties.getIntegerWithDefault("auth.token.ttl", 3600);
			if (diff / 1000 > ttl) {
				logger.warn("The token has expired, show view 403");
				return common.getNoAccessResult(context, i18n.getMessage(context, "auth.session.expired"));
			}
		} catch (Exception EX) {
			logger.error("Error during security filter check: " + EX.getMessage(), EX);
			return common.getErrorResult(EX);
		} catch (Throwable e) {
			logger.error("Error during security filter check: " + e.getMessage(), e);
			return common.getErrorResult(e);
		}

		logger.debug("done: security check!");
		
		return chain.next(context);
	}
	
	
}