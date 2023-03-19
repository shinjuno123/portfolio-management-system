package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="work")
public class Work {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="project_title")
	private String projectTitle;
	
	@Column(name="project_description")
	private String projectDescription;
	
	@Column(name="project_url")
	private String prjectUrl;
	
	@Column(name="project_image_url")
	private String projectImageUrl;
	
	
	
	public Work() {
		
	}



	public Work(int id, String projectTitle, String projectDescription, String prjectUrl, String projectImageUrl) {
		super();
		this.id = id;
		this.projectTitle = projectTitle;
		this.projectDescription = projectDescription;
		this.prjectUrl = prjectUrl;
		this.projectImageUrl = projectImageUrl;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getProjectTitle() {
		return projectTitle;
	}



	public void setProjectTitle(String title) {
		this.projectTitle = title;
	}



	public String getProjectDescription() {
		return projectDescription;
	}



	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}



	public String getPrjectUrl() {
		return prjectUrl;
	}



	public void setPrjectUrl(String prjectUrl) {
		this.prjectUrl = prjectUrl;
	}



	public String getProjectImageUrl() {
		return projectImageUrl;
	}



	public void setProjectImageUrl(String projectImageUrl) {
		this.projectImageUrl = projectImageUrl;
	}



	@Override
	public String toString() {
		return "Work [id=" + id + ", projectTitle=" + projectTitle + ", projectDescription=" + projectDescription + ", prjectUrl="
				+ prjectUrl + ", projectImageUrl=" + projectImageUrl + "]";
	}
	
	
	
	
}
