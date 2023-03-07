package com.amazing.juno.springwebapp.dto;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.amazing.juno.springwebapp.entity.AboutEntity;
import com.amazing.juno.springwebapp.entity.ContactEntity;
import com.amazing.juno.springwebapp.entity.IntroductionEntity;
import com.amazing.juno.springwebapp.validator.IsEmpty;

public class IntegratedDto {
	@IsEmpty
	IntroductionEntity intro;

	@IsEmpty
	AboutEntity about;

	ContactEntity contact;
	Map<String, String> links;

	String techs;

	List<TechnologyListDto> convertedTechs;

	public IntegratedDto() {
		System.out.println("\n\n\n\n\n-----------");
		System.out.println("Initializing Integratedto");
		System.out.println("techs=" + techs);

		System.out.println("-----------\n\n\n\n\n");
	}

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public IntroductionEntity getIntro() {
		return intro;
	}

	public void setIntro(IntroductionEntity intro) {
		this.intro = intro;
	}

	public AboutEntity getAbout() {
		return about;
	}

	public void setAbout(AboutEntity about) {
		this.about = about;
	}

	public Map<String, String> getLinks() {
		return links;
	}

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	public String getTechs() {
		return techs;
	}

	private static List<TechnologyListDto> convertTechs(String tech) {
		Stack<Integer> indicesStack = new Stack<>();
		Stack<Character> bracketsStack = new Stack<>();
		
		for (int i = 0; i < tech.length(); i++) {
			
			bracketsStack.push(tech.charAt(i));
		}
	}

	public void setTechs(String techs) {
		System.out.println("\n\n\n\n\n-----------");
		System.out.println("set Techs");
		System.out.println("techs=" + techs);

		System.out.println("-----------\n\n\n\n\n");
		this.techs = techs;
		this.convertedTechs = convertTechs(techs);
	}

	@Override
	public String toString() {
		return "IntegratedDto [intro=" + intro + "\nabout=" + about + "\ncontact=" + contact + "\nlinks=" + links
				+ "\ntechs=" + techs + "]";
	}

}
