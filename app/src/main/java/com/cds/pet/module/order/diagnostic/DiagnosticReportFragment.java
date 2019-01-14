package com.cds.pet.module.order.diagnostic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
import com.cds.pet.data.entity.Diagnostics;
import com.cds.pet.data.entity.OrderInfo;
import com.cds.pet.data.entity.Symptom;
import com.cds.pet.module.adapter.FeesAdapter;
import com.cds.pet.module.order.open.OpenOrderActivity;
import com.cds.pet.util.AppManager;
import com.cds.pet.util.Utils;
import com.cds.pet.util.picasso.PicassoCircleTransform;
import com.cds.pet.view.CustomDialog;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/14 16:16
 * @Version: 3.0.0
 * <p>
 * 诊断报告详情
 */
public class DiagnosticReportFragment extends BaseFragment implements DiagnosticReportContract.View, View.OnClickListener {
    @Bind(R.id.previous_btn)
    ImageView previousBtn;
    @Bind(R.id.indicator_tv)
    TextView indicatorTv;
    @Bind(R.id.next_btn)
    ImageView nextBtn;
    @Bind(R.id.head_img)
    AppCompatImageView headImg;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.ratingbar)
    ScaleRatingBar ratingbar;
    @Bind(R.id.diagnostic_no)
    TextView diagnosticNo;
    @Bind(R.id.diagnostic_create_time)
    TextView diagnosticCreateTime;
    @Bind(R.id.diagnostic_report)
    TextView diagnosticReport;
    @Bind(R.id.fee_list)
    ListView feeListView;
    @Bind(R.id.subtotal)
    TextView subtotal;
    @Bind(R.id.detail_list)
    ListView detailListView;
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
    @Bind(R.id.confirm_text)
    AppCompatTextView confirmText;
    @Bind(R.id.confirm)
    RelativeLayout confirm;

    @Bind(R.id.cure_cost)
    TextView cureCost;
    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.diagnostic_pay_way)
    TextView diagnosticPayWay;
    @Bind(R.id.diagnostic_pay_time)
    TextView diagnosticPayTime;
    @Bind(R.id.diagnostic_pay_time_layout)
    RelativeLayout diagnosticPayTimeLayout;
    @Bind(R.id.diagnostic_need_review)
    TextView diagnosticNeedReview;

    FeesAdapter adapter;

    OrderInfo orderInfo;

    Diagnostics mDiagnostics;

    int index;

    boolean needPay = false;

    DiagnosticReportContract.Presenter mPresenter;

    public static DiagnosticReportFragment newInstance(OrderInfo bean, int position) {
        DiagnosticReportFragment fragment = new DiagnosticReportFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", bean);
        bundle.putInt("index", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diagnostic_report;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.confirm).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new DiagnosticReportPresenter(this);
        orderInfo = (OrderInfo) getArguments().getSerializable("orderInfo");
        index = getArguments().getInt("index");
        reloadData(orderInfo);
    }

    public void reloadData(OrderInfo resp) {
        mDiagnostics = resp.getDiagnostics().get(index);
        if (!TextUtils.isEmpty(mDiagnostics.getDoctor_head_img())) {
            Picasso.with(getActivity())
                    .load(mDiagnostics.getDoctor_head_img())
                    .error(R.mipmap.doctor_loginportraits)
                    .transform(new PicassoCircleTransform())
                    .into(headImg);
        }
        nickname.setText(mDiagnostics.getDoctor_name());
        ratingbar.setRating(Float.parseFloat(mDiagnostics.getRating()));

        diagnosticNo.setText(mDiagnostics.getNo());
        diagnosticPayTime.setText(mDiagnostics.getPay_time());
        diagnosticCreateTime.setText(mDiagnostics.getCreate_time());
        diagnosticReport.setText(mDiagnostics.getResult());

        adapter = new FeesAdapter(getActivity());
        feeListView.setAdapter(adapter);
        if (mDiagnostics != null && mDiagnostics.getFees_list() != null && mDiagnostics.getFees_list().size() > 0) {
            adapter.setDataList(mDiagnostics.getFees_list());
            Utils.calListViewWidthAndHeight(feeListView);
        }
//        CharSequence text = getString(R.string.viewpager_indicator,
//                index + 1, resp.getDiagnostics().size());
        indicatorTv.setText(String.format("诊断报告%1$d/%2$d", index + 1, resp.getDiagnostics().size()));
        diagnosticPayWay.setText(mDiagnostics.getPay_type_value());

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

        subtotal.setText("¥" + mDiagnostics.getFees_total());
        cureCost.setText("¥" + mDiagnostics.getPrice());
        total.setText("¥" + mDiagnostics.getTotal_price());

        confirm.setVisibility(View.VISIBLE);
        if (mDiagnostics.getPay_state() == 0) {
            needPay = true;
            confirmText.setText("用户已线下支付");
            diagnosticPayTimeLayout.setVisibility(View.GONE);
            if (mDiagnostics.getNeed_review() == 0) {
                diagnosticNeedReview.setText("不需要复诊");
            } else if (mDiagnostics.getNeed_review() == 1) {
                diagnosticNeedReview.setText(mDiagnostics.getReview_time());
            }
        } else if (mDiagnostics.getPay_state() == 1) {
            needPay = false;
            diagnosticPayTimeLayout.setVisibility(View.VISIBLE);
            if (mDiagnostics.getNeed_review() == 0) {//不需要复诊
                confirm.setVisibility(View.GONE);
                diagnosticNeedReview.setText("不需要复诊");
            } else if (mDiagnostics.getNeed_review() == 1) {//需要复诊
                confirmText.setText("复诊开单");
                diagnosticNeedReview.setText(mDiagnostics.getReview_time());
                if("5".equals(resp.getState_key())){
                    confirm.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void getOrderInfoSuccess(OrderInfo resp) {
        orderInfo = resp;
        reloadData(resp);
    }

    @Override
    public void getOrderInfoFailed() {

    }

    @Override
    public void confirmReceiptSuccess() {
        ToastUtils.showShort("请求用户支付成功");
        confirm.setVisibility(View.GONE);
        mPresenter.getOrderInfo(orderInfo.getAppoint_id(), orderInfo.getState());
    }

    @Override
    public void setPresenter(DiagnosticReportContract.Presenter presenter) {
        mPresenter = presenter;
    }

    CustomDialog customDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (needPay) {
                    if (customDialog == null) {
                        customDialog = new CustomDialog(AppManager.getInstance().getTopActivity())
                                .setTitle("是否确认已收款？")
                                .setMessage("请确保用户已通过现金或其他方式支付了足额的费用。")
                                .setPositiveButton("是", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mPresenter.confirmReceipt(orderInfo.getAppoint_id(), mDiagnostics.getId());
                                    }
                                });
                    }
                    if (!customDialog.isShowing()) {
                        customDialog.show();
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), OpenOrderActivity.class);
                    intent.putExtra("orderId", orderInfo.getAppoint_id());
                    getActivity().startActivityForResult(intent, 101);
                }
                break;
            default:
                break;
        }
    }
}
