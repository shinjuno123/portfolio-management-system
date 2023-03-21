package com.amazing.juno.springwebapp.dao;

import java.util.Map;

public interface SnsLinksDao {
	public Map<String, String> getLinks();
	public void setLinks(Map<String, String> links);
}
