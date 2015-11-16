package net.binggl.ninja.oauth;

import com.google.inject.AbstractModule;

import net.binggl.ninja.oauth.client.OauthClient;
import net.binggl.ninja.oauth.client.OauthGoogleClient;

public class NinjaOauthModule extends AbstractModule {

	@Override
    protected void configure() {
    	bind(OauthClient.class).to(OauthGoogleClient.class);
    	bind(OauthAuthorizationService.class).to(OauthAuthorizationServiceImpl.class);
    }
}