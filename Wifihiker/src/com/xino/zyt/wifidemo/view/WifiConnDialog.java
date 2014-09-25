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
import com.xino.zyt.wifidemo.util.WifiAdmin;
import com.xino.zyt.wifidemo.util.WifiConnect.WifiCipherType;

/**
 * Class Name: WifiConnDialog.java<br>
 * Function:Wifi连接对话框<br>
 * 
 * Modifications:<br>
 * 
 * @author ZYT DateTime 2014-5-14 下午2:23:37<br>
 * @version 1.0<br>
 * <br>
 */
public class WifiConnDialog extends Dialog {

	private Context context;

	private ScanResult scanResult;
	private String wifiName;
	private int level;
	private String securigyLevel;

	private TextView txtWifiName;
	private TextView txtSinglStrength;
	private TextView txtSecurityLevel;
	//private EditText edtPassword;
	//private CheckBox cbxShowPass;

	private TextView txtBtnConn;
	private TextView txtBtnCancel;

	public WifiConnDialog(Context context, int theme) {
		super(context, theme);
	}

	private WifiConnDialog(Context context, int theme, String wifiName,
			int singlStren, String securityLevl) {
		super(context, theme);
		this.context = context;
		this.wifiName = wifiName;
		this.level = singlStren;
		this.securigyLevel = securityLevl;
	}

	public WifiConnDialog(Context context, int theme, ScanResult scanResult,
			OnNetworkChangeListener onNetworkChangeListener) {
		this(context, theme, scanResult.SSID, scanResult.level,
				scanResult.capabilities);
		this.scanResult = scanResult;
		//this.onNetworkChangeListener = onNetworkChangeListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_wifi_conn);
		setCanceledOnTouchOutside(false);

		initView();
		setListener();
	}

	private void setListener() {


		txtBtnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("txtBtnCancel");
				WifiConnDialog.this.dismiss();
			}
		});

		txtBtnConn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

		/*		WifiCipherType type = null;
				if (scanResult.capabilities.toUpperCase().contains("WPA")) {
					type = WifiCipherType.WIFICIPHER_WPA;
				} else if (scanResult.capabilities.toUpperCase()
						.contains("WEP")) {
					type = WifiCipherType.WIFICIPHER_WEP;
				} else {
					type = WifiCipherType.WIFICIPHER_NOPASS;
				}

				// 连接网络
				WifiAdmin mWifiAdmin = new WifiAdmin(context);
				boolean bRet = mWifiAdmin.connect(scanResult.SSID, edtPassword
						.getText().toString().trim(), type);
				if (bRet) {
					showShortToast("连接成功");
					onNetworkChangeListener.onNetWorkConnect();
				} else {
					showShortToast("连接失败");
					onNetworkChangeListener.onNetWorkConnect();
				}*/
				Intent intent = new Intent(WifiListActivity.instance,MethodActivity.class);
				WifiListActivity.instance.startActivity(intent);
				WifiConnDialog.this.dismiss();
			}
		});
	}

	private void initView() {
		txtWifiName = (TextView) findViewById(R.id.txt_wifi_name);
		txtSinglStrength = (TextView) findViewById(R.id.txt_signal_strength);
		txtSecurityLevel = (TextView) findViewById(R.id.txt_security_level);
		//edtPassword = (EditText) findViewById(R.id.edt_password);
		//cbxShowPass = (CheckBox) findViewById(R.id.cbx_show_pass);
		txtBtnCancel = (TextView) findViewById(R.id.txt_btn_cancel);
		txtBtnConn = (TextView) findViewById(R.id.txt_btn_connect);

		txtWifiName.setText(wifiName);
		txtSinglStrength.setText(WifiAdmin.singlLevToStr(level));
		txtSecurityLevel.setText(securigyLevel);

		//txtBtnConn.setEnabled(false);
		//cbxShowPass.setEnabled(false);

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

	//private OnNetworkChangeListener onNetworkChangeListener;

}
