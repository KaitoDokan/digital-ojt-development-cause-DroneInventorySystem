package com.digitalojt.web.validation;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.form.StockInfoForm;
import com.digitalojt.web.util.ParamCheckUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 分類一覧画面のバリデーションチェック 実装クラス
 * 
 * @author KaitoDokan
 */
public class StockInfoFormValidatorImpl implements ConstraintValidator<StockInfoFormValidator, StockInfoForm> {

	/**
	 * バリデーションチェック
	 */
	@Override
	public boolean isValid(StockInfoForm form, ConstraintValidatorContext context) {

	    boolean allFieldsEmpty = (form.getCategoryId() == null) &&
	            (form.getStockName() == null || form.getStockName().isEmpty())
	            &&(form.getAmount() == null)
	            ;
	    
		// すべてのフィールドが空かをチェック
		if (allFieldsEmpty) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorMessage.ALL_FIELDS_EMPTY_ERROR_MESSAGE)
					.addConstraintViolation();
			return false;
		}

		// カテゴリIDのチェック
		// 半角数字かどうかチェック
		if (ParamCheckUtil.isNumeric(form.getCategoryId())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE)
					.addConstraintViolation();
			return false;
		}

		// 在庫名のチェック
		// 不正文字列チェック
		if (ParamCheckUtil.isParameterInvalid(form.getStockName())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE)
					.addConstraintViolation();
			return false;
		}
		
		// 数量のチェック
		// 半角数字かどうかチェック
		if (ParamCheckUtil.isNumeric(form.getAmount())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE)
					.addConstraintViolation();
			return false;
		}
		
		// 以上以下フラグのチェック
		// 半角数字かどうかチェック
		if (ParamCheckUtil.isNumeric(form.getIsAboveOrBelowFlag())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE)
					.addConstraintViolation();
			return false;
		}

		// その他のバリデーションに問題なければtrueを返す
		return true;
	}
}
