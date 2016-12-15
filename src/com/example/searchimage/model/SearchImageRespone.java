package com.example.searchimage.model;

import java.util.ArrayList;

public class SearchImageRespone {
	private int TotalNumber;
	private int ReturnNumber;
	private ArrayList<Image> ResultArray;
	public int getTotalNumber() {
		return TotalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		TotalNumber = totalNumber;
	}
	public int getReturnNumber() {
		return ReturnNumber;
	}
	public void setReturnNumber(int returnNumber) {
		ReturnNumber = returnNumber;
	}
	public ArrayList<Image> getResultArray() {
		return ResultArray;
	}
	public void setResultArray(ArrayList<Image> resultArray) {
		ResultArray = resultArray;
	}
	@Override
	public String toString() {
		return "SearchImageRespone [TotalNumber=" + TotalNumber
				+ ", ReturnNumber=" + ReturnNumber + ", ResultArray="
				+ ResultArray + "]";
	}
	

}
