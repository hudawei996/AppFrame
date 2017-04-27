package com.vanke.libvanke.base;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vanke.libvanke.R;


public class ProgressDialogFragment extends DialogFragment {

    private AnimationDrawable mFrameAnimation = null;
    private ImageView mImageView;

    public static void showProgress(FragmentManager manager) {
        ProgressDialogFragment fragment = (ProgressDialogFragment) manager.findFragmentByTag(ProgressDialogFragment.class.getSimpleName());
        FragmentTransaction ft = manager.beginTransaction();

        if (fragment != null) {
            ft.remove(fragment);
        }
        fragment = new ProgressDialogFragment();
        ft.add(fragment, ProgressDialogFragment.class.getSimpleName());
        ft.commitAllowingStateLoss();

    }

    public static void dismissProgress(FragmentManager manager) {
        if (manager == null)
            return;
        ProgressDialogFragment fragment = (ProgressDialogFragment) manager
                .findFragmentByTag(ProgressDialogFragment.class.getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            fragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    @Override
    public void onStart() {

        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    // @Override
    // public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Dialog dialog = new ProgressDialog(getActivity());
    // // Dialog dialog = super.onCreateDialog(savedInstanceState);
    // dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    // dialog.setCanceledOnTouchOutside(false);
    //
    // return dialog;
    // }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_dialog, container, false);
//        ImageView imageView = (ImageView) view.findViewById(R.id.img);
//        mFrameAnimation = (AnimationDrawable) imageView.getBackground();
//        if (mFrameAnimation != null)
//            mFrameAnimation.start();
        return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
