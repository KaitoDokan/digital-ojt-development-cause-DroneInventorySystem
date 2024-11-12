package com.digitalojt.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bean定義クラス
 *
 * @author KaitoDokan
 * 
 */
@Configuration
public class BeanDefine {

	/**
	 * PasswordEncoderをDI
	 * TODO: パスワードは必ずハッシュ化してください。
	 * 
	 * @return
	 */
    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
