package net.binggl.ninja.oauth.util;

import static net.binggl.ninja.oauth.Constants.SESSION_TOKEN;

import org.apache.commons.lang3.StringUtils;

import net.binggl.ninja.oauth.models.Token;
import ninja.Context;

public class TokenHelper {

	/**
	 * set the token in the context
	 * @param context
	 * @param token
	 * @throws Throwable
	 */
	public static void setToken(Context context, Token token) throws Throwable {
		// put the values in the session
		context.getSession().put(SESSION_TOKEN, JsonSerializer.serialize(token));
	}

	/**
	 * get the token from the context
	 * @param context
	 * @return
	 * @throws Throwable
	 */
	public static Token getToken(Context context) throws Throwable {
		Token token = null;
		String sessionPayload = "";
		if(context.getSession() != null) {
			sessionPayload = context.getSession().get(SESSION_TOKEN);
		}
		if(StringUtils.isNotEmpty(sessionPayload)) {
			token = JsonSerializer.deserialize(sessionPayload, Token.class);
		}
		return token;
	}
}
