package com.xino.zyt.wifidemo;

import java.net.Inet4Address;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xino.zyt.wifidemo.util.WifiAdmin;
import com.xino.zyt.wifidemo.view.AbountMethodDialog;

public class MethodActivity extends Activity {

	/** Called when the activity is first created. */
	
	private Button suiji;
	private Button baoli;
	private Button shuju;
	private Button proMethod;
	private Button about;
	private WifiAdmin mWifiAdmin;
	// 扫描结果列表
	private List<ScanResult> list;
	private ScanResult mScanResult;
	private StringBuffer sb = new StringBuffer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWifiAdmin = new WifiAdmin(MethodActivity.this);
		init();
	}

	public void init() {	
		baoli = (Button) findViewById(R.id.baoli);
		shuju = (Button) findViewById(R.id.shuju);
		suiji = (Button) findViewById(R.id.suiji);
		proMethod = (Button) findViewById(R.id.proMethod);
		about = (Button) findViewById(R.id.about);
		baoli.setOnClickListener(new MyListener());
		shuju.setOnClickListener(new MyListener());
		suiji.setOnClickListener(new MyListener());
		proMethod.setOnClickListener(new MyListener());
		about.setOnClickListener(new MyListener());

	}

	private class MyListener implements OnClickListener {
		Intent intent = new Intent(MethodActivity.this,Break1Activity.class);
		Bundle mybBundle = new Bundle();
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.baoli:
				mybBundle.putInt("method", 1);
				intent.putExtras(mybBundle);
				startActivity(intent);
				break;
			case R.id.suiji:
				mybBundle.putInt("method", 2);
				intent.putExtras(mybBundle);
				startActivity(intent);
				break;
			case R.id.shuju:
				mybBundle.putInt("method", 3);
				intent.putExtras(mybBundle);
				startActivity(intent);
				break;
			case R.id.proMethod:
				mybBundle.putInt("method", 4);
				intent.putExtras(mybBundle);
				startActivity(intent);
				break;
			case R.id.about:
				AbountMethodDialog abountMethodDialog = new AbountMethodDialog(MethodActivity.this, R.style.PopDialog);
				abountMethodDialog.show();
			}

		}	
	}





}