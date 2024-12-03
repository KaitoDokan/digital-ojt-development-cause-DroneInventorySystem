package com.digitalojt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.dto.StockListViewData;
import com.digitalojt.web.form.StockInfoForm;
import com.digitalojt.web.service.StockInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 在庫一覧画面コントローラークラス
 * 
 * @author KaitoDokan
 *
 */
@Controller
@RequiredArgsConstructor
public class StockListController extends AbstractController {

	/*在庫一覧サービス*/
	private final StockInfoService stockInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/** ログ設定 */
	private static Logger logger = LoggerFactory.getLogger("StockList");

	/**
	 * 初期表示
	 * 
	 * @return String(path)
	 */
	@GetMapping(UrlConsts.STOCK_LIST)
	public String stockListView(Model model, StockInfoForm form) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "stockListView", "START"));

		try {
			// 共通データをサービスから取得
			StockListViewData data = stockInfoService.getStockListViewData(form);

			//取得に失敗していればエラー
			if (data == null) {
				throw new RuntimeException("データの取得に失敗しました");
			}

			//共通データをmodelにセット
			stockInfoService.setCommonModelAttributes(model, data);

		} catch (RuntimeException e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "GET", "stockListView", e));
		}

		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "stockListView", "END"));
		return "admin/stockList/index";
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(UrlConsts.STOCK_LIST_SEARCH)
	public String search(Model model, @Valid StockInfoForm form, BindingResult bindingResult) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "search", "START"));

		try {
			// 共通データをサービスから取得
			StockListViewData data = stockInfoService.getStockListViewData(form);

			//取得に失敗していればエラー
			if (data == null) {
				throw new RuntimeException("データの取得に失敗しました");
			}

			// Valid項目チェック
			if (bindingResult.hasErrors()) {
				// エラーメッセージをプロパティファイルから取得
				String errorMsg = MessageManager.getMessage(messageSource,
						bindingResult.getGlobalError().getDefaultMessage());
				model.addAttribute("errorMsg", errorMsg);
				throw new RuntimeException(errorMsg);
			} else {
				//フォームに入力された情報から検索、再セット
				data.setSelectStockInfoList(stockInfoService.getStockInfoData(form.getCategoryId()));
				data.setStockInfoList(stockInfoService.getStockInfoData(form));
			}
			//共通データをmodelにセット
			stockInfoService.setCommonModelAttributes(model, data);

		} catch (RuntimeException e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "search", e));
		}
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "search", "END"));
		return "admin/stockList/index";
	}
}
