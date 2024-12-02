package com.digitalojt.web.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * ログ出力用の診断コンテキストを設定するFilterクラス.
 * 
 * @author KaitoDokan
 */
public class LogSettingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 診断コンテキストを設定します.
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			// IPアドレスを設定
			MDC.put("ipAddress", servletRequest.getRemoteAddr());

			// ユーザーIDを設定
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			MDC.put("userId", userId);
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
		    // MDCからキーを削除
		    MDC.remove("ipAddress");
		    MDC.remove("userId");
		}
	}

	@Override
	public void destroy() {

	}
}
