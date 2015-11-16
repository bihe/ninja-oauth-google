package net.binggl.ninja.oauth;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import net.binggl.ninja.oauth.client.OauthClient;
import ninja.Context;
import ninja.NinjaTest;
import ninja.utils.FakeContext;

public class OauthClientTest extends NinjaTest {

	private OauthClient client;
	
	@Before
    public void setup() throws Exception{
		client = getInjector().getInstance(OauthClient.class);
    }

    @Test
    public void testInit() {
        assertNotNull(client);
    }

    @Test
    public void testRedirectUrl() {
    	FakeContext context = new FakeContext();
    	context.setSessionCookie(null);
    	
    	// FakeSession impl Session 
    	
    	//String redirectUrl = client.getRedirectUrl(context));
    	//assertNotNull(redirectUrl);
    }
    
}

