package com.cts.sba.iiht.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sba.iiht.projectmanager.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
