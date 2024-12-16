package com.digitalojt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.form.StockInfoForm;
import com.digitalojt.web.service.StockInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 在庫一覧画面コントローラークラス
 * 
 * @author KaitoDokan
 */
@Controller
@RequiredArgsConstructor
public class StockListController extends AbstractController {

	/* 在庫一覧サービス */
	private final StockInfoService stockInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/** ログ設定 */
	private static Logger logger = LoggerFactory.getLogger("StockList");

	/**
	 * 初期表示
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param form 在庫一覧の検索条件を含むフォームオブジェクト {@link StockInfoForm}
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@GetMapping(UrlConsts.STOCK_LIST)
	public String stockListView(Model model, StockInfoForm form, RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "GET", "stockListView");

		try {
			stockInfoService.setUpStockListViewData(model, form);

		} catch (Exception e) {
			// 例外処理
			logError(logger, "GET", "stockListView-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.ERROR;
		}
		// 終了処理
		logEnd(logger, "GET", "stockListView");
		return "admin/stockList/index";
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param form 在庫一覧の検索条件を含むフォームオブジェクト {@link StockInfoForm}
	 * @param bindingResult バインディング結果
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@PostMapping(UrlConsts.STOCK_LIST_SEARCH)
	public String search(Model model, @Valid StockInfoForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "POST", "search");
		try {
			// Valid項目チェック
			if (bindingResult.hasErrors()) {
				// エラーメッセージをプロパティファイルから取得
				String errorMsg = MessageManager.getMessage(messageSource,
						bindingResult.getGlobalError().getDefaultMessage());
				model.addAttribute("errorMsg", errorMsg);
				stockInfoService.setUpStockListViewData(model, new StockInfoForm());
				model.addAttribute("inputtedValue", form); // フォームの再セット
				// 例外処理
				logValidationError(logger, "POST", "search-ValidError", form + errorMsg);
			} else {
				stockInfoService.setUpStockListViewData(model, form);
			}
		} catch (Exception e) {
			// 例外処理
			logError(logger, "POST", "search-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.ERROR;
		}
		// 終了処理
		logEnd(logger, "POST", "search");
		return "admin/stockList/index";
	}
}