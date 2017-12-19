package home.kyuyeol.campuswatch;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import com.squareup.picasso.Transformation;

/**
 * Created by KyuYeol on 2017-12-03.
 */

public class CircleTransform implements com.squareup.picasso.Transformation {

    private int radius;
    private int lMargin;
    private int tMargin;
    private int rMargin;
    private int bMargin;

    public CircleTransform(int radius, int lMargin, int tMargin, int rMargin, int bMargin) {
        this.radius = radius;
        this.lMargin = lMargin;
        this.tMargin = tMargin;
        this.rMargin = rMargin;
        this.bMargin = bMargin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);

        final Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#D4D4D4"));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(4);
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint1);
        Log.d("Margin", lMargin + " " + tMargin + " " + rMargin + " " + bMargin);
        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}