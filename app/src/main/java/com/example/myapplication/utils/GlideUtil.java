package com.example.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.example.myapplication.R;


public class GlideUtil {

    private static GlideUtil instance;

    public static GlideUtil getInstance() {
        if (instance == null) {
            synchronized (GlideUtil.class) {
                if (instance == null) {
                    instance = new GlideUtil();
                }
            }
        }
        return instance;
    }


    //-----------------------圆角图片----------------------

    /**
     * 加载设置圆角图片 4个角都圆角
     *
     * @param context
     * @param path
     * @param imageView
     * @param roundradius 圆角大小（>0）
     */
    @SuppressWarnings("unchecked")
    public void loadRoundBitmap(Context context, String path, ImageView imageView, int roundradius) {
        if (roundradius < 0) {
            Glide.with(context).load(path).bitmapTransform(new GlideRoundTransform(context)).into(imageView);
        } else {
            Glide.with(context).load(path).bitmapTransform(new GlideRoundTransform(context, roundradius)).into(imageView);
        }
    }

    /**
     * 加载设置圆角图片 2个上角是圆角
     *
     * @param context
     * @param path
     * @param imageView
     * @param roundradius 圆角大小（>0）
     */
    @SuppressWarnings("unchecked")
    public void loadHalfRoundBitmap(Context context, String path, ImageView imageView, int roundradius) {
        if (roundradius < 0) {
            Glide.with(context).load(path).bitmapTransform(new GlideHalfRoundTransform(context)).into(imageView);
        } else {
            Glide.with(context).load(path).bitmapTransform(new GlideHalfRoundTransform(context, roundradius)).into(imageView);
        }
    }


    /**
     * @param context 上下文
     * @param url     图片资源url
     * @param view    image载体
     * @param error   网络错误
     */
    public void loadUrlImage(Context context, String url, ImageView view, int error) {
        Glide.with(context).load(url).error(error).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(view);
    }
    public void loadUrlImage(Context context, String url, ImageView view) {
        loadUrlImage(context,url,view,false);
    }

    public void loadUrlImage(Context context, String url, ImageView view,boolean longPlaceholder) {
        if(longPlaceholder){
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.mipmap.icon_default).skipMemoryCache(true).into(view);
        }else{
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(view);
        }
    }
    /**
     * @param context 上下文
     * @param resId     图片资源id
     * @param view    image载体
     */
    public void loadResImage(Context context, int resId, ImageView view) {
        loadResImage(context,resId,view,false);
    }

    public void loadResImage(Context context, int resId, ImageView view,boolean longPlaceholder) {
        if(longPlaceholder){
            Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.mipmap.icon_default).skipMemoryCache(true).into(view);
        }else{
            Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(view);
        }
    }

    //-------------------图片转换圆角图片------------------------------

    /**
     * 图片转换圆角图片
     */
    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * 6;
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

    /**
     * 图片转换圆角图片
     */
    public class GlideHalfRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideHalfRoundTransform(Context context) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * 6;
        }

        /**
         * 自定义圆角大小
         *
         * @param context
         * @param dp
         */
        public GlideHalfRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            RectF rectRound = new RectF(0f, 100f, source.getWidth(), source.getHeight());
            canvas.drawRect(rectRound, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

}
