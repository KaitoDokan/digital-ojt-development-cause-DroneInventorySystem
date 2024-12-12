package com.digitalojt.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.service.CenterInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 在庫センター情報画面のコントローラークラス
 * 
 * @author KaitoDokan
 */
@Controller
@RequiredArgsConstructor
public class CenterInfoController extends AbstractController {

	/** センター情報 サービス */
	private final CenterInfoService centerInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/** ログ設定 */
	private static Logger logger = LoggerFactory.getLogger("CenterInfo");

	/**
	 * 初期表示
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param form 在庫センター情報の検索条件を含むフォームオブジェクト {@link CenterInfoForm}
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@GetMapping(UrlConsts.CENTER_INFO)
	public String centerListView(Model model, CenterInfoForm form, RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "GET", "centerListView");

		try {
			centerInfoService.setUpCenterListViewData(model, form);
		} catch (Exception e) {
			// 例外処理
			logError(logger, "GET", "centerListView-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.STOCK_LIST; // ここで予期せぬエラーが出たら在庫リストにリダイレクト
		}
		// 終了処理
		logEnd(logger, "GET", "centerListView");
		return "admin/centerInfo/index";
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param form 在庫センター情報の検索条件を含むフォームオブジェクト {@link CenterInfoForm}
	 * @param bindingResult バインディング結果
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@PostMapping(UrlConsts.CENTER_INFO_SEARCH)
	public String search(Model model, @Valid CenterInfoForm form, BindingResult bindingResult,
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
				centerInfoService.setUpCenterListViewData(model, new CenterInfoForm());
				model.addAttribute("inputtedValue", form); // formの再セット
				// バリデーションエラー処理
				logValidationError(logger, "POST", "search-ValidError", form + errorMsg);
			} else {
				centerInfoService.setUpCenterListViewData(model, form);
			}
		} catch (Exception e) {
			// 例外処理
			logError(logger, "POST", "search-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.CENTER_INFO;
		}
		// 終了処理
		logEnd(logger, "POST", "search");

		return "admin/centerInfo/index";
	}

	/**
	 * 登録フォームの表示
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param centerInfo 登録する在庫センター情報 {@link CenterInfo}
	 * @return 画面のパス
	 */
	@GetMapping(UrlConsts.CENTER_INFO_REGISTER)
	public String centerRegister(Model model, CenterInfo centerInfo) {
		logStart(logger, "GET", "centerRegister");
		logEnd(logger, "GET", "centerRegister");
		return "admin/centerInfo/register";
	}

