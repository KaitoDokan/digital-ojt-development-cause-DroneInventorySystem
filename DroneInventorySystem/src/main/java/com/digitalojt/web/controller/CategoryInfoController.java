package com.digitalojt.web.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.CategoryInfo;
import com.digitalojt.web.form.CategoryInfoForm;
import com.digitalojt.web.service.CategoryInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 分類情報管理画面コントローラークラス
 * 
 * @author KaitoDokan
 *
 */
@Controller
@RequiredArgsConstructor
public class CategoryInfoController extends AbstractController {

	/*分類情報サービス*/
	private final CategoryInfoService categoryInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/**
	 * 初期表示
	 * 
	 * @return String(path)
	 */
	@GetMapping(UrlConsts.CATEGORY_INFO)
	public String categoryInfoView(Model model) {

		// 分類情報画面に表示するデータを全件取得
		List<CategoryInfo> categoryInfoList = categoryInfoService.getCategoryInfoData();

		//modelにカテゴリ情報定数クラスをセット
		model.addAttribute("categoryInfoList", categoryInfoList);

		return "admin/categoryInfo/index";
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(UrlConsts.CATEGORY_INFO_SEARCH)
	public String search(Model model, @Valid CategoryInfoForm form, BindingResult bindingResult) {

		// Valid項目チェック
		if (bindingResult.hasErrors()) {

			// エラーメッセージをプロパティファイルから取得
			String errorMsg = MessageManager.getMessage(messageSource,
					bindingResult.getGlobalError().getDefaultMessage());
			model.addAttribute("errorMsg", errorMsg);
			return "admin/categoryInfo/index";
		}

		// 在庫センター情報画面に表示するデータを取得
		List<CategoryInfo> categoryInfoList = categoryInfoService.getCategoryInfoData(form.getCategoryName());

		// 画面表示用に商品情報リストをセット
		model.addAttribute("categoryInfoList", categoryInfoList);

		return "admin/categoryInfo/index";
	}
}