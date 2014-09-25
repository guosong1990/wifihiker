package com.xino.zyt.wifidemo;


import com.xino.zyt.wifidemo.customview.BreakView;
import com.xino.zyt.wifidemo.view.WifiConnDialog;
import com.xino.zyt.wifidemo.view.WifiConnFailDialog;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;



public class Break1Activity extends Activity {

	public static int level = 1;
	public Handler handler;
	public static Break1Activity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(new BreakView(this));
		handler = new MyHandler();
		Bundle mybBundle = getIntent().getExtras();
		level = mybBundle.getInt("method");
	}
	

	
	class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			WifiConnFailDialog wifiConnFailDialog = new WifiConnFailDialog(instance, R.style.PopDialog);
			wifiConnFailDialog.show();
		}
		
	}
}
