package com.cds.pet.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.data.entity.Medicine;
import com.cds.pet.module.Observer;
import com.cds.pet.module.Subject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/8 11:26
 * @Version: 3.0.0
 */
public class MedicineAdapter extends BaseAdapter implements Subject {
    Context context;
    List<Medicine> mDataList = new ArrayList<>();

    Observer observer;

    @Override
    public void registerObserver(Observer o) {
        observer = o;
    }

    @Override
    public void removeObserver(Observer o) {
        observer = null;
    }

    @Override
    public void notifyObserver(int index, boolean selected, int num) {
        if (observer != null)
            observer.update(index, selected, num);
    }

    public MedicineAdapter(Context context) {
        this.context = context;
    }

    public MedicineAdapter(Context context, List<Medicine> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    public List<Medicine> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Medicine> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
//        return 5;
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
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null || view.getTag() == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_medicine, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (mDataList != null && !mDataList.isEmpty()) {
            Medicine bean = mDataList.get(position);
            holder.nameTv.setText(bean.getName());
            holder.priceTv.setText("¥" + bean.getPrice());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int num = (Integer.parseInt(holder.numTv.getText().toString()));
                    notifyObserver(position, isChecked, num);
                }
            });

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = (Integer.parseInt(holder.numTv.getText().toString()));
                    if (num > 1) {
                        num--;
                        holder.numTv.setText(String.valueOf(num));
                    }
                    notifyObserver(position, holder.checkBox.isChecked(), num);
                }
            });
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = (Integer.parseInt(holder.numTv.getText().toString()));
                    num++;
                    holder.numTv.setText(String.valueOf(num));
                    notifyObserver(position, holder.checkBox.isChecked(), num);
                }
            });
        }
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.checkBox)
        CheckBox checkBox;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.price_tv)
        TextView priceTv;
        @Bind(R.id.delete_btn)
        Button deleteBtn;
        @Bind(R.id.num_tv)
        TextView numTv;
        @Bind(R.id.add_btn)
        Button addBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}