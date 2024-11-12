package com.digitalojt.web.form;

import lombok.Data;

/**
 * ログイン画面のフォームクラス
 * 
 * @author KaitoDokan
 *
 */
@Data
public class LoginForm {

	/**
	 * 管理者ID
	 */
	private String adminId;

	/**
	 * パスワード
	 */
	private String password;
}
