package com.cds.pet.module.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.data.Constant;
import com.cds.pet.data.entity.OrderInfo;
import com.cds.pet.data.entity.Symptom;
import com.cds.pet.module.order.open.OpenOrderActivity;
import com.cds.pet.module.order.transfer.TransferActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;


/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 11:39
 * @Version: 3.0.0
 * 订单详情
 */
public class OrderInfoActivity extends BaseActivity implements View.OnClickListener, OrderInfoContract.View {
    @Bind(R.id.pet_img)
    AppCompatImageView petImg;
    @Bind(R.id.pet_nickname)
    TextView petNickname;
    @Bind(R.id.pet_varieties)
    TextView petVarieties;
    @Bind(R.id.flow_layout)
    TagFlowLayout flowLayout;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.order_no)
    TextView orderNo;
    @Bind(R.id.create_time)
    TextView createTime;
    @Bind(R.id.contact_name)
    TextView contactName;
    @Bind(R.id.contact_phone)
    TextView contactPhone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.reservation_time)
    TextView reservationTime;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.pay_time)
    TextView payTime;
    @Bind(R.id.pay_way)
    TextView payWay;
    @Bind(R.id.state_tv)
    TextView stateTv;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.transfer)
    RelativeLayout transfer;
    @Bind(R.id.confirm)
    RelativeLayout confirm;
    @Bind(R.id.confirm_text)
    AppCompatTextView confirmText;


    String type;
    String orderId;

    OrderInfoContract.Presenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("订单详情");
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
        transfer.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new OrderInfoPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            type = getIntent().getStringExtra("type");
            orderId = getIntent().getStringExtra("orderId");
            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(orderId)) {
                mPresenter.getOrderInfo(orderId, type);
                mLoadingView.showLoading();
                mLoadingView.setRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.getOrderInfo(orderId, type);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.transfer:
                intent.setClass(this, TransferActivity.class);
                intent.putExtra("orderId", orderId);
                startActivityForResult(intent, 100);
                break;
            case R.id.confirm:
                if (type.equals(Constant.ORDER_TYPE_ORDER_RECEIVED)) {
                    intent.setClass(this, OpenOrderActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivityForResult(intent, 101);
                } else if (type.equals(Constant.ORDER_TYPE_WAITING_ORDER)) {
                    showProgressDilog();
                    mPresenter.acceptOrder(orderId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
        if (requestCode == 101 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void getOrderInfoSuccess(OrderInfo resp) {
        mLoadingView.showContent();
        petNickname.setText(resp.getPet_nickname());
        petVarieties.setText(resp.getPet_varieties());
        orderNo.setText(resp.getAppoint_id());
        content.setText(resp.getSymptom_desc());
        createTime.setText(resp.getCreate_time());
        contactName.setText(resp.getContact_name());
        contactPhone.setText(resp.getContact_phone());
        address.setText(resp.getContact_address());
        reservationTime.setText(resp.getReservation_time());
        price.setText("¥" + resp.getOrder_cost());
        payTime.setText(resp.getPay_time());
        payWay.setText(resp.getPay_type_value());



        List<Symptom> list = resp.getSymptom();
        if (list != null && !list.isEmpty()) {
            String[] mVals = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                mVals[i] = list.get(i).getName();
            }
            TagAdapter mAdapter = new TagAdapter<String>(mVals) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(OrderInfoActivity.this).inflate(R.layout.item_tv,
                            flowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            };
            flowLayout.setAdapter(mAdapter);
        }

        if (type.equals(Constant.ORDER_TYPE_ORDER_RECEIVED)) {
            transfer.setVisibility(View.GONE);
            confirmText.setText("开单");
        } else if (type.equals(Constant.ORDER_TYPE_OTHER)) {
            bottomLayout.setVisibility(View.GONE);
        }

        if("已取消".equals(resp.getState_value()) || "已完成".equals(resp.getState_value())){
            stateTv.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        }else {
            stateTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOrderInfoFailed() {
        mLoadingView.showError();
    }

    @Override
    public void acceptOrderSuccess() {
        ToastUtils.showShort("接单成功！");
        hideProgressDilog();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void acceptOrderFailed() {
        hideProgressDilog();
    }

    @Override
    public void setPresenter(OrderInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
