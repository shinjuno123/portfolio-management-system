package com.amazing.juno.springwebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazing.juno.springwebapp.dao.NoteworthyProjectDaoImpl;
import com.amazing.juno.springwebapp.dao.WorkDaoJpaImpl;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.entity.Work;

import jakarta.transaction.Transactional;


@Service
public class WorkServiceImpl implements WorkService {
	
	@Autowired
	WorkDaoJpaImpl workDaoJpaImpl;
	
	@Autowired
	NoteworthyProjectDaoImpl noteWorthyProjectImpl;

	
	@Override
	@Transactional
	public List<Work> getWork() {
		return workDaoJpaImpl.findAll();
	}

	@Override
	@Transactional
	public Work[] saveOrUpdateWorks(Work[] works) {
		return workDaoJpaImpl.saveOrUpdate(works);
	}

	@Override
	@Transactional
	public void deleteWorks(Integer[] works) {
		workDaoJpaImpl.deleteByIds(works);
	}

	@Override
	@Transactional
	public List<NoteworthyProject> findAllNoteworthyProjects() {
		return noteWorthyProjectImpl.findAll();
	}

	@Override
	@Transactional
	public void saveOrUpdateAllNoteworthyProjects(NoteworthyProject[] projects) {
		noteWorthyProjectImpl.saveOrUpdateAll(projects);
		
	}

	@Override
	@Transactional
	public void deleteAllNoteworthyProjects(Integer[] ids) {
		noteWorthyProjectImpl.deleteAll(ids);
	}

}
