package com.example.administrator.seachman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyRightView extends View {
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private TextPaint textPaint;//文本画笔
    private int length;
    private int everyHeight;
    private int everyWidth;//每个单元格的宽度
    private Rect rect;//中间正方形
    private int index=-1;//记录点击的是哪个

   public MyRightView(Context context) {
       this(context, null);
   }

    public MyRightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initpaint();


    }
    private void initpaint() {
        textPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(25);
        rect=new Rect();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_MOVE:
                //如果是点击或者滑动状态，则使字幕变色，并回调实现方法
                index = (int) (event.getY() / everyHeight);
                //获取被点击的字幕，然后回调出去
                if (indexPressWord != null && index >= 0) {
                    indexPressWord.setIndexPressWord(words[index]);
                }
                invalidate();//重走onDraw方法。
                break;
            case MotionEvent.ACTION_UP://手指抬起，所有字母变为黑色
                index = -1;
                invalidate();
                break;
            default:
        }
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initHeight();
        for (int i = 0; i < length; i++) {
            String word = words[i];
            textPaint.getTextBounds(word, 0, 1, rect);
            int width = rect.width();
            int height = rect.height();
            if (index == i) {
                textPaint.setColor(Color.RED);
            } else {
                textPaint.setColor(Color.BLACK);
            }
            canvas.drawText(word, everyWidth / 2 - width / 2, everyHeight * i + (everyHeight / 2 + height / 2)
                    , textPaint);
        }
    }

    private void initHeight() {
        everyWidth=getWidth();
        length=words.length;
        everyHeight=getHeight()/length;
    }


    private void initheight() {
        everyWidth = getWidth();
        length = words.length;
        everyHeight = getHeight() / length;
    }
    public interface IndexPressWord {
        void setIndexPressWord(String word);
    }

    private IndexPressWord indexPressWord;

    public void setIndexPressWord(IndexPressWord indexPressWord) {
        this.indexPressWord = indexPressWord;
    }
}
