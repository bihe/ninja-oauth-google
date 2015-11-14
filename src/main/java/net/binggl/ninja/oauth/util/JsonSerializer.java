package net.binggl.ninja.oauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * serialize, deserialize JSON
 * @author henrik
 */
public class JsonSerializer {

	/**
	 * serialize an object to a string
	 * @param object
	 * @return
	 * @throws Throwable
	 */
	public static <T> String serialize(T object) throws Throwable {
		String serialized = null;
		ObjectMapper mapper = new ObjectMapper();
		serialized = mapper.writeValueAsString(object);
		return serialized;
	}
	
	/**
	 * deserialize the value
	 * @param value
	 * @return
	 * @throws Throwable
	 */
	public static <T> T deserialize(String value, Class<T> clasz) throws Throwable {
		T deserialized;
		ObjectMapper mapper = new ObjectMapper();
		deserialized = (T)mapper.readValue(value, clasz);
		return deserialized;
	}
}
