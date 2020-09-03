package com.shantery.result2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	  //変更処理
	   public User update(User user) {
		   return  userRepository.save(user);
	    }
	   public void delete(String loginid) {
		   userRepository.deleteById(loginid);
	    }


}
