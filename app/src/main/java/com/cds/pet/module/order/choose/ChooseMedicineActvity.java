package com.cds.pet.module.order.choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.data.entity.Fees;
import com.cds.pet.data.entity.FeesList;
import com.cds.pet.data.entity.Medicine;
import com.cds.pet.module.Observer;
import com.cds.pet.module.adapter.MedicineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 16:49
 * @Version: 3.0.0
 * <p>
 * 选择药材界面
 */
public class ChooseMedicineActvity extends BaseActivity implements View.OnClickListener, ChooseMedicineContract.View, Observer {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.subtotal_tv)
    AppCompatTextView subtotalTv;

    MedicineAdapter adapter;

    ChooseMedicineContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_medicine;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("选择药品和耗材");
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
        String subtotal = "<font color='#000000'>小计：</font>¥" + 0.0;
        subtotalTv.setText(Html.fromHtml(subtotal));
    }

    @Override
    protected void initData() {
        new ChooseMedicinePresenter(this);
        adapter = new MedicineAdapter(this);
        listView.setAdapter(adapter);
        adapter.registerObserver(this);
        mPresenter.getMedicine();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.removeObserver(this);
        mPresenter.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }

    @OnClick(R.id.confirm)
    public void onViewClicked() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("feesList", new FeesList(feesList));
        intent.putExtras(bundle);
        intent.putExtra("subtotal", sum);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void getMedicineSuccess(List<Medicine> list) {
        adapter.setDataList(list);
    }

    @Override
    public void setPresenter(ChooseMedicineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    List<Fees> feesList;
    float sum;

    @Override
    public void update(int index, boolean selected, int num) {
        adapter.getDataList().get(index).setSelected(selected);
        adapter.getDataList().get(index).setNum(num);
        adapter.setDataList(adapter.getDataList());

        feesList = new ArrayList<>();
        sum = 0F;
        for (Medicine bean : adapter.getDataList()) {
            if (bean.isSelected()) {
                sum += bean.getPrice() * bean.getNum();
                Fees fees = new Fees();
                fees.setId(bean.getId());
                fees.setName(bean.getName());
                fees.setCount(String.valueOf(bean.getNum()));
                fees.setPrice(String.valueOf(bean.getPrice()));
                feesList.add(fees);
            }
        }

        String subtotal = "<font color='#000000'>小计：</font>¥" + String.format("%.2f",sum);
        subtotalTv.setText(Html.fromHtml(subtotal));
    }
}
