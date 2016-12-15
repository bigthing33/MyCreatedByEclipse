package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpCallBackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

public class ChooseAreaActivity extends Activity {
	public static final int LEVEL_PROVINCE=0;
	public static final int LEVEL_CITY=1;
	public static final int LEVEL_COUNTY=2;
	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList=new ArrayList<String>();
	/**
	 * ʡ�б�
	 */
	private List<Province> provinceList;
	/**
	 * ���б�
	 */
	private List<City> cityList;
	/**
	 * ʡ�б�
	 */
	private List<County> countyList;
	/**
	 * ѡ�е�ʡ��
	 */
	private Province selectedProvince;
	/**
	 * ѡ�еĳ���
	 */
	private City selectedCity;
	/**
	 * ѡ�еļ���
	 */
	private int currentLevel;
	/**
	 * �Ƿ��WeatherAvctivity����ת������
	 */
	private boolean isFromWeatherActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isFromWeatherActivity=getIntent().getBooleanExtra("from_weather_activity", false);
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		
		if(prefs.getBoolean("city_selected", false)&&!isFromWeatherActivity){
			Intent intent=new Intent(this, WeatherActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView=(ListView) findViewById(R.id.content_lv);
		titleText=(TextView) findViewById(R.id.title_tv);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(currentLevel==LEVEL_PROVINCE){
					selectedProvince=provinceList.get(position);
					queryCities();
				}else if(currentLevel==LEVEL_CITY){
					selectedCity=cityList.get(position);
					queryCounties();
				}else if(currentLevel==LEVEL_COUNTY){
					String countyCode=countyList.get(position).getCountyCode();
					Intent intent=new Intent(ChooseAreaActivity.this, WeatherActivity.class);
					intent.putExtra("county_code", countyCode);
					startActivity(intent);
					finish();
				}
				
			}

		
		});
		queryProvinces();
	}
	/**
	 * ��ѯȫ�����е�ʡ�����ȴ����ݿ��в�ѯ�����û���ٵ��������ϲ�ѯ
	 */
	private void queryProvinces() {
		provinceList=coolWeatherDB.loadProvince();
		if(provinceList.size()>0){
			dataList.clear();
			for(Province p: provinceList){
				dataList.add(p.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("�й�");
			currentLevel=LEVEL_PROVINCE;
		}else{
			//����������ѯ�ķ���
			queryFromServer(null,"province");
		}
		
	}
	/**
	 * ��ѯѡ�е�ʡ���У����ȴ����ݿ��в�ѯ�����û���ٵ��������ϲ�ѯ
	 */
	private void queryCities() {
		cityList=coolWeatherDB.loadCity(selectedProvince.getId());
		if(cityList.size()>0){
			dataList.clear();
			for(City c: cityList){
				dataList.add(c.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel=LEVEL_CITY;
		}else{
			//����������ѯ�ķ���
			queryFromServer(selectedProvince.getProvinceCode(),"city");
		}
		
	}
	/**
	 * ��ѯѡ�е��е��أ����ȴ����ݿ��в�ѯ�����û���ٵ��������ϲ�ѯ
	 */
	private void queryCounties() {
		countyList=coolWeatherDB.loadCounty(selectedCity.getId());
		if(countyList.size()>0){
			dataList.clear();
			for(County c: countyList){
				dataList.add(c.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel=LEVEL_COUNTY;
		}else{
			//����������ѯ�ķ���
			queryFromServer(selectedCity.getCityCode(),"county");
		}
		
	}
	/**
	 * ���ݴ���Ĵ��ź����ʹӷ������ϲ�ѯʡ���ص�����
	 */
	public void queryFromServer(final String code,final String type){
		String address;
		if(!TextUtils.isEmpty(code)){
			address="http://www.weather.com.cn/data/list3/city"+code+".xml";
		}else{
			address="http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
			
			@Override
			public void onFinish(String response) {
				boolean result=false;
				if("province".equals(type)){
					result=Utility.handleProvincesResponse(coolWeatherDB, response);
				}else if("city".equals(type)){
					result=Utility.handleCitiesResponse(coolWeatherDB, response,selectedProvince.getId());
					
				}else if("county".equals(type)){
					result=Utility.handleConutiesResponse(coolWeatherDB, response,selectedCity.getId());
					
				}
				if(result){
					//ͨ��runOnUiThread()�����ص����̴߳����߼�
					runOnUiThread(new  Runnable() {
						public void run() {
							closeProgressDialog();
							if("province".equals(type)){
								queryProvinces();
							}else if("city".equals(type)){
								queryCities();
							}else if("county".equals(type)){
								queryCounties();
							}
						}
					});
					
				}
				
			}
			
			@Override
			public void onErro(Exception e) {
				runOnUiThread(new  Runnable() {
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "����ʧ��", 1).show();
					}
				});
				
			}
		});
		
	}
	/**
	 * ��ʾ���ȶԻ���
	 */
	private void showProgressDialog() {
		if(progressDialog==null){
			progressDialog=new ProgressDialog(this);
			progressDialog.setMessage("���ڼ���");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	/**
	 * �رնԻ���
	 */
	private void closeProgressDialog() {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		
	}
	@Override
	public void onBackPressed() {
		if(currentLevel==LEVEL_COUNTY){
			queryCities();
		}else if(currentLevel==LEVEL_CITY){
			queryProvinces();
		}else{
			if(isFromWeatherActivity){
				Intent intent=new Intent(this,WeatherActivity.class);
				startActivity(intent);
			}
			finish();
		}
	}
	
	
	

}
