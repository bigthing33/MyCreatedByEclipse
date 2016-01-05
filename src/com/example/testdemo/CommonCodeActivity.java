package com.example.testdemo;

import java.lang.reflect.Field;
import java.util.Properties;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyConstants;
import com.example.testdemo.utils.MyUtils;

public class CommonCodeActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = CommonCodeActivity.class.getName();
	private Context mContext = CommonCodeActivity.this;
	private EditText edittext;
	private Button getDeviceInfo;
	private Button hasSDCard;
	private Button showkeyboard;
	private Button hidekeyboard;
	private Button goHome;
	private Button getStatusBarHeight;
	private Button getTopBarHeight;
	private Button getPhoneType;
	private Button getNetworkOperatorName;
	private Button getNetWorkClass;
	private Button getNetWorkStatus;
	private Button getScreenInfo;
	private Button getMetaData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoncode);
		initUI();

//		hideSoftInput(this);已经在清单文件中设置了
	}
	

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void initUI() {
		edittext = (EditText) findViewById(R.id.edittext);
		getDeviceInfo = (Button) findViewById(R.id.getDeviceInfo);
		hasSDCard = (Button) findViewById(R.id.hasSDCard);
		hidekeyboard = (Button) findViewById(R.id.hidekeyboard);
		showkeyboard = (Button) findViewById(R.id.showkeyboard);
		goHome = (Button) findViewById(R.id.goHome);
		getStatusBarHeight = (Button) findViewById(R.id.getStatusBarHeight);
		getTopBarHeight = (Button) findViewById(R.id.getTopBarHeight);
		getPhoneType = (Button) findViewById(R.id.getPhoneType);
		getNetWorkStatus = (Button) findViewById(R.id.getNetWorkStatus);
		getNetworkOperatorName = (Button) findViewById(R.id.getNetworkOperatorName);
		getNetWorkClass = (Button) findViewById(R.id.getNetWorkClass);
		getScreenInfo = (Button) findViewById(R.id.getScreenInfo);
		getMetaData = (Button) findViewById(R.id.getMetaData);
		getScreenInfo.setOnClickListener(this);
		getMetaData.setOnClickListener(this);
		getNetworkOperatorName.setOnClickListener(this);
		getPhoneType.setOnClickListener(this);
		getNetWorkClass.setOnClickListener(this);
		getTopBarHeight.setOnClickListener(this);
		getDeviceInfo.setOnClickListener(this);
		getNetWorkStatus.setOnClickListener(this);
		hasSDCard.setOnClickListener(this);
		showkeyboard.setOnClickListener(this);
		hidekeyboard.setOnClickListener(this);
		goHome.setOnClickListener(this);
		getStatusBarHeight.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.getDeviceInfo:
			Log.d(TAG, "onClick R.id.getDeviceInfo:");
			Toast.makeText(mContext, collectDeviceInfo(mContext).toString(),
					Toast.LENGTH_LONG).show();
			break;
		case R.id.hasSDCard:
			Log.d(TAG, "onClick R.id.gestureDetectortest:");
			Toast.makeText(mContext, haveSDCard() ? "yes" : "not",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.showkeyboard:
			showSoftInput(mContext, edittext);
			Log.d(TAG, "onClick R.id.showkeyboard:");
			break;
		case R.id.hidekeyboard:
			// hideSoftInput(this);
			// hideSoftInput(this,edittext);
			toggleSoftInput(this, edittext);
			Log.d(TAG, "onClick R.id.hidekeyboard:");
			break;
		case R.id.goHome:
			goHome(mContext);
			Log.d(TAG, "onClick R.id.goHome:");
			break;
		case R.id.getStatusBarHeight:
			getStatusBarHeight(this);
			edittext.setText(getStatusBarHeight(this) + "");
			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;
		case R.id.getTopBarHeight:
			edittext.setText(getTopBarHeight(this) + "");
			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;
		case R.id.getPhoneType:
			/**
			 * * 11.返回移动终端类型 PHONE_TYPE_NONE :0 手机制式未知 PHONE_TYPE_GSM :1
			 * 手机制式为GSM，移动和联通 PHONE_TYPE_CDMA :2 手机制式为CDMA，电信 PHONE_TYPE_SIP:3
			 */
			String phone_typeString;
			switch (getPhoneType(this)) {
			case 0:
				phone_typeString = "手机制式未知 ";
				break;
			case 1:
				phone_typeString = "手机制式为GSM，移动和联通";
				break;
			case 2:
				phone_typeString = "手机制式为CDMA，电信 ";
				break;

			default:
				phone_typeString = "获取失败";
				break;
			}
			Toast.makeText(mContext, phone_typeString, Toast.LENGTH_LONG)
					.show();
			edittext.setText(getPhoneType(this) + "");

			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;
		case R.id.getNetworkOperatorName:

			edittext.setText(getNetworkOperatorName(this) + "");
			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;
		case R.id.getNetWorkClass:
			edittext.setText(getNetWorkClass(this) + "");
			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;
		case R.id.getScreenInfo:
			int[] screenInfo = MyUtils.getScreenInfo(mContext);
			showToast("屏幕的高："+screenInfo[1]+"屏幕的宽"+screenInfo[0]);
			break;
		case R.id.getMetaData:
			String appMetaData_Int = (String) MyUtils.getAppMetaData(mContext, "metaTest");
				showToast(appMetaData_Int+"");
			break;
		case R.id.getNetWorkStatus:
			String netWorkStatus = null;
			switch (getNetWorkStatus(this)) {
			case 0:
				netWorkStatus = "未知 ";
				break;
			case MyConstants.NETWORK_WIFI:
				netWorkStatus = "wifi";
				break;
			case MyConstants.NETWORK_CLASS_2_G:
				netWorkStatus = "2G ";
				break;
			case MyConstants.NETWORK_CLASS_3_G:
				netWorkStatus = "3G ";
				break;
			case MyConstants.NETWORK_CLASS_4_G:
				netWorkStatus = "4G ";
				break;
			default:
				netWorkStatus = "获取失败";
				break;
			}
			Toast.makeText(mContext, netWorkStatus + "", Toast.LENGTH_LONG)
					.show();
			edittext.setText(getNetWorkStatus(this) + "");
			Log.d(TAG, "onClick R.id.getStatusBarHeight:");
			break;

		default:
			break;
		}
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, CommonCodeActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 获得设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static Properties collectDeviceInfo(Context context) {
		Properties mDeviceCrashInfo = new Properties();
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(MyConstants.VERSION_NAME,
						pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(MyConstants.VERSION_CODE, pi.versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}
		}

		return mDeviceCrashInfo;
	}

	/**
	 * 判断是否有SD卡
	 * 
	 * @return
	 */
	public static boolean haveSDCard() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 动态显示软键盘
	 * 
	 * @param context
	 * @param edit
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void showSoftInput(Context context, EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(edit, 0);
	}

	/**
	 * 动态隐藏软件盘
	 * 
	 * @param activity
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void hideSoftInput(Activity activity) {
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void hideSoftInput(Context context, EditText edit) {
		edit.clearFocus();
		InputMethodManager inputmanger = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}

	/**
	 * 动态显示或隐藏软件盘
	 * 
	 * @param context
	 * @param edit
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void toggleSoftInput(Context context, EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 主动回到Home，后台运行
	 * 
	 * @param context
	 */
	public static void goHome(Context context) {
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
		mHomeIntent.addCategory(Intent.CATEGORY_HOME);
		mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		context.startActivity(mHomeIntent);
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param activity
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}

	/**
	 * 获取状态栏高度＋标题栏(ActionBar)高度//TODO
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTopBarHeight(Activity activity) {
		return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
	}

	/**
	 * 11.返回移动终端类型 PHONE_TYPE_NONE :0 手机制式未知 PHONE_TYPE_GSM :1 手机制式为GSM，移动和联通
	 * PHONE_TYPE_CDMA :2 手机制式为CDMA，电信 PHONE_TYPE_SIP:3
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getPhoneType();
	}

	/**
	 * 返回移动网络运营商的名字 (例：中国联通、中国移动、中国电信) 仅当用户已在网络注册时有效, CDMA 可能会无效)
	 */
	public static String getNetworkOperatorName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getNetworkOperatorName();
	}

	/**
	 * 判断手机连接的网络类型(2G,3G,4G)
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetWorkClass(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return MyConstants.NETWORK_CLASS_2_G;

		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
		case TelephonyManager.NETWORK_TYPE_EHRPD:
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return MyConstants.NETWORK_CLASS_3_G;

		case TelephonyManager.NETWORK_TYPE_LTE:
			return MyConstants.NETWORK_CLASS_4_G;

		default:
			return MyConstants.NETWORK_CLASS_UNKNOWN;
		}
	}

	/**
	 * 13.判断当前手机的网络类型(WIFI还是2,3,4G) 需要用到上面的方法:
	 */

	public static int getNetWorkStatus(Context context) {
		int netWorkType = MyConstants.NETWORK_CLASS_UNKNOWN;

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();

			if (type == ConnectivityManager.TYPE_WIFI) {
				netWorkType = MyConstants.NETWORK_WIFI;
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				netWorkType = getNetWorkClass(context);
			}
		}

		return netWorkType;
	}
}
