package net.binggl.ninja.oauth;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.oauth.profile.google2.Google2Profile;

import net.binggl.ninja.oauth.models.Token;

/**
 * implement the security-service; store the ids in the session
 * @author henrik
 */
public class OauthAuthorizationServiceImpl implements OauthAuthorizationService {

	@Override
	public Token lookupProfile(Google2Profile profile) {
		Token t = null;
		if(profile != null && StringUtils.isNotEmpty(profile.getAccessToken())) {
			t = new Token();
			t.setTimeStamp(new Date());
			t.setSessionId(profile.getId());
		}
		
		return t;
	}
}
