package com.cds.pet.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.data.entity.Order;
import com.cds.pet.data.entity.Symptom;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 15:14
 * @Version: 3.0.0
 */
public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> mDataList = new ArrayList<>();

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public List<Order> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Order> mDataList) {
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

    String[] mVals = new String[]{"腹泻", "厌食", "怕光"};

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null || view.getTag() == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Order bean = mDataList.get(position);

        holder.orderNo.setText("预约号：" + bean.getAppoint_no());
        holder.userName.setText(bean.getUser_name());
        holder.phone.setText(bean.getPhone_number());
        holder.varieties.setText(bean.getPet_varieties());
        holder.time.setText(bean.getReservation_time());
        holder.address.setText(bean.getAddress());

        List<Symptom> list = bean.getSymptom();
        if (list != null && !list.isEmpty()) {
            mVals = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                mVals[i] = list.get(i).getName();
            }
            TagAdapter adapter = new TagAdapter<String>(mVals) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tv,
                            holder.flowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            };
            holder.flowLayout.setAdapter(adapter);
        }else {
            holder.flowLayout.setVisibility(View.GONE);
        }

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onPhoneClick(bean.getPhone_number());
                }
//                PhoneUtils.callDial(context,bean.getPhone_number());
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.orderNo)
        TextView orderNo;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.varieties)
        TextView varieties;
        @Bind(R.id.flow_layout)
        TagFlowLayout flowLayout;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnPhoneClickListener listener;

    public void setListener(OnPhoneClickListener listener) {
        this.listener = listener;
    }

    public interface OnPhoneClickListener {
        void onPhoneClick(String phone);
    }
}
