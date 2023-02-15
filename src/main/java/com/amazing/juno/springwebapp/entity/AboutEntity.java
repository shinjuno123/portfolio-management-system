package com.amazing.juno.springwebapp.entity;

import java.util.Arrays;

public class AboutEntity {
	private String description;
	private String[] sentences;
	private String period;
	private String degree;
	private String school;
	private String regionCountry;

	public AboutEntity() {
	}

	public AboutEntity(String description, String period, String degree, String school, String regionCountry) {
		setDescription(description);
		this.period = period;
		this.degree = degree;
		this.school = school;
		this.regionCountry = regionCountry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		String[] lines = description.split("(\r\n|\r|\n)");
		
		setSentences(lines);
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getRegionCountry() {
		return regionCountry;
	}

	public void setRegionCountry(String regionCountry) {
		this.regionCountry = regionCountry;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	
	public String[] getSentences() {
		return sentences;
	}

	public void setSentences(String[] sentences) {
		this.sentences = sentences;
	}

	@Override
	public String toString() {
		return "AboutEntity [description=" + description + "\nsentences=" + Arrays.toString(sentences) + "\nperiod="
				+ period + "\ndegree=" + degree + "\nschool=" + school + "\nregionCountry=" + regionCountry + "]";
	}

}
