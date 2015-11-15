package net.binggl.ninja.oauth;

import org.pac4j.oauth.profile.google2.Google2Profile;

import net.binggl.ninja.oauth.models.Token;

/**
 * the service is used to lookup the provided oauth profile in a
 * profile store. If the profile is valid create a Token which is 
 * stored in the session
 * @author henrik
 */
public interface OauthAuthorizationService {

	/**
	 * lookup the profile in a store and create a token
	 * @param profile
	 * @return
	 */
	Token lookupProfile(Google2Profile profile);
	
}
