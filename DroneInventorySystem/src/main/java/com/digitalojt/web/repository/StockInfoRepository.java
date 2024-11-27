package com.digitalojt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
