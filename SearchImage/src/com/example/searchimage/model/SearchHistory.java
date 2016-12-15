package com.example.searchimage.model;

import java.io.Serializable;

/**
 * 
 * @author caiyq@lzt.com.cn
 *
 */

public class SearchHistory implements Serializable  {
	
	private static final long serialVersionUID = -5756484271125712047L;
	private String name;
	private int sort;

	public SearchHistory() {
		super();
	}

	public SearchHistory(String name, int sort) {
		super();
		this.name = name;
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
