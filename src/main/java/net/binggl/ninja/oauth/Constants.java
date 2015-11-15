package net.binggl.ninja.oauth;

/**
 * global constants, values, ...
 * @author henrik
 */
public class Constants {

	/**
	 * The name of the key used in the session
	 */
	public static final String SESSION_TOKEN = "auth.sess.token";
	
	/**
	 * date&time pattern used for serialisation
	 */
	public static final String DATETIME_PATTERN = "yyyy.MM.dd_HH:mm:ss";
	
	/**
	 * the path to the system-error template
	 */
	public final static String SYSTEM_ERROR = "/views/system/500error.ftl.html";
	
	/**
	 * the google oauth key
	 */
	public final static String OAUTH_GOOGLE_KEY = "ninja.oauth.google.key";
	
	/**
	 * the google oauth secret
	 */
	public final static String OAUTH_GOOGLE_SECRET = "ninja.auth.google.secret";
	
	/**
	 * the google oauth callback url
	 */
	public final static String OAUTH_GOOGLE_CALLBACK_URL = "ninja.auth.callback.url";
	
	/**
	 * use this url for successful authentications
	 */
	public final static String AUTH_SUCCESS_URL = "ninja.auth.success.url";
	
	/**
	 * use this url for authentications errors
	 */
	public final static String AUTH_FAILURE_URL = "ninja.auth.failure.url";
	
	/**
	 * time to life for tokens
	 */
	public final static String AUTH_TOKEN_TTL = "ninja.auth.token.ttl";
	
}
