package com.digitalojt.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalojt.web.entity.CenterInfo;

/**
 * センター情報テーブルリポジトリー
 *
 * @author KaitoDokan
 * 
 */
@Repository
public interface CenterInfoRepository extends JpaRepository<CenterInfo, Integer> {

	
	@Query("SELECT c FROM CenterInfo c WHERE c.deleteFlag = '0'")
	List<CenterInfo> findAll();//全件取得
	
	/**
	 * 引数に合致する在庫センター情報を取得
	 * 
	 * @param centerName
	 * @param region
	 * @param storageCapacityFrom
	 * @param storageCapacityTo
	 * @return paramで検索した結果
	 */
	@Query("SELECT c FROM CenterInfo c WHERE " +
			"(:centerName = '' OR c.centerName LIKE %:centerName%) AND " +
			"(:region = '' OR c.address LIKE %:region%) AND " +
			"(:storageCapacityFrom IS NULL OR c.currentStorageCapacity >= :storageCapacityFrom) AND " +
			"(:storageCapacityTo IS NULL OR c.currentStorageCapacity <= :storageCapacityTo) AND " +
			"c.deleteFlag = '0'")
	List<CenterInfo> findByCenterNameAndRegionAndStorageCapacity(
			String centerName,
			String region,
			Integer storageCapacityFrom,
			Integer storageCapacityTo);
}
