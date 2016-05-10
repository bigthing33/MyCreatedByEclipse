package com.cyq.mvshow.imageutils;

import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.db.TianGouImageDB;
import com.cyq.mvshow.listener.GetClassesListener;
import com.cyq.mvshow.listener.GetGalleriesListener;
import com.cyq.mvshow.listener.GetGallryDetailsListener;
import com.cyq.mvshow.model.GallryDetailsRespone;
import com.cyq.mvshow.model.GetGalleriesRespone;
import com.cyq.mvshow.model.GetGalleryclassRespone;
import com.cyq.mvshow.utils.DataUtil;
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.utils.MyUrl;
import com.cyq.mvshow.utils.SharedPreferencesManager;


public class ImageFetcherTianGouImp implements ImageFetcher {
	/**
	 * 获取最新图片
	 * @param id 热点热词的id，
	 * @param rows 返回图片个数
	 * @param classify 分类id
	 * @param isForHead 一个布尔值，会在listener的onSuccess方法里体现
	 * @param listener 对外的接口
	 * 会根据不同的id和分类id，在对外接口里提供不同的图片数据，这个方法里使用了Random来产生一个随机的id，分类id为0代表没有分类
	 */

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
/**
 * 
 * @param id 图片类型1-8
 * @param page 页数
 * @param rows 返回图片个数
 * @param isForHead
 * @param listener
 */
	public void getImageListByID(int id,  int page, int rows,final boolean isForHead, final GetGalleriesListener listener) {
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
	/**
	 * 根据相册id加载组图数据
	 * @param id
	 * @param listener
	 * @param isForHead
	 */
	public void getImageDetailsByID(final int id, final GetGallryDetailsListener listener, final boolean isForHead) {
		Parameters para = new Parameters();
		para.put("id", id + "");
		LogUtil.e("getImageDetailsByID", id+"");
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_AtlasDetails, ApiStoreSDK.GET, para,
				new ApiCallBack() {
			
			@Override
					public void onSuccess(int status, String responseString) {
						LogUtil.e(" getImageDetailsByID", "onSuccess:"+id );
						GallryDetailsRespone getGalleryListRespone = HandleResponse.handlevGetImageDetailsByID(responseString);
						listener.success(getGalleryListRespone, isForHead);
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
				GallryDetailsRespone loadGallryDetailsRespone = TianGouImageDB.getInstance(MyApplication.context).loadGallryDetailsRespone(id);
				if (loadGallryDetailsRespone!=null) {
					listener.success(loadGallryDetailsRespone, isForHead);
				}else {
					listener.erro(responseString);
				}
			}
		});
	}
	/**
	 * 根据相册id加载组图数据,并将数据保存到数据库中
	 * @param id
	 * @param listener
	 * @param isForHead
	 */
	public void getAndSaveImageDetailsByID(final int id, final GetGallryDetailsListener listener, final boolean isForHead) {
		Parameters para = new Parameters();
		para.put("id", id + "");
		LogUtil.e("getImageDetailsByID", id+"");
		ApiStoreSDK.execute(MyUrl.SEARCH_TIANGOU_AtlasDetails, ApiStoreSDK.GET, para,
				new ApiCallBack() {
			
			@Override
			public void onSuccess(int status, String responseString) {
				LogUtil.e(" getImageDetailsByID", "onSuccess:"+id );
				GallryDetailsRespone getGalleryListRespone = HandleResponse.handlevGetImageDetailsByID(responseString);
				// 保存或替换数据库数据
				TianGouImageDB.getInstance(MyApplication.context).saveGallryDetailsRespone(getGalleryListRespone);
				
				listener.success(getGalleryListRespone, isForHead);
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
				GallryDetailsRespone loadGallryDetailsRespone = TianGouImageDB.getInstance(MyApplication.context).loadGallryDetailsRespone(id);
				if (loadGallryDetailsRespone!=null) {
					listener.success(loadGallryDetailsRespone, isForHead);
				}else {
					listener.erro(responseString);
				}
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
