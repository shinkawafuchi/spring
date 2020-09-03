package com.shantery.result2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoutService {
	@Autowired
	ShoutRepository shoutRepository;
	  public Shout findOne(Long shout_id) {
	        return shoutRepository.findById(shout_id).orElse(null);
	    }
	  public void delete(Long shout_id) {
		  shoutRepository.deleteById(shout_id);
	    }
}
