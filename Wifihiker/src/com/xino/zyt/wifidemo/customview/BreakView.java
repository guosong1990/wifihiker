package com.xino.zyt.wifidemo.customview;

import java.util.Random;

import com.xino.zyt.wifidemo.Break1Activity;
import com.xino.zyt.wifidemo.MethodActivity;
import com.xino.zyt.wifidemo.R;
import com.xino.zyt.wifidemo.TryFailActivity;
import com.xino.zyt.wifidemo.WifiListActivity;
import com.xino.zyt.wifidemo.util.ProducePWD;
import com.xino.zyt.wifidemo.view.WifiConnDialog;
import com.xino.zyt.wifidemo.view.WifiConnFailDialog;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BreakView extends SurfaceView implements Callback ,Runnable{

	private SurfaceHolder sfh;
	private Paint paint;
	private Canvas canvas;
	public static boolean flag;
	private Thread th;
	private int screenH;
	private int screenW;
	private static int count = 0;
	private ProducePWD producePWD;
	private Movie movie;
	private long mStart;
	private int points;
	private String myponit = "";
	public static long startTime;
	public static long nowTime;
	private Context context;
	private String methodsString;
	public static BreakView instance;
	private static final int TIMES = 60;
	
	public BreakView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置背景常亮
		this.setKeepScreenOn(true);
		producePWD = new ProducePWD();
		instance = this;
		// TODO Auto-generated constructor stub
	}



	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		methodsString = getMethodTitle();
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		startTime = System.currentTimeMillis();
		th.start();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		methodsString = getMethodTitle();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			myDraw();
			logic();
		}
	}
	private void initGame() {
		// TODO Auto-generated method stub
		movie = Movie.decodeStream(getResources().openRawResource(R.drawable.saomiao));  
		
	}
	
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas!=null){
				canvas.drawColor(Color.BLACK);
				if(count%100==0){
					myponit = getThreePoints();
				}
				playGIF(canvas);
				paint.setTextSize(48);
				canvas.drawText(methodsString, screenW/2-168, 100, paint);
				paint.setTextSize(30);
				canvas.drawText("正在努力破解中"+myponit, screenW/2-125, 180, paint);
				canvas.drawText("尝试密码次数"+(count++)+"数", 0, screenH/5*4, paint);
				canvas.drawText(producePWD.getRandomPWD(randomL()), 0, screenH/5*4+50, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}

	}
	public int randomL(){
		Random random = new Random();
		return random.nextInt(55)+8;
	}
	
	public void playGIF(Canvas canvas){
		long now = android.os.SystemClock.uptimeMillis();   
        
        if (mStart == 0) { // first time   
            mStart = now;   
        }   
        if (movie != null) {   
              
            int dur = movie.duration();   
            if (dur == 0) {   
                dur = 1000;   
            }   
            int relTime = (int) ((now - mStart) % dur);
            movie.setTime(relTime);   
            movie.draw(canvas, screenW/2-movie.width()/2, 300);
        } 
	}
	
	public String getThreePoints(){
		points++;
		if(points%4==0){
			return "";
		}
		if(points%4==1){
			return ".";
		}
		if(points%4==2){
			return "..";
		}
		if(points%4==3){
			return "...";
		}
		return "";
	}
	
	public void  logic(){
		nowTime = System.currentTimeMillis();
		if((nowTime-startTime)/1000>TIMES){
			flag = false;
			Message message = Break1Activity.instance.handler.obtainMessage();
			Break1Activity.instance.handler.sendMessage(message);
		}
	}
	
	private String getMethodTitle() {
		// TODO Auto-generated method stub
		String temp = "";
		switch (Break1Activity.level) {
		case 1:
			temp = "暴力破解使用中";
			break;
		case 2:
			temp = "随机破解使用中";
			break;
		case 3:
			temp = "字典破解使用中";
			break;
		case 4:
			temp = "专业破解使用中";
			break;

		default:
			break;
		}
		return temp;
	}
}
