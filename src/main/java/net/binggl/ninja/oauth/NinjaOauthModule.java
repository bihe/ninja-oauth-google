package net.binggl.ninja.oauth;

import com.google.inject.AbstractModule;

import net.binggl.ninja.oauth.util.CommonResults;
import net.binggl.ninja.oauth.util.CustomObjectMapper;
import net.binggl.ninja.oauth.util.InternationalizationHelper;

public class NinjaOauthModule extends AbstractModule {

	@Override
    protected void configure() {
    	bind(OauthClient.class).to(OauthGoogleClient.class);
    	bind(OauthAuthorizationService.class).to(OauthAuthorizationServiceImpl.class);

		// helpers
		bind(InternationalizationHelper.class);
    	bind(CommonResults.class);
    	
    	// startup
    	bind(CustomObjectMapper.class);
    }
}