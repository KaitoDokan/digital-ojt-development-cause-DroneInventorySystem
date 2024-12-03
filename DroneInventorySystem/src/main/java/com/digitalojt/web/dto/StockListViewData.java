package com.digitalojt.web.dto;

import java.util.List;

import com.digitalojt.web.entity.CategoryInfo;
import com.digitalojt.web.entity.StockInfo;
import com.digitalojt.web.form.StockInfoForm;

import lombok.Data;

@Data
public class StockListViewData {
	private List<StockInfo> stockInfoList;// 在庫一覧データ
	private List<StockInfo> selectStockInfoList; // プルダウン用在庫データ
	private List<CategoryInfo> categories; // 分類情報
	private StockInfoForm form; // 入力フォームの保持データ
}
