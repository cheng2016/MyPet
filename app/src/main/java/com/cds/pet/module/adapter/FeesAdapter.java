package com.cds.pet.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.data.entity.Fees;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/10 13:40
 * @Version: 3.0.0
 */
public class FeesAdapter extends BaseAdapter {
    private Context context;
    private List<Fees> mDataList = new ArrayList<>();

    public FeesAdapter(Context context) {
        this.context = context;
    }

    public List<Fees> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Fees> mDataList) {
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
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null || view.getTag() == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fees, null,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (mDataList != null && !mDataList.isEmpty()) {
            Fees bean = mDataList.get(position);
            holder.nameTv.setText(bean.getName());
            holder.priceTv.setText("¥" + bean.getPrice() + " x " + bean.getCount());
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.price_tv)
        TextView priceTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
