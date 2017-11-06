package com.sinaproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinaproject.R;

import java.util.List;

/**
 * Created by DeMon on 2017/11/6.
 */

public abstract class TemplateAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int layoutId;
    private List<T> list;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_EMPTY = 0;
    private int TYPE_MAX = 2;//数据加载完成显示底部
    private LayoutInflater mInflater;
    private boolean max = false;

    public TemplateAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View view = mInflater.inflate(R.layout.list_emptyview, parent, false);
            return new DataHolder(view);
        } else if (viewType == TYPE_MAX) {
            View view = mInflater.inflate(R.layout.list_maxdata, parent, false);
            return new DataHolder(view);
        } else {
            View view = mInflater.inflate(layoutId, parent, false);
            MyViewHolder holder = new MyViewHolder(context, view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            convert((MyViewHolder) holder, position, list.get(position));
        }
    }

    //抽象方法回调
    public abstract void convert(MyViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        if (max) {//如果没有更多数据，则+1用于显示底部
            return list.size() + 1;
        }
        return list.size() == 0 ? 1 : list.size();//list为空，仍返回1，用于加载空数据视图
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            return TYPE_EMPTY;
        }
        if (position == getItemCount() - 1 && max) {
            return TYPE_MAX;
        }
        return TYPE_ITEM;
    }

    /**
     * 选中刷新当前位置
     *
     * @param position
     * @return
     */
    public int setSelected(int position) {
        notifyDataSetChanged();
        notifyItemChanged(position);
        return position;
    }

    /**
     * 判断是否加载完成
     *
     * @param max
     */
    public void isMax(boolean max) {
        this.max = max;
    }
}
