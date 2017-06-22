package com.gengqiquan.adapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.gengqiquan.adapter.interfaces.BAdapter;
import com.gengqiquan.adapter.interfaces.PConverter;
import com.gengqiquan.adapter.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的ListView/GridView的Adapter
 * 有position参数回调
 * @author gengqiqauan
 * @time 2015/4/22
 */
public class SPAdapter<T> extends BaseAdapter implements BAdapter<BaseAdapter> {
    private Context mContext;
    private List<? super T> list;
    protected LayoutInflater mInflater;
    private int mItemLayoutId;
    PConverter<? super T> pConverter;

    public SPAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = new ArrayList<>();
    }

    public SPAdapter(Context context, List list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = list;

    }

    public SPAdapter(Context context, List list, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = itemLayoutId;
        this.list = list;

    }

    public SPAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = itemLayoutId;
        this.list = new ArrayList();

    }

    public SPAdapter<T> list(List list) {
        this.list = list;
        return this;
    }

    public SPAdapter<T> layout(int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        return this;
    }


    public SPAdapter<T> bindPositionData(PConverter<? super T> pConverter) {
        this.pConverter = pConverter;
        return this;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public List getList() {
        // TODO Auto-generated method stub
        return this.list;
    }

    @Override
    public void appendList(List list) {
        // TODO Auto-generated method stub
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public BaseAdapter getAdapter() {
        return this;
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void addList(List list2) {
        // TODO Auto-generated method stub
        this.list.addAll(list2);
        notifyDataSetChanged();
    }

    @Override
    public BAdapter<BaseAdapter> addHeaderView(View headerView) {
        return this;
    }

    @Override
    public BAdapter<BaseAdapter> addFooterView(View footerView) {
        return this;
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return (T) this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        pConverter.convert(viewHolder, getItem(position), position);
        return viewHolder.getItemView();

    }


    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

}
