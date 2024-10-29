package com.digitalojt.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalojt.web.entity.CategoryInfo;

/**
 * 分類情報テーブルリポジトリー
 *
 * @author KaitoDokan
 * 
 */
@Repository
public interface CategoryInfoRepository extends JpaRepository<CategoryInfo, Integer> {
	List<CategoryInfo> findByCategoryName(String categoryName);
}
