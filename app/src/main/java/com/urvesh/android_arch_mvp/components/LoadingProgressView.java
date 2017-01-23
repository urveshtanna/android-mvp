package com.urvesh.android_arch_mvp.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.urvesh.android_arch_mvp.R;
import com.urvesh.android_arch_mvp.tools.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoadingProgressView extends LinearLayout {

    @Bind(R.id.progress_wheel)
    protected ProgressWheel progressWheel;

    @Bind(R.id.progress_text)
    protected TextView mProgressText;

    public LoadingProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_load_progress, null);
        this.addView(view);
        ButterKnife.bind(this, view);
        setAttributes(attrs);
    }

    public void setProgressColor(int color)
    {
        progressWheel.setBarColor(color);
    }

    private void setAttributes(AttributeSet attributes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributes, R.styleable.LoadingProgressView, 0, 0);
        int color = typedArray.getInt(R.styleable.LoadingProgressView_loadingBarColor, ContextCompat.getColor(getContext(), R.color.colorAccent));
        String progressText = typedArray.getString(R.styleable.LoadingProgressView_progressText);
        typedArray.recycle();
        if (progressWheel != null) progressWheel.setBarColor(color);
        if (progressText != null && mProgressText != null) {
            mProgressText.setText(progressText);
            mProgressText.setVisibility(View.VISIBLE);
        }
    }

    public void setProgressWheelSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = progressWheel.getLayoutParams();
        layoutParams.width = ViewUtils.dpToPx(getContext(), width);
        layoutParams.height = ViewUtils.dpToPx(getContext(), height);
        progressWheel.setLayoutParams(layoutParams);
    }

    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }

    public void setProgressText(String progressText) {
        if (progressText != null && mProgressText != null) {
            mProgressText.setText(progressText);
            mProgressText.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressText() {
        if (mProgressText != null) {
            mProgressText.setVisibility(View.GONE);
        }
    }

    public void showProgressText() {
        if (mProgressText != null) {
            mProgressText.setVisibility(View.VISIBLE);
        }
    }
}

