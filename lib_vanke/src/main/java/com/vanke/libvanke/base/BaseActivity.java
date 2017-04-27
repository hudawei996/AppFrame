package com.vanke.libvanke.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.vanke.libvanke.varyview.IInteractorView;
import com.vanke.libvanke.varyview.InteractorViewHandler;
import com.vanke.libvanke.varyview.VaryViewHelperController;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: PAPA
 * Date: 2017-03-30
 */

public abstract class BaseActivity extends AppCompatActivity implements IInteractorView{

    protected static String TAG_LOG = null;// Log tag
    /**
     * loading view controller
     */
    protected VaryViewHelperController mVaryViewHelperController = null;
    protected InteractorViewHandler mInteractorViewHandler;

    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }


    private void init(Bundle savedInstanceState){
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (isBindEventBusHere()) {
            //// TODO: 2017/3/30 后期可能考虑加入eventbus，暂时可以考虑用Rxbus实现组件间通讯
            //EventBus.getDefault().register(this);
        }

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        }

        initViewsAndEvents();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
       mUnbinder = ButterKnife.bind(this);
        mInteractorViewHandler = new InteractorViewHandler(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
            mInteractorViewHandler.setInteractorHandler(mVaryViewHelperController);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mInteractorViewHandler = new InteractorViewHandler(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
            mInteractorViewHandler.setInteractorHandler(mVaryViewHelperController);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder !=null)
            mUnbinder.unbind();
        BaseAppManager.getInstance().removeActivity(this);
        if (isBindEventBusHere()) {
            //EventBus.getDefault().unregister(this);
        }
    }


    /**
     * get bundle data
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * is bind eventBus
     *
     * @return
     */
    protected boolean isBindEventBusHere() {
        return false;
    }


    /**
     * get loading target view
     */
    protected View getLoadingTargetView() {
        return null;
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }



    protected  abstract  void initViewsAndEvents();

    protected abstract  int getContentViewLayoutID();


    @Override
    public void showLoading(String msg) {
        mInteractorViewHandler.showLoading(msg);
    }

    @Override
    public void showEmpty(String msg, String info, int imgResId, View.OnClickListener onClickListener, String btnText) {
        mInteractorViewHandler.showEmpty(msg, info, imgResId, onClickListener, btnText);
    }

    @Override
    public void showError(String msg, String info, int imgResId, View.OnClickListener onClickListener) {
        mInteractorViewHandler.showError(msg, info, imgResId, onClickListener);
    }

    @Override
    public void restore() {
        mInteractorViewHandler.restore();
    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgressDialog() {
        mInteractorViewHandler.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        mInteractorViewHandler.dismissProgressDialog();
    }
}
