package com.digitalojt.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
	 * @param form 検索条件を含む {@link StockInfoForm}
	 * @return 検索結果の在庫一覧情報リスト
	 */
	public List<StockInfo> getStockInfoData(StockInfoForm form) {
		List<StockInfo> stockInfoList;
		try {
			//フォームの内容によって在庫情報を検索
			if (form.getCategoryId() == null
					&& form.getStockName() == null
					&& form.getAmount() == null
					&& form.getIsAboveOrBelowFlag() == null) {
				stockInfoList = repository.findAll();
			} else {
				stockInfoList = repository.findByCategoryIdAndStockNameAndAmount(
						form.getCategoryId(),
						form.getStockName(),
						form.getAmount(),
						form.getIsAboveOrBelowFlag());
			}
		} catch (Exception e) {
			throw e;
		}
		return stockInfoList;
	}

	/**
	 * 在庫一覧情報をcategoryIdによる検索で取得
	 * 
	 * @param categoryId 分類ID
	 * @return 指定された分類IDに対応する在庫一覧情報リスト
	 */
	public List<StockInfo> getStockInfoData(Integer categoryId) {
		List<StockInfo> stockInfoList;
		try {
			//指定されたcategoryIdで在庫情報を取得
			if (categoryId == null) {
				stockInfoList = repository.findAll();
			} else {
				stockInfoList = repository.findByCategoryInfo_CategoryId(categoryId);
			}
		} catch (Exception e) {
			throw e;
		}
		return stockInfoList;
	}

	/**
	 * 在庫一覧情報をcenterInfoによる検索で取得
	 * 
	 * @param centerInfo 在庫センター情報
	 * @return 指定された在庫センターに対応する在庫一覧情報リスト
	 */
	public List<StockInfo> getStockInfoData(CenterInfo centerInfo) {
		List<StockInfo> stockInfoList;
		try {
			//指定された在庫センター情報で在庫情報を取得
			stockInfoList = repository.findByCenterInfo(centerInfo);
		} catch (Exception e) {
			throw e;
		}
		return stockInfoList;
	}

	/**
	 * 在庫一覧画面の共通データを取得
	 * 
	 * @param model 画面のモデルオブジェクト
	 * @param form 在庫一覧の検索条件を含むフォームオブジェクト {@link StockInfoForm}
	 */
	public void setUpStockListViewData(Model model, StockInfoForm form) {
		try {
			//画面表示用データの取得
			List<StockInfo> stockInfoList = this.getStockInfoData(form);
			List<StockInfo> selectStockInfoList = this.getStockInfoData(form.getCategoryId());
			List<CategoryInfo> categories = categoryInfoService.getCategoryInfoData();

			//画面表示用データのセット
			model.addAttribute("inputtedValue", form); // 検索結果を保持するためのセット
			model.addAttribute("stockInfoList", stockInfoList); // 画面表示用に検索結果をセット
			model.addAttribute("categories", categories); // 分類プルダウン情報をセット
			model.addAttribute("selectStockInfoList", selectStockInfoList); // 在庫プルダウン情報をセット
		} catch (Exception e) {
			throw e;
		}
	}
}