package com.digitalojt.web.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * センター情報Entity
 * 
 * @author KaitoDokan
 *
 */
@Data
@Entity
public class CenterInfo {

	/**
	 * センターID
	 */
	@Id
	private int centerId;

	/**
	 * センター名
	 */
	private String centerName;

	/**
	 * 住所
	 */
	private String address;

	/**
	 * 電話番号
	 */
	private String phoneNumber;

	/**
	 * 管理者名
	 */
	private String managerName;

	/**
	 * 未使用フラグ
	 */
	private int operationalStatus;

	/**
	 * 最大容量
	 */
	private Integer maxStorageCapacity;

	/**
	 * 現在容量
	 */
	private Integer currentStorageCapacity;

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
