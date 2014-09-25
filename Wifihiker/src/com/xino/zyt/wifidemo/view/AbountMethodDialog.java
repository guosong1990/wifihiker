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
import com.xino.zyt.wifidemo.R;
import com.xino.zyt.wifidemo.WifiListActivity;
import com.xino.zyt.wifidemo.util.WifiAdmin;
import com.xino.zyt.wifidemo.util.WifiConnect.WifiCipherType;


public class AbountMethodDialog extends Dialog {

	private Context context;




	private TextView mingbai;

	public AbountMethodDialog(Context context, int theme) {
		super(context, theme);
	}

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_wifi_aboutmethod);
		setCanceledOnTouchOutside(false);

		initView();
		setListener();
	}

	private void setListener() {


		mingbai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("txtBtnCancel");
				AbountMethodDialog.this.dismiss();
			}
		});


	}

	private void initView() {
	
		mingbai = (TextView) findViewById(R.id.txt_btn_mingbai);


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
