package com.urvesh.android_arch_mvp.components;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.urvesh.android_arch_mvp.R;
import com.urvesh.android_arch_mvp.tools.Logger;
import com.urvesh.android_arch_mvp.tools.ViewUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseDialogFragment extends DialogFragment {

    // Dialogs can have maximum three butons
    public static final String ACTION_ONE_BTN = "actionOneBtn";
    public static final String ACTION_TWO_BTN = "actionTwoBtn";
    public static final String ACTION_THREE_BTN = "actionThreeBtn";
    public static final String TAG = "dialog";

    // Include View Type
    public static final String INCLUDE_TEXT_VIEW = "include_textview";
    public static final String INCLUDE_EDITTEXT_VIEW = "include_edittext_view";

    public DialogModel model = new DialogModel();

    @Bind(R.id.actionOneBtn)
    protected TextView actionOneBtn;

    @Bind(R.id.actionTwoBtn)
    protected TextView actionTwoBtn;

    @Bind(R.id.actionThreeBtn)
    protected TextView actionThreeBtn;

    @Bind(R.id.img_icon)
    protected ImageView imgIcon;

    @Bind(R.id.dialog_edit_text)
    protected EditText mDialogEditText;

    @Bind(R.id.dialog_progressbar)
    protected LoadingProgressView mDialogEditProgress;

    @Bind(R.id.dialog_title_text)
    protected TextView mDialogTitleText;

    @Bind(R.id.dialog_description_title_text)
    protected TextView mTitleText;

    @Bind(R.id.dialog_description_text)
    protected TextView mDescriptionText;

    @Bind(R.id.editbox_view)
    protected View promoCodeView;

    @Bind({R.id.editbox_view})
    protected List<View> mViewList;

    private HashMap<String, TextView> actionBtnTextViewByName = new HashMap<>();
    private HashMap<String, View.OnClickListener> actionBtnListenerByName = new HashMap<>();
    private IDialogFragmentListener listener;

    @SuppressLint("ValidFragment")
    public BaseDialogFragment(String includeViewType) {
        setIncludeViewType(includeViewType);
    }

    public BaseDialogFragment() {
        setIncludeViewType(INCLUDE_TEXT_VIEW);
    }

    public static BaseDialogFragment newActionInstance() {
        return new BaseDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if(dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_round_corner);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        setDialogSize();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && model.getIncludeViewType().equals(INCLUDE_EDITTEXT_VIEW))
            showKeyboard();
    }

    private void setDialogSize() {
        if (getActivity() == null)
            return;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(getDialog() != null && getDialog().getWindow() != null) {
            int dialogWidth = getDialog().getWindow().getAttributes().width;
            int dialogHeight = getDialog().getWindow().getAttributes().height;
            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
            getDialog().getWindow().setGravity(Gravity.CENTER);
        }

    }

    private void setDialogSizeFullScreen() {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_customized, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDialogContent();
    }

    protected void includeEditTextView() {
        ViewUtils.handleViewsVisibility(mViewList, promoCodeView);
    }

    private void enableAllActionButtons() {
        enableActionBtn(model.getActionNameOne(), actionOneBtn, ACTION_ONE_BTN);
        enableActionBtn(model.getActionNameTwo(), actionTwoBtn, ACTION_TWO_BTN);
        enableActionBtn(model.getActionNameThree(), actionThreeBtn, ACTION_THREE_BTN);
    }

    private void enableActionBtn(String actionBtnName, TextView actionBtn, String defaultName) {
        if (actionBtn == null) {
            return;
        }
        if (actionBtnName == null) {
            actionBtn.setVisibility(View.GONE);
            return;
        }

        actionBtnTextViewByName.put(actionBtnName, actionBtn);
        actionBtn.setClickable(true);
        enableActionListener(actionBtnName);
        actionBtn.setText(actionBtnName);
        actionBtn.setVisibility(View.VISIBLE);
    }

    private void enableActionListener(String actionName) {
        if (actionName != null) {
            TextView btn = getActionBtn(actionName);
            View.OnClickListener listener = actionBtnListenerByName.get(actionName);
            if (btn != null && listener != null) {
                btn.setOnClickListener(listener);
            }
        }
    }

    private void setActionListener(String actionName, View.OnClickListener listener) {
        actionBtnListenerByName.put(actionName, listener);
    }

    public void setTitle(String text) {
        model.setmTitle(text);
    }

    public void setDialogTitle(String text) {
        model.setmDialogTitle(text);
    }

    public void setImageIcon(String url) {
        model.setImgIcon(url);
    }

    public void setDescription(String text) {
        model.setDescription(text);
    }

    private void setIncludeViewType(String type) {
        model.setIncludeViewType(type);
    }

    public String getDialogName() {
        return model.getDialogName();
    }

    public void setDialogName(String text) {
        model.setDialogName(text);
    }

    private TextView getActionBtn(String actionBtnName) {
        return actionBtnTextViewByName.get(actionBtnName);
    }

    public void updateDialogContent() {

        if (model.getmDialogTitle() != null) {
            mDialogTitleText.setVisibility(View.VISIBLE);
            mDialogTitleText.setText(model.getmDialogTitle());
        }

        if (model.getDescription() != null) {
            mDescriptionText.setVisibility(View.VISIBLE);
            mDescriptionText.setText(model.getDescription());
        }

        if (model.getmTitle() != null) {
            mTitleText.setVisibility(View.VISIBLE);
            mTitleText.setText(model.getmTitle());
        }

        if (model.getImgIcon() != null) {
            imgIcon.setVisibility(View.VISIBLE);
            ViewUtils.loadImage(imgIcon.getContext(), model.getImgIcon(), ViewUtils.SCALE_TYPE_CENTER_CROP, imgIcon);
        }

        enableAllActionButtons();
        model.getIncludeViewType();
        switch (model.getIncludeViewType()) {
            case INCLUDE_EDITTEXT_VIEW:
                includeEditTextView();
                mDialogEditText.requestFocus();
                break;
        }
    }

    public void setPositiveButton(String actionBttnName, View.OnClickListener onClickListener) {
        model.setActionNameThree(actionBttnName);
        setActionListener(actionBttnName, onClickListener);
    }

    public void setNegativeButton(String actionBttnName, View.OnClickListener onClickListener) {
        model.setActionNameOne(actionBttnName);
        setActionListener(actionBttnName, onClickListener);
    }

    public void setNeutralButton(String actionBttnName, View.OnClickListener onClickListener) {
        model.setActionNameTwo(actionBttnName);
        setActionListener(actionBttnName, onClickListener);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {
            listener.onDialogDismiss(this);
        }
    }

    public void showProgressBar() {
        mDialogEditProgress.setVisibility(View.VISIBLE);
        mDialogEditText.setEnabled(false);
    }

    public void hideProgressBar() {
        mDialogEditProgress.setVisibility(View.GONE);
        mDialogEditText.setEnabled(true);
    }

    public void setError(String errorMsg) {
        mDialogEditText.setError(errorMsg);
    }

    public String getEditBoxText() {
        return mDialogEditText.getText().toString();
    }

    public void setDialogListener(IDialogFragmentListener callbackObj) {
        listener = callbackObj;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) {
            return;
        } else {
            try {
                super.show(manager, tag);
            } catch (IllegalStateException e) {
                Logger.exception(getClass().getName(), e);
            }
        }
    }

    @Override
    public void dismiss() {
        closeKeyboard();
        super.dismiss();
        if (mDialogEditText.getError() != null) {
            mDialogEditText.setError(null);
        }
        if (mDialogEditText.getText() != null) {
            mDialogEditText.setText("");
        }
    }

    public interface IDialogFragmentListener {
        void onDialogDismiss(BaseDialogFragment dialog);
    }

    class DialogModel {

        private String mDialogTitle;
        private String mTitle;
        private String mDescription;
        private String imgIcon;
        private String mDialogName;
        private String mActionNameOne;
        private String mActionNameTwo;
        private String mActionNameThree;
        private String mIncludeViewType;

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getmDialogTitle() {
            return mDialogTitle;
        }

        public void setmDialogTitle(String mDialogTitle) {
            this.mDialogTitle = mDialogTitle;
        }

        public String getImgIcon() {
            return imgIcon;
        }

        public void setImgIcon(String imgIcon) {
            this.imgIcon = imgIcon;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public String getDialogName() {
            return mDialogName;
        }

        public void setDialogName(String dialogName) {
            this.mDialogName = dialogName;
        }

        public String getActionNameOne() {
            return mActionNameOne;
        }

        public void setActionNameOne(String actionNameOne) {
            this.mActionNameOne = actionNameOne;
        }

        public String getActionNameTwo() {
            return mActionNameTwo;
        }

        public void setActionNameTwo(String actionNameTwo) {
            this.mActionNameTwo = actionNameTwo;
        }

        public String getActionNameThree() {
            return mActionNameThree;
        }

        public void setActionNameThree(String actionNameThree) {
            this.mActionNameThree = actionNameThree;
        }

        public String getIncludeViewType() {
            return mIncludeViewType;
        }

        public void setIncludeViewType(String includeViewType) {
            this.mIncludeViewType = includeViewType;
        }
    }

    public void showKeyboard() {
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void closeKeyboard() {
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11. ("Cannot perform this action after onSavedInstance)
    }
}
