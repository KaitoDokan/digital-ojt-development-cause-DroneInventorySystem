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

		// すべてのフィールドが空かをチェック
		if (areAllFieldsEmpty(form)) {
			setErrorMessage(context, ErrorMessage.ALL_FIELDS_EMPTY_ERROR_MESSAGE);
			return false;
		}

		// 分類IDのチェック
		if (form.getCategoryId() != null) {

			//半角数字チェック
			if (ParamCheckUtil.isNumeric(form.getCategoryId())) {
				setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
				return false;
			}
		}

		// 在庫名のチェック
		if (form.getStockName() != null) {

			//不正文字列チェック
			if (ParamCheckUtil.isParameterInvalid(form.getStockName())) {
				setErrorMessage(context, ErrorMessage.INVALID_INPUT_ERROR_MESSAGE);
				return false;
			}
		}

		// 数量のチェック
		if (form.getAmount() != null) {

			//半角数字チェック
			if (ParamCheckUtil.isNumeric(form.getAmount())) {
				setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
				return false;
			}
			// 数値の範囲をチェック
			if (ParamCheckUtil.isWithinRange(form.getAmount())) {
				setErrorMessage(context, ErrorMessage.UNEXPECTED_NUMBER_INPUT_ERROR_MESSAGE);
				return false;
			}
		}

		// 以上以下フラグのチェック
		if (form.getIsAboveOrBelowFlag() != null) {

			//半角数字チェック
			if (ParamCheckUtil.isNumeric(form.getIsAboveOrBelowFlag())) {
				setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
				return false;
			}
		}

		//半角数字チェック
		if (ParamCheckUtil.isNumeric(form.getIsAboveOrBelowFlag())) {
			setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * すべてのフィールドが空かどうかをチェック
	 */
	private boolean areAllFieldsEmpty(StockInfoForm form) {
		return (form.getCategoryId() == null) &&
				(form.getStockName() == null || form.getStockName().isEmpty()) &&
				(form.getAmount() == null);
	}

	/**
	 * エラーメッセージを設定する
	 */
	private void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(errorMessage)
				.addConstraintViolation();
	}
}
