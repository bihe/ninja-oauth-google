Google-Oauth2 module for Ninja framework
=====================
This is a plugable module for the [Ninja web framework](http://www.ninjaframework.org/) which supports Google oauth2 authentication.

[![Build Status](https://travis-ci.org/bihe/ninja-oauth-google.png)](https://travis-ci.org/bihe/ninja-oauth-google)
[![license](http://img.shields.io/badge/license-apache_2.0-red.svg?style=flat)](https://raw.githubusercontent.com/bihe/ninja-mongodb/master/LICENSE)

Setup
-----

1) Add the ninja-google-oauth dependency to your pom.xml:

	<dependency>
	    <groupId>net.binggl</groupId>
	    <artifactId>ninja-google-oauth-module</artifactId>
	    <version>1.0.1</version>
	</dependency>
	
2) Google Developer Console

Setup a new client ID of type 'Web application'. The 'Client ID' and 'Client secret' are used in the application.conf.

```
## oauth authentication
ninja.oauth.google.key=KEY
ninja.oauth.google.secret=SECRET
ninja.oauth.callback.url=http://localhost:8080/oauth2callback
ninja.oauth.success.url=http://localhost:8080/
ninja.oauth.failure.url=http://localhost:8080/login
```

3) Setup the module

```java
package conf;

import com.google.inject.AbstractModule;
import net.binggl.ninja.oauth.NinjaOauthModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        install(new NinjaOauthModule());
    }

}
```

4) Add routes

The callback route needs to match the URL specified in the Google Developer Console!

```java
public void init(Router router) {  

    // authentication routes
    router.GET().route("/startauth").with(NinjaOauthController.class, "startauth");
    router.GET().route("/oauth2callback").with(NinjaOauthController.class, "oauth2callback");
    
    ///////////////////////////////////////////////////////////////////////
    // Index / Catchall shows index page
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/.*").with(HomeController.class, "index");
}
```

To start the authentication process call the URL [/startauth](/startauth). A redirect is created in the browser and you are forwarded to the Google login
and OAuth consent screen.

5) Authorization logic

The module is only responsible for authentication using Google Oauth2. The authorization process needs to be implemented.

```java
protected void configure() {
        
    bind(OauthAuthorizationService.class).toInstance(new OauthAuthorizationService() {
        @Override
        public boolean lookupAndProcessProfile(Context context, Google2Profile profile) {
            boolean profileValid = false;
            if(profile != null && StringUtils.isNotEmpty(profile.getAccessToken())) {
                profileValid = true;
                context.getSession().put("id", profile.getId());
            }
            return profileValid;
        }
    });        
}
```
