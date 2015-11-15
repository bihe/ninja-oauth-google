package net.binggl.ninja.oauth;

import ninja.Context;
import ninja.utils.NinjaProperties;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.oauth.client.Google2Client;

import com.google.inject.Inject;

import static net.binggl.ninja.oauth.Constants.*;

import net.binggl.ninja.oauth.util.NinjaWebContext;

import org.pac4j.oauth.credentials.OAuthCredentials;
import org.pac4j.oauth.profile.google2.Google2Profile;

/**
 * get a authentication client
 * @author henrik
 */
public class OauthGoogleClient implements OauthClient {

	// members
	private Google2Client client = null;
	private boolean isInit = false;

	// inject
	@Inject 
	NinjaProperties ninjaProperties;
	
	// constructors
	
	/**
	 * default
	 */
	public OauthGoogleClient() {
		this.client = new Google2Client();
	}

	// init methods

	/**
	 * init the client
	 */
	protected void initClient() {
		if(this.isInit == false) {
			
			if(StringUtils.isEmpty(ninjaProperties.get(OAUTH_GOOGLE_KEY)))
				throw new IllegalArgumentException("Empty property " + OAUTH_GOOGLE_KEY);
			if(StringUtils.isEmpty(ninjaProperties.get(OAUTH_GOOGLE_SECRET)))
				throw new IllegalArgumentException("Empty property " + OAUTH_GOOGLE_SECRET);
			if(StringUtils.isEmpty(ninjaProperties.get(OAUTH_GOOGLE_CALLBACK_URL)))
				throw new IllegalArgumentException("Empty property " + OAUTH_GOOGLE_CALLBACK_URL);
			
			this.client.setKey(ninjaProperties.get(OAUTH_GOOGLE_KEY));
			this.client.setSecret(ninjaProperties.get(OAUTH_GOOGLE_SECRET));
			this.client.setCallbackUrl(ninjaProperties.get(OAUTH_GOOGLE_CALLBACK_URL));
			this.isInit = true;
		}
	}

	// methods

	/**
	 * get the redirect URL starting the OAuth process
	 * @param context
	 * @return
	 */
	@Override
	public String getRedirectUrl(Context context) {
		this.initClient();
		NinjaWebContext webCtx = new NinjaWebContext(context);
		return client.getRedirectionUrl(webCtx);
	}

	/**
	 * get the google oauth client
	 * @return
	 */
	@Override
	public Google2Client getClient() {
		this.initClient();
		return this.client;
	}

	/**
	 * get the profile data
	 * @return
	 */
	@Override
	public Google2Profile getProfile(Context context) throws Throwable {
		this.initClient();
		NinjaWebContext webCtx = new NinjaWebContext(context);
		OAuthCredentials credentials = this.client.getCredentials(webCtx);
		// get the profile
		Google2Profile profile = client.getUserProfile(credentials, webCtx);

		return profile;
	}
}
