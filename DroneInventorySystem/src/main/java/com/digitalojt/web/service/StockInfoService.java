package com.digitalojt.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalojt.web.entity.StockInfo;
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

	/**
	 * 在庫一覧情報を全件検索で取得
	 * 
	 * @return
	 */
	public List<StockInfo> getStockInfoData() {
		List<StockInfo> stockInfoList = repository.findAll();
		return stockInfoList;
	}

	/**
	 * 引数に合致する分類情報を取得
	 * 
	 * @param categoryId
	 * @param stockName
	 * @param amount
	 * @param isAboveOrBelowFlag
	 * @return 
	 */
	public List<StockInfo> getStockInfoData(Integer categoryId,String stockName,Integer amount,Integer isAboveOrBelowFlag) {
		// リポジトリを用いて検索、セット
		List<StockInfo> stockInfoList = repository.findByCategoryIdAndStockNameAndAmount(categoryId,stockName,amount,isAboveOrBelowFlag);
		return stockInfoList;
	}
}
