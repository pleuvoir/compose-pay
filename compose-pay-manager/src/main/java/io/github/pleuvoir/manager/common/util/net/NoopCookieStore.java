package io.github.pleuvoir.manager.common.util.net;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

/**
 * 空实现cookie存储器，不存储cookie
 */
public class NoopCookieStore implements CookieStore, Serializable{
	private static final long serialVersionUID = -4336010788666994L;

	private Vector<Cookie> cookies = new Vector<>();
	
	@Override
	public void addCookie(Cookie cookie) {
	}

	@Override
	public List<Cookie> getCookies() {
		return cookies;
	}

	@Override
	public boolean clearExpired(Date date) {
		return false;
	}

	@Override
	public void clear() {
	}
}