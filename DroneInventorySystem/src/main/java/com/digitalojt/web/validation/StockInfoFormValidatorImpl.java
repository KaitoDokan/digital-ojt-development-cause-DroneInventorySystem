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
	//	public boolean isValid(StockInfoForm form, ConstraintValidatorContext context) {

	public boolean isValid(StockInfoForm form, ConstraintValidatorContext context) {

		//すべてのフィールドが空かをチェック
		if (areAllFieldsEmpty(form)) {
			setErrorMessage(context, ErrorMessage.ALL_FIELDS_EMPTY_ERROR_MESSAGE);
			return false;
		}

		//分類IDに不正入力があるかをチェック
		if (ParamCheckUtil.isNumeric(form.getCategoryId())) {
			setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
		}

		//在庫名に不正入力があるかをチェック
		if (ParamCheckUtil.isParameterInvalid(form.getStockName())) {
			setErrorMessage(context, ErrorMessage.INVALID_INPUT_ERROR_MESSAGE);
		}

		//数量に不正入力があるかをチェック
		if (ParamCheckUtil.isNumeric(form.getAmount())) {
			setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
		}

		//以上以下フラグに不正入力があるかをチェック
		if (ParamCheckUtil.isNumeric(form.getIsAboveOrBelowFlag())) {
			setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
		}

		return true;
	}

	private boolean areAllFieldsEmpty(StockInfoForm form) {
		return (form.getCategoryId() == null) &&
				(form.getStockName() == null || form.getStockName().isEmpty()) &&
				(form.getAmount() == null);
	}

	private void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(errorMessage)
				.addConstraintViolation();
	}
}