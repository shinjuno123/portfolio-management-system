package com.amazing.juno.springwebapp.dto;

import java.util.List;

import com.amazing.juno.springwebapp.entity.TechnologyEntity;
import com.amazing.juno.springwebapp.validator.IsTechElemForm;

public class TechnologyListDto {
	private String categoryName;
	
	private List<TechnologyEntity> techList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<TechnologyEntity> getTechList() {
		return techList;
	}

	public void setTechList(List<TechnologyEntity> techList) {
		this.techList = techList;
	}

	@Override
	public String toString() {
		return "TechnologyListDto [categoryName=" + categoryName + ", techList=" + techList + "]";
	}
}
