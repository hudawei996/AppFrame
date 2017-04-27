package com.vanke.libvanke.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanke.libvanke.varyview.IInteractorView;
import com.vanke.libvanke.varyview.InteractorViewHandler;
import com.vanke.libvanke.varyview.VaryViewHelperController;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Papa on 2016/6/1.
 */
public abstract class BaseDialogFragment extends DialogFragment
        implements IInteractorView {


    /**
     * context
     */
    protected Context mContext = null;


    protected VaryViewHelperController mVaryViewHelperController = null;
    protected InteractorViewHandler mInteractorViewHandler;
    protected Unbinder mUnbinder;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBindEventBusHere()) {
            //EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container,false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view !=null)
            mUnbinder = ButterKnife.bind(this, view);
        mInteractorViewHandler = new InteractorViewHandler(getActivity());
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
            mInteractorViewHandler.setInteractorHandler(mVaryViewHelperController);
        }

        initViewsAndEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder!=null)
            mUnbinder.unbind();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBindEventBusHere()) {
            //EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();


    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();


    /**
     * is bind eventBus
     *
     * @return
     */
    protected  boolean isBindEventBusHere(){
        return false;
    }


    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        mInteractorViewHandler.showToast(msg);
    }

    // 展示进度框
    @Override
    public void showProgressDialog() {
        mInteractorViewHandler.showProgressDialog();
    }

    // 关闭进度框
    @Override
    public void dismissProgressDialog() {
        mInteractorViewHandler.dismissProgressDialog();
    }



    /**
     * toggle show loading
     */
    @Override
    public void showLoading(String msg) {
        mInteractorViewHandler.showLoading(msg);
    }

    /**
     * toggle show empty
     *
     * @param info
     * @param imgResId
     * @param btnText
     */
    @Override
    public void showEmpty(String msg, String info, int imgResId, View.OnClickListener
            onClickListener, String btnText) {
        mInteractorViewHandler.showEmpty(msg, "", 0, onClickListener, btnText);
    }

    /**
     * toggle show error
     *
     * @param info
     * @param imgResId
     */
    @Override
    public void showError(String msg, String info, int imgResId, View.OnClickListener
            onClickListener) {
        mInteractorViewHandler.showError(msg, "", 0, onClickListener);
    }


    @Override
    public void restore() {
        mInteractorViewHandler.restore();
    }
}
