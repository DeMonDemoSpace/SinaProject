package com.sinaproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinaproject.R;
import com.sinaproject.adapter.EndlessRecyclerOnScrollListener;
import com.sinaproject.adapter.MyViewHolder;
import com.sinaproject.adapter.TemplateAdapter;
import com.sinaproject.contract.CommentContract;
import com.sinaproject.contract.Presenter.CommentPresenter;
import com.sinaproject.data.Comments;
import com.sinaproject.data.Constant;
import com.sinaproject.util.DateUtil;
import com.sinaproject.util.GlideUtil;
import com.sinaproject.util.MapUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeMon on 2017/11/4.
 */

public class CommentFragment extends Fragment implements CommentContract.View {
    private static final String TAG = "CommentFragment";
    @BindView(R.id.rv_weibo)
    RecyclerView rvWeibo;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    TextView toolbar;
    private View view;
    public static CommentFragment instance = null;
    private CommentPresenter presenter;
    private List<Comments> list = new ArrayList<>();
    private TemplateAdapter<Comments> adapter;
    private LinearLayoutManager linearLayoutManager;
    private int size;

    public static CommentFragment getInstance() {
        if (instance == null) {
            instance = new CommentFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weibo, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setText("评论");
        init();
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvWeibo.setLayoutManager(linearLayoutManager);
        presenter = new CommentPresenter(getActivity(), this);
        Map<String, Object> map = MapUtil.getMap();
        map.put("count", 10);
        presenter.getComment(map);
        adapter = new TemplateAdapter<Comments>(getActivity(), R.layout.list_comments, list) {
            @Override
            public void convert(MyViewHolder holder, int position, Comments comments) {
                CircleImageView icon = holder.getView(R.id.img_icon);
                GlideUtil.setImage(getActivity(), comments.getUser().getAvatar_hd(), icon);
                holder.setText(R.id.tv_name, comments.getUser().getScreen_name());
                Date startDate = new Date(comments.getCreated_at());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(startDate);
                holder.setText(R.id.tv_time, DateUtil.getInterval(date));
                holder.setText(R.id.tv_text, comments.getText());
                if (comments.getStatus().getPic_urls().size() > 0) {
                    ImageView image = holder.getView(R.id.status_image);
                    image.setVisibility(View.VISIBLE);
                    GlideUtil.setImage(getActivity(), comments.getStatus().getPic_urls().get(0).getThumbnail_pic(), image);
                }
                holder.setText(R.id.status_text, comments.getStatus().getText());
            }
        };
        rvWeibo.setAdapter(adapter);
        rvWeibo.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (size == 10) {
                    Map<String, Object> map = MapUtil.getMap();
                    map.put("count", 10);
                    map.put("page", currentPage);
                    presenter.getComment(map);
                } else {
                    adapter.isMax(true);
                    adapter.notifyDataSetChanged();
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
                        list.clear();
                        Map<String, Object> map = MapUtil.getMap();
                        map.put("count", 10);
                        presenter.getComment(map);
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
        List<Comments> newList = new Gson().fromJson(s, new TypeToken<List<Comments>>() {
        }.getType());
        list.addAll(newList);
        size = newList.size();
        adapter.notifyDataSetChanged();
    }
}
