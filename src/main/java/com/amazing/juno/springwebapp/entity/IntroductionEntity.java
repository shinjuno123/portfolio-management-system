package com.amazing.juno.springwebapp.entity;

public class IntroductionEntity {
	
	private String titleMain;

	private String myName;

	private String subTitle;

	private String detail;

	public IntroductionEntity() {
	}

	public IntroductionEntity(String titleMain, String myName, String subTitle, String detail) {
		this.titleMain = titleMain;
		this.myName = myName;
		this.subTitle = subTitle;
		this.detail = detail;
	}

	public String getTitleMain() {
		return titleMain;
	}

	public void setTitleMain(String titleMain) {
		this.titleMain = titleMain;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "IntroductionEntity [titleMain=" + titleMain + ", myName=" + myName + ", subTitle=" + subTitle
				+ ", detail=" + detail + "]";
	}

}
