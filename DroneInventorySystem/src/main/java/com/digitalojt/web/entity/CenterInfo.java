package com.digitalojt.web.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int centerId;

	/**
	 * センター名
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	@Size(min = 1, max = 20, message = "{centerName.length.input}")
	private String centerName;

	/**
	 * 郵便番号
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "{invaled.postCode.format}")
	private String postCode;

	/**
	 * 住所
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	@Size(min = 1, max = 20, message = "{address.length.input}")
	private String address;

	/**
	 * 電話番号
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	@Pattern(regexp = "^0\\d{1,4}-\\d{1,4}-\\d{4}$", message = "{invaled.phoneNumber.format}")
	private String phoneNumber;

	/**
	 * 管理者名
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	private String managerName;

	/**
	 * 未使用フラグ
	 */
	private int operationalStatus;

	/**
	 * 最大容量
	 */
	@Max(value = 99999999, message = "{unexpected.number.input}")
	@Min(value = 0, message = "{unexpected.number.input}")
	private Integer maxStorageCapacity;

	/**
	 * 現在容量
	 */
	@Max(value = 99999999, message = "{unexpected.number.input}")
	@Min(value = 0, message = "{unexpected.number.input}")
	private Integer currentStorageCapacity;

	/**
	 * 備考
	 */
	@Pattern(regexp = "^[^{}=;&$'*? \\(\\)\\\\]*$", message = "{invalid.input}")
	private String notes;

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
