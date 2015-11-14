package net.binggl.ninja.oauth.util;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Lang;
import ninja.i18n.Messages;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * @author henrik
 */
public class InternationalizationHelper {

	@Inject
	Messages messages;
	
	@Inject
	Lang lang;
	
	/**
	 * get the current language
	 * @param context
	 * @return
	 */
	public String getLanguage(Context context) {
		Result result = Results.ok();
		Optional<String> language = lang.getLanguage(context, Optional.of(result));
		if(language.isPresent())
			return language.get();
		return null;
	}

	/**
	 * get the current language
	 * @param context
	 * @param result
	 * @return
	 */
	public String getLanguage(Context context, Optional<Result> result) {
		Optional<String> language = lang.getLanguage(context, result);
		if(language.isPresent())
			return language.get();
		return null;
	}

	/**
	 * get a translated message
	 * @param context
	 * @param key
	 * @param parameters
	 * @return
	 */
	public String getMessage(Context context, String key, Object... parameter) {
		Result result = Results.ok();
		Optional<String> language = lang.getLanguage(context, Optional.of(result));
		Optional<String> message =  messages.get(key, language, parameter);
		if(message.isPresent())
			return message.get();
		return null;
	}

}
