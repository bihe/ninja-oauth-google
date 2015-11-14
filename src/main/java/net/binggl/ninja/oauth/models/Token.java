package net.binggl.ninja.oauth.models;

import java.util.Date;

/**
 * simple bean holding session/authentication information
 * @author henrik
 */
public class Token {
	// members
	private String sessionId;
	private Date timeStamp;
	
	/**
	 * default
	 */
	public Token() {
		super();
	}
	
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the userId to set
	 */
	public void setSessionId(String userId) {
		this.sessionId = userId;
	}
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}