package themollo.app.mollo.util;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;

import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.ShapeSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

import themollo.app.mollo.R;

/**
 * Created by alex on 2018. 8. 2..
 */

public class LullabyAnimator extends SpriteContainer {

    public LullabyAnimator() {

    }

    public Sprite[] onCreateChild() {
        Sprite[] sprites = new Sprite[]{new LullabyAnimator.Circle(R.drawable.gradient_list,0, 360)};
        //Color.parseColor("#8B8AFF")
        return new Sprite[]{
                new LullabyAnimator.Circle(Color.parseColor("#8B8AFF"),0, 360)
        };
    }

    public void setColor(int color) {
    }

    class Circle extends ShapeSprite {
        private int startAngle;
        private int endAngle;
        private float[] p0;
        private float[] p1;
        private float[] p2;
        private float[] p3;
        private float[] c0;
        private float[] c1;
        private float[] c2;
        private float[] c3;
        private float[] c4;
        private float[] c5;
        private float[] c6;
        private float[] c7;
        private Path path;
        private int strokeWidth;

        public Circle(int color, int startAngle, int endAngle) {
            this.setColor(color);
            this.startAngle = startAngle;
            this.endAngle = endAngle;
        }

        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            this.setDrawBounds(this.clipSquare(bounds));
            int w = this.getDrawBounds().width();
            int h = this.getDrawBounds().height();
            this.p0 = new float[]{0.0F, (float)(h / 2)};
            this.p1 = new float[]{(float)(w / 2), 0.0F};
            this.p2 = new float[]{(float)w, (float)(h / 2)};
            this.p3 = new float[]{(float)(w / 2), (float)h};
            this.c0 = new float[]{0.0F, (float)(h * 3 / 4)};
            this.c1 = new float[]{0.0F, (float)(h / 9)};
            this.c2 = new float[]{(float)(w / 4), 0.0F};
            this.c3 = new float[]{(float)(w * 3 / 4), 0.0F};
            this.c4 = new float[]{(float)w, (float)(h / 9)};
            this.c5 = new float[]{(float)w, (float)(h * 3 / 4)};
            this.c6 = new float[]{(float)(w * 3 / 4), (float)h};
            this.c7 = new float[]{(float)(w / 8), (float)h};
            this.path = new Path();
            this.path.moveTo(this.p0[0], this.p0[1]);
            this.cubicTo(this.path, this.c1, this.c2, this.p1);
            this.cubicTo(this.path, this.c3, this.c4, this.p2);
            this.cubicTo(this.path, this.c5, this.c6, this.p3);
            this.cubicTo(this.path, this.c7, this.c0, this.p0);
            this.strokeWidth = this.getDrawBounds().width() / 18;
        }

        public void drawShape(Canvas canvas, Paint paint) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
//            paint.setStrokeWidth((float)this.strokeWidth);
//            paint.setStyle(Paint.Style.STROKE);
            paint.setStyle(Paint.Style.FILL);
            canvas.translate((float)this.getDrawBounds().left, (float)this.getDrawBounds().top);
            canvas.drawPath(this.path, paint);
        }

        private void cubicTo(Path path, float[] c0, float[] c1, float[] p) {
            path.cubicTo(c0[0], c0[1], c1[0], c1[1], p[0], p[1]);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = new float[]{0.0F, 1.0F};
            return (new SpriteAnimatorBuilder(this)).rotate(fractions, new int[]{this.startAngle, this.endAngle}).duration(5500L).build();
        }
    }
}

