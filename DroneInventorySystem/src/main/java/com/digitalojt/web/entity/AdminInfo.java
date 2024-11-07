package com.digitalojt.web.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理者情報Entity
 * 
 * @author KaitoDokan
 *
 */
@Data
@Getter
@Setter
@Entity
public class AdminInfo {

	/**
	 * 管理者ID
	 */
	@Id
	private String adminId;

	/**
	 * 管理者名
	 */
	private String adminName;

	/**
	 * メールアドレス
	 */
	private String mail;

	/**
	 * 電話番号
	 */
	private String phoneNumber;

	/**
	 * パスワード
	 */
	private String password;

	/**
	 * 論理削除フラグ
	 */
	private String deleteFlag;

	/**
	 * 登録日
	 */
	private Timestamp createDate;

	/**
	 * 更新日
	 */
	private Timestamp updateDate;
}
