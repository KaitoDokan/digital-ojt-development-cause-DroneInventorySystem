package com.digitalojt.web.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.digitalojt.web.consts.Region;
import com.digitalojt.web.dto.CenterListViewData;
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
 * 
 */
@Service
@RequiredArgsConstructor
public class CenterInfoService {

	/** 在庫一覧サービス */
	private final StockInfoService stockInfoService;

	/** センター情報テーブル リポジトリー */
	private final CenterInfoRepository repository;

	/**
	 * 在庫センター情報をformによる検索で取得
	 * 
	 * @param CenterInfoForm
	 * @return CenterInfo
	 */
	public List<CenterInfo> getCenterInfoData(CenterInfoForm form) {

		if (form.getCenterName() == null
				&& form.getRegion() == null
				&& form.getStorageCapacityFrom() == null
				&& form.getStorageCapacityTo() == null) {
			return repository.findAll(); // 全件取得
		}
		return repository.findByCenterNameAndRegionAndStorageCapacity(
				form.getCenterName(),
				form.getRegion(),
				form.getStorageCapacityFrom(),
				form.getStorageCapacityTo());
	}

	/**
	 * 在庫センター情報をcenterIdによる検索で取得
	 * 
	 * @param centerId
	 * @return centerInfo
	 */
	public CenterInfo getCenterInfoData(Integer centerId) {
		CenterInfo centerInfo = repository.findById(centerId).orElse(null);
		if (centerInfo == null) {
			throw new EntityNotFoundException("Center not found with ID: " + centerId);
		}
		return centerInfo;
	}
	
	/**
	 * 在庫が存在するセンターかどうか
	 * 
	 * @param centerId センターID
	 * @return 在庫が存在する場合はtrue、それ以外はfalse
	 */
	public boolean isCenterWithStockExists(CenterInfo centerinfo) {
	    // centerId に一致する在庫情報を取得
	    List<StockInfo> stockInfoList = stockInfoService.getStockInfoData(centerinfo);
	    System.out.println(stockInfoList);

	    // 取得したリストが null または 空でない場合は true、それ以外は false
	    return stockInfoList != null && !stockInfoList.isEmpty();
	}


	/**
	 * 在庫一覧画面の共通データを取得
	 * 
	 * @param form フォームオブジェクト
	 * @return 在庫画面の共通データを格納するDTO
	 */
	public CenterListViewData getCenterListViewData(CenterInfoForm form) {
		CenterListViewData data = new CenterListViewData();

		// 在庫センター情報の取得
		List<CenterInfo> centerInfoList = this.getCenterInfoData(form);

		// 都道府県リストの取得
		List<Region> regions = Arrays.asList(Region.values());

		// DTOにセット
		data.setCenterInfoList(centerInfoList);
		data.setRegions(regions);
		data.setForm(form);

		return data;
	}

	/**
	 * 共通のmodelをセットする
	 * 
	 * @param model   Modelオブジェクト
	 * @param data    在庫一覧画面の共通データ
	 */
	public void setCommonModelAttributes(Model model, CenterListViewData data) {
		model.addAttribute("inputtedValue", data.getForm()); // 検索結果を保持するためのセット
		model.addAttribute("centerInfoList", data.getCenterInfoList()); // 画面表示用に検索結果をセット
		model.addAttribute("regions", data.getRegions()); // 在庫プルダウン情報をセット
	}

	//	public void registerCenterInfo(CenterRegisterForm form) {
	//		// フォームからエンティティへの変換
	//		CenterInfo centerInfo = new CenterInfo();
	//		centerInfo.setCenterName(form.getCenterName());
	//		centerInfo.setPostCode(form.getPostCode());
	//		centerInfo.setAddress(form.getAddress());
	//		centerInfo.setPhoneNumber(form.getPhoneNumber());
	//		centerInfo.setManagerName(form.getManagerName());
	//		centerInfo.setOperationalStatus(form.getOperationalStatus());
	//		centerInfo.setMaxStorageCapacity(form.getMaxStorageCapacity());
	//		centerInfo.setCurrentStorageCapacity(form.getCurrentStorageCapacity());
	//		centerInfo.setDeleteFlag("0"); // 新規登録の場合、論理削除フラグは"未削除"に設定
	//		centerInfo.setCreateDate(new Timestamp(System.currentTimeMillis())); // 登録日
	//		centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日
	//
	//		// エンティティをデータベースに保存
	//		repository.save(centerInfo);
	//	}

	public void registerCenterInfo(CenterInfo centerInfo) {
		centerInfo.setDeleteFlag("0"); // 新規登録の場合、論理削除フラグは"未削除"に設定
		centerInfo.setCreateDate(new Timestamp(System.currentTimeMillis())); // 登録日
		centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日

		// エンティティをデータベースに保存
		repository.save(centerInfo);
	}

	public void updateCenterInfo(CenterInfo centerInfo) {
		//hiddenでフォームから送らせても良いが、書き換えのリスクを鑑みてここで再取得
		// DBから既存データを再取得
		CenterInfo oldCenterInfo = repository.findById(centerInfo.getCenterId())
				.orElseThrow(() -> new IllegalArgumentException("指定されたセンター情報が存在しません"));

		centerInfo.setDeleteFlag(oldCenterInfo.getDeleteFlag()); // 更新の場合、論理削除フラグは"未削除"に設定
		centerInfo.setCreateDate(oldCenterInfo.getCreateDate()); // 登録日
		centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日

		// エンティティをデータベースに保存
		repository.save(centerInfo);
	}

	public void deleteCenterInfo(CenterInfo centerInfo) {
		//hiddenでフォームから送らせても良いが、書き換えのリスクを鑑みてここで再取得
		// DBから既存データを再取得

		CenterInfo oldCenterInfo = repository.findById(centerInfo.getCenterId())
				.orElseThrow(() -> new IllegalArgumentException("指定されたセンター情報が存在しません"));
		System.out.println(centerInfo);
		centerInfo.setDeleteFlag("1"); // 更新の場合、論理削除フラグは"未削除"に設定
		centerInfo.setCreateDate(oldCenterInfo.getCreateDate()); // 登録日
		centerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新日
		System.out.println(centerInfo);
		// エンティティをデータベースに保存
		repository.save(centerInfo);
	}

}
