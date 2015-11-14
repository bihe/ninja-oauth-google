package net.binggl.ninja.oauth;

import java.util.Date;

import ninja.Context;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static net.binggl.ninja.oauth.Constants.*;
import net.binggl.ninja.oauth.models.Token;
import net.binggl.ninja.oauth.util.JsonSerializer;

/**
 * implement the security-service; store the ids in the session
 * @author henrik
 */
public class SecurityServiceImpl implements SecurityService {

	final DateTimeFormatter DateFormat = DateTimeFormat.forPattern(DATETIME_PATTERN);
	
	/**
	 * create a new token
	 * @throws Throwable 
	 */
	@Override
	public Token createToken(Context context, String id) throws Throwable {
		Token token = new Token();
		token.setSessionId(id);
		token.setTimeStamp(new Date());
		
		// put the values in the session
		context.getSession().put(SESSION_TOKEN, JsonSerializer.serialize(token));
		
		return token;
	}

	@Override
	public Token getToken(Context context) throws Throwable {
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
