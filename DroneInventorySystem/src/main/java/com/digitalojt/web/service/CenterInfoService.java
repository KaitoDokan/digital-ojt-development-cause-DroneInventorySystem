package com.digitalojt.web.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.digitalojt.web.consts.Region;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.entity.StockInfo;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.repository.CenterInfoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * 在庫センター情報画面のサービスクラス
 *
 * @author KaitoDokan
 */
@Service
@RequiredArgsConstructor
public class CenterInfoService {

	/** 在庫一覧サービス */
	private final StockInfoService stockInfoService;

	/** センター情報テーブル リポジトリー */
	private final CenterInfoRepository repository;

	/**
	 * 在庫センター情報をフォームの検索条件に基づいて取得
	 *
	 * @param form 検索条件を含む {@link CenterInfoForm}
	 * @return 検索結果の在庫センター情報リスト
	 */
	public List<CenterInfo> getCenterInfoData(CenterInfoForm form) {
		List<CenterInfo> centerInfoList;

		try {
			if (form.getCenterName() == null && form.getRegion() == null) {
				centerInfoList = repository.findAll(); // 全件取得
			} else {
				centerInfoList = repository.findByCenterNameAndRegionAndStorageCapacity(
						form.getCenterName(),
						form.getRegion(),
						form.getStorageCapacityFrom(),
						form.getStorageCapacityTo()); // formの内容によって検索
			}
		} catch (Exception e) {
			throw e;
		}
		return centerInfoList;
	}

	/**
	 * センターIDを指定して在庫センター情報を取得
	 *
	 * @param centerId センターID
	 * @return 指定したIDに対応する {@link CenterInfo}
	 * @throws EntityNotFoundException 指定したIDのセンター情報が存在しない場合にスロー
	 */
	public CenterInfo getCenterInfoData(Integer centerId) {
		CenterInfo centerInfo;
		try {
			centerInfo = repository.findById(centerId).orElseThrow();
		} catch (Exception e) {
			throw e;
		}
		return centerInfo;
	}

	/**
	 * 指定したセンターに在庫が存在するかを判定
	 *
	 * @param centerInfo 在庫センター情報 {@link CenterInfo}
	 * @return 在庫が存在する場合は true、それ以外は false
	 */
	public boolean isCenterWithStockExists(CenterInfo centerInfo) {
		List<StockInfo> stockInfoList = stockInfoService.getStockInfoData(centerInfo);
		return stockInfoList != null && !stockInfoList.isEmpty();
	}

	/**
	 * 在庫センター情報画面の共通データを取得
	 *
	 * @param model 画面のモデルオブジェクト
	 * @param form 検索条件を含む {@link CenterInfoForm}
	 */
	public void setUpCenterListViewData(Model model, CenterInfoForm form) {

		// 在庫センター情報の取得
		List<CenterInfo> centerInfoList = this.getCenterInfoData(form);

		// 都道府県リストの取得
		List<Region> regions = Arrays.asList(Region.values());

		model.addAttribute("inputtedValue", form); // 検索結果を保持するためのセット
		model.addAttribute("centerInfoList", centerInfoList); // 画面表示用に検索結果をセット
		model.addAttribute("regions", regions); // 在庫プルダウン情報をセット
	}

	// DB操作メソッド
	/**
	 * 在庫センター情報を新規登録
	 *
	 * @param centerInfo 登録する在庫センター情報 {@link CenterInfo}
	 */
	public void registerCenterInfo(CenterInfo centerInfo) {
		try {
			centerInfo.setDeleteFlag("0"); // 新規登録の場合、論理削除フラグは"未削除"に設定
			centerInfo.setCreateDate(new Timestamp(System.currentTimeMillis())); // 登録日
			centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日

			// エンティティをデータベースに保存
			repository.save(centerInfo);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 在庫センター情報を更新
	 *
	 * @param centerInfo 更新する在庫センター情報 {@link CenterInfo}
	 * @throws EntityNotFoundException 更新対象のセンター情報が存在しない場合にスロー
	 */
	public void updateCenterInfo(CenterInfo centerInfo) {
		// DBから既存データを再取得
		try {
			CenterInfo oldCenterInfo = repository.findById(centerInfo.getCenterId()).orElseThrow();
			centerInfo.setDeleteFlag(oldCenterInfo.getDeleteFlag()); // 更新の場合、論理削除フラグは"未削除"に設定
			centerInfo.setCreateDate(oldCenterInfo.getCreateDate()); // 登録日
			centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日

			// エンティティをデータベースに保存
			repository.save(centerInfo);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 在庫センター情報を論理削除
	 *
	 * @param centerInfo 論理削除する在庫センター情報 {@link CenterInfo}
	 * @throws EntityNotFoundException 削除対象のセンター情報が存在しない場合にスロー
	 */
	public void deleteCenterInfo(CenterInfo centerInfo) {
		// DBから既存データを再取得
		try {
			CenterInfo oldCenterInfo = repository.findById(centerInfo.getCenterId()).orElseThrow();
			centerInfo.setDeleteFlag("1"); // 論理削除フラグを"削除"に設定
			centerInfo.setCreateDate(oldCenterInfo.getCreateDate()); // 登録日
			centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日

			// エンティティをデータベースに保存
			repository.save(centerInfo);
		} catch (Exception e) {
			throw e;
		}
	}
}