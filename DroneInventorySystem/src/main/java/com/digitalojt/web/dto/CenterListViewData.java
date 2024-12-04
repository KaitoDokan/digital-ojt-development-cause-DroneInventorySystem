package com.digitalojt.web.dto;

import java.util.List;

import com.digitalojt.web.consts.Region;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.form.CenterInfoForm;

import lombok.Data;

@Data
public class CenterListViewData {
	private List<CenterInfo> centerInfoList;// 在庫センター一覧データ
	private List<Region> regions; // 都道府県プルダウン
	private CenterInfoForm form; // 入力フォームの保持データ
}