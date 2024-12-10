package com.digitalojt.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.digitalojt.web.dto.StockListViewData;
import com.digitalojt.web.entity.CategoryInfo;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.entity.StockInfo;
import com.digitalojt.web.form.StockInfoForm;
import com.digitalojt.web.repository.StockInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * 在庫一覧画面のサービスクラス
 *
 * @author KaitoDokan
 * 
 */
@Service
@RequiredArgsConstructor
public class StockInfoService {

	//在庫一覧テーブルリポジトリ
	private final StockInfoRepository repository;

	//分類情報サービス
	private final CategoryInfoService categoryInfoService;

	/**
	 * 在庫一覧情報をformによる検索で取得
	 * 
	 * @param form
	 * @return
	 */
	public List<StockInfo> getStockInfoData(StockInfoForm form) {
		if (form.getCategoryId() == null
				&& form.getStockName() == null
				&& form.getAmount() == null
				&& form.getIsAboveOrBelowFlag() == null) {
			return repository.findAll(); // 全件取得
		}
		return repository.findByCategoryIdAndStockNameAndAmount(
				form.getCategoryId(),
				form.getStockName(),
				form.getAmount(),
				form.getIsAboveOrBelowFlag());
	}

	/**
	 * 在庫一覧情報をcategoryIdによる検索で取得
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<StockInfo> getStockInfoData(Integer categoryId) {
		if (categoryId == null) {
			return repository.findAll(); // categoryIdがnullなら全件取得
		}
		return repository.findByCategoryInfo_CategoryId(categoryId); // 指定されたcategoryIdで絞り込んで取得
	}
	
	/**
	 * 在庫一覧情報をcenterInfoによる検索で取得
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<StockInfo> getStockInfoData(CenterInfo centerInfo) {
		return repository.findByCenterInfo(centerInfo); // 指定されたcategoryIdで絞り込んで取得
	}

	/**
	 * 在庫一覧画面の共通データを取得
	 * 
	 * @param form フォームオブジェクト
	 * @return 在庫画面の共通データを格納するDTO
	 */
	public StockListViewData getStockListViewData(StockInfoForm form) {
		StockListViewData data = new StockListViewData();

		// 在庫情報の取得
		List<StockInfo> stockInfoList = this.getStockInfoData(form);
		List<StockInfo> selectStockInfoList = this.getStockInfoData(form);

		// 分類情報の取得
		List<CategoryInfo> categories = categoryInfoService.getCategoryInfoData();

		// DTOにセット
		data.setStockInfoList(stockInfoList);
		data.setSelectStockInfoList(selectStockInfoList);
		data.setCategories(categories);
		data.setForm(form);

		return data;
	}

	/**
	 * 共通のmodelをセットする
	 * 
	 * @param model   Modelオブジェクト
	 * @param data    在庫一覧画面の共通データ
	 */
	public void setCommonModelAttributes(Model model, StockListViewData data) {
		model.addAttribute("inputtedValue", data.getForm()); // 検索結果を保持するためのセット
		model.addAttribute("stockInfoList", data.getStockInfoList()); // 画面表示用に検索結果をセット
		model.addAttribute("categories", data.getCategories()); // 分類プルダウン情報をセット
		model.addAttribute("selectStockInfoList", data.getSelectStockInfoList()); // 在庫プルダウン情報をセット
	}
}
