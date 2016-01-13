package com.example.searchimage.imageutils.imp;

import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.searchimage.imageutils.GetClassesListener;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.imageutils.ImageFetcher;
import com.example.searchimage.model.GetGalleryListRespone;
import com.example.searchimage.model.GetGalleryclassRespone;
import com.example.searchimage.utils.MyUrl;

public class ImageFetcherTianGouImp implements ImageFetcher {


	@Override
	public void searchImg(String searchText, int pageNum, int mPreNum,
			Boolean isCleanListView) {

	}
	public void searchImgClassify(final GetClassesListener listener){
		Parameters para = new Parameters();
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_CLASSIFY, ApiStoreSDK.GET, para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
						Log.e("SEARCH_TIANGOU_CLASSIFY", "onSuccess");
						Log.e("SEARCH_TIANGOU_CLASSIFY", responseString);
						GetGalleryclassRespone getGalleryclassRespone = HandleResponse.handleGetGalleryclass(responseString);
						listener.success(getGalleryclassRespone);
					}
					@Override
					public void onComplete() {
						Log.e("searchImgClassify", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("searchImgClassify", "onError, status: " + status);
						Log.e("searchImgClassify","errMsg: " + (e == null ? "" : e.getMessage()));
						listener.erro(responseString);
					}
				});
	}


	@Override
	public void setListener(ImageFetcherListener imageFetcherListener) {
		// TODO Auto-generated method stub

	}
	public void getImageListByID(int id,int page,int rows,final GetGalleriesListener listener) {
		Parameters para = new Parameters();
		para.put("id", id + "");
		para.put("page", page + "");// 返回的页数
		para.put("rows", rows + "");// 返回的图片数量
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_LIST, ApiStoreSDK.GET, para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
						Log.e("SEARCH_TIANGOU_CLASSIFY", "onSuccess");
						Log.e("SEARCH_TIANGOU_CLASSIFY", responseString);
						GetGalleryListRespone getGalleryListRespone = HandleResponse.handleGetGalleryList(responseString);
						listener.success(getGalleryListRespone);
					}
					@Override
					public void onComplete() {
						Log.e("sdkdemo", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("sdkdemo", "onError, status: " + status);
						Log.e("sdkdemo","errMsg: " + (e == null ? "" : e.getMessage()));
						listener.erro(responseString);
					}
				});
		
	}

}
