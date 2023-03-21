package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.Work;

public class WorkListDto {
	Work[] works;
	
	
	public WorkListDto() {

	}
	
	

	public WorkListDto(Work[] works) {
		this.works = works;
	}



	public Work[] getWorks() {
		return works;
	}



	public void setWorks(Work[] works) {
		this.works = works;
	}

}
