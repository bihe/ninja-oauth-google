package net.binggl.ninja.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Before;
import org.junit.Test;

import net.binggl.ninja.oauth.client.OauthClient;
import net.binggl.ninja.utils.FakeSession;
import ninja.NinjaTest;
import ninja.session.Session;
import ninja.utils.FakeContext;

public class OauthClientTest extends NinjaTest {

	private OauthClient client;
	
	@Before
    public void setup() throws Exception{
		client = getInjector().getInstance(OauthClient.class);
    }

    @Test
    public void testClientInit() {
        assertNotNull(client);
    }

    @Test
    public void testRedirectUrl() throws Throwable {
    	FakeContext context = new FakeContext();
    	Session session = new FakeSession();
    	session.init(context);
    	context.setSessionCookie(session);
    	
    	String redirectUrl = client.getRedirectUrl(context);
    	assertNotNull(redirectUrl);
    	
    	List<NameValuePair> params = URLEncodedUtils.parse(new URI(redirectUrl), "UTF-8");
    	assertNotNull(params);
    	
    	for(NameValuePair tuple : params) {
    		if("client_id".equals(tuple.getName())) {
    			assertEquals("KEY",tuple.getValue());	
    		}
    		
    		if("redirect_uri".equals(tuple.getName())) {
    			assertEquals("http://localhost:8080/oauth2callback",tuple.getValue());	
    		}
    		
    		if("scope".equals(tuple.getName())) {
    			assertEquals("profile email",tuple.getValue());	
    		}
    	}
    }
}

