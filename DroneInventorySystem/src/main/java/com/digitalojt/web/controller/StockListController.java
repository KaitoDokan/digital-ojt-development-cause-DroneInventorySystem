package com.digitalojt.web.controller;

import java.util.List;

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
import com.digitalojt.web.entity.CategoryInfo;
import com.digitalojt.web.entity.StockInfo;
import com.digitalojt.web.form.StockInfoForm;
import com.digitalojt.web.service.CategoryInfoService;
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
	private final CategoryInfoService categoryInfoService;

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
			
			//プルダウン用の在庫を全件取得
			List<StockInfo> selectStockInfoList = stockInfoService.getStockInfoData();
			//在庫一覧画面に表示するデータを全件取得
			List<StockInfo> stockInfoList = stockInfoService.getStockInfoData();
			//分類情報を取得して、nameだけを直接リストに追加
			List<CategoryInfo> categories = categoryInfoService.getCategoryInfoData();

			//取得に失敗していればエラー
			if (stockInfoList == null || categories == null || form == null) {
				throw new RuntimeException("データの取得に失敗しました");
			}

			model.addAttribute("inputtedValue", form); //検索結果を保持するためのセット
			model.addAttribute("stockInfoList", stockInfoList); //全件取得したリストをセット
			model.addAttribute("categories", categories); //分類プルダウン情報をセット
			model.addAttribute("selectStockInfoList", selectStockInfoList); // 在庫プルダウン情報をセット

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

		//分類情報を取得して、nameだけを直接リストに追加
		List<CategoryInfo> categories = categoryInfoService.getCategoryInfoData();
		//プルダウン用の在庫を全件取得
		List<StockInfo> selectStockInfoList = stockInfoService.getStockInfoData();

		//検索結果を表示するためにあらかじめ生成
		List<StockInfo> stockInfoList;

		// Valid項目チェック
		if (bindingResult.hasErrors()) {
			// エラーメッセージをプロパティファイルから取得
			String errorMsg = MessageManager.getMessage(messageSource,
					bindingResult.getGlobalError().getDefaultMessage());
			model.addAttribute("errorMsg", errorMsg);
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "search", errorMsg));
			// 分類情報画面に表示するデータを全件取得 検索失敗
			stockInfoList = stockInfoService.getStockInfoData();
		} else {
			// 分類情報画面に表示するデータを取得 検索成功
			stockInfoList = stockInfoService.getStockInfoData(
					form.getCategoryId(),
					form.getStockName(),
					form.getAmount(),
					form.getIsAboveOrBelowFlag());
		}

		model.addAttribute("inputtedValue", form); //検索結果を保持するためのセット
		model.addAttribute("stockInfoList", stockInfoList); // 画面表示用に検索結果をセット
		model.addAttribute("categories", categories); // 分類プルダウン情報をセット
		model.addAttribute("selectStockInfoList", selectStockInfoList); // 在庫プルダウン情報をセット

		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "search", "END"));
		return "admin/stockList/index";
	}
}
