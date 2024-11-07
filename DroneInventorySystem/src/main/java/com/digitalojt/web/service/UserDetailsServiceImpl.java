package com.digitalojt.web.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalojt.web.entity.AdminInfo;
import com.digitalojt.web.repository.AdminInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * ログイン認証のサービスクラス
 *
 * @author KaitoDokan
 * 
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	//管理者リポジトリ
	private final AdminInfoRepository repository;

	
//	ユーザー情報生成
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminInfo adminInfo = repository.findByAdminId(username)
				.orElseThrow(()->new UsernameNotFoundException(username));

		return User
				.withUsername(adminInfo.getAdminId())
				.password(adminInfo.getPassword())
				.roles("ADMIN").build();
	}
}