package com.digitalojt.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalojt.web.consts.CategoryInfoConsts;
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

		// 分類情報作成
		List<CategoryInfo> categoryInfoList = createCategoryInfo();

		// 検索処理
		categoryInfoList = searchCategoryInfoData(categoryInfoList, categoryName);

		return categoryInfoList;
	}

	/**
	 * 検索処理
	 * 
	 * @param categoryInfoList
	 * @param categoryName
	 * @return
	 */
	private List<CategoryInfo> searchCategoryInfoData(List<CategoryInfo> categoryInfoList,
			String categoryName) {

		List<CategoryInfo> hitCategoryInfoList = new ArrayList<>();

		// 引数の文字列と合致する要素のみリストに追加
		categoryInfoList.forEach(item -> {
			if (categoryName.equals(item.getCategoryName())) {
				hitCategoryInfoList.add(item);
			}
		});

		return hitCategoryInfoList;
	}

	/**
	 * 分類情報作成
	 * 
	 * @return
	 */
	private List<CategoryInfo> createCategoryInfo() {

		List<CategoryInfo> categoryInfoList = new ArrayList<>();

		// 1コード目作成
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.FRAME);
		categoryInfoList.add(categoryInfo);

		// 2コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.PROPELLER);
		categoryInfoList.add(categoryInfo);

		// 3コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.ELECTRIC_MOTOR);
		categoryInfoList.add(categoryInfo);

		// 4コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.ELECTRIC_SPEED_CONTROLLER);
		categoryInfoList.add(categoryInfo);

		// 5コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.BATTERY);
		categoryInfoList.add(categoryInfo);

		// 6コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.FLIGHT_CONTROLLER);
		categoryInfoList.add(categoryInfo);

		// 7コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.REMOTE_CONTROLLER);
		categoryInfoList.add(categoryInfo);

		// 8コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.RECEIVER);
		categoryInfoList.add(categoryInfo);

		// 9コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.GPS_MODULE);
		categoryInfoList.add(categoryInfo);

		// 10コード目作成
		categoryInfo = new CategoryInfo();
		categoryInfo.setCategoryName(CategoryInfoConsts.CAMERA_SENSOR);
		categoryInfoList.add(categoryInfo);

		return categoryInfoList;
	}

}
