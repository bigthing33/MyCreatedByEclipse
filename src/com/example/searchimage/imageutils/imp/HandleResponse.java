package com.example.searchimage.imageutils.imp;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.Galleryclassify;
import com.example.searchimage.model.GallryDetailsRespone;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.model.GetGalleryclassRespone;
import com.example.searchimage.model.Image;
import com.example.searchimage.model.SearchImageRespone;
import com.google.gson.Gson;

public class HandleResponse {
	public static SearchImageRespone handleSearchImag(String response, String searchTag) {
		SearchImageRespone searchImageRespone = new SearchImageRespone();
		try {
			JSONObject jsonObjiect = new JSONObject(response);
			JSONObject status = jsonObjiect.getJSONObject("status");
			if (!status.getString("code").equals("0")) {
				// 如果返回码不正确则直接返回，不执行下面的操作
				return null;
			}

			JSONObject data = jsonObjiect.getJSONObject("data");
			searchImageRespone = new SearchImageRespone();
			searchImageRespone.setReturnNumber(data.getInt("ReturnNumber"));
			searchImageRespone.setTotalNumber(data.getInt("TotalNumber"));
			JSONArray resultArray = data.getJSONArray("ResultArray");
			ArrayList<Image> images = new ArrayList<Image>();
			
			JSONObject imgaeJsonObject;
			for (int i = 0; i < resultArray.length(); i++) {
				Image image = new Image();
				imgaeJsonObject = resultArray.getJSONObject(i);
				image.setKey(imgaeJsonObject.getString("Key"));
				image.setObjUrl(imgaeJsonObject.getString("ObjUrl"));
				image.setFromUrl(imgaeJsonObject.getString("FromUrl"));
				image.setPictype(imgaeJsonObject.getString("Pictype"));
				image.setDesc(imgaeJsonObject.getString("Desc"));
				image.setSearchTag(searchTag);
				image.setSearchTime(System.currentTimeMillis());
				images.add(image);
			}
			searchImageRespone.setResultArray(images);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			return searchImageRespone;
	}
	public static GetGalleryclassRespone handleGetGalleryclass(String responseString) {
		GetGalleryclassRespone getGalleryclassRespone=new GetGalleryclassRespone();
		try {
			JSONObject jsonObjiect = new JSONObject(responseString);
			getGalleryclassRespone.setStatus(jsonObjiect.getBoolean("status"));
			JSONArray jsonArray = jsonObjiect.getJSONArray("tngou");
			ArrayList<Galleryclassify> galleryclasses = new ArrayList<Galleryclassify>();
			
			JSONObject classifyJsonObject;
			for (int i = 0; i < jsonArray.length(); i++) {
				Galleryclassify galleryclassify = new Galleryclassify();
				classifyJsonObject = jsonArray.getJSONObject(i);
				galleryclassify.setDescription(classifyJsonObject.getString("description"));
				galleryclassify.setId(classifyJsonObject.getInt("id"));
				galleryclassify.setKeywords(classifyJsonObject.getString("keywords"));
				galleryclassify.setName(classifyJsonObject.getString("name"));
				galleryclassify.setSeq(classifyJsonObject.getInt("seq"));
				galleryclassify.setTitle(classifyJsonObject.getString("title"));
				galleryclasses.add(galleryclassify);
			}
			getGalleryclassRespone.setTngou(galleryclasses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGalleryclassRespone;
	}
	public static GallryDetailsRespone handlevGetImageDetailsByID(String responseString) {
		GallryDetailsRespone gallryDetailsRespone=new Gson().fromJson(responseString, GallryDetailsRespone.class);
		return gallryDetailsRespone;
	}
	public static GetGalleriesRespone handleGetGalleries(
			String responseString) {
		GetGalleriesRespone getGalleryListRespone=new GetGalleriesRespone();
		try {
			JSONObject jsonObjiect = new JSONObject(responseString);
			getGalleryListRespone.setStatus(jsonObjiect.getBoolean("status"));
			getGalleryListRespone.setTotal(jsonObjiect.getInt("total"));
			JSONArray jsonArray = jsonObjiect.getJSONArray("tngou");
			CopyOnWriteArrayList<Gallery> galleries = new CopyOnWriteArrayList<Gallery>();
			JSONObject galleryJsonObject;
			for (int i = 0; i < jsonArray.length(); i++) {
				Gallery gallery = new Gallery();
				galleryJsonObject = jsonArray.getJSONObject(i);
				gallery.setCount(galleryJsonObject.getInt("count"));
				gallery.setFcount(galleryJsonObject.getInt("fcount"));
				gallery.setGalleryclass(galleryJsonObject.getInt("galleryclass"));
				gallery.setId(galleryJsonObject.getInt("id"));
				gallery.setImg(galleryJsonObject.getString("img"));
				gallery.setRcount(galleryJsonObject.getInt("rcount"));
				gallery.setSize(galleryJsonObject.getInt("size"));
				gallery.setTitle(galleryJsonObject.getString("title"));
				galleries.add(gallery);
			}
			getGalleryListRespone.setTngou(galleries);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGalleryListRespone;
	}

}
