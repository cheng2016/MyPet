package com.cds.pet.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.data.entity.SMessage;
import com.cds.pet.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Chengzj
 * @date 2018/9/29 15:58
 */
public class MessageAdapter extends BaseAdapter {
    Context context;
    List<SMessage> mDataList = new ArrayList<>();

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public MessageAdapter(Context context, List<SMessage> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    public List<SMessage> getDataList() {
        return mDataList;
    }

    public void setDataList(List<SMessage> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (mDataList != null && !mDataList.isEmpty()) {
            SMessage bean = mDataList.get(index);
            holder.titleTv.setText(bean.getTitle());
            holder.contentTv.setText(bean.getContent());
            holder.timeTv.setText(DateUtils.parseMillisToString(Long.parseLong(bean.getTailtime())));
        }
        return view;
    }

    class ViewHolder {
        @Bind(R.id.title_tv)
        TextView titleTv;
        @Bind(R.id.content_tv)
        TextView contentTv;
        @Bind(R.id.detail_tv)
        TextView detailTv;
        @Bind(R.id.time_tv)
        TextView timeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
