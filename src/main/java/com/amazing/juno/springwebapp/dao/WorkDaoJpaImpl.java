package com.amazing.juno.springwebapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.Work;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class WorkDaoJpaImpl implements WorkDao {
	
	// define field for entityManager
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public WorkDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Work> findAll() {
		// create a query
		TypedQuery<Work> query = entityManager.createQuery("from Work", Work.class);
		
		// execute query and get result list
		List<Work> works = query.getResultList();
		
		// return the results
		return works;
	}

}
