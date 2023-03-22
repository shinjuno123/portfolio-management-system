package com.amazing.juno.springwebapp.dao;

import java.util.List;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;

public interface NoteworthyProjectDao {
	
	public List<NoteworthyProject> findAll();
	public void saveOrUpdateAll(NoteworthyProject[] projects);
	public void deleteAll(Integer[] ids);
}
