package com.digitalojt.web.form;

import com.digitalojt.web.validation.StockInfoFormValidator;

import lombok.Data;

/**
 * 在庫一覧画面のフォームクラス
 * 
 * @author KaitoDokan
 *
 */
@Data
@StockInfoFormValidator
public class StockInfoForm {
	

	/**
	 * カテゴリID
	 */
	private Integer categoryId;
	
	/**
	 * 在庫名
	 */
	private String stockName;

	/**
	 * 数量
	 */
	private Integer amount;

	/**
	 * 以上(0) 以下(1)のフラグ
	 */
	private Integer isAboveOrBelowFlag;


}
