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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class VaryViewHelper implements IVaryViewHelper {
	private View view;
	private ViewGroup parentView;
	private int viewIndex;
	private ViewGroup.LayoutParams params;
	private View currentView;

	public VaryViewHelper(View view) {
		super();
		this.view = view;
	}

	private void init() {
		//获得布局参数
		params = view.getLayoutParams();
		//获得父布局：如果父布局不为空，获取父布局；如果为空设置content为父布局
		if (view.getParent() != null) {
			parentView = (ViewGroup) view.getParent();
		} else {
			parentView = (ViewGroup) view.getRootView().findViewById(android.R.id.content);
		}

		//获得子view的数量
		int count = parentView.getChildCount();
		//找到对应index的子view
		for (int index = 0; index < count; index++) {
			if (view == parentView.getChildAt(index)) {
				viewIndex = index;
				break;
			}
		}
		currentView = view;
	}

	/**
	 * 获得当前view
	 * @return
	 */
	@Override
	public View getCurrentLayout() {
		return currentView;
	}

	@Override
	public void restoreView() {
		showLayout(view);
	}

	@Override
	public void showLayout(View view) {
		if (parentView == null) {
			init();
		}
		this.currentView = view;
		// 如果已经是那个view，那就不需要再进行替换操作了
		if (parentView.getChildAt(viewIndex) != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			parentView.removeViewAt(viewIndex);
			parentView.addView(view, viewIndex, params);
		}
	}

	@Override
	public View inflate(int layoutId) {
		return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
	}

	@Override
	public Context getContext() {
		return view.getContext();
	}

	@Override
	public View getView() {
		return view;
	}
}
