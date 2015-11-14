package net.binggl.ninja.oauth.util;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.session.FlashScope;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import static net.binggl.ninja.oauth.Constants.*;

/**
 * useful result actions
 * @author henrik
 */
public class CommonResults {
	
	@Inject
	NinjaProperties ninjaProperties;

	@Inject
	InternationalizationHelper i18n;

	/**
	 * forward to login page and display message
	 * @param context
	 * @param message
	 * @return
	 */
	public Result getNoAccessResult(Context context, String message) {
		Result r = Results.forbidden();
		
		context.getSession().clear();
		FlashScope flashScope = context.getFlashScope();
		String flashMessage = i18n.getMessage(context, "auth.login.error", message);
		flashScope.error(flashMessage);
		r = Results.redirect(ninjaProperties.get("auth.error.url"));
		
		return r;
	}
	
	/**
	 * return a error result
	 * @param thr
	 * @return
	 */
	public Result getErrorResult(Throwable thr) {
		Result result = Results.internalServerError();
		result.render("message", thr.getMessage());
		return result.html().template(SYSTEM_ERROR);
	}
	
	/**
	 * return a error result
	 * @param error
	 * @return
	 */
	public Result getErrorResult(String error) {
		Result result = Results.internalServerError();
		result.render("message", error);
		return result.html().template(SYSTEM_ERROR);
	}
}
