package net.binggl.ninja.oauth;

import static net.binggl.ninja.oauth.Constants.OAUTH_FAILURE_URL;
import static net.binggl.ninja.oauth.Constants.OAUTH_SUCCESS_URL;

import org.pac4j.oauth.profile.google2.Google2Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.binggl.ninja.oauth.client.OauthClient;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.session.FlashScope;
import ninja.utils.NinjaProperties;

@Singleton
public class NinjaOauthController {
	
	// members
	private static final Logger logger = LoggerFactory.getLogger(NinjaOauthController.class);
	private static final String PARAM_ERROR = "error";
	
	// inject
	
	private @Inject OauthClient oauthClient;

	private @Inject	NinjaProperties ninjaProperties;
	
	private @Inject	OauthAuthorizationService authorizationService;
	
	// methods
	
	/**
	 * start the OAUTH process by sending the user to the auth-system url
	 * @param context environment context (request, repsonse, session, ...)
	 * @return redirects to the oauth
	 * @throws Throwable 
	 */
	public Result startauth(Context context) throws Throwable {
		return Results.redirect(oauthClient.getRedirectUrl(context));
    }
	
	/**
	 * process the OAuth callback
	 * @param context environment context (request, repsonse, session, ...)
	 * @return on success forward to a configured url
	 * @throws Throwable
	 */
	public Result oauth2callback(Context context) throws Throwable {
		Result r = Results.ok();
		
		try {
			FlashScope flashScope = context.getFlashScope();
			
			Google2Profile profile = (Google2Profile)oauthClient.getProfile(context);
			if(profile == null) {
				logger.error("Could not get a profile from oauth service - null!");
				String reason = context.getParameter(PARAM_ERROR);
				logger.debug("Got reason from backend {}", reason);
				flashScope.error("No profile provided from authentication service! " + "(" + reason + ")");
				return Results.redirect(ninjaProperties.get(OAUTH_FAILURE_URL));
			}
			logger.info("Got a profile from oauth provider; email: {}", profile.getEmail());
			
			boolean validProfile = authorizationService.lookupAndProcessProfile(context, profile);
			if(!validProfile) {
				logger.error("Could not lookup given profile {}!", profile);
				flashScope.error("Could not lookup and process the profile! " + profile.getEmail());
				return Results.redirect(ninjaProperties.get(OAUTH_FAILURE_URL));
			}
			
			r = Results.redirect(ninjaProperties.get(OAUTH_SUCCESS_URL));

		} catch(Exception EX) {
			logger.error("Could not authenticate the user {}; stack: {}", EX.getMessage(), EX);
			
			FlashScope flashScope = context.getFlashScope();
			flashScope.error(EX.getMessage());
			
			r = Results.redirect(ninjaProperties.get(OAUTH_FAILURE_URL));
		}
		return r;
	}
}