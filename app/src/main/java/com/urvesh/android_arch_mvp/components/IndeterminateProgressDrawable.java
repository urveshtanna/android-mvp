package com.urvesh.android_arch_mvp.components;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.urvesh.android_arch_mvp.R;


public class IndeterminateProgressDrawable extends Drawable implements Animatable {

    protected boolean mUseIntrinsicPadding = true;
    protected boolean mAutoMirrored;
    protected int mAlpha = 0xFF;
    protected ColorFilter mColorFilter;
    protected ColorStateList mTintList;
    protected PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
    protected PorterDuffColorFilter mTintFilter;

    private Paint mPaint;

    protected Animator[] mAnimators;

    private static final float PROGRESS_INTRINSIC_SIZE_DP = 3.2f;
    private static final float PADDED_INTRINSIC_SIZE_DP = 16;
    private static final RectF RECT_BOUND = new RectF(-21, -21, 21, 21);
    private static final RectF RECT_PADDED_BOUND = new RectF(-24, -24, 24, 24);
    private static final RectF RECT_PROGRESS = new RectF(-19, -19, 19, 19);

    private int mProgressIntrinsicSize;
    private int mPaddedIntrinsicSize;

    private RingPathTransform mRingPathTransform = new RingPathTransform();
    private RingRotation mRingRotation = new RingRotation();

    /**
     * Create a new {@code IndeterminateProgressDrawable}.
     *
     * @param context the {@code Context} for retrieving style information.
     */
    public IndeterminateProgressDrawable(Context context) {
        setAutoMirrored(true);
        int colorControlActivated = getColorFromAttrRes(R.attr.colorControlActivated,
                context);
        // setTint() has been overridden for compatibility; DrawableCompat won't work because
        // wrapped Drawable won't be Animatable.
        setTint(colorControlActivated);

        float density = context.getResources().getDisplayMetrics().density;
        mProgressIntrinsicSize = Math.round(PROGRESS_INTRINSIC_SIZE_DP * density);
        mPaddedIntrinsicSize = Math.round(PADDED_INTRINSIC_SIZE_DP * density);

        mAnimators = new Animator[] {
                createIndeterminate(mRingPathTransform),
                createIndeterminateRotation(mRingRotation)
        };
    }

