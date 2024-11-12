package com.digitalojt.web.consts;

/**
 * URL定数クラス
 *
 * @author KaitoDokan
 * 
 */
public class UrlConsts {

	// ログイン
	public static final String LOGIN = "/login";

	// 認証
	public static final String AUTHENTICATE = "/authenticate";

	// 在庫一覧画面
	public static final String STOCK_LIST = "/admin/stockList";

	// 在庫一覧画面 検索
	public static final String STOCK_LIST_SEARCH = "/admin/stockList/search";

	// 在庫センター情報画面
	public static final String  CENTER_INFO = "/admin/centerInfo";

	// 在庫センター情報画面 検索
	public static final String CENTER_INFO_SEARCH = "/admin/centerInfo/search";

	// 分類情報画面
	public static final String  CATEGORY_INFO = "/admin/categoryInfo";
	
	// 分類情報画面 検索
	public static final String CATEGORY_INFO_SEARCH = "/admin/categoryInfo/search";
	
	// 認証不要画面
	public static final String[] NO_AUTHENTICATION = {LOGIN, AUTHENTICATE};
}
