package net.binggl.ninja.oauth.client;

import ninja.Context;
import ninja.utils.NinjaProperties;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.oauth.client.Google2Client;

import com.google.inject.Inject;

import net.binggl.ninja.oauth.NinjaWebContext;

import static net.binggl.ninja.oauth.Constants.*;

import org.pac4j.oauth.credentials.OAuthCredentials;
import org.pac4j.oauth.profile.google2.Google2Profile;

/**
 * get a authentication client
 * @author henrik
 */
public class OauthGoogleClient implements OauthClient {

	private Google2Client client = null;
	private boolean isInit = false;

	@Inject NinjaProperties ninjaProperties;
	
	
	public OauthGoogleClient() {
		this.client = new Google2Client();
	}


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


	@Override
	public String getRedirectUrl(Context context) {
		this.initClient();
		NinjaWebContext webCtx = new NinjaWebContext(context);
		return client.getRedirectionUrl(webCtx);
	}

	@Override
	public Google2Client getClient() {
		this.initClient();
		return this.client;
	}
	
	@Override
	public Google2Profile getProfile(Context context) throws Throwable {
		this.initClient();
		NinjaWebContext webCtx = new NinjaWebContext(context);
		OAuthCredentials credentials = this.client.getCredentials(webCtx);
		Google2Profile profile = client.getUserProfile(credentials, webCtx);

		return profile;
	}
}
