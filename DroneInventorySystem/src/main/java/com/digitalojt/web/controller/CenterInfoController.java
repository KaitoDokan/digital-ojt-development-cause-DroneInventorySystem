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

import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.dto.CenterListViewData;
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
 *
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
				throw new Exception("データの取得に失敗しました");
			}
			//共通データをmodelにセット
			centerInfoService.setCommonModelAttributes(model, data);

		} catch (Exception e) {
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
	 * @param bindingResult
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
				//dataのリセット
				data = centerInfoService.getCenterListViewData(new CenterInfoForm());
				//L2出力
				logger.error(String.format(LogMessage.ERROR_LOG, "POST", "search-ValidError",
						bindingResult.getFieldErrors()));
				//大きすぎる数字を送ったときにnullでる。
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

	/**
	 * 登録フォームの表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO_REGISTER)
	public String centerRegister(Model model, CenterInfo centerInfo) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerRegister", "START"));

		//		try {
		//			// 共通データをサービスから取得
		//			CenterListViewData data = centerInfoService.getCenterListViewData(form);
		//			//取得に失敗していればエラー
		//			if (data == null) {
		//				throw new RuntimeException("データの取得に失敗しました");
		//			}
		//			//共通データをmodelにセット
		//			centerInfoService.setCommonModelAttributes(model, data);
		//
		//		} catch (RuntimeException e) {
		//			//L2出力
		//			logger.error(String.format(LogMessage.ERROR_LOG, "GET", "centerRegister", e));
		//		}
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerRegister", "END"));

		return "admin/centerInfo/register";
	}

	/**
	 * 登録内容確認
	 * 
	 * @param model
	 * @param centerInfo
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_REGISTER)
	@Transactional
	public String centerRegister(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerRegister-transaction", "START"));

		try {
			//入力チェック（バリデーション）
			if (bindingResult.hasFieldErrors()) {

				List<String> errorFields = new ArrayList<>();
				for (FieldError fieldError : bindingResult.getFieldErrors()) {
					// エラーがあるフィールド名をリストに追加
					errorFields.add(fieldError.getField());
				}
				// L2出力
				logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerRegister-ValidError", errorFields));
				return "admin/centerInfo/register"; // バリデーションエラーがあった場合は登録フォームに戻る
			}

			// センター情報を登録
			centerInfoService.registerCenterInfo(centerInfo);
			// 登録成功メッセージをフラッシュスコープに設定
			redirectAttributes.addFlashAttribute("successMsg", "センター情報が登録されました。");

		} catch (Exception e) {
			// L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerRegister-unexpected", e));
			return "admin/centerInfo/register"; // 例外が発生した場合は登録フォームに戻る
		}
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerRegister-transaction", "END"));

		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}

	/**
	 * 更新フォームの表示
	 * 
	 * @param model
	 * @param centerInfo
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO_UPDATE + "/{centerId}")
	public String centerUpdate(@PathVariable Integer centerId, Model model) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerUpdate", "START"));

		try {
			//centerIdによって検索取得
			CenterInfo centerInfo = centerInfoService.getCenterInfoData(centerId);
			//取得に失敗していればエラー
			if (centerInfo == null) {
				throw new Exception("データの取得に失敗しました");
			}
			model.addAttribute("centerInfo", centerInfo);

		} catch (Exception e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "GET", "centerUpdate", e));
		}
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerUpdate", "END"));

		return "admin/centerInfo/update";
	}

	/**
	 * 更新内容確認
	 * 
	 * @param model
	 * @param centerInfo
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_UPDATE + "/{centerId}")
	@Transactional
	public String centerUpdate(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerUpdate-transaction", "START"));

		try {
			//入力チェック（バリデーション）
			if (bindingResult.hasFieldErrors()) {

				List<String> errorFields = new ArrayList<>();
				for (FieldError fieldError : bindingResult.getFieldErrors()) {
					// エラーがあるフィールド名をリストに追加
					errorFields.add(fieldError.getField());
				}
				// L2出力
				logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerUpdate-ValidError", errorFields));
				return "admin/centerInfo/update"; // バリデーションエラーがあった場合は登録フォームに戻る
			}

			// センター情報を更新
			centerInfoService.updateCenterInfo(centerInfo);
			// 更新成功メッセージをフラッシュスコープに設定
			redirectAttributes.addFlashAttribute("successMsg", "センター情報が更新されました。");

		} catch (Exception e) {
			// L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerRegister-unexpected", e));
			return "admin/centerInfo/update"; // 例外が発生した場合は登録フォームに戻る
		}
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerUpdate-transaction", "END"));

		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}

	/**
	 * 削除確認画面の表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO_DELETE + "/{centerId}")
	public String centerDelete(@PathVariable Integer centerId, Model model) {

		//L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerDelete", "START"));

		try {
			//centerIdによって検索取得
			CenterInfo centerInfo = centerInfoService.getCenterInfoData(centerId);
			//取得に失敗していればエラー
			if (centerInfo == null) {
				throw new Exception("データの取得に失敗しました");
			}
			model.addAttribute("centerInfo", centerInfo);

		} catch (Exception e) {
			//L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "GET", "centerDelete", e));
		}
		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerDelete", "END"));

		//L3出力
		logger.info(String.format(LogMessage.APP_LOG, "GET", "centerDelete", "END"));

		return "admin/centerInfo/delete";
	}

	/**
	 * 削除内容確認
	 * 
	 * @param model
	 * @param centerInfo
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_DELETE + "/{centerId}")
	@Transactional
	public String centerDelete(Model model, @Valid CenterInfo centerInfo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// L1出力
		logger.info(String.format(LogMessage.ACCESS_LOG));
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerDelete-transaction", "START"));

		try {
			if (centerInfoService.isCenterWithStockExists(centerInfo)) {
				// L2出力
				logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerDelete-", "在庫が存在するため削除できません的な"));
				// 更新成功メッセージをフラッシュスコープに設定
				redirectAttributes.addFlashAttribute("errorMsg", "※在庫が存在するため削除できません");
				return "redirect:" + UrlConsts.CENTER_INFO; //在庫センターに戻る
			}

			// センター情報を削除する処理
			centerInfoService.deleteCenterInfo(centerInfo);
			// 更新成功メッセージをフラッシュスコープに設定
			redirectAttributes.addFlashAttribute("successMsg", "センター情報が削除されました。");

		} catch (Exception e) {
			// L2出力
			logger.error(String.format(LogMessage.ERROR_LOG, "POST", "centerDelete-unexpected", e));
			return "admin/centerInfo/update"; // 例外が発生した場合は登録フォームに戻る
		}
		// L3出力
		logger.info(String.format(LogMessage.APP_LOG, "POST", "centerDelete-transaction", "END"));

		// 在庫センター情報画面に戻る
		return "redirect:" + UrlConsts.CENTER_INFO; // 在庫センター情報画面にリダイレクト
	}
}
