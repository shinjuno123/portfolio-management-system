package com.amazing.juno.springwebapp.dto;

import java.util.Arrays;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;

import jakarta.validation.constraints.NotNull;

public class NoteworthyProjectArray {
	
	@NotNull
	private NoteworthyProject[] projects;

	public NoteworthyProject[] getProjects() {
		return projects;
	}

	public void setProjects(NoteworthyProject[] projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "NoteworthyProjectArray [projects=" + Arrays.toString(projects) + "]";
	}
	
	
}
