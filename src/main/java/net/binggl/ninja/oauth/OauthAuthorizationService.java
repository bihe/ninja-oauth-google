package net.binggl.ninja.oauth;

import org.pac4j.oauth.profile.google2.Google2Profile;

import ninja.Context;

/**
 * the service is used to lookup the provided oauth profile in a
 * profile store. The logic determines if the profile is valid and
 * processes the profile date (e.g. store it in session, ...)
 * @author henrik
 */
public interface OauthAuthorizationService {

	/**
	 * lookup the profile in a store and handle further processing
	 * @param profile
	 * @return
	 */
	boolean lookupAndProcessProfile(Context context, Google2Profile profile);
	
}
