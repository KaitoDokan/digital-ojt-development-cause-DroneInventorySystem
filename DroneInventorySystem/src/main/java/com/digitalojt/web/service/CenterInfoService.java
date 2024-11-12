package com.digitalojt.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.repository.CenterInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * 在庫センター情報画面のサービスクラス
 *
 * @author KaitoDokan
 * 
 */
@Service
@RequiredArgsConstructor
public class CenterInfoService {

	/** センター情報テーブル リポジトリー */
	private final CenterInfoRepository repository;

	/**
	 * 在庫センター情報を全件検索で取得
	 * 
	 * @return
	 */
	public List<CenterInfo> getCenterInfoData() {

		// 在庫センター情報作成
		List<CenterInfo> centerInfoList = createCenterInfo();

		return centerInfoList;
	}

	/**
	 * 引数に合致する在庫センター情報を取得
	 * 
	 * @param centerName
	 * @param region 
	 * @param storageCapacityFrom 
	 * @param storageCapacityTo
	 * @return 
	 */
	public List<CenterInfo> getCenterInfoData(String centerName, String region) {

		// 在庫センター情報作成
		List<CenterInfo> centerInfoList = createCenterInfo();

		// 検索処理
		centerInfoList = searchCenterInfoData(centerInfoList, centerName, region);

		return centerInfoList;
	}

	/**
	 * 検索処理
	 * 
	 * @param centerInfoList
	 * @param centerName
	 * @param region 
	 * @return
	 */
	private List<CenterInfo> searchCenterInfoData(List<CenterInfo> centerInfoList, String centerName, String region) {

		List<CenterInfo> hitCenterInfoList = new ArrayList<>();
		
		// 引数の文字列と合致する要素のみリストに追加
		centerInfoList.forEach(item -> {
			if (centerName.equals(item.getCenterName()) && region.equals(item.getAddress())
					|| StringUtils.isEmpty(centerName) && item.getAddress().contains(region)
					|| StringUtils.isEmpty(region) && item.getCenterName().contains(centerName)) {
				hitCenterInfoList.add(item);
			}
		});

		return hitCenterInfoList;
	}

	/**
	 * 在庫センター情報作成
	 * 
	 * @return
	 */
	private List<CenterInfo> createCenterInfo() {

		List<CenterInfo> centerInfoList = new ArrayList<>();

		// 1コード目作成
		CenterInfo centerInfo = new CenterInfo();
		centerInfo.setCenterName("東京物流センター");
		centerInfo.setAddress("東京都港区芝公園4-2-8");
		centerInfo.setPhoneNumber("03-1234-5678");
		centerInfo.setManagerName("田中 太郎");
		centerInfoList.add(centerInfo);

		// 2コード目作成
		centerInfo = new CenterInfo();
		centerInfo.setCenterName("大阪物流センター");
		centerInfo.setAddress("大阪府大阪市北区梅田1-1-3");
		centerInfo.setPhoneNumber("06-8765-4321");
		centerInfo.setManagerName("鈴木 一郎");
		centerInfoList.add(centerInfo);

		// 3コード目作成
		centerInfo = new CenterInfo();
		centerInfo.setCenterName("名古屋物流センター");
		centerInfo.setAddress("愛知県名古屋市中村区名駅3-2-1");
		centerInfo.setPhoneNumber("052-123-4567");
		centerInfo.setManagerName("佐藤 花子");
		centerInfoList.add(centerInfo);

		// 4コード目作成
		centerInfo = new CenterInfo();
		centerInfo.setCenterName("仙台物流センター");
		centerInfo.setAddress("宮城県仙台市青葉区一番町4-4-1");
		centerInfo.setPhoneNumber("022-234-5678");
		centerInfo.setManagerName("中田 太郎");
		centerInfoList.add(centerInfo);

		// 5コード目作成
		centerInfo = new CenterInfo();
		centerInfo.setCenterName("福岡物流センター");
		centerInfo.setAddress("福岡県福岡市博多区博多駅前2-1-1");
		centerInfo.setPhoneNumber("092-234-5678");
		centerInfo.setManagerName("近藤 一郎");
		centerInfoList.add(centerInfo);

		// 6コード目作成
		centerInfo = new CenterInfo();
		centerInfo.setCenterName("北海道物流センター");
		centerInfo.setAddress("北海道札幌市中央区大通西3-6");
		centerInfo.setPhoneNumber("011-234-5678");
		centerInfo.setManagerName("小池 花子");
		centerInfoList.add(centerInfo);

		return centerInfoList;
	}

}
