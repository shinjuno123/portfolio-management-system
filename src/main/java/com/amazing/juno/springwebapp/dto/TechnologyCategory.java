package com.amazing.juno.springwebapp.dto;

import java.util.List;

import com.amazing.juno.springwebapp.entity.Technology;

public class TechnologyCategory {
	private String categoryName;
	
	private List<Technology> techList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Technology> getTechList() {
		return techList;
	}

	public void setTechList(List<Technology> techList) {
		this.techList = techList;
	}

	@Override
	public String toString() {
		return "TechnologyListDto [categoryName=" + categoryName + ", techList=" + techList + "]";
	}
}
