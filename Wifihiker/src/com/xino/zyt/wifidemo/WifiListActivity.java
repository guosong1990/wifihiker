package com.xino.zyt.wifidemo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import cn.waps.AppConnect;

import com.xino.yztwifidemo.ad.QuitPopAd;
import com.xino.zyt.wifidemo.adapter.MyListViewAdapter;
import com.xino.zyt.wifidemo.customview.MyListView;
import com.xino.zyt.wifidemo.customview.MyListView.OnRefreshListener;
import com.xino.zyt.wifidemo.util.WifiAdmin;
import com.xino.zyt.wifidemo.view.OnNetworkChangeListener;
import com.xino.zyt.wifidemo.view.WifiConnDialog;
import com.xino.zyt.wifidemo.view.WifiStatusDialog;

public class WifiListActivity extends Activity {

	protected static final String TAG = WifiListActivity.class.getSimpleName();

	private static final int REFRESH_CONN = 100;

	private static final int REQ_SET_WIFI = 200;

	// Wifi管理类
	private WifiAdmin mWifiAdmin;
	// 扫描结果列表
	private List<ScanResult> list = new ArrayList<ScanResult>();
	// 显示列表
	private MyListView listView;
	private ToggleButton tgbWifiSwitch;

	private MyListViewAdapter mAdapter;
	
	public static WifiListActivity instance;

	private OnNetworkChangeListener mOnNetworkChangeListener = new OnNetworkChangeListener() {

		@Override
		public void onNetWorkDisConnect() {
			getWifiListInfo();
			mAdapter.setDatas(list);
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public void onNetWorkConnect() {
			getWifiListInfo();
			mAdapter.setDatas(list);
			mAdapter.notifyDataSetChanged();
		}
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_wifi_list);
		initData();
		initView();
		setListener();

		refreshWifiStatusOnTime();
		instance = this;
		
		/*
		 * 万普广告
		 */
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).initPopAd(this);
		// 互动广告调用方式
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.AdLinearLayout);
		AppConnect.getInstance(this).showBannerAd(this, layout);
	}

	private void initData() {
		mWifiAdmin = new WifiAdmin(WifiListActivity.this);
		// 获得Wifi列表信息
		getWifiListInfo();
	}

	private void initView() {

		tgbWifiSwitch = (ToggleButton) findViewById(R.id.tgb_wifi_switch);
		listView = (MyListView) findViewById(R.id.freelook_listview);
		mAdapter = new MyListViewAdapter(this, list);
		listView.setAdapter(mAdapter);
		//
		int wifiState = mWifiAdmin.checkState();
		if (wifiState == WifiManager.WIFI_STATE_DISABLED
				|| wifiState == WifiManager.WIFI_STATE_DISABLING
				|| wifiState == WifiManager.WIFI_STATE_UNKNOWN) {
			tgbWifiSwitch.setChecked(false);
		} else {
			tgbWifiSwitch.setChecked(true);
		}
	}

	private void setListener() {

		tgbWifiSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					Log.w(TAG, "======== open wifi ========");
					// 打开Wifi
					mWifiAdmin.openWifi();
				} else {
					Log.w(TAG, "======== close wifi ========");
					// 关闭Wifi
					boolean res = mWifiAdmin.closeWifi();
					if (!res) {
						gotoSysCloseWifi();
					}
				}
			}
		});

		// 设置刷新监听
		listView.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {

				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						getWifiListInfo();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mAdapter.setDatas(list);
						mAdapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

				}.execute();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				int position = pos - 1;
				// String wifiName = list.get(position).SSID;
				// String singlStrength = "" + list.get(position).frequency;
				// String secuString = list.get(position).capabilities;
				
				
				ScanResult scanResult = list.get(position);
				
				
				if (mWifiAdmin.isConnect(scanResult)) {
					// 已连接，显示连接状态对话框
			
					WifiStatusDialog mStatusDialog = new WifiStatusDialog(
							WifiListActivity.this, R.style.PopDialog,
							scanResult, mOnNetworkChangeListener);
					mStatusDialog.show();
				} else {
					// 未连接显示连接输入对话框
	
					WifiConnDialog mDialog = new WifiConnDialog(
							WifiListActivity.this, R.style.PopDialog,
							scanResult, mOnNetworkChangeListener);
					mDialog.show();
					
				}
			}
		});
	}

	private void getWifiListInfo() {
		
		mWifiAdmin.startScan();
		List<ScanResult> tmpList = mWifiAdmin.getWifiList();
		if (tmpList == null) {
			list.clear();
		} else {
			list = tmpList;
		}
	}

	private Handler mHandler = new MyHandler(this);

	protected boolean isUpdate = true;

	private static class MyHandler extends Handler {

		private WeakReference<WifiListActivity> reference;

		public MyHandler(WifiListActivity activity) {
			this.reference = new WeakReference<WifiListActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {

			WifiListActivity activity = reference.get();

			switch (msg.what) {
			case REFRESH_CONN:
				activity.getWifiListInfo();
				activity.mAdapter.setDatas(activity.list);
				activity.mAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	}

	/**
	 * Function:定时刷新Wifi列表信息<br>
	 * 
	 * @author ZYT DateTime 2014-5-15 上午9:14:34<br>
	 * <br>
	 */
	private void refreshWifiStatusOnTime() {
		new Thread() {
			public void run() {
				while (isUpdate) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(REFRESH_CONN);
				}
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isUpdate = false;
		AppConnect.getInstance(this).close();
	}

	/**
	 * Function:到系统中设置wifi，如果用户手动关闭失败，跳转到系统中进行关闭Wifi<br>
	 * 
	 * @author ZYT DateTime 2014-5-15 上午10:03:15<br>
	 * <br>
	 */
	private void gotoSysCloseWifi() {
		// 05-15 09:57:56.351: I/ActivityManager(397): START
		// {act=android.settings.WIFI_SETTINGS flg=0x14000000
		// cmp=com.android.settings/.Settings$WifiSettingsActivity} from pid 572

		Intent intent = new Intent("android.settings.WIFI_SETTINGS");
		intent.setComponent(new ComponentName("com.android.settings",
				"com.android.settings.Settings$WifiSettingsActivity"));
		startActivityForResult(intent, REQ_SET_WIFI);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQ_SET_WIFI:
			// 处理改变wifi状态结果
			//
			int wifiState = mWifiAdmin.checkState();
			if (wifiState == WifiManager.WIFI_STATE_DISABLED
					|| wifiState == WifiManager.WIFI_STATE_DISABLING
					|| wifiState == WifiManager.WIFI_STATE_UNKNOWN) {
				tgbWifiSwitch.setChecked(false);
			} else {
				tgbWifiSwitch.setChecked(true);
			}
			break;

		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
				// 调用退屏广告
			QuitPopAd.getInstance().show(this);
		}
		return true;
	}
}
