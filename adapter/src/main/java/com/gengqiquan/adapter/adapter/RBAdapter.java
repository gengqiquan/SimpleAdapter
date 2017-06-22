package com.gengqiquan.adapter.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.gengqiquan.adapter.interfaces.BAdapter;
import com.gengqiquan.adapter.interfaces.Converter;
import com.gengqiquan.adapter.viewholder.RViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * gengqiquan2016年6月3日16:58:44
 */
public class RBAdapter<T> extends Adapter<RViewHolder> implements BAdapter<Adapter<RViewHolder>> {

    public static final int TYPE_HEAD = -2;
    public static final int TYPE_FOOTER = -3;
    public static final int TYPE_NORMAL = -4;

    List<View> mHeaderViews = new ArrayList<>();
    List<View> mFooterViews = new ArrayList<>();

    private Context mContext;
    private List<? super T> mList;
    private int mItemLayoutId;
    Converter<? super T> mConverter;

    public RBAdapter(Context context) {
        this(context, null);
    }

    public RBAdapter(Context context, List list) {
        this(context, list, 0);
    }

    public RBAdapter(Context context, List list, int itemLayoutId) {
        this.mContext = context;

        if (itemLayoutId == 0)
            itemLayoutId = new LinearLayout(context).getId();
        this.mItemLayoutId = itemLayoutId;

        if (list == null) {
            list = new ArrayList<>();
        }
        this.mList = list;

    }

    public RBAdapter<T> list(List list) {
        this.mList = list;
        return this;
    }

    public RBAdapter<T> layout(int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        return this;
    }

    public RBAdapter<T> bindViewData(Converter<? super T> converter) {
        this.mConverter = converter;
        return this;
    }

    @Override
    public List<? super T> getList() {
        return this.mList;
    }

    @Override
    public void appendList(List list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Adapter<RViewHolder> getAdapter() {
        return this;
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void addList(List list2) {
        this.mList.addAll(list2);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mList.size() + mHeaderViews.size() + mFooterViews.size();

    }

    @Override
    public RBAdapter<T> addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
        return this;
    }

    @Override
    public RBAdapter<T> addFooterView(View footerView) {
        this.mFooterViews.add(footerView);
        return this;
    }

    public List<View> getmHeaderViews() {
        return mHeaderViews;
    }

    public List<View> getmFooterViews() {
        return mFooterViews;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        if (position >= mHeaderViews.size() && position < mHeaderViews.size() + mList.size())
            mConverter.convert((RViewHolder) holder, (T) mList.get(position - mHeaderViews.size()));
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int type = decodeViewType(viewType);
        switch (type) {
            case TYPE_HEAD:
                return new RViewHolder(mHeaderViews.get(getViewTypePosition(viewType)));
            case TYPE_FOOTER:
                return new RViewHolder(mFooterViews.get(getViewTypePosition(viewType)));
            default:
                return RViewHolder.get(mContext, parent, mItemLayoutId, getViewTypePosition(viewType));

        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (position < mHeaderViews.size()) {
                        return (gridLayoutManager.getSpanCount());
                    } else if (position >= mHeaderViews.size() + mList.size()) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());

        } else {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onViewAttachedToWindow(RViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (position >= mHeaderViews.size() && position < mHeaderViews.size() + mList.size()) {
            super.onViewAttachedToWindow(holder);
        } else {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取视图类型
     *
     * @author gengqiquan
     * @date 2017/6/12 下午5:33
     */
    @Override
    public int getItemViewType(int position) {
        if (position < mHeaderViews.size()) {
            return encodedViewType(position, TYPE_HEAD);
        } else if (position >= mHeaderViews.size() + mList.size()) {
            return encodedViewType(position, TYPE_FOOTER);
        } else
            return encodedViewType(position, TYPE_NORMAL);
    }

    /**
     * 更据type编码
     *
     * @author gengqiquan
     * @date 2017/6/12 下午5:31
     */
    int encodedViewType(int position, int type) {
        int viewType = type * 1000 - position;
        return viewType;

    }

    /**
     * 解码得到type
     *
     * @author gengqiquan
     * @date 2017/6/12 下午5:31
     */
    int decodeViewType(int viewType) {
        if (viewType <= TYPE_NORMAL * 1000) {
            return TYPE_NORMAL;
        }
        if (viewType <= TYPE_FOOTER * 1000)
            return TYPE_FOOTER;
        return TYPE_HEAD;
    }

    /**
     * 解码得到实际的对应类型列表的位置
     *
     * @author gengqiquan
     * @date 2017/6/12 下午5:32
     */
    int getViewTypePosition(int viewType) {
        if (viewType <= TYPE_NORMAL * 1000) {
            return -(viewType - TYPE_NORMAL * 1000) - mHeaderViews.size();
        }
        if (viewType <= TYPE_FOOTER * 1000)
            return -(viewType - TYPE_FOOTER * 1000) - mHeaderViews.size() - mList.size();
        return -(viewType - TYPE_HEAD * 1000);
    }


}
