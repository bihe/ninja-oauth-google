package net.binggl.ninja.oauth;

import net.binggl.ninja.oauth.models.Token;
import ninja.Context;

/**
 * simple service to take care of security
 * @author henrik
 */
public interface SecurityService {

	/**
	 * create a new token
	 * @param context
	 * @param id
	 */
	Token createToken(Context context, String id) throws Throwable;
	
	/**
	 * get the token
	 * @param context
	 * @return
	 */
	Token getToken(Context context) throws Throwable;
}
