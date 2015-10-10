package com.baiqiz.project1.adapter;

import com.baiqiz.project1.model.Automotive;

public interface AutoServer {
	public void insertAutoIntoHashMap(Automotive automotive);
	public String getAutoList();
	public Automotive getAutoFromList(String name);
}
