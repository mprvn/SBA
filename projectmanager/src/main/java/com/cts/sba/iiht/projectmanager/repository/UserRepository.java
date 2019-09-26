package com.cts.sba.iiht.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sba.iiht.projectmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public List<User> findByProjectId(Integer id);
	public List<User> findByTaskId(Integer id);
}
