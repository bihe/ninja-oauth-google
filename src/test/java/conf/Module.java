package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import net.binggl.ninja.oauth.NinjaOauthModule;

@Singleton
public class Module extends AbstractModule {
    
    protected void configure() {
        
    	install(new NinjaOauthModule());
       
    }

}