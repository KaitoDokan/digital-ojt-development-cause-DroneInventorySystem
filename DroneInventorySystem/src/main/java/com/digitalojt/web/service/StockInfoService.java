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
		System.out.println(stockInfoList);
		return stockInfoList;
	}

}
