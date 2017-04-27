package com.vanke.libvanke.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vanke.libvanke.base.BaseActivity;
import com.vanke.libvanke.util.TUtil;

/**
 * 个人理解的ＭＶＰ实现
 * User: PAPA
 * Date: 2017-03-31
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements
        BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.setView(this);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }
}
