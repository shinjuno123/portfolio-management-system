package com.amazing.juno.springwebapp.dao;

import java.util.List;

import com.amazing.juno.springwebapp.entity.TechnologyEntity;

public interface TechnologyInterface {
	public List<TechnologyEntity> getFrontendList();
	public List<TechnologyEntity> getBackendList();
}
