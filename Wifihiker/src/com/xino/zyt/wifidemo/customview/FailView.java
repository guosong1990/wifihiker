package com.xino.zyt.wifidemo.customview;


import com.xino.zyt.wifidemo.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class FailView extends View {

	private Paint paint;
	private Bitmap cry;
	private Resources res = this.getResources();
	public FailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		cry = BitmapFactory.decodeResource(res, R.drawable.cry);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(cry, 10, 10, paint);
	}
	
	
}
