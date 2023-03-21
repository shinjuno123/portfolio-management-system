package com.amazing.juno.springwebapp.dao;

import java.util.List;

import com.amazing.juno.springwebapp.dto.TechnologyCategory;

public interface TechnologyDao {
	public List<TechnologyCategory> getTechnologyStack();
	public void setTechnologyStack(List<TechnologyCategory> stack);
}
