package com.amazing.juno.springwebapp.entity;

public class TechnologyEntity {
	private Integer score;
	private String skill;
	private String technologyDetail;
	
	
	public TechnologyEntity(Integer score, String skill) {
		this.score = score;
		this.skill = skill;
		this.setTechnologyDetail(this.chainStrings());
	}


	public String chainStrings() {
		StringBuilder stb = new StringBuilder();
		stb.append(skill);
		stb.append(" ⭐");
		stb.append(score);
		
		return stb.toString();
	}


	public String getTechnologyDetail() {
		return technologyDetail;
	}


	public void setTechnologyDetail(String technologyDetail) {
		this.technologyDetail = technologyDetail;
	}


	@Override
	public String toString() {
		return technologyDetail;
	}
	
	
	
	
}