	/**
	 * 登録内容確認
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param centerInfo 登録する在庫センター情報 {@link CenterInfo}
	 * @param bindingResult バインディング結果
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@PostMapping(UrlConsts.CENTER_INFO_REGISTER)
	@Transactional
	public String centerRegister(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "POST", "centerRegister");

		try {
			// 入力チェック（バリデーション）
			if (bindingResult.hasFieldErrors()) {
				List<String> errorFields = new ArrayList<>();
				for (FieldError fieldError : bindingResult.getFieldErrors()) {
					// エラーがあるフィールド名をリストに追加
					errorFields.add(fieldError.getField());
				}
				//バリデーションエラー処理
				logValidationError(logger, "POST", "centerRegister-ValidError", errorFields.toString());
				return "admin/centerInfo/register"; // バリデーションエラーがあった場合は登録フォームに戻る
			}
			// センター情報を登録
			centerInfoService.registerCenterInfo(centerInfo);
			// 登録成功メッセージをフラッシュスコープに設定
			redirectAttributes.addFlashAttribute("successMsg", centerInfo.getCenterName() + "が登録されました。");

		} catch (Exception e) {
			// 例外処理
			logError(logger, "POST", "centerRegister-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
		}
		// 終了処理
		logEnd(logger, "POST", "centerRegister");
		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}

	/**
	 * 更新フォームの表示
	 * 
	 * @param centerId センターID
	 * @param model 画面のモデルオブジェクト
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@GetMapping(UrlConsts.CENTER_INFO_UPDATE + "/{centerId}")
	public String centerUpdate(@PathVariable Integer centerId, Model model, RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "GET", "centerUpdate");

		try {
			// centerIdによって検索取得
			CenterInfo centerInfo = centerInfoService.getCenterInfoData(centerId);
			model.addAttribute("centerInfo", centerInfo);

		} catch (Exception e) {
			// 例外処理
			logError(logger, "GET", "centerUpdate-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.CENTER_INFO;
		}
		// 終了処理
		logEnd(logger, "GET", "centerUpdate");

		return "admin/centerInfo/update";
	}

	/**
	 * 更新内容確認
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param centerInfo 更新する在庫センター情報 {@link CenterInfo}
	 * @param bindingResult バインディング結果
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@PostMapping(UrlConsts.CENTER_INFO_UPDATE + "/{centerId}")
	@Transactional
	public String centerUpdate(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "POST", "centerUpdate-transaction");

		try {
			// 入力チェック（バリデーション）
			if (bindingResult.hasFieldErrors()) {

				List<String> errorFields = new ArrayList<>();
				for (FieldError fieldError : bindingResult.getFieldErrors()) {
					// エラーがあるフィールド名をリストに追加
					errorFields.add(fieldError.getField());
				}
				// バリデーションエラー処理
				logValidationError(logger, "POST", "centerUpdate-ValidError", errorFields.toString());
				return "admin/centerInfo/update"; // バリデーションエラーがあった場合は登録フォームに戻る
			}
			// センター情報を更新
			centerInfoService.updateCenterInfo(centerInfo);
			// 更新成功メッセージをフラッシュスコープに設定
			redirectAttributes.addFlashAttribute("successMsg", centerInfo.getCenterName() + "が更新されました。");

		} catch (Exception e) {
			// 例外処理
			logError(logger, "POST", "centerRegister-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
		}
		// 終了処理
		logEnd(logger, "POST", "centerUpdate-transaction");

		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}

	/**
	 * 削除確認画面の表示
	 * 
	 * @param centerId センターID
	 * @param model 画面のモデルオブジェクト
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@GetMapping(UrlConsts.CENTER_INFO_DELETE + "/{centerId}")
	public String centerDelete(@PathVariable Integer centerId, Model model, RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "GET", "centerDelete");

		try {
			// centerIdによって検索取得
			CenterInfo centerInfo = centerInfoService.getCenterInfoData(centerId);
			model.addAttribute("centerInfo", centerInfo);

		} catch (Exception e) {
			// 例外処理
			logError(logger, "GET", "centerDelete-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
			return "redirect:" + UrlConsts.CENTER_INFO;
		}
		// 終了処理
		logEnd(logger, "GET", "centerDelete");
		return "admin/centerInfo/delete";
	}

	/**
	 * 削除内容確認
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param centerInfo 論理削除する在庫センター情報 {@link CenterInfo}
	 * @param bindingResult バインディング結果
	 * @param redirectAttributes リダイレクト属性
	 * @return 画面のパス
	 */
	@PostMapping(UrlConsts.CENTER_INFO_DELETE + "/{centerId}")
	@Transactional
	public String centerDelete(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// 開始処理
		logStart(logger, "POST", "centerDelete-transaction");

		try {
			if (centerInfoService.isCenterWithStockExists(centerInfo)) {
				// エラーメッセージをプロパティファイルから取得
				String errorMsg = MessageManager.getMessage(messageSource,
						ErrorMessage.CANNOT_DELETE_CENTER_ERROR_MESSAGE);
				// バリデーションエラー処理
				logValidationError(logger, "POST", "centerDelete", errorMsg);
				// 削除エラーメッセージをフラッシュスコープに設定
				redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
			} else {
				// センター情報を削除する処理
				centerInfoService.deleteCenterInfo(centerInfo);
				// 更新成功メッセージをフラッシュスコープに設定
				redirectAttributes.addFlashAttribute("successMsg", centerInfo.getCenterName() + "が削除されました。");
			}
		} catch (Exception e) {
			// 例外処理
			logError(logger, "POST", "centerDelete-unexpected", e);
			setFlashErrorMsg(messageSource, redirectAttributes, ErrorMessage.UNEXPECTED_INPUT_ERROR_MESSAGE);
		}
		// 終了処理
		logEnd(logger, "POST", "centerDelete-transaction");

		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}
}