package com.digitalojt.web.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * 在庫情報Entity
 * 
 * @author KaitoDokan
 *
 */
@Data
@Entity

public class StockInfo {

	/**
	 * 在庫ID
	 */
	@Id
	private int stockId;

	/**
	 * 分類ID
	 * 関連付け
	 */
	@ManyToOne
	@JoinColumn(name = "categoryId", insertable = false, updatable = false)
	private CategoryInfo categoryInfo;

	/**
	 * 名前
	 */
	private String stockName;

	/**
	 * 在庫センターID
	 * 関連付け
	 */
	@ManyToOne
	@JoinColumn(name = "centerId", insertable = false, updatable = false)
	private CenterInfo centerInfo;

	/**
	 * 説明
	 */
	private String description;

	/**
	 * 数量
	 */
	private int amount;

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
