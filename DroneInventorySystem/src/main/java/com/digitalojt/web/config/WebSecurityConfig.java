package com.digitalojt.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.digitalojt.web.consts.UrlConsts;

import lombok.RequiredArgsConstructor;

/**
 * WebSecurityConfig
 *
 * @author KaitoDokan
 * 
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	/** パスワードエンコーダー */
	private final PasswordEncoder passwordEncoder;

	/** ユーザー情報取得Service */
	private final UserDetailsService userDetailsService;

	/**
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// LoginController呼び出し
		http
				.authorizeHttpRequests(authz -> authz.requestMatchers(UrlConsts.NO_AUTHENTICATION).permitAll()
						.anyRequest().authenticated())
				.formLogin(login -> login.loginPage(UrlConsts.LOGIN)//ログインページへの遷移
						.loginProcessingUrl(UrlConsts.AUTHENTICATE)//ログインフォームのPOST送信先URL
						.defaultSuccessUrl(UrlConsts.STOCK_LIST)//ログイン成功時のリダイレクト先
						.usernameParameter("adminId"))
				.logout(logout -> logout
						.logoutSuccessUrl(UrlConsts.LOGIN)
				);
		return http.build();
	}

	@Bean
	AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
