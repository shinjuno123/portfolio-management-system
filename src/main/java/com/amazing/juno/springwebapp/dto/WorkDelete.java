package com.amazing.juno.springwebapp.dto;

import jakarta.validation.constraints.NotNull;

public class WorkDelete {
	@NotNull
	Integer[] projectIds;
	
	
	public WorkDelete() {
	}

	public Integer[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(Integer[] projectIds) {
		this.projectIds = projectIds;
	}
	
	
}
