package com.digitalojt.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalojt.web.entity.CategoryInfo;
import com.digitalojt.web.repository.CategoryInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * 分類情報画面のサービスクラス
 *
 * @author KaitoDokan
 * 未編集
 */
@Service
@RequiredArgsConstructor
public class CategoryInfoService {

	/** 分類情報テーブル リポジトリー */
	private final CategoryInfoRepository repository;

	/**
	 * 分類情報を全件検索で取得
	 * 
	 * @return
	 */
	public List<CategoryInfo> getCategoryInfoData() {
		List<CategoryInfo> categoryInfoList = repository.findAll();
		return categoryInfoList;
	}

	/**
	 * 引数に合致する分類情報を取得
	 * 
	 * @param categoryName
	
	 * @return 
	 */
	public List<CategoryInfo> getCategoryInfoData(String categoryName) {
		// リポジトリを用いて検索、セット
		List<CategoryInfo> categoryInfoList = repository.findByCategoryName(categoryName);
		return categoryInfoList;
	}
}
