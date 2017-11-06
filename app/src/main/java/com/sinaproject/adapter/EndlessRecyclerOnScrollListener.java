package com.sinaproject.adapter;

/**
 * Created by DeMon on 2017/11/6.
 */


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * Created by DeMon on 2017/7/19.
 * 继承实现RecyclerView.OnScrollListener滑动监听
 */

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    //此处设置为静态变量是因为如果在同一recyclerview需要多次进行滑动加载
    //如入库记录管理，需要根据日期分类数据，每个日期数据都可以滑动加载分页数据
    //此时需要重置两个变量的初值
    //不可为一个recyclerview绑定多个滑动事件，防止一次滑动重复多次加载
    public static int previousTotal = 0;
    public static int currentPage = 1;

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.previousTotal = 0;
        this.currentPage = 1;
    }

    /**
     * 重写监听滑动状态改变的方法
     * 滑动状态改变后才刷新数据
     *
     * @param recyclerView
     * @param newState
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        visibleItemCount = recyclerView.getChildCount();//可见数据量
        totalItemCount = mLinearLayoutManager.getItemCount();//总数据量
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem && totalItemCount >
                visibleItemCount) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    /**
     * 为防止一直滑动连续不断的加载
     * 所以不重写此方法
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }

    public abstract void onLoadMore(int currentPage);
}

