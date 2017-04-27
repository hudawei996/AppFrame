package com.vanke.zhuze.module.home;

import android.view.View;
import android.widget.ListView;

import com.vanke.libvanke.adapter.CommonAdapter;
import com.vanke.libvanke.adapter.ViewHolder;
import com.vanke.libvanke.base.BaseLazyFragment;
import com.vanke.libvanke.data.HttpManager;
import com.vanke.libvanke.net.HttpResult;
import com.vanke.libvanke.net.RxSubscribe;
import com.vanke.zhuze.R;
import com.vanke.zhuze.data.api.ApiService;
import com.vanke.zhuze.model.response.CommunityPostsResponse;
import com.vanke.zhuze.model.response.Post;

import butterknife.BindView;

/**
 * User: PAPA
 * Date: 2017-04-01
 */

public class HomeFragment extends BaseLazyFragment {


    @BindView(R.id.list_view)
    ListView mListView;

    CommonAdapter<Post> mAdapter;

    @Override
    protected void initViewsAndEvents() {
        mAdapter = new CommonAdapter<Post>(getContext(), R.layout.list_item) {
            @Override
            protected void convert(ViewHolder viewHolder, Post item, int position) {
                viewHolder.setText(R.id.text_view, item.content);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFirstUserVisible() {
        loadData();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mListView;
    }


    private void loadData() {
        ApiService service = HttpManager.get().getApi(ApiService.class);
        mRxManager.addSubscription(service.getPost(1, 20),
                new RxSubscribe<HttpResult<CommunityPostsResponse>>(this) {
                    @Override
                    protected void onSuccess(HttpResult<CommunityPostsResponse> listHttpResult) {
                        mAdapter.replaceAll(listHttpResult.getData().items);
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }


}
