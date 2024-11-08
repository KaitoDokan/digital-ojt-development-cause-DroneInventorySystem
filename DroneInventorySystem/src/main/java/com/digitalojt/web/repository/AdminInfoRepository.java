package com.digitalojt.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalojt.web.entity.AdminInfo;

/**
 * 管理者情報テーブルリポジトリー
 *
 * @author KaitoDokan
 * 
 */
@Repository
public interface AdminInfoRepository extends JpaRepository<AdminInfo, String> {
	Optional<AdminInfo> findByAdminId(String AdminId);

}
