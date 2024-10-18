package com.digitalojt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String index() {

		return "admin/categoryInfo/index";
	}
}