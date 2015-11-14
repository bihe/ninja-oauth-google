package net.binggl.ninja.oauth;

import ninja.Context;
import ninja.utils.NinjaProperties;

import org.pac4j.oauth.client.Google2Client;

import com.google.inject.Inject;
import org.pac4j.oauth.credentials.OAuthCredentials;
import org.pac4j.oauth.profile.google2.Google2Profile;

/**
 * get a authentication client
 * @author henrik
 */
public class GoogleClient implements OauthClient {

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
	public GoogleClient() {
		this.client = new Google2Client();
	}

	// init methods

	/**
	 * init the client
	 */
	protected void initClient() {
		if(this.isInit == false) {
			this.client.setKey(ninjaProperties.get("auth.google.key"));
			this.client.setSecret(ninjaProperties.get("auth.google.secret"));
			this.client.setCallbackUrl(ninjaProperties.get("auth.callback.url"));
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
