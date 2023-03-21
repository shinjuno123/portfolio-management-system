package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	private String projectUrl;
	
	@Column(name="project_image_url")
	private String projectImageUrl;
	
	@Transient
	private String projectImageDataURl;
	

	
	
	
	public Work() {

	}



	public Work(int id, String projectTitle, String projectDescription, String projectUrl, String projectImageUrl) {
		super();
		this.id = id;
		this.projectTitle = projectTitle;
		this.projectDescription = projectDescription;
		this.projectUrl = projectUrl;
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


	public String getProjectUrl() {
		return projectUrl;
	}



	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}



	public String getProjectImageUrl() {
		return projectImageUrl;
	}



	public void setProjectImageUrl(String projectImageUrl) {
		this.projectImageUrl = projectImageUrl;
	}
	


	public String getProjectImageDataURl() {
		return projectImageDataURl;
	}



	public void setProjectImageDataURl(String projectImageDataURl) {
		this.projectImageDataURl = projectImageDataURl;
	}
	





	@Override
	public String toString() {
		return "\nWork [id=" + id + "\nprojectTitle=" + projectTitle + "\nprojectDescription=" + projectDescription + "\nprojectUrl="
				+ projectUrl + "\nprojectImageUrl=" + projectImageUrl + "]\n";
	}
	
	
	
	
}
