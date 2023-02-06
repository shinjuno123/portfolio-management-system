package com.amazing.juno.springwebapp.dao;

import java.util.List;
import java.util.Map;

import com.amazing.juno.springwebapp.dto.TechnologyListDto;
import com.amazing.juno.springwebapp.entity.TechnologyEntity;

public interface TechnologyInterface {
	public List<TechnologyEntity> getFrontendList();
	public List<TechnologyEntity> getBackendList();
	public List<TechnologyListDto> getTechnologyStack();
}
