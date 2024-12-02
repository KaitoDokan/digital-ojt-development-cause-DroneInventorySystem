package com.digitalojt.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalojt.web.entity.StockInfo;

/**
 * 在庫一覧テーブルリポジトリー
 *
 * @author KaitoDokan
 * 
 */
@Repository
public interface StockInfoRepository extends JpaRepository<StockInfo, Integer> {

	@Query("SELECT s FROM StockInfo s WHERE s.deleteFlag = '0'")
	List<StockInfo> findAll();

	/**
	 * 引数に合致する在庫情報を取得
	 * 
	 * @param categoryId
	 * @param stockName
	 * @param amount
	 * @param isAboveOrBelowFlag
	 * @return paramで検索した結果
	 */
	@Query("SELECT s FROM StockInfo s " +
			"WHERE (:categoryId IS NULL OR s.categoryInfo.categoryId = :categoryId) " +
			"AND (:stockName IS NULL OR s.stockName LIKE %:stockName%) " +
			"AND (:amount IS NULL OR " +
			"     (:isAboveOrBelowFlag = 0 AND s.amount >= :amount) OR " +
			"     (:isAboveOrBelowFlag = 1 AND s.amount <= :amount)) " +
			"AND s.deleteFlag = '0'")
	List<StockInfo> findByCategoryIdAndStockNameAndAmount(
			Integer categoryId,
			String stockName,
			Integer amount,
			Integer isAboveOrBelowFlag);
}
