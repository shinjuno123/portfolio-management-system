package com.amazing.juno.springwebapp.dao;

import java.util.List;

import com.amazing.juno.springwebapp.entity.Work;

public interface WorkDao {
	public List<Work> findAll();
	public Work[] saveOrUpdate(Work[] works);
}
