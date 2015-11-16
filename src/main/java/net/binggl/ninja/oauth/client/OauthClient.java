package net.binggl.ninja.oauth.client;

import ninja.Context;

import org.pac4j.core.client.Client;
import org.pac4j.oauth.profile.OAuth20Profile;

/**
 * define a client for different OAuth implementations
 */
public interface OauthClient {

	/**
	 * get the redirect URL for the auth process
	 * @param context
	 * @return
	 */
	String getRedirectUrl(Context context);

	/**
	 * get a security OAuthClient
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Client getClient();

	/**
	 * get the OAuthProfile
	 * @return
	 */
	OAuth20Profile getProfile(Context context) throws Throwable;
}
