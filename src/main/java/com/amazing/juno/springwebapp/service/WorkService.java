package com.amazing.juno.springwebapp.service;

import java.util.List;

import com.amazing.juno.springwebapp.entity.Work;

public interface WorkService {
	public List<Work> getWork();
	public Work[] saveOrUpdate(Work[] works);
}
