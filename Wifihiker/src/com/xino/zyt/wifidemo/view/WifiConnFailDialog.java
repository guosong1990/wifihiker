package com.xino.zyt.wifidemo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xino.zyt.wifidemo.Break1Activity;
import com.xino.zyt.wifidemo.MethodActivity;
import com.xino.zyt.wifidemo.R;
import com.xino.zyt.wifidemo.WifiListActivity;
import com.xino.zyt.wifidemo.customview.BreakView;
import com.xino.zyt.wifidemo.util.WifiAdmin;
import com.xino.zyt.wifidemo.util.WifiConnect.WifiCipherType;


public class WifiConnFailDialog extends Dialog {

	private Context context;


	private TextView txtWifiName;


	private TextView txtBtnConn;
	private TextView txtBtnCancel;

	public WifiConnFailDialog(Context context, int theme) {
		super(context, theme);
	}

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_wifi_connfail);
		setCanceledOnTouchOutside(false);

		initView();
		setListener();
	}

	private void setListener() {


		txtBtnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Break1Activity.instance,MethodActivity.class);
				Break1Activity.instance.startActivity(intent);
				WifiConnFailDialog.this.dismiss();
			}
		});

		txtBtnConn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BreakView.flag = true;
				BreakView.startTime = System.currentTimeMillis();
				new Thread(BreakView.instance).start();
				WifiConnFailDialog.this.dismiss();
			}
		});
	}

	private void initView() {
		txtWifiName = (TextView) findViewById(R.id.txt_wifi_name_fail);
	
		txtBtnCancel = (TextView) findViewById(R.id.txt_btn_cancel_fail);
		txtBtnConn = (TextView) findViewById(R.id.txt_btn_connect_fail);



	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		super.show();
		getWindow().setLayout((int) (size.x * 9 / 10),
				LayoutParams.WRAP_CONTENT);
	}

	private void showShortToast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}


}
