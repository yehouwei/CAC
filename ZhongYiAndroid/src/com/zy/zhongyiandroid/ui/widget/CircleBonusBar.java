package com.zy.zhongyiandroid.ui.widget;

import java.util.zip.Inflater;

import com.zy.zhongyiandroid.R;

import android.R.anim;
import android.R.color;
import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnDrawListener;




public class CircleBonusBar extends View {
	private int progress=0;
	private int max=100;
	private Paint paint ;
	private RectF oval;
	private float pivotX;
	private float pivotY;
	Canvas canvas;
	float radius;

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}

	public CircleBonusBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint = new Paint();
		
		oval = new RectF();
/*		pivotX=getPivotX();
		pivotY=getPivotY();
		radius=pivotX/2;*/

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		pivotX=this.getWidth()/2;
		pivotY=this.getHeight()/2;
		radius=pivotX/2;
		
		paint.setAntiAlias(true);// �����Ƿ񿹾��
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);// �����������
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(radius*7/24);
		//设置阴影
		paint.setColor(getResources().getColor(R.color.shadow_bonus));
		canvas.drawCircle(pivotX, pivotY, radius+10, paint);
		//绘制园
		paint.setColor(getResources().getColor(R.color.bonus_progress_bg));
		canvas.drawCircle(pivotX, pivotY, radius, paint);// ������Ϊ��100,100���ĵط������뾶Ϊ55��Բ�����ΪsetStrokeWidth��10��Ҳ���ǻ�ɫ�ĵױ�
/*		//绘制进度弧
		paint.setColor(getResources().getColor(R.color.bonus_progress));// ���û���Ϊ��ɫ
		oval.set(pivotX-radius, pivotY-radius, pivotX+radius, pivotY+radius);// �������������Ͻ����꣨45,45�������½����꣨155,155��������Ҳ�ͱ�֤�˰뾶Ϊ55
		canvas.drawArc(oval, -90, ((float) getProgress() / getMax()) * 360, false, paint);// ��Բ�����ڶ�������Ϊ����ʼ�Ƕȣ�������Ϊ��ĽǶȣ����ĸ�Ϊtrue��ʱ����ʵ�ģ�false��ʱ��Ϊ��
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(70);
		canvas.drawText("��ǰ����", pivotX-40, pivotX-10, paint);*/
		paint.setColor(getResources().getColor(R.color.bonus_progress));
		oval.set(pivotX-radius, pivotY-radius, pivotX+radius, pivotY+radius);
		canvas.drawArc(oval, -90, ((float) getProgress() / getMax()) * 360, false, paint);

	}
	public void drawProcess(){
		paint.setColor(getResources().getColor(R.color.bonus_progress));
		oval.set(pivotX-radius, pivotY-radius, pivotX+radius, pivotY+radius);
		canvas.drawArc(oval, -90, ((float) getProgress() / getMax()) * 360, false, paint);
		
	}
	
	



}
