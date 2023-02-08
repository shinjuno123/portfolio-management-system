package com.amazing.juno.springwebapp.dao;

import java.util.List;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;

public interface TechnologyInterface {
	public List<TechnologyListDto> getTechnologyStack();
	public void setTechnologyStack(List<TechnologyListDto> stack);
}
