package com.news.util;

import javax.servlet.http.HttpSession;

public class NewsUtil {
	public static boolean isLogin(HttpSession session) {
		if(session.getAttribute("username") != null) {
			return true;
		} else {
			return false;
		}
	}
}
