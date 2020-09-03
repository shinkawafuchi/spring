package com.shantery.result2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User,String>{
	//ログイン機能
	User findByLoginidAndPassword(String loginid,String password);
	//検索機能
	List<User> findByUsernameOrIconOrLoginid(String username,String icon,String logonid);

	User findByLoginid(String loginid);
}
