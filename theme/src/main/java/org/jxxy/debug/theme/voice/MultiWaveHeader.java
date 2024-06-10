package org.jxxy.debug.theme.voice;
import android.graphics.Path;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import org.jxxy.debug.theme.R;

/**
 * 多重水波纹
 * Created by SCWANG on 2017/12/11.
 */
@SuppressWarnings("unused")
public class MultiWaveHeader extends ViewGroup {

    protected Path mPath;
    protected ShapeType mShape = ShapeType.Rect;
    protected Paint mPaint = new Paint();
    protected Matrix mMatrix = new Matrix();
    protected List<Wave> mltWave = new ArrayList<>();
    protected float mCornerRadius;
    protected int mWaveHeight;
    protected int mStartColor;
    protected int mCloseColor;
    protected int mGradientAngle;
    protected boolean mIsRunning;
    protected boolean mEnableFullScreen;
    protected float mVelocity;
    protected float mColorAlpha;
    protected float mProgress;
    protected float mCurProgress;
    protected long mLastTime = 0;
    protected ValueAnimator reboundAnimator;

    public MultiWaveHeader(Context context) {
        this(context, null, 0);
    }

    public MultiWaveHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiWaveHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiWaveHeader);

        mWaveHeight = ta.getDimensionPixelOffset(R.styleable.MultiWaveHeader_mwhWaveHeight, Util.dp2px(50));
        mStartColor = ta.getColor(R.styleable.MultiWaveHeader_mwhStartColor, 0xFF056CD0);
        mCloseColor = ta.getColor(R.styleable.MultiWaveHeader_mwhCloseColor, 0xFF31AFFE);
        mColorAlpha = ta.getFloat(R.styleable.MultiWaveHeader_mwhColorAlpha, 0.45f);
        mVelocity = ta.getFloat(R.styleable.MultiWaveHeader_mwhVelocity, 1f);
        mGradientAngle = ta.getInt(R.styleable.MultiWaveHeader_mwhGradientAngle, 45);
        mIsRunning = ta.getBoolean(R.styleable.MultiWaveHeader_mwhIsRunning, true);
        mEnableFullScreen = ta.getBoolean(R.styleable.MultiWaveHeader_mwhEnableFullScreen, false);
        mCornerRadius = ta.getDimensionPixelOffset(R.styleable.MultiWaveHeader_mwhCornerRadius, Util.dp2px(25));
        mShape = ShapeType.values()[ta.getInt(R.styleable.MultiWaveHeader_mwhShape, mShape.ordinal())];
        mProgress = mCurProgress = ta.getFloat(R.styleable.MultiWaveHeader_mwhProgress, 1f);

        if (ta.hasValue(R.styleable.MultiWaveHeader_mwhWaves)) {
            setTag(ta.getString(R.styleable.MultiWaveHeader_mwhWaves));
        } else if (getTag() == null) {
            setTag("70,25,1.4,1.4,-26\n" +
                    "100,5,1.4,1.2,15\n" +
                    "420,0,1.15,1,-10\n" +
                    "520,10,1.7,1.5,20\n" +
                    "220,0,1,1,-15");
        }

        ta.recycle();
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//
//        int count = getChildCount();
//        if (count > 0) {
//            for (int i = 0; i < count; i++) {
//                View child = getChildAt(i);
//                if (child instanceof Wave) {
//                    child.setVisibility(GONE);
//                } else {
//                    throw new RuntimeException("只能用Wave作为子视图，You can only use Wave as a subview.");
//                }
//            }
//        }
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mltWave.isEmpty()) {
            updateWavePath();
            updateWavePath(r - l, b - t);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateShapePath();
        updateWavePath(w, h);
        updateLinearGradient(w, h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mltWave.size() > 0) {

            if (mPath != null) {
                canvas.save();
                canvas.clipPath(mPath);
            }

            View thisView = this;
            int height = thisView.getHeight();
            long thisTime = System.currentTimeMillis();
            for (Wave wave : mltWave) {
                mMatrix.reset();
                canvas.save();
                if (mIsRunning && mLastTime > 0 && wave.velocity != 0) {
                    float offsetX = (wave.offsetX - (wave.velocity * mVelocity * (thisTime - mLastTime) / 1000f));
                    if (-wave.velocity > 0) {
                        offsetX %= (float) wave.width / 2;
                    } else {
                        while (offsetX < 0) {
                            offsetX += ((float)wave.width / 2);
                        }
                    }
                    wave.offsetX = offsetX;
                    mMatrix.setTranslate(offsetX, (1 - mCurProgress) * height);//wave.offsetX =
                    canvas.translate(-offsetX, -wave.offsetY - (1 - mCurProgress) * height);
                } else{
                    mMatrix.setTranslate(wave.offsetX, (1 - mCurProgress) * height);
                    canvas.translate(-wave.offsetX, -wave.offsetY - (1 - mCurProgress) * height);
                }
                mPaint.getShader().setLocalMatrix(mMatrix);
                canvas.drawPath(wave.path, mPaint);
                canvas.restore();
            }
            mLastTime = thisTime;

            if (mPath != null) {
                canvas.restore();
            }

            if (mIsRunning) {
                invalidate();
            }
        }
    }

    private void updateLinearGradient(int width, int height) {
        int startColor = ColorUtils.setAlphaComponent(mStartColor, (int)(mColorAlpha*255));
        int closeColor = ColorUtils.setAlphaComponent(mCloseColor, (int)(mColorAlpha*255));
        //noinspection UnnecessaryLocalVariable
        double w = width;
        double h = height * mCurProgress;
        double r = Math.sqrt(w * w + h * h) / 2;
        double y = r * Math.sin(2 * Math.PI * mGradientAngle / 360);
        double x = r * Math.cos(2 * Math.PI * mGradientAngle / 360);
        mPaint.setShader(new LinearGradient((int)(w/2-x), (int)(h/2-y), (int)(w/2+x), (int)(h/2+y), startColor, closeColor, Shader.TileMode.CLAMP));
    }


    protected void updateShapePath() {
        View thisView = this;
        int w = thisView.getWidth();
        int h = thisView.getHeight();
        if (w > 0 && h > 0 && mShape != null && mShape != ShapeType.Rect) {
            mPath = new Path();
            switch (mShape) {
                case RoundRect:
                    mPath.addRoundRect(new RectF(0, 0, w, h), mCornerRadius, mCornerRadius, Path.Direction.CW);
                    break;
                case Oval:
                    mPath.addOval(new RectF(0, 0, w, h), Path.Direction.CW);
                    break;
            }
        } else {
            mPath = null;
        }
    }

    protected void updateWavePath() {
        mltWave.clear();
        if (getTag() instanceof String) {
            String[] waves = getTag().toString().split("\\s+");
            if ("-1".equals(getTag())) {
                waves = "70,25,1.4,1.4,-26\n100,5,1.4,1.2,15\n420,0,1.15,1,-10\n520,10,1.7,1.5,20\n220,0,1,1,-15".split("\\s+");
            } else if ("-2".equals(getTag())) {
                waves = "0,0,1,0.5,90\n90,0,1,0.5,90".split("\\s+");
            }
            for (String wave : waves) {
                String[] args = wave.split ("\\s*,\\s*");
                if (args.length == 5) {
                    mltWave.add(new Wave(Util.dp2px(parseFloat(args[0])), Util.dp2px(parseFloat(args[1])), Util.dp2px(parseFloat(args[4])), parseFloat(args[2]), parseFloat(args[3]), mWaveHeight/2));
                }
            }
        } else {
            mltWave.add(new Wave(Util.dp2px(50), Util.dp2px(0), Util.dp2px(5), 1.7f, 2f, mWaveHeight / 2));
        }
    }


    protected void updateWavePath(int w, int h) {
        for (Wave wave : mltWave) {
            wave.updateWavePath(w, h, mWaveHeight / 2, mEnableFullScreen, mCurProgress);
        }
    }

    /**
     * 执行回弹动画
     * @param progress 目标值
     * @param interpolator 加速器
     * @param duration 时长
     */
    protected void animProgress(float progress, Interpolator interpolator, int duration) {
        if (mCurProgress != progress) {
            if (reboundAnimator != null) {
                reboundAnimator.cancel();
            }
            reboundAnimator = ValueAnimator.ofFloat(mCurProgress, progress);
            reboundAnimator.setDuration(duration);
            reboundAnimator.setInterpolator(interpolator);
            reboundAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    reboundAnimator = null;
                }
            });
            reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    updateProgress((float)animation.getAnimatedValue());
                }
            });
            reboundAnimator.start();
        }
    }


    protected void updateProgress(float progress) {
        View thisView = this;
        mCurProgress = progress;
        updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        if (mEnableFullScreen) {
            for (Wave wave : mltWave) {
                wave.updateWavePath(thisView.getWidth(), thisView.getHeight(), mCurProgress);
            }
        }
        if (!mIsRunning) {
            invalidate();
        }
    }

    //<editor-fold desc="method api">
    public void setWaves(String waves) {
        setTag(waves);
        if (mLastTime > 0) {
            View thisView = this;
            updateWavePath();
            updateWavePath(thisView.getWidth(), thisView.getHeight());
        }
    }

    public int getWaveHeight() {
        return mWaveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.mWaveHeight = Util.dp2px(waveHeight);
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateWavePath(thisView.getWidth(), thisView.getHeight());
        }
    }

    public float getVelocity() {
        return mVelocity;
    }

    public void setVelocity(float velocity) {
        this.mVelocity = velocity;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        if (!mIsRunning) {
            updateProgress(progress);
        } else {
            animProgress(progress, new DecelerateInterpolator(), 300);
        }
    }

    public void setProgress(float progress, Interpolator interpolator, int duration) {
        this.mProgress = progress;
        animProgress(progress, new DecelerateInterpolator(), duration);
    }

    public int getGradientAngle() {
        return mGradientAngle;
    }

    public void setGradientAngle(int angle) {
        this.mGradientAngle = angle;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setStartColor(int color) {
        this.mStartColor = color;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void setStartColorId(@ColorRes int colorId) {
        final View thisView = this;
        setStartColor(Util.getColor(thisView.getContext(), colorId));
    }

    public int getCloseColor() {
        return mCloseColor;
    }

    public void setCloseColor(int color) {
        this.mCloseColor = color;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void setCloseColorId(@ColorRes int colorId) {
        final View thisView = this;
        setCloseColor(Util.getColor(thisView.getContext(), colorId));
    }

    public float getColorAlpha() {
        return mColorAlpha;
    }

    public void setColorAlpha(float alpha) {
        this.mColorAlpha = alpha;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void start() {
        if (!mIsRunning) {
            mIsRunning = true;
            mLastTime = System.currentTimeMillis();
            invalidate();
        }
    }

    public void stop() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setEnableFullScreen(boolean fullScreen) {
        this.mEnableFullScreen = fullScreen;
    }

    public boolean isEnableFullScreen() {
        return mEnableFullScreen;
    }

    public void setShape(ShapeType shape) {
        this.mShape = shape;
        updateShapePath();
    }

    public ShapeType getShape() {
        return mShape;
    }

    //</editor-fold>
}
enum ShapeType {
    Rect,
    RoundRect,
    Oval,
}
class Util {

    /**
     * 获取颜色
     * @param context 上下文
     * @param colorId 颜色ID
     * @return 颜色
     */
    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorId);
        }
        //noinspection deprecation
        return context.getResources().getColor(colorId);
    }

    /**
     * dp转px
     * @param dpVal dp 值
     * @return px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }
}

/**
 * 水波对象
 * Created by SCWANG on 2017/12/11.
 */
@SuppressWarnings("WeakerAccess")
class Wave /*extends View*/ {

    Path path;          //水波路径
    int width;          //画布宽度（2倍波长）
    int wave;           //波幅（振幅）
    float offsetX;        //水波的水平偏移量
    float offsetY;        //水波的竖直偏移量
    float velocity;       //水波移动速度（像素/秒）
    private float scaleX;       //水平拉伸比例
    private float scaleY;       //竖直拉伸比例
    private int curWave;
//    int startColor;     //开始颜色
//    int closeColor;     //结束颜色
//    float alpha;        //颜色透明度

    /**
     * 通过参数构造一个水波对象
     * @param offsetX   水平偏移量
     * @param offsetY   竖直偏移量
     * @param velocity  移动速度（像素/秒）
     * @param scaleX    水平拉伸量
     * @param scaleY    竖直拉伸量
     * @param wave      波幅（波宽度）
     */
//    @SuppressWarnings("PointlessArithmeticExpression")
    Wave(/*Context context, */int offsetX, int offsetY, int velocity, float scaleX, float scaleY, int wave) {
//        super(context);
        this.wave = wave;           //波幅（波宽）
        this.scaleX = scaleX;       //水平拉伸量
        this.scaleY = scaleY;       //竖直拉伸量
        this.offsetX = offsetX;     //水平偏移量
        this.offsetY = offsetY;     //竖直偏移量
        this.velocity = velocity;   //移动速度（像素/秒）
        this.path = new Path();
    }

//    /*
//     * 根据 波长度、中轴线高度、波幅 绘制水波路径
//     */
//    public Wave(Context context) {
//        this(context, null, 0);
//    }
//
//    public Wave(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Wave);
//
////        startColor = ta.getColor(R.styleable.Wave_mwhStartColor, 0);
////        closeColor = ta.getColor(R.styleable.Wave_mwhCloseColor, 0);
////        alpha = ta.getFloat(R.styleable.Wave_mwhColorAlpha, 0f);
//        scaleX = ta.getFloat(R.styleable.Wave_mwScaleX, 1);
//        scaleY = ta.getFloat(R.styleable.Wave_mwScaleY, 1);
//        offsetX = ta.getDimensionPixelOffset(R.styleable.Wave_mwOffsetX, 0);
//        offsetY = ta.getDimensionPixelOffset(R.styleable.Wave_mwOffsetY, 0);
//        velocity = ta.getDimensionPixelOffset(R.styleable.Wave_mwVelocity, Util.dp2px(10));
//        wave = ta.getDimensionPixelOffset(R.styleable.Wave_mwWaveHeight, 0) / 2;
//
//        ta.recycle();
//    }

    protected void updateWavePath(int w, int h, int waveHeight, boolean fullScreen, float progress) {
        this.wave = waveHeight;
        this.width = (int) (2* scaleX * w);  //画布宽度（2倍波长）
        this.path = buildWavePath(width, h, fullScreen, progress);
    }

    protected void updateWavePath(int w, int h, float progress) {
        int wave = (int) (scaleY * this.wave);//计算拉伸之后的波幅
        float maxWave = h * Math.max(0, (1 - progress));
        if (wave > maxWave) {
            wave = (int)maxWave;
        }

        if (curWave != wave) {
            this.width = (int) (2 * scaleX * w);  //画布宽度（2倍波长）
            this.path = buildWavePath(width, h, true, progress);
        }
    }

    protected Path buildWavePath(int width, int height, boolean fullScreen, float progress) {
        int DP = Util.dp2px(1);//一个dp在当前设备表示的像素量（水波的绘制精度设为一个dp单位）
        if (DP < 1) {
            DP = 1;
        }

        int wave = (int) (scaleY * this.wave);//计算拉伸之后的波幅

        if (fullScreen) {
            float maxWave = height * Math.max(0, (1 - progress));
            if (wave > maxWave) {
                wave = (int) maxWave;
            }
        }
        this.curWave = wave;

//        Path path = new Path();
        path.reset();

        path.moveTo(0, 0);
        path.lineTo(0, height - wave);

        if (wave > 0) {
            for (int x = DP; x < width; x += DP) {
                path.lineTo(x, height - wave - wave * (float) Math.sin(4.0 * Math.PI * x / width));
            }
        }

        path.lineTo(width, height - wave);
        path.lineTo(width, 0);
        path.close();
        return path;
    }
}