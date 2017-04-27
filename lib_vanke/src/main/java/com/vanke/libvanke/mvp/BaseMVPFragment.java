package com.vanke.libvanke.mvp;

import android.os.Bundle;

import com.vanke.libvanke.base.BaseLazyFragment;
import com.vanke.libvanke.util.TUtil;

/**
 * User: PAPA
 * Date: 2017-03-31
 */

public abstract class BaseMVPFragment<P extends BasePresenter> extends BaseLazyFragment {

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.setView(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();

    }
}
