package com.shantery.result2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoutRepository extends JpaRepository<Shout,Long>{

	List<Shout> findAll();


}
