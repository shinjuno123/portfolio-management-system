package com.amazing.juno.springwebapp.service;

import java.util.List;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.entity.Work;

public interface WorkService {
	public List<Work> getWork();
	public Work[] saveOrUpdateWorks(Work[] works);
	public void deleteWorks(Integer[] works);
	
	public List<NoteworthyProject> findAllNoteworthyProjects();
	public void saveOrUpdateAllNoteworthyProjects(NoteworthyProject[] projects);
	public void deleteAllNoteworthyProjects(Integer[] ids);
}
