/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.binggl.ninja.oauth;

import ninja.Context;
import ninja.Result;
import ninja.Results;

import org.pac4j.core.context.Cookie;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;

import java.io.*;
import java.util.Collection;
import java.util.Map;

/**
 *  found here: https://raw.githubusercontent.com/makotan/ninja-pac4j/master/src/main/java/com/makotan/ninja/authz/pac4j/NinjaWebContext.java
 */
public class NinjaWebContext implements WebContext {
    private static final Logger logger = LoggerFactory.getLogger(NinjaWebContext.class);

    Context context;
    Result result = Results.ok();

    public NinjaWebContext(Context context) {
        this.context = context;
    }

    @Override
    public String getRequestParameter(String name) {
        return context.getParameter(name);
    }

    @Override
    public Map<String, String[]> getRequestParameters() {
        return context.getParameters();
    }

    @Override
    public String getRequestHeader(String name) {
        return context.getHeader(name);
    }

    @Override
    public void setSessionAttribute(String name, Object value) {
        logger.debug("setSessionAttribute name: {} , value: {}" , name , value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(value);
        } catch (IOException e) {
            throw new RuntimeException("" , e);
        }
        String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
        context.getSession().put(name , data);
    }

    @Override
    public Object getSessionAttribute(String name) {
        String data = context.getSession().get(name);
        if (data == null) {
            return null;
        }
        byte[] bytes = DatatypeConverter.parseBase64Binary(data);
        logger.debug("getSession {} {}", name, data);
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return in.readObject();
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }

    @Override
    public String getRequestMethod() {
        return context.getMethod();
    }

    @Override
    public void writeResponseContent(String content) {
        result.render(content);
    }

    @Override
    public void setResponseStatus(int code) {
        result.status(code);
    }

    @Override
    public void setResponseHeader(String name, String value) {
        result.addHeader(name , value);
    }

    public Result getResult() {
        return result;
    }

	@Override
	public String getFullRequestURL() {
		return null;
	}

	@Override
	public String getScheme() {
		return null;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public int getServerPort() {
		return 0;
	}

	@Override
	public void addResponseCookie(Cookie arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRequestAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Cookie> getRequestCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSessionIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequestAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResponseContentType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}