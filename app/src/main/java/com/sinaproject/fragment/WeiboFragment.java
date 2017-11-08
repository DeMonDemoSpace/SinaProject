package com.sinaproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sinaproject.R;
import com.sinaproject.adapter.EndlessRecyclerOnScrollListener;
import com.sinaproject.adapter.ImageAdapter;
import com.sinaproject.adapter.MyViewHolder;
import com.sinaproject.adapter.TemplateAdapter;
import com.sinaproject.contract.Presenter.WeiboPresenter;
import com.sinaproject.contract.WeiboContract;
import com.sinaproject.data.WeiBo;
import com.sinaproject.data.WeiBo_Status;
import com.sinaproject.util.DateUtil;
import com.sinaproject.util.GlideUtil;
import com.sinaproject.util.MapUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DeMon on 2017/11/4.
 */

public class WeiboFragment extends Fragment implements WeiboContract.View {
    private static final String TAG = "WeiboFragment";
    @BindView(R.id.toolbar)
    TextView toolbar;
    @BindView(R.id.rv_weibo)
    RecyclerView rvWeibo;
    Unbinder unbinder;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private View view;
    public static WeiboFragment instance = null;
    private WeiboPresenter weiboPresenter;
    private TemplateAdapter<WeiBo_Status> weiboAapter;
    private List<WeiBo_Status> statusesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int size;

    public static WeiboFragment getInstance() {
        if (instance == null) {
            instance = new WeiboFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weibo, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setText("关注");
        init();
        return view;
    }

    private void init() {
        weiboPresenter = new WeiboPresenter(getActivity(), this);
        Map<String, Object> map = MapUtil.getMap();
        map.put("count", 10);
        weiboPresenter.getWeibo(map);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvWeibo.setLayoutManager(linearLayoutManager);

        weiboAapter = new TemplateAdapter<WeiBo_Status>(getActivity(), R.layout.list_weibo, statusesList) {
            @Override
            public void convert(MyViewHolder holder, int position, WeiBo_Status weiBo_status) {
                RecyclerView rv_img = holder.getView(R.id.rv_img);
                StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
                rv_img.setLayoutManager(sgm);
                WeiBo_Status.User user = weiBo_status.getUser();
                GlideUtil.setImage(getActivity(), user.getAvatar_hd(), (ImageView) holder.getView(R.id.img_icon));
                holder.setText(R.id.tv_name, user.getScreen_name());
                Date startDate = new Date(weiBo_status.getCreated_at());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(startDate);
                holder.setText(R.id.tv_time, DateUtil.getInterval(date));
                holder.setText(R.id.tv_text, weiBo_status.getText());
                ImageAdapter imageAdapter = new ImageAdapter(getActivity(), weiBo_status.getPic_urls());
                rv_img.setAdapter(imageAdapter);

                if (weiBo_status.getRetweeted_status() != null && holder.getAdapterPosition() == position) {
                    LinearLayout linearLayout = holder.getView(R.id.re_layout);
                    linearLayout.setVisibility(View.VISIBLE);
                    WeiBo_Status retweeted_status = weiBo_status.getRetweeted_status();
                    WeiBo_Status.User retweeted_user = retweeted_status.getUser();
                    Log.i(TAG, "convert: " + retweeted_status.getText());
                    holder.setText(R.id.tv_re_text, "@" + retweeted_user.getScreen_name() + "：" + retweeted_status.getText());
                    RecyclerView rv_re_img = holder.getView(R.id.rv_re_img);
                    StaggeredGridLayoutManager sgm1 = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
                    rv_re_img.setLayoutManager(sgm1);
                    ImageAdapter adapter = new ImageAdapter(getActivity(), retweeted_status.getPic_urls());
                    rv_re_img.setAdapter(adapter);
                }

                holder.setText(R.id.tv_forward, "转发:" + weiBo_status.getReposts_count());
                holder.setText(R.id.tv_comment, "评论:" + weiBo_status.getComments_count());
                holder.setText(R.id.tv_innocuous, "点赞:" + weiBo_status.getAttitudes_count());
            }
        };

        rvWeibo.setAdapter(weiboAapter);
        rvWeibo.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (size == 10) {
                    Map<String, Object> map = MapUtil.getMap();
                    map.put("count", 10);
                    map.put("page", currentPage);
                    weiboPresenter.getWeibo(map);
                } else {
                    weiboAapter.isMax(true);
                    weiboAapter.notifyDataSetChanged();
                }

            }
        });

        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        statusesList.clear();
                        Map<String, Object> map = MapUtil.getMap();
                        map.put("count", 10);
                        weiboPresenter.getWeibo(map);
                        swipe.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void result(String s) {
        WeiBo weiBo = new Gson().fromJson(s, WeiBo.class);
        statusesList.addAll(weiBo.getStatuses());
        size = weiBo.getStatuses().size();
        weiboAapter.notifyDataSetChanged();
    }
}
