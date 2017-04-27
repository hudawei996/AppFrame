package com.vanke.libvanke.varyview;

import android.view.View;

/**
 *
 */
public interface IInteractorView {

    void showLoading(String msg);

    void showEmpty(String msg, String info, int imgResId, View.OnClickListener onClickListener,
                   String btnText);

    void showError(String msg, String info, int imgResId, View.OnClickListener
            onClickListener);

    void restore();

    void showToast(String msg);

    // 展示进度条
    void showProgressDialog();

    // 关闭进度条
    void dismissProgressDialog();

}
