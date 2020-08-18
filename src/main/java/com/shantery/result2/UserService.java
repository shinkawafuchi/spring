package com.shantery.result2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	  public List<User> search(String username, String icon){
	        List<User> result = userRepository.findAll();
	        return result;
	    }
}
