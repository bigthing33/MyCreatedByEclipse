package com.example.searchimage.imageutils.imp;

import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.searchimage.imageutils.ImageFetcher;
import com.example.searchimage.listener.GetClassesListener;
import com.example.searchimage.listener.GetGalleriesListener;
import com.example.searchimage.listener.GetGallryDetailsListener;
import com.example.searchimage.model.GallryDetailsRespone;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.model.GetGalleryclassRespone;
import com.example.searchimage.utils.DataUtil;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyUrl;
import com.example.searchimage.utils.SharedPreferencesManager;

public class ImageFetcherTianGouImp implements ImageFetcher {

	public void getGallriesNews( int id, int rows, int classify,final Boolean isForHead,final GetGalleriesListener listener) {
		Parameters para = new Parameters();
		id=DataUtil.getRandomInt(550);
		para.put("id", id + "");//long	当前最新的图库关键词id
		para.put("classify", classify + "");// 图库分类id
		para.put("rows", rows + "");// 返回的图片数量
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_AtlasNews, ApiStoreSDK.GET, para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
//						Log.e("SEARCH_TIANGOU_AtlasNews", "onSuccess");
						Log.e("SEARCH_TIANGOU_AtlasNews", responseString);
						GetGalleriesRespone getGalleryListRespone = HandleResponse.handleGetGalleries(responseString);
						//如果是第一页的请求，则保存数据到sharepreference
						if (isForHead) {
							SharedPreferencesManager.saveNews(responseString);
						}
						listener.success(getGalleryListRespone,isForHead);
					}

					@Override
					public void onComplete() {
						Log.e("SEARCH_TIANGOU_AtlasNews", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("SEARCH_TIANGOU_AtlasNews", "onError, status: " + status);
						Log.e("SEARCH_TIANGOU_AtlasNews","errMsg: " + (e == null ? "" : e.getMessage()));
						listener.erro(responseString);
					}
				});

	}

	public void getImageListByID(int id,  int page, int rows,
			final boolean isForHead, final GetGalleriesListener listener) {
		Parameters para = new Parameters();
		if (isForHead) {
			para.put("page", 1+ "");// 返回的页数
		}else {
			
			para.put("page", page + "");// 返回的页数
		}
		para.put("id", id + "");
		para.put("rows", rows + "");// 返回的图片数量
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_LIST, ApiStoreSDK.GET, para,
				new ApiCallBack() {

					@Override
					public void onSuccess(int status, String responseString) {
//						Log.e("SEARCH_TIANGOU_CLASSIFY", "onSuccess");
//						Log.e("SEARCH_TIANGOU_CLASSIFY", responseString);
						GetGalleriesRespone getGalleryListRespone = HandleResponse.handleGetGalleries(responseString);
						listener.success(getGalleryListRespone,isForHead);
					}

					@Override
					public void onComplete() {
						Log.e("SEARCH_TIANGOU_LIST", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("SEARCH_TIANGOU_LIST", "onError, status: " + status);
						Log.e("SEARCH_TIANGOU_LIST","errMsg: " + (e == null ? "" : e.getMessage()));
						listener.erro(responseString);
					}
				});
	}
	public void getImageDetailsByID(int id, final GetGallryDetailsListener listener, final boolean isForHead) {
		Parameters para = new Parameters();
		para.put("id", id + "");
		LogUtil.e("getImageDetailsByID", id+"");
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_AtlasDetails, ApiStoreSDK.GET, para,
				new ApiCallBack() {
			
			@Override
			public void onSuccess(int status, String responseString) {
//						Log.e("SEARCH_TIANGOU_CLASSIFY", "onSuccess");
//				Log.e("SEARCH_TIANGOU_CLASSIFY", responseString);
			GallryDetailsRespone getGalleryListRespone = HandleResponse.handlevGetImageDetailsByID(responseString);
				listener.success(getGalleryListRespone,isForHead);
			}
			
			@Override
			public void onComplete() {
				Log.e("SEARCH_TIANGOU_LIST", "onComplete");
			}
			
			@Override
			public void onError(int status, String responseString,
					Exception e) {
				Log.e("SEARCH_TIANGOU_LIST", "onError, status: " + status);
				Log.e("SEARCH_TIANGOU_LIST","errMsg: " + (e == null ? "" : e.getMessage()));
				listener.erro(responseString);
			}
		});
	}

	public void getImgClassify(final GetClassesListener listener){
		Parameters para = new Parameters();
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_CLASSIFY, ApiStoreSDK.GET, para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
//						Log.e("SEARCH_TIANGOU_CLASSIFY", "onSuccess");
						Log.e("SEARCH_TIANGOU_CLASSIFY", responseString);
						GetGalleryclassRespone getGalleryclassRespone = HandleResponse.handleGetGalleryclass(responseString);
						//保存数据到sharepreference
						SharedPreferencesManager.saveClassies(responseString);
						listener.success(getGalleryclassRespone);
					}
					@Override
					public void onComplete() {
//						Log.e("searchImgClassify", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("SEARCH_TIANGOU_CLASSIFY", "onError, status: " + status);
						Log.e("SEARCH_TIANGOU_CLASSIFY","errMsg: " + (e == null ? "" : e.getMessage()));
						listener.erro(responseString);
					}
				});
	}


	@Override
	public void setListener(ImageFetcherListener imageFetcherListener) {

	}
	@Override
	public void searchImg(String searchText, int pageNum, int mPreNum,
			Boolean isCleanListView) {

	}

}
