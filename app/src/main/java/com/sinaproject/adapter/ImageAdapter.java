package com.sinaproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinaproject.R;
import com.sinaproject.activity.ImageActivity;
import com.sinaproject.data.Constant;
import com.sinaproject.data.Pic_urls;
import com.sinaproject.data.WeiBo_Status;
import com.sinaproject.util.GlideUtil;

import java.util.ArrayList;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GlideUtil.setImage(context, list.get(position).getThumbnail_pic(), (ImageView) holder.getView(R.id.img));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提取色板的图片path信息
                ArrayList<String> paths = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    paths.add(list.get(i).getThumbnail_pic());
                }
                showImage(position, paths);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 用来显示图片大图
     *
     * @param position 点击的图片的位置
     * @param items    存放图片url的数组
     */
    private void showImage(int position, ArrayList<String> items) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putStringArrayListExtra(Constant.EXTRA_IMAGE_URLS, items);
        intent.putExtra(Constant.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }
}
