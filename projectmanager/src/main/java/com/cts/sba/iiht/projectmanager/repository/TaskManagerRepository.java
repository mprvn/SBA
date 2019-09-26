package com.cts.sba.iiht.projectmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sba.iiht.projectmanager.entity.Task;

@Repository
public interface TaskManagerRepository extends JpaRepository<Task, Integer> {
	 List<Task> findByTask(String task);
	 List<Task> findByEndDate(Date endDate);
	 List<Task> findByStartDate(Date startDate);
	 List<Task> findByParentTaskId(Integer id);
	 List<Task> findByProjectId(Integer id);
	 List<Task> findByParentTaskTask(String task);
	 List<Task> findByPriorityBetween(Integer from, Integer to);
	 List<Task> findByPriorityLessThanEqual(Integer priority);
	 List<Task> findByPriorityGreaterThanEqual(Integer priority);
}
