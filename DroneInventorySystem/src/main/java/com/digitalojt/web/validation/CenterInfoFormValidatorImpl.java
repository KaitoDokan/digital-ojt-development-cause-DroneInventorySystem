package com.digitalojt.web.validation;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.SearchParams;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.util.ParamCheckUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 在庫センター情報画面のバリデーションチェック 実装クラス
 * 
 * @author KaitoDokan
 */
public class CenterInfoFormValidatorImpl implements ConstraintValidator<CenterInfoFormValidator, CenterInfoForm> {

	/**
	 * バリデーションチェック
	 */
	@Override
	public boolean isValid(CenterInfoForm form, ConstraintValidatorContext context) {

		boolean isValid = true;

		// すべてのフィールドが空かをチェック
		if (areAllFieldsEmpty(form)) {
			setErrorMessage(context, ErrorMessage.ALL_FIELDS_EMPTY_ERROR_MESSAGE);
			isValid = false;
		}

		// センター名のチェック
		if (form.getCenterName() != null) {

			//不正文字列チェック
			if (ParamCheckUtil.isParameterInvalid(form.getCenterName())) {
				setErrorMessage(context, ErrorMessage.INVALID_INPUT_ERROR_MESSAGE);
				isValid = false;
			}

			// 文字数チェック
			if (form.getCenterName().length() > SearchParams.CENTER_MAX_LENGTH) {
				setErrorMessage(context, ErrorMessage.CENTER_NAME_LENGTH_ERROR_MESSAGE);
				isValid = false;
			}
		}

		// 都道府県のチェック
		if (form.getRegion() != null) {

			// 不正文字列チェック
			if (ParamCheckUtil.isParameterInvalid(form.getRegion())) {
				setErrorMessage(context, ErrorMessage.INVALID_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
		}

		// 数量(From)のチェック
		if (form.getStorageCapacityFrom() != null) {

			//半角数字チェック
			if (ParamCheckUtil.isNumeric(form.getStorageCapacityFrom())) {
				setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
			// 数値の範囲をチェック
			if (ParamCheckUtil.isWithinRange(form.getStorageCapacityFrom())) {
				setErrorMessage(context, ErrorMessage.UNEXPECTED_NUMBER_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
		}

		// 数量(To)のチェック
		if (form.getStorageCapacityTo() != null) {
			//半角数字チェック
			if (ParamCheckUtil.isNumeric(form.getStorageCapacityTo())) {
				setErrorMessage(context, ErrorMessage.NON_NUMERIC_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
			// 数値の範囲をチェック
			if (ParamCheckUtil.isWithinRange(form.getStorageCapacityTo())) {
				setErrorMessage(context, ErrorMessage.UNEXPECTED_NUMBER_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
		}

		//FromTo論理チェック
		if (form.getStorageCapacityFrom() != null & form.getStorageCapacityTo() != null) {

			if (ParamCheckUtil.compareFromTo(form.getStorageCapacityFrom(), form.getStorageCapacityTo())) {
				setErrorMessage(context, ErrorMessage.FROM_TO_INPUT_ERROR_MESSAGE);
				isValid = false;
			}
		}

		// その他のバリデーションに問題なければtrueを返す
		return isValid;
	}

	private boolean areAllFieldsEmpty(CenterInfoForm form) {
		return (form.getCenterName().isEmpty()) &&
				(form.getRegion().isEmpty()) &&
				((form.getStorageCapacityFrom() == null) | (form.getStorageCapacityTo() == null));
	}

	private void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(errorMessage)
				.addConstraintViolation();
	}

}
