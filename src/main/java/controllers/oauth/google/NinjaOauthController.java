package controllers.oauth.google;

import static net.binggl.ninja.oauth.Constants.AUTH_FAILURE_URL;
import static net.binggl.ninja.oauth.Constants.AUTH_SUCCESS_URL;

import org.pac4j.oauth.profile.google2.Google2Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.binggl.ninja.oauth.OauthAuthorizationService;
import net.binggl.ninja.oauth.OauthClient;
import net.binggl.ninja.oauth.models.Token;
import net.binggl.ninja.oauth.util.InternationalizationHelper;
import net.binggl.ninja.oauth.util.TokenHelper;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.session.FlashScope;
import ninja.utils.NinjaProperties;

@Singleton
public class NinjaOauthController {
	
	// members
	private static final Logger logger = LoggerFactory.getLogger(NinjaOauthController.class);
	
	// inject
	
	private @Inject OauthClient oauthClient;

	private @Inject	NinjaProperties ninjaProperties;
	
	private @Inject	OauthAuthorizationService authorizationService;
	
	private @Inject InternationalizationHelper i18n;
	
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
			Google2Profile profile = (Google2Profile)oauthClient.getProfile(context);
			if(profile == null) {
				logger.error("Could not get a profile from oauth service - null!");
				throw new NullPointerException("No profile provided from authentication service!");
			}
			logger.info("Got a profile from oauth provider; email: {}", profile.getEmail());
			
			Token userToken = authorizationService.lookupProfile(profile);
			if(userToken == null) {
				logger.error("Could not lookup given profile {} - token is null!", profile);
				throw new NullPointerException("No token for given profile available! " + profile.toString());
			}
			
			logger.info("Set token {}", userToken.toString());
			TokenHelper.setToken(context, userToken);
			
			r = Results.redirect(ninjaProperties.get(AUTH_SUCCESS_URL));

		} catch(Exception EX) {
			
			logger.error("Could not authenticate the user {}; stack: {}", EX.getMessage(), EX);
			
			FlashScope flashScope = context.getFlashScope();
			String flashMessage = i18n.getMessage(context, "auth.login.error", EX.getMessage());
			flashScope.error(flashMessage);
			
			r = Results.redirect(ninjaProperties.get(AUTH_FAILURE_URL));
			
		}
		return r;
	}
}