package com.love_cookies.any_image.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.love_cookies.any_image.R;
import com.love_cookies.any_image.app.AnyImageApplication;
import com.love_cookies.any_image.config.AppConfig;
import com.love_cookies.any_image.model.bean.ImageBean;
import com.love_cookies.any_image.presenter.ImagePresenter;
import com.love_cookies.any_image.view.interfaces.IImageView;
import com.love_cookies.cookie_library.activity.BaseActivity;
import com.love_cookies.cookie_library.adapter.CommonRecyclerAdapter;
import com.love_cookies.cookie_library.adapter.CommonRecyclerViewHolder;
import com.love_cookies.cookie_library.adapter.OnRecyclerItemClickListener;
import com.love_cookies.cookie_library.utils.ScreenUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiekun on 2016/8/15 0015.
 *
 * 主页
 */
@ContentView(R.layout.activity_main)
public class ImageActivity extends BaseActivity implements IImageView {

    @ViewInject(R.id.tool_bar)
    private Toolbar toolbar;
    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.recycler_view)
    private RecyclerView recyclerView;
    private CommonRecyclerAdapter recyclerAdapter;

    private List<ImageBean.ImagesBean> images = new ArrayList<>();

    private ImagePresenter imagePresenter = new ImagePresenter(this);

    /**
     * 初始化控件
     * @param savedInstanceState
     */
    @Override
    public void initWidget(Bundle savedInstanceState) {
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImageList();
            }
        });
        swipeRefreshLayout.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 24));
        getImageList();
    }

    /**
     * 控件点击
     * @param view
     */
    @Override
    public void widgetClick(View view) {

    }

    /**
     * 获取图片列表
     */
    @Override
    public void getImageList() {
        swipeRefreshLayout.setRefreshing(true);
        imagePresenter.getImageList();
    }

    /**
     * 获取图片列表成功
     * @param imageBean
     */
    @Override
    public void getImageListSuccess(ImageBean imageBean) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerAdapter = new CommonRecyclerAdapter<ImageBean.ImagesBean>(this, R.layout.item_image_recycler_veiw, images) {
            @Override
            public void setData(CommonRecyclerViewHolder holder, ImageBean.ImagesBean imagesBean) {
                x.image().bind((ImageView) holder.getView(R.id.image_view), AppConfig.IMAGE_960x540 + imagesBean.getId(), AnyImageApplication.NormalImageOptions);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", images.get(position).getId());
                turn(DetailActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });

        setImageList(imageBean);
    }

    /**
     * 获取图片列表失败
     */
    @Override
    public void getImageListFailed() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置图片列表
     * @param imageBean
     */
    @Override
    public void setImageList(ImageBean imageBean) {
        images.addAll(imageBean.getImages());
        Collections.reverse(images);
        recyclerAdapter.notifyDataSetChanged();
    }
}