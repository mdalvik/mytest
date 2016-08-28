/*
 * @Copyright:           Copyright© 2007-2016 DTDS
 * @Company:             深圳市动态电子商务有限公司
 * @Project:             Tao_mobile
 * @see                  com.dtds.tsh_mobile.utils                              
 */
package com.weixin.zhongli.service;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;

import java.util.List;

public class LocationService {

	private Context mContext;
	private LocationClient mLocationClient = null;
	private MyLocationListener listener = null;
	private Object objLock = new Object();

	public LocationService(Context mContext) {
		this.mContext = mContext;
		initBaidu();
	}
	
	/**
	 * 停止定位
	 */
	public void stop(){
		if (null != listener){
			mLocationClient.unRegisterLocationListener(listener);
		}
		synchronized (objLock) {
			if(mLocationClient != null && mLocationClient.isStarted()){
				mLocationClient.stop();
			}
		}
	}

	private void initBaidu() {
		synchronized (objLock) {
			mLocationClient = new LocationClient(mContext);
			initLocation();
			listener = new MyLocationListener();
			mLocationClient.registerLocationListener(listener);
			if(!mLocationClient.isStarted()){
				mLocationClient.start();
			}
		}
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);		// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("gcj02");			// 可选，默认gcj02，设置返回的定位结果坐标系，
//		option.setScanSpan(10000);				// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);			// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);				// 可选，默认false,设置是否使用gps
		// option.setLocationNotify(true);		// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);		// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		// option.setEnableSimulateGps(false);	// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setIsNeedLocationDescribe(true);	// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);	// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * 实现实时位置回掉监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
//			L.e("------------------------longitude" + longitude);
//			L.e("------------------------latitude" + latitude);
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				// sb.append(location.getDirection());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");
				onSuccess(location);
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
				onSuccess(location);
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
				onSuccess(location);
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				onFail(location);
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
				onFail(location);
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				onFail(location);
			}
			sb.append("\nlocationdescribe : ");// 位置语义化信息
			sb.append(location.getLocationDescribe());
			List<Poi> list = location.getPoiList();// POI信息
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}

		private void onSuccess(BDLocation location) {
			if (null != mLocationFinishListener){
				mLocationFinishListener.OnLocationSuccess(location.getCity());
			}
		}

		private void onFail(BDLocation location) {
			if (null != mLocationFinishListener){
				mLocationFinishListener.OnLocationFail(location.getLocType());
			}
		}
	}
	
	private OnLocationFinishListener mLocationFinishListener = null;
	
	public void setOnLocationFinishListener(OnLocationFinishListener mLocationFinishListener) {
		this.mLocationFinishListener = mLocationFinishListener;
	}
	
	public interface OnLocationFinishListener {
		void OnLocationSuccess(String city);
		void OnLocationFail(int errorCode);
	}
}
