package com.mummyding.app.leisure.support;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.LeisureApplication;


/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ImageUtil {
    public static int getImageColor(Bitmap bitmap){
        Palette palette = Palette.from(bitmap).generate();
        if(palette == null || palette.getDarkMutedSwatch() == null){
            return Color.LTGRAY;
        }
        return palette.getDarkMutedSwatch().getRgb();
    }

    public static Bitmap getBitmap(SimpleDraweeView imageView){
        Bitmap bitmap;
        if (imageView.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            Drawable d = imageView.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }
        return bitmap;
    }

    public static Bitmap bitmapToCircle(Bitmap bitmap){
        //处理Bitmap 转成正方形
        Bitmap newBitmap = dealRawBitmap(bitmap);
        //将newBitmap 转换成圆形
        return toRoundCorner(newBitmap, DisplayUtil.dip2px(LeisureApplication.AppContext,50));
    }

    //将原始图像裁剪成正方形
    private static Bitmap dealRawBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //获取宽度
        int minWidth = width > height ?  height:width ;
        //计算正方形的范围
        int leftTopX = (width - minWidth)/2;
        int leftTopY = (height - minWidth)/2;
        //裁剪成正方形
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,leftTopX,leftTopY,minWidth,minWidth,null,false);
        return  scaleBitmap(newBitmap);
    }
    //将头像按比例缩放
    private static Bitmap scaleBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        //一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
        float scale = (float)width/(float)bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    private static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Paint paint = new Paint();
        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0,bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
