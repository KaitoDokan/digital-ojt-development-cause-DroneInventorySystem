package com.digitalojt.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.digitalojt.web.consts.CategoryInfoConsts;
import com.digitalojt.web.consts.UrlConsts;

/**
 * 分類情報管理画面コントローラークラス
 * 
 * @author KaitoDokan
 *
 */
@Controller
public class CategoryInfoController extends AbstractController {

	/**
	 * 初期表示
	 * 
	 * @return String(path)
	 */
	@GetMapping(UrlConsts.CATEGORY_INFO)
	public String categoryInfoView(Model model) {
		//基本パターン
		//modelにカテゴリ情報定数クラスをセット
		//model.addAttribute("categories", CategoryInfoConsts.class);

		//Enum使用OKパターン
		//カテゴリ情報Enumをリストに変換
		List<CategoryInfoConsts> categories = Arrays.asList(CategoryInfoConsts.values());
		//カテゴリ情報をセット
		model.addAttribute("categories", categories);
		return "admin/categoryInfo/index";
	}
}