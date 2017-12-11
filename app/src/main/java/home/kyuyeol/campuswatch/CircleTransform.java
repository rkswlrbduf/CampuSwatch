package home.kyuyeol.campuswatch;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by KyuYeol on 2017-12-03.
 */

public class CircleTransform implements com.squareup.picasso.Transformation {
    private final int radius;
    //private final int margin;  // dp
    private final int lMargin;
    private final int tMargin;
    private final int rMargin;
    private final int bMargin;

    // radius is corner radii in dp
    // margin is the board in dp
    public CircleTransform(final int radius, final int lMargin, final int tMargin, final int rMargin, final int bMargin) {
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
        canvas.drawRoundRect(new RectF(lMargin, tMargin, source.getWidth() - rMargin, source.getHeight() - bMargin), radius, radius, paint); // LEFT, TOP, RIGHT, BOTTOM

        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#D4D4D4"));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(4);
        canvas.drawRoundRect(new RectF(lMargin, tMargin, source.getWidth() - rMargin, source.getHeight() - bMargin), radius, radius, paint1);
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