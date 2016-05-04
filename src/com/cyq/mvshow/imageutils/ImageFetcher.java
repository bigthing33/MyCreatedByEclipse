package com.cyq.mvshow.imageutils;

import com.cyq.mvshow.model.SearchImageRespone;


public interface ImageFetcher {
	public void searchImg(String searchText, int pageNum, int mPreNum,Boolean isCleanListView);
	public void setListener(ImageFetcherListener imageFetcherListener);
	public interface ImageFetcherListener {
		public void ImageFetcherSuccess(SearchImageRespone searchImageRespone,Boolean isCleanListView);
		public void ImageFetcherErro(String responseString);
	}

}
