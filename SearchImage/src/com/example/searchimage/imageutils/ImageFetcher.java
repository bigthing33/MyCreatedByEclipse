package com.example.searchimage.imageutils;

import com.example.searchimage.model.SearchImageRespone;

public interface ImageFetcher {
	public void searchImg(String searchText,int pageNum,int mPreNum,Boolean isCleanListView);
	public void setListener(ImageFetcherListener imageFetcherListener);
	public interface ImageFetcherListener{
		public void ImageFetcherSuccess(SearchImageRespone searchImageRespone,Boolean isCleanListView);
		public void ImageFetcherErro(String responseString);
	}

}
