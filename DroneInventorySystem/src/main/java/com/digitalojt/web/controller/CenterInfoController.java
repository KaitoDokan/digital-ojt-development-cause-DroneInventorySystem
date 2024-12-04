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
import com.digitalojt.web.dto.CenterListViewData;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.service.CenterInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 在庫センター情報画面のコントローラークラス
 * 
 * @author KaitoDokan
 *
 */
@Controller
@RequiredArgsConstructor
public class CenterInfoController {

	/** センター情報 サービス */
	private final CenterInfoService centerInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/** ログ設定 */
	private static Logger logger = LoggerFactory.getLogger("CenterInfo");

	/**
	 * 初期表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO)
	public String centerListView(Model model, CenterInfoForm form) {
		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerListView", "START"));

		try {
			// 共通データをサービスから取得
			CenterListViewData data = centerInfoService.getCenterListViewData(form);
			//取得に失敗していればエラー
			if (data == null) {
				throw new RuntimeException("データの取得に失敗しました");
			}
			//共通データをmodelにセット
			centerInfoService.setCommonModelAttributes(model, data);

		} catch (RuntimeException e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "GET", "centerListView", e));
		}
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerListView", "END"));
		return "admin/centerInfo/index";
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_SEARCH)
	public String search(Model model, @Valid CenterInfoForm form, BindingResult bindingResult) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "search", "START"));

		CenterListViewData data;
		try {
			// Valid項目チェック
			if (bindingResult.hasErrors()) {
				// エラーメッセージをプロパティファイルから取得
				String errorMsg = MessageManager.getMessage(messageSource,
						bindingResult.getGlobalError().getDefaultMessage());
				model.addAttribute("errorMsg", errorMsg);
				data = centerInfoService.getCenterListViewData(new CenterInfoForm());
				//L2出力
				logger.error(String.format(LogMessage.ERROR_LOG, "POST", "search-ValidError", form+errorMsg));
			} else {
				//フォームに入力された情報から検索、再セット
				data = centerInfoService.getCenterListViewData(form);
			}
			//共通データをmodelにセット
			centerInfoService.setCommonModelAttributes(model, data);

		} catch (Exception e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "search", e));
		}

		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "search", "END"));

		return "admin/centerInfo/index";
	}
}