    private int getIntrinsicSize() {
        return mUseIntrinsicPadding ? mPaddedIntrinsicSize : mProgressIntrinsicSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIntrinsicWidth() {
        return getIntrinsicSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIntrinsicHeight() {
        return getIntrinsicSize();
    }

    protected void onPreparePaint(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.MITER);
    }

    protected void onDraw(Canvas canvas, int width, int height, Paint paint) {

        if (mUseIntrinsicPadding) {
            canvas.scale(width / RECT_PADDED_BOUND.width(), height / RECT_PADDED_BOUND.height());
            canvas.translate(RECT_PADDED_BOUND.width() / 2, RECT_PADDED_BOUND.height() / 2);
        } else {
            canvas.scale(width / RECT_BOUND.width(), height / RECT_BOUND.height());
            canvas.translate(RECT_BOUND.width() / 2, RECT_BOUND.height() / 2);
        }

        drawRing(canvas, paint);
    }

    private void drawRing(Canvas canvas, Paint paint) {

        int saveCount = canvas.save();
        canvas.rotate(mRingRotation.mRotation);

        // startAngle starts at 3 o'clock on a watch.
        float startAngle = -90 + 360 * (mRingPathTransform.mTrimPathOffset
                + mRingPathTransform.mTrimPathStart);
        float sweepAngle = 360 * (mRingPathTransform.mTrimPathEnd
                - mRingPathTransform.mTrimPathStart);
        canvas.drawArc(RECT_PROGRESS, startAngle, sweepAngle, false, paint);

        canvas.restoreToCount(saveCount);
    }

    public class RingPathTransform {

        public float mTrimPathStart;
        public float mTrimPathEnd;
        public float mTrimPathOffset;

        @Keep
        @SuppressWarnings("unused")
        public void setTrimPathStart(float trimPathStart) {
            mTrimPathStart = trimPathStart;
        }

        @Keep
        @SuppressWarnings("unused")
        public void setTrimPathEnd(float trimPathEnd) {
            mTrimPathEnd = trimPathEnd;
        }

        @Keep
        @SuppressWarnings("unused")
        public void setTrimPathOffset(float trimPathOffset) {
            mTrimPathOffset = trimPathOffset;
        }
    }

    public class RingRotation {

        private float mRotation;

        @Keep
        @SuppressWarnings("unused")
        public void setRotation(float rotation) {
            mRotation = rotation;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {

        if (isStarted()) {
            return;
        }

        for (Animator animator : mAnimators) {
            animator.start();
        }
        invalidateSelf();
    }

    private boolean isStarted() {
        for (Animator animator : mAnimators) {
            if (animator.isStarted()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        for (Animator animator : mAnimators) {
            animator.end();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        for (Animator animator : mAnimators) {
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean getUseIntrinsicPadding() {
        return mUseIntrinsicPadding;
    }

    /**
     * {@inheritDoc}
     */
    public void setUseIntrinsicPadding(boolean useIntrinsicPadding) {
        if (mUseIntrinsicPadding != useIntrinsicPadding) {
            mUseIntrinsicPadding = useIntrinsicPadding;
            invalidateSelf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAutoMirrored() {
        return mAutoMirrored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAutoMirrored(boolean mirrored) {
        if (mAutoMirrored != mirrored) {
            mAutoMirrored = mirrored;
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlpha(int alpha) {
        if (mAlpha != alpha) {
            mAlpha = alpha;
            invalidateSelf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mColorFilter = colorFilter;
        invalidateSelf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTint(@ColorInt int tintColor) {
        setTintList(ColorStateList.valueOf(tintColor));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTintList(ColorStateList tint) {
        mTintList = tint;
        mTintFilter = makeTintFilter(mTintList, mTintMode);
        invalidateSelf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        mTintMode = tintMode;
        mTintFilter = makeTintFilter(mTintList, mTintMode);
        invalidateSelf();
    }

    private PorterDuffColorFilter makeTintFilter(ColorStateList tint, PorterDuff.Mode tintMode) {

        if (tint == null || tintMode == null) {
            return null;
        }

        int color = tint.getColorForState(getState(), Color.TRANSPARENT);
        // They made PorterDuffColorFilter.setColor() and setMode() @hide.
        return new PorterDuffColorFilter(color, tintMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOpacity() {
        // Be safe.
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(@NonNull Canvas canvas) {

        Rect bounds = getBounds();
        if (bounds.width() == 0 || bounds.height() == 0) {
            return;
        }

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLACK);
            onPreparePaint(mPaint);
        }
        mPaint.setAlpha(mAlpha);
        ColorFilter colorFilter = mColorFilter != null ? mColorFilter : mTintFilter;
        mPaint.setColorFilter(colorFilter);

        int saveCount = canvas.save();

        canvas.translate(bounds.left, bounds.top);
        if (needMirroring()) {
            canvas.translate(bounds.width(), 0);
            canvas.scale(-1, 1);
        }

        onDraw(canvas, bounds.width(), bounds.height(), mPaint);

        canvas.restoreToCount(saveCount);

        if (isStarted()) {
            invalidateSelf();
        }
    }

    private boolean needMirroring() {
        return mAutoMirrored
                && DrawableCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    private int getColorFromAttrRes(int attr, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[] {attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    private float getFloatFromAttrRes(int attrRes, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[] {attrRes});
        try {
            return a.getFloat(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static Animator createIndeterminate(Object target) {

        ObjectAnimator trimPathStartAnimator = ObjectAnimator.ofFloat(target, "trimPathStart", 0,
                0.75f);
        trimPathStartAnimator.setDuration(1333);
        trimPathStartAnimator.setInterpolator(TRIM_PATH_START.INSTANCE);
        trimPathStartAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator trimPathEndAnimator = ObjectAnimator.ofFloat(target, "trimPathEnd", 0,
                0.75f);
        trimPathEndAnimator.setDuration(1333);
        trimPathEndAnimator.setInterpolator(TRIM_PATH_END.INSTANCE);
        trimPathEndAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator trimPathOffsetAnimator = ObjectAnimator.ofFloat(target, "trimPathOffset", 0,
                0.25f);
        trimPathOffsetAnimator.setDuration(1333);
        trimPathOffsetAnimator.setInterpolator(LINEAR.INSTANCE);
        trimPathOffsetAnimator.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(trimPathStartAnimator, trimPathEndAnimator,
                trimPathOffsetAnimator);
        return animatorSet;
    }

    public static Animator createIndeterminateRotation(Object target) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(target, "rotation", 0, 720);
        rotationAnimator.setDuration(6665);
        rotationAnimator.setInterpolator(LINEAR.INSTANCE);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        return rotationAnimator;
    }

    public static class TRIM_PATH_START {
        // L 0.5,0
        // C 0.7,0 0.6,1 1,1
        private static final Path PATH_TRIM_PATH_START;
        static {
            PATH_TRIM_PATH_START = new Path();
            PATH_TRIM_PATH_START.lineTo(0.5f, 0);
            PATH_TRIM_PATH_START.cubicTo(0.7f, 0, 0.6f, 1, 1, 1);
        }
        public static final Interpolator INSTANCE =
                PathInterpolatorCompat.create(PATH_TRIM_PATH_START);
    }

    /**
     * Backported Interpolator for {@code @android:interpolator/trim_end_interpolator}.
     */
    public static class TRIM_PATH_END {
        // C 0.2,0 0.1,1 0.5,1
        // L 1,1
        private static final Path PATH_TRIM_PATH_END;
        static {
            PATH_TRIM_PATH_END = new Path();
            PATH_TRIM_PATH_END.cubicTo(0.2f, 0, 0.1f, 1, 0.5f, 1);
            PATH_TRIM_PATH_END.lineTo(1, 1);
        }
        public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_TRIM_PATH_END);
    }

    public static class LINEAR {
        public static final Interpolator INSTANCE = new LinearInterpolator();
    }
}