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
}
