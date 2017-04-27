/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vanke.libvanke.varyview;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanke.libvanke.R;

/**
 * 显示帮助信息视图
 */
public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 显示错误信息
     *
     * @param msg
     * @param info
     * @param resId
     * @param onClickListener
     */
    public void showError(String msg, String info, int resId, View.OnClickListener onClickListener) {
        //TODO 2017/04/05 不明白，一个接口怎么就直接inflate()方法了？
        View layout = helper.inflate(R.layout.vary_message_layout);

        //显示错误信息
        TextView messageTv = (TextView) layout.findViewById(R.id.message_tv);
        //如果没有设置错误信息，就显示网络不可用
        if (!TextUtils.isEmpty(msg)) {
            messageTv.setText(msg);
        } else {
            messageTv.setText(helper.getContext().getResources().getString(R.string
                    .common_error_msg));
        }

        //显示信息
        TextView infoTv = (TextView) layout.findViewById(R.id.schedule_info_tv);
        if (!TextUtils.isEmpty(info)) {
            infoTv.setText(info);
        } else {
            infoTv.setText(helper.getContext().getResources().getString(R.string
                    .common_error_msg));
        }

        //设置提示图片
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(resId);

        //点击刷新按钮
        Button refreshBtn = (Button) layout.findViewById(R.id.refresh_btn);
        refreshBtn.setText(R.string.common_refresh_retry);
        if (null != onClickListener) {
            refreshBtn.setOnClickListener(onClickListener);
        }

        //helper显示设置的帮助视图
        helper.showLayout(layout);
    }

    /**
     * 显示数据为空视图
     *
     * @param msg
     * @param info
     * @param resId
     * @param onClickListener
     * @param btnText
     */
    public void showEmpty(String msg, String info, int resId, View.OnClickListener onClickListener, String btnText) {
        //实例化空视图
        View layout = helper.inflate(R.layout.vary_message_layout);

        //设置数据为空提示文案
        TextView messageTv = (TextView) layout.findViewById(R.id.message_tv);
        if (!TextUtils.isEmpty(msg)) {
            messageTv.setText(msg);
        } else {
            messageTv.setText(helper.getContext().getResources().getString(R.string
                    .common_no_data));
        }

        //设置方案信息文案
        TextView infoTv = (TextView) layout.findViewById(R.id.schedule_info_tv);
        if (!TextUtils.isEmpty(info)) {
            infoTv.setVisibility(View.VISIBLE);
            infoTv.setText(info);
        } else {
            infoTv.setVisibility(View.GONE);
        }

        //设置空视图默认图标
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if (resId > 0) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(resId);
        } else {
            imageView.setVisibility(View.GONE);
        }

        //设置刷新按钮
        Button refreshBtn = (Button) layout.findViewById(R.id.refresh_btn);
        if (!TextUtils.isEmpty(btnText)) {
            refreshBtn.setText(btnText);
        }
        if (null != onClickListener) {
            infoTv.setVisibility(View.VISIBLE);
            refreshBtn.setOnClickListener(onClickListener);
        } else {
            refreshBtn.setVisibility(View.GONE);
        }

        helper.showLayout(layout);
    }

    /**
     * 显示菊花
     *
     * @param msg
     */
    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.vary_loading_layout);
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
