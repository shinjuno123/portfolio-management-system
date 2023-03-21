package com.amazing.juno.springwebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazing.juno.springwebapp.dao.WorkDaoJpaImpl;
import com.amazing.juno.springwebapp.entity.Work;

import jakarta.transaction.Transactional;


@Service
public class WorkServiceImpl implements WorkService {
	
	@Autowired
	WorkDaoJpaImpl workDaoJpaImpl;

	
	@Override
	@Transactional
	public List<Work> getWork() {
		return workDaoJpaImpl.findAll();
	}

	@Override
	@Transactional
	public Work[] saveOrUpdate(Work[] works) {
		return workDaoJpaImpl.saveOrUpdate(works);
	}

}
