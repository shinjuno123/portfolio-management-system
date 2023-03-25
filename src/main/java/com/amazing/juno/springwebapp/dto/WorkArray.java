package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.Work;

public class WorkArray {
	Work[] works;
	
	
	public WorkArray() {

	}
	
	

	public WorkArray(Work[] works) {
		this.works = works;
	}



	public Work[] getWorks() {
		return works;
	}



	public void setWorks(Work[] works) {
		this.works = works;
	}

}
