package com.xino.zyt.wifidemo.adapter;

import java.util.List;

import com.xino.zyt.wifidemo.R;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {

	private List<ScanResult> datas;
	private Context context;
	// 取得WifiManager对象
	private WifiManager mWifiManager;
	private WifiInfo connInfo;

	public void setDatas(List<ScanResult> datas) {
		this.datas = datas;
		connInfo = mWifiManager.getConnectionInfo();
	}

	public MyListViewAdapter(Context context, List<ScanResult> datas) {
		super();
		this.datas = datas;
		this.context = context;
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		connInfo = mWifiManager.getConnectionInfo();
	}

	@Override
	public int getCount() {
		if (datas == null) {
			return 0;
		}
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.wifi_info_item, null);
			Holder tag = new Holder();
			tag.txtWifiName = (TextView) convertView
					.findViewById(R.id.txt_wifi_name);
			tag.txtWifiDesc = (TextView) convertView
					.findViewById(R.id.txt_wifi_desc);
			tag.imgWifiLevelIco = (ImageView) convertView
					.findViewById(R.id.img_wifi_level_ico);
			convertView.setTag(tag);
		}

		// 设置数据
		Holder holder = (Holder) convertView.getTag();
		// Wifi 名字
		holder.txtWifiName.setText(datas.get(position).SSID);
		// Wifi 描述
		String desc = "";
		String descOri = datas.get(position).capabilities;
		if (descOri.toUpperCase().contains("WPA-PSK")) {
			desc = "WPA";
		}
		if (descOri.toUpperCase().contains("WPA2-PSK")) {
			desc = "WPA2";
		}
		if (descOri.toUpperCase().contains("WPA-PSK")
				&& descOri.toUpperCase().contains("WPA2-PSK")) {
			desc = "WPA/WPA2";
		}

		if (TextUtils.isEmpty(desc)) {
			desc = "未受保护的网络";
		} else {
			desc = "通过 " + desc + " 进行保护";
		}

		// 是否连接，如果刚刚断开连接，connInfo.SSID==null
		connInfo = mWifiManager.getConnectionInfo();
		String connSSID = "";
		if(connInfo!=null){
			int end  = connInfo.getSSID().length();
			connSSID = connInfo.getSSID().substring(1, end-1);
		}
		
		
		if (connInfo != null && !TextUtils.isEmpty(connInfo.getSSID())
				&& !TextUtils.isEmpty(datas.get(position).SSID)
				&& connSSID.equals(datas.get(position).SSID)) {
			System.out.println("getView() ==== " + position);
			desc = "已连接";
		}

		holder.txtWifiDesc.setText(desc);

		// 网络信号强度
		int level = datas.get(position).level;
		int imgId = R.drawable.wifi05;
		if (Math.abs(level) > 100) {
			imgId = R.drawable.wifi05;
		} else if (Math.abs(level) > 80) {
			imgId = R.drawable.wifi04;
		} else if (Math.abs(level) > 70) {
			imgId = R.drawable.wifi04;
		} else if (Math.abs(level) > 60) {
			imgId = R.drawable.wifi03;
		} else if (Math.abs(level) > 50) {
			imgId = R.drawable.wifi02;
		} else {
			imgId = R.drawable.wifi01;
		}
		holder.imgWifiLevelIco.setImageResource(imgId);
		return convertView;
	}

	public static class Holder {
		public TextView txtWifiName;
		public TextView txtWifiDesc;
		public ImageView imgWifiLevelIco;
	}

}