package com.amazing.juno.springwebapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class NoteworthyProjectDaoImpl implements NoteworthyProjectDao {

	@Autowired
	private EntityManager entityManager;


	@Override
	public List<NoteworthyProject> findAll() {
		
		TypedQuery<NoteworthyProject> query = entityManager.createQuery("FROM NoteworthyProject", NoteworthyProject.class);

		return query.getResultList();
	}

	@Override
	public void saveOrUpdateAll(NoteworthyProject[] projects) {
		// TODO Auto-generated method stub
		for(NoteworthyProject project: projects) {
			entityManager.merge(project);
		}

	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder();
		
		query.append("DELETE FROM NoteworthyProject WHERE id IN (");
		
		for(int id: ids) {
			query.append(id);
			query.append(", ");
		}
		query.delete(query.length() - 2, query.length());
		query.append(")");
		
		
		System.out.println(query.toString());
		
		Query result = entityManager.createQuery(query.toString());
		result.executeUpdate();
		

	}

}
