package com.xino.zyt.wifidemo;

import com.xino.zyt.wifidemo.customview.FailView;

import android.app.Activity;
import android.os.Bundle;

public class TryFailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(new FailView(this));
		
	}
		
}
