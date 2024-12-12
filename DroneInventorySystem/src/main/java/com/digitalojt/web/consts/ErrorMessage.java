package com.digitalojt.web.consts;

/**
 * エラーメッセージ定数クラス
 * 
 * @author KaitoDokan
 *
 */
public class ErrorMessage {

	// ログイン情報の入力に誤りがあった場合に、出力するエラーメッセージのID
	public static final String LOGIN_WRONG_INPUT = "login.wrongInput";

	// すべての項目が空の場合のエラーメッセージ
	public static final String ALL_FIELDS_EMPTY_ERROR_MESSAGE = "allField.empty";

	//項目が空の場合のエラーメッセージ
	public static final String FIELDS_EMPTY_ERROR_MESSAGE = "Field.empty";

	// 空文字検索に関するエラーメッセージ
	public static final String UNEXPECTED_INPUT_ERROR_MESSAGE = "unexpected.input";

	// 不正な文字列を入力した際に関するエラーメッセージ
	public static final String INVALID_INPUT_ERROR_MESSAGE = "invalid.input";

	//不正なフォーマットを入力した際に関するエラーメッセージ
	public static final String INVALID_FORMAT_ERROR_MESSAGE = "invaled.format";

	// 文字超過に関するエラーメッセージ センター情報
	public static final String CENTER_NAME_LENGTH_ERROR_MESSAGE = "centerName.length.input";

	// 文字超過に関するエラーメッセージ 分類情報
	public static final String CATEGORY_NAME_LENGTH_ERROR_MESSAGE = "categoryName.length.input";

	// 半角数字以外が入力されたときに関するエラーメッセージ
	public static final String NON_NUMERIC_INPUT_ERROR_MESSAGE = "non.numeric.input";

	//Integer型として受け付けない数値が入力されたときに関するエラーメッセージ
	public static final String UNEXPECTED_NUMBER_INPUT_ERROR_MESSAGE = "unexpected.number.input";

	//FromToの理論に異常があるときに関するエラーメッセージ
	public static final String FROM_TO_INPUT_ERROR_MESSAGE = "from.to.input";
	
	// 在庫が存在する在庫センターを削除しようとしたときに関するエラーメッセージ
	public static final String CANNOT_DELETE_CENTER_ERROR_MESSAGE = "cannot.delete.center";

}
