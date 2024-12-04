package com.digitalojt.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.digitalojt.web.consts.Region;
import com.digitalojt.web.dto.CenterListViewData;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.form.CenterInfoForm;
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
	 * 在庫センター情報をformによる検索で取得
	 * 
	 * @param centerName
	 * @param region 
	 * @param storageCapacityFrom 
	 * @param storageCapacityTo
	 * @return 
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
}
