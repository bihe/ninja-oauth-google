package net.binggl.ninja.oauth;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.oauth.profile.google2.Google2Profile;

import ninja.Context;


/**
 * lookup the oauth profile and process it
 * @author henrik
 */
public class OauthAuthorizationServiceImpl implements OauthAuthorizationService {

	@Override
	public boolean lookupAndProcessProfile(Context context, Google2Profile profile) {
		boolean profileValid = false;
		if(profile != null && StringUtils.isNotEmpty(profile.getAccessToken())) {
			profileValid = true;
			
			context.getSession().put("id", profile.getId());
		}
		
		return profileValid;
	}
}
