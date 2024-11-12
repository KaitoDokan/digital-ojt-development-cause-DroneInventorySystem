package com.digitalojt.web.form;

import com.digitalojt.web.validation.CategoryInfoFormValidator;

import lombok.Data;

/**
 * 分類情報画面のフォームクラス
 * 
 * @author KaitoDokan
 *
 */
@Data
@CategoryInfoFormValidator
public class CategoryInfoForm {

	/**
	 * 分類名
	 */
	private String categoryName;

}
