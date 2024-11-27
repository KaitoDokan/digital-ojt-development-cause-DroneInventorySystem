package com.digitalojt.web.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.StockInfo;
import com.digitalojt.web.service.StockInfoService;

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

	/**
	 * 初期表示
	 * 
	 * @return String(path)
	 */
	@GetMapping(UrlConsts.STOCK_LIST)
	public String stockListView(Model model) {

		//在庫一覧画面に表示するデータを全件取得
		List<StockInfo> stockInfoList = stockInfoService.getStockInfoData();

		//modelに全件取得したリストをセット
		model.addAttribute("stockInfoList", stockInfoList);

		return "admin/stockList/index";
	}
}
