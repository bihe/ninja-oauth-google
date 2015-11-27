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
	 * @param context the Ninja Context
	 * @return the oauth redirect url
	 */
	String getRedirectUrl(Context context);

	/**
	 * get a security OAuthClient
	 * @return a pac4j client
	 */
	@SuppressWarnings("rawtypes")
	Client getClient();

	/**
	 * get the OAuthProfile
	 * @param context the Ninja Context
	 * @return a Google oauth2 profile
	 * @throws Throwable error during fetch profile
	 */
	OAuth20Profile getProfile(Context context) throws Throwable;
}
