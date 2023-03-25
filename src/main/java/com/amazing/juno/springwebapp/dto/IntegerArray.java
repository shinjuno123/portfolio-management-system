package com.amazing.juno.springwebapp.dto;

import jakarta.validation.constraints.NotNull;

public class IntegerArray {
	@NotNull
	Integer[] projectIds;
	
	
	public IntegerArray() {
	}

	public Integer[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(Integer[] projectIds) {
		this.projectIds = projectIds;
	}
	
	
}
