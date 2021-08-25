package com.example.myapplication.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.myapplication.R;

import java.util.regex.Pattern;



public class MyImageView extends AppCompatImageView {
    //宽高比float类型
    private float mRatio = 1.0f;
    //宽高比String类型
    private String mScale = "1:1";
    //
    private String mTag = "default";

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyImageView);
        // mRatio = typedArray.getFloat(R.styleable.MyImageView_ratio, 0.0f);
        mScale = typedArray.getString(R.styleable.MyImageView_scale);
        mTag = typedArray.getString(R.styleable.MyImageView_tag);
        typedArray.recycle();
        init();
    }

    //计算比列
    private void init() {
        if (!mScale.contains(":")) {
            System.out.println("--无法设定宽高比，输入的比列格式不正确--");
            throw new RuntimeException("exception");
        } else {
            String[] split = mScale.split(":");
            if ((!TextUtils.isEmpty(split[0]) && isNumber(split[0])) && (!TextUtils.isEmpty(split[1]) && isNumber(split[1]))) {
                if(split[0].equals("0") || split[1].equals("0")){
                    System.out.println("--无法设定宽高比，比列不能为0--");
                    throw new RuntimeException("exception");
                }else {
                    mRatio = Float.valueOf(split[1])/ Float.valueOf(split[0]);
                }
            } else {
                System.out.println("--无法设定宽高比，输入的比列格式不正确--");
                throw new RuntimeException("exception");
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireWidth = 0;
        Drawable drawable = getDrawable();
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽确定，高不确定
        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && widthSize != 0) {
            heightSize = (int) (widthSize * mRatio + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0 && heightMode != 0) {
            widthSize = (int) (heightSize / mRatio + 0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.AT_MOST && widthMode == MeasureSpec.AT_MOST) {
            System.out.println("---" + desireWidth + "---");
            if (drawable != null) {
                desireWidth = drawable.getIntrinsicWidth();
                heightSize = (int) (desireWidth * mRatio + 0.5f);//根据宽度和比例计算高度
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(desireWidth, MeasureSpec.EXACTLY);
            }
        } else {
            throw new RuntimeException("--无法设定宽高比--");
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置宽高比
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    //设置tag
    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public String getmTag() {
        return mTag;
    }
    //判断是否是整数或浮点数
    public static boolean isNumber(String str) {
        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();

        return isInt || isDouble;
    }
}
