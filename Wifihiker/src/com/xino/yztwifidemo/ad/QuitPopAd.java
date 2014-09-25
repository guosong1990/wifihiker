package com.xino.yztwifidemo.ad;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.SDKUtils;

public class QuitPopAd {

	private static Dialog dialog;
	
	private static QuitPopAd quitPopAd;
	
	public static QuitPopAd getInstance(){
		if(quitPopAd == null){
			quitPopAd = new QuitPopAd();
		}
		return quitPopAd;
	}
	
	/**
	 * 展示退屏广告
	 * @param context
	 */
	public void show(final Context context){
		
		dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);//第二个样式参数,可根据自己应用或游戏中的布局进行设置
		// 判断插屏广告是否已初始化完成，用于确定是否能成功调用插屏广告
		if(AppConnect.getInstance(context).hasPopAd(context)){
			View view = null;
			if(((Activity)context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				view = getQuitView_Portrait(context, dialog);
			}else{
				view = getQuitView_Landscape(context, dialog);
			}
			if(view != null){
				dialog.setContentView(view);
				dialog.show();
			}else{
				new AlertDialog.Builder(context)
				.setTitle("退出提示")
				.setMessage("确定要退出当前应用吗？")
				.setPositiveButton("确定", new AlertDialog.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(dialog != null){
							dialog.cancel();
						}
						((Activity)context).finish();
					}
				})
				.setNegativeButton("取消", new AlertDialog.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.create().show();
				
			}
		}else{
			new AlertDialog.Builder(context)
			.setTitle("退出提示")
			.setMessage("确定要退出当前应用吗？")
			.setPositiveButton("确定", new AlertDialog.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(dialog != null){
						dialog.cancel();
					}
					((Activity)context).finish();
				}
			})
			.setNegativeButton("取消", new AlertDialog.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create().show();
		}
	}
	
	/**
	 * 关闭退屏广告对话框
	 */
	public void close(){
		if(dialog != null && dialog.isShowing()){
			dialog.cancel();
		}
	}
	
	/**
	 * 获取竖屏样式的退出布局
	 * @param context
	 * @param dialog 加载退出布局的dialog
	 * @return
	 */
	private LinearLayout getQuitView_Portrait(final Context context, final Dialog dialog){
		// 对小屏手机进行屏幕判断
		int displaySize = SDKUtils.getDisplaySize(context);
		
		//设置标题布局的两个顶角为圆角
		float num = 10f;
		float[] outerR = new float[] { num, num, num, num, 0, 0, 0, 0};
		ShapeDrawable title_layout_shape = new ShapeDrawable(new RoundRectShape(outerR, null, null));
		title_layout_shape.getPaint().setColor(Color.argb(240, 10, 10, 10));
		
		//设置按钮布局的两个底角为圆角
		float[] outerR2 = new float[] { 0, 0, 0, 0, num, num, num, num};
		ShapeDrawable btn_layout_shape = new ShapeDrawable(new RoundRectShape(outerR2, null, null));
		btn_layout_shape.getPaint().setColor(Color.argb(240, 20, 20, 20));
		
		//最外层布局
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setBackgroundColor(Color.argb(80, 0, 0, 0));
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		//用于排放标题，popAd的布局
		final RelativeLayout r_layout = new RelativeLayout(context);
		r_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		r_layout.setGravity(Gravity.CENTER);
		
		//标题布局
		LinearLayout title_layout = new LinearLayout(context);
		TextView textView = new TextView(context);
		textView.setText("确定要退出吗？");
		textView.setTextSize(18);
		textView.setTextColor(Color.WHITE);
		title_layout.setId((int)(System.currentTimeMillis()));
		if(displaySize == 320){
			title_layout.setPadding(10, 10, 0, 10);
		}else if(displaySize == 240){
			title_layout.setPadding(10, 5, 0, 5);
		}else{
			title_layout.setPadding(15, 15, 0, 15);
		}
		title_layout.setBackgroundDrawable(title_layout_shape);
		
		title_layout.addView(textView);
		
		//获取插屏布局
		LinearLayout pop_layout = AppConnect.getInstance(context).getPopAdView(context);
		
		if(pop_layout == null){
			return null;
		}
		
		pop_layout.setBackgroundColor(Color.argb(200, 40, 40, 40));
		pop_layout.setId((int)(System.currentTimeMillis()+1));
		pop_layout.setPadding(5, 0, 5, 0);
		
		//按钮组布局
		LinearLayout btn_layout = new LinearLayout(context);
		btn_layout.setGravity(Gravity.CENTER);
		btn_layout.setOrientation(LinearLayout.HORIZONTAL);
		btn_layout.setPadding(3, 10, 3, 10);
		btn_layout.setBackgroundDrawable(btn_layout_shape);
		btn_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		Button okButton = new Button(context);
		okButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1F));
		okButton.setText(" 退 出 ");
		okButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(dialog != null){
					dialog.cancel();
				}
				((Activity)context).finish();
			}
		});
		
		Button cancelButton = new Button(context);
		cancelButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1F));
		cancelButton.setText(" 取 消 ");
		cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		
		Button moreButton = new Button(context);
		moreButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1F));
		moreButton.setText(" 更 多 ");
		moreButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AppConnect.getInstance(context).showOffers(context);
				if(dialog != null){
					dialog.cancel();
				}
			}
		});
		
		btn_layout.addView(okButton);
		btn_layout.addView(cancelButton);
		btn_layout.addView(moreButton);
		
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_LEFT, pop_layout.getId());
		params1.addRule(RelativeLayout.ALIGN_RIGHT, pop_layout.getId());
		
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.BELOW, title_layout.getId());
		
		r_layout.addView(title_layout, params1);
		r_layout.addView(pop_layout, params2);
		
		// 用于排放r_layout(标题和popAd布局)和按钮的布局
		LinearLayout l_layout = new LinearLayout(context);
		l_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		l_layout.setOrientation(LinearLayout.VERTICAL);
		l_layout.addView(r_layout);
		l_layout.addView(btn_layout);
		
		layout.addView(l_layout);
		
		dialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				r_layout.removeAllViews();
			}
		});
		
		return layout;
	}
	
	/**
	 * 获取横屏样式的退出布局
	 * @param context
	 * @param dialog 加载退出布局的dialog
	 * @return
	 */
	private LinearLayout getQuitView_Landscape(final Context context, final Dialog dialog){
		
		//设置标题布局的两个顶角为圆角
		float num = 10f;
		float[] outerR = new float[] { num, num, 0, 0, 0, 0, num, num};
		ShapeDrawable title_layout_shape = new ShapeDrawable(new RoundRectShape(outerR, null, null));
		title_layout_shape.getPaint().setColor(Color.argb(200, 10, 10, 10));
		
		//设置按钮布局的两个底角为圆角
		float[] outerR2 = new float[] { 0, 0, num, num, num, num, 0, 0};
		ShapeDrawable btn_layout_shape = new ShapeDrawable(new RoundRectShape(outerR2, null, null));
		btn_layout_shape.getPaint().setColor(Color.argb(200, 20, 20, 20));
		
		//最外层布局
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setBackgroundColor(Color.argb(80, 0, 0, 0));
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		//用于排放标题，popAd，按钮组的整体布局
		final RelativeLayout r_layout = new RelativeLayout(context);
		r_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		r_layout.setGravity(Gravity.CENTER);
		
		//标题布局
		LinearLayout title_layout = new LinearLayout(context);
		TextView textView = new TextView(context);
		textView.setText("确定要退出吗？");
		textView.setTextSize(18);
		textView.setEms(1);
		textView.setTextColor(Color.WHITE);
		title_layout.setId((int)(System.currentTimeMillis()));
		title_layout.setPadding(10, 10, 10, 0);
		title_layout.setBackgroundDrawable(title_layout_shape);
		
		title_layout.addView(textView);
		
		LinearLayout pop_layout = null;
		//获取插屏布局
		int height_full = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
		
		int height_tmp = height_full - 75;//75为设备状态栏加标题栏的高度
		
		int height = height_tmp - 55;//55为自定义
		if(height_full <= 480){
			pop_layout = AppConnect.getInstance(context).getPopAdView(context, height, height);
		}else{
			pop_layout = AppConnect.getInstance(context).getPopAdView(context);
		}
		
		if(pop_layout == null){
			return null;
		}
		pop_layout.setBackgroundColor(Color.argb(200, 40, 40, 40));
		pop_layout.setId((int)(System.currentTimeMillis()+1));
		pop_layout.setPadding(2, 0, 2, 0);
		
		//按钮组布局
		LinearLayout btn_layout = new LinearLayout(context);
		btn_layout.setOrientation(LinearLayout.VERTICAL);
		btn_layout.setBackgroundDrawable(btn_layout_shape);
		btn_layout.setPadding(3, 8, 3, 3);
		btn_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		
		//按钮布局中顶部的子布局
		LinearLayout top_layout = new LinearLayout(context);
		top_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		top_layout.setOrientation(LinearLayout.VERTICAL);
		top_layout.setGravity(Gravity.TOP);
		//按钮布局中底部的子布局
		LinearLayout bottom_layout = new LinearLayout(context);
		bottom_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		bottom_layout.setGravity(Gravity.BOTTOM);
		
		
		Button okButton = new Button(context);
		okButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		okButton.setText(" 退 出 ");
		okButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(dialog != null){
					dialog.cancel();
				}
				((Activity)context).finish();
			}
		});
		
		Button cancelButton = new Button(context);
		cancelButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		cancelButton.setText(" 取 消 ");
		cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		
		Button moreButton = new Button(context);
		moreButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		moreButton.setText(" 更 多 ");
		moreButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AppConnect.getInstance(context).showOffers(context);
				if(dialog != null){
					dialog.cancel();
				}
			}
		});
		
		top_layout.addView(okButton);
		top_layout.addView(cancelButton);
		
		bottom_layout.addView(moreButton);
		
		btn_layout.addView(top_layout);
		btn_layout.addView(bottom_layout);
		
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_TOP, pop_layout.getId());
		params1.addRule(RelativeLayout.ALIGN_BOTTOM, pop_layout.getId());
		
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.RIGHT_OF, title_layout.getId());
		
		r_layout.addView(title_layout, params1);
		r_layout.addView(pop_layout, params2);
		
		// 用于排放r_layout(标题和popAd布局)和按钮的布局
		LinearLayout l_layout = new LinearLayout(context);
		l_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		l_layout.setOrientation(LinearLayout.HORIZONTAL);
		l_layout.addView(r_layout);
		l_layout.addView(btn_layout);
		
		layout.addView(l_layout);
		
		dialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				r_layout.removeAllViews();
			}
		});
		
		return layout;
	}
}
