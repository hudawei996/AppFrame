package com.vanke.libvanke.varyview;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.vanke.libvanke.base.ProgressDialogFragment;


public class InteractorViewHandler implements IInteractorView {

    // 内嵌式进度框处理
    protected VaryViewHelperController mVaryViewHelperController;

    private Context mContext;

    //
    public InteractorViewHandler(Context context) {
        mContext = context;
//        mExceptionHandle = new BaseResponseExceptionHandle(null);
    }

    public void setInteractorHandler(VaryViewHelperController varyViewHelperController) {
        mVaryViewHelperController = varyViewHelperController;
        //mExceptionHandle = new BaseResponseExceptionHandle(mVaryViewHelperController);
    }

    /**
     * toggle show loading
     */
    @Override
    public void showLoading(String msg) {
        if (null == mVaryViewHelperController) {
            return;
        }
        mVaryViewHelperController.showLoading(msg);
    }

    /**
     * toggle show empty
     *  @param info
     * @param imgResId
     * @param btnText
     */
    @Override
    public void showEmpty(String msg, String info, int imgResId, View.OnClickListener onClickListener, String btnText) {
        if (null == mVaryViewHelperController) {
            return;
        }
        mVaryViewHelperController.showEmpty(msg, info, imgResId, onClickListener, btnText);
    }


    /**
     * toggle show error
     *
     * @param info
     * @param imgResId
     */
    @Override
    public void showError(String msg, String info, int imgResId, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            return;
        }

        mVaryViewHelperController.showError(msg, info, imgResId, onClickListener);
    }

    @Override
    public void restore() {
        if (null == mVaryViewHelperController) {
            return;
        }
        mVaryViewHelperController.restore();
    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    // 展示进度框
    @Override
    public void showProgressDialog() {
        if (mContext instanceof FragmentActivity) {
            ProgressDialogFragment.showProgress(((FragmentActivity) mContext)
                    .getSupportFragmentManager());
        }
    }

    // 取消进度框
    @Override
    public void dismissProgressDialog() {
        if (mContext instanceof FragmentActivity) {
            ProgressDialogFragment.dismissProgress(((FragmentActivity) mContext)
                    .getSupportFragmentManager());
        }
    }
}
