package com.digitalojt.web.consts;

/**
 * ログメッセージ定数クラス
 * 
 * @author KaitoDokan
 *
 */
public class LogMessage {
	//アクセスログ
	//ACCESSだけ表示される
	public static final String ACCESS_LOG = "ACCESS";

	//エラーログ
	//(GETかPOST)-(処理対象の関数名)-(エラー内容)
	public static final String ERROR_LOG = "ERROR: %s - %s - %s";

	//アプリケーションログ
	//(GETかPOST)-(処理対象の関数名)-(開始か終了)
	public static final String APP_LOG = "APP: %s - %s - %s";

}