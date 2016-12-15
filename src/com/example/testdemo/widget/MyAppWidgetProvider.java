package com.example.testdemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.testdemo.R;

/**
 * 这其实就是一个必须静态配置的广播接收器
 * @author Administrator
 *
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.ryg.chapter_5.action.CLICK";
    public MyAppWidgetProvider() {
        super();
    }
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Log.e(TAG, "onReceive : action ="+intent.getAction());
		
		if (intent.getAction().equals(CLICK_ACTION)) {
			//如果是自己的action，则做一个动画效果。
			Toast.makeText(context, "clike it", Toast.LENGTH_SHORT).show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Bitmap srcbBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.icon1);
					AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
					RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget);
					remoteViews.setImageViewBitmap(R.id.imageView1,srcbBitmap);
					Intent intentClick = new Intent(CLICK_ACTION);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
					remoteViews.setOnClickPendingIntent(R.id.imageView1,pendingIntent);
//					appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class),remoteViews);
//					for (int i = 0; i < 37; i++) {
//						float degree=(i*10)%360;
//						RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget);
//						remoteViews.setImageViewBitmap(R.id.imageView1, rotateBitmap(context, srcbBitmap, degree));
//						Intent intentClick = new Intent(CLICK_ACTION);
//						PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
//						remoteViews.setOnClickPendingIntent(R.id.imageView1,pendingIntent);
//						//这应该是更新操作吧
//						appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class),remoteViews);
//						SystemClock.sleep(30);
//					}
				}
			}).start();
			
			
		}
	}
    /**
     * 每次窗口小部件被点击更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate");

        final int counter = appWidgetIds.length;
        Log.e(TAG, "counter = " + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }

    }
    /**
     * 窗口小部件更新
     * 
     * @param context
     * @param appWidgeManger
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context,
            AppWidgetManager appWidgeManger, int appWidgetId) {

        Log.e(TAG, "appWidgetId = " + appWidgetId);
        //每次都应该重新实例化这个remoteViews
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
        // "窗口小部件"点击事件发送的Intent广播
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
    }
    private Bitmap rotateBitmap(Context context, Bitmap srcbBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcbBitmap, 0, 0,srcbBitmap.getWidth(), srcbBitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }
    

}
