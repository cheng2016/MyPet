package com.cds.pet.module.order.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
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
 * @CreateDate: 2019/1/7 16:11
 * @Version: 3.0.0
 */
public class OrderInfoFragment extends BaseFragment implements View.OnClickListener, OrderInfoContract.View {
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

    public static OrderInfoFragment newInstance(OrderInfo bean) {
        OrderInfoFragment fragment = new OrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_info;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        transfer.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new OrderInfoPresenter(this);
        if (getArguments() != null) {
            OrderInfo bean = (OrderInfo) getArguments().getSerializable("orderInfo");
            if (bean != null) {
                getOrderInfoSuccess(bean);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.transfer:
                intent.setClass(getActivity(), TransferActivity.class);
                intent.putExtra("orderId", orderId);
                startActivityForResult(intent, 100);
                break;
            case R.id.confirm:
                if (type.equals(Constant.ORDER_TYPE_ORDER_RECEIVED)) {
                    intent.setClass(getActivity(), OpenOrderActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivityForResult(intent, 101);
                } else if (type.equals(Constant.ORDER_TYPE_WAITING_ORDER)
                        || type.equals(Constant.ORDER_TYPE_APPOINTMENT_SUCCESS)) {
                    showProgressDilog();
                    mPresenter.acceptOrder(orderId);
                    confirm.setClickable(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    @Override
    public void getOrderInfoSuccess(OrderInfo resp) {
        orderId = resp.getAppoint_id();
        petNickname.setText(resp.getPet_nickname());
        petVarieties.setText(resp.getPet_varieties());
        orderNo.setText(resp.getAppoint_no());
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
                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tv,
                            flowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            };
            flowLayout.setAdapter(mAdapter);
        }

        type = resp.getState_key();

        if (type.equals(Constant.ORDER_TYPE_ORDER_RECEIVED)) {//3：待上门（即已接订单）
            transfer.setVisibility(View.GONE);
            confirmText.setText("开单");
        } else if(type.equals("5") || type.equals("6")){//5：已完成、6：已取消
            stateTv.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOrderInfoFailed() {
    }

    @Override
    public void acceptOrderSuccess() {
        ToastUtils.showShort("接单成功！");
        hideProgressDilog();
        getActivity().finish();
    }

    @Override
    public void acceptOrderFailed() {
        hideProgressDilog();
        confirm.setClickable(true);
    }

    @Override
    public void setPresenter(OrderInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
