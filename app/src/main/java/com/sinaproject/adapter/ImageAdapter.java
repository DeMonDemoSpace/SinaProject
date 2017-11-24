package com.sinaproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinaproject.R;
import com.sinaproject.data.Pic_urls;
import com.sinaproject.data.WeiBo_Status;
import com.sinaproject.util.GlideUtil;

import java.util.List;

/**
 * Created by DeMon on 2017/11/6.
 */

public class ImageAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Pic_urls> list;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<Pic_urls> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_image, parent, false);
        MyViewHolder holder = new MyViewHolder(context, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GlideUtil.setImage(context, list.get(position).getThumbnail_pic(), (ImageView) holder.getView(R.id.img));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
