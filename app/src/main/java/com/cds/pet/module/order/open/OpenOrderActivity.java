package com.cds.pet.module.order.open;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.data.entity.FeesList;
import com.cds.pet.data.entity.OpenOrderReq;
import com.cds.pet.module.adapter.FeesAdapter;
import com.cds.pet.module.order.choose.ChooseMedicineActvity;
import com.cds.pet.util.Logger;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.cds.pet.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 17:13
 * @Version: 3.0.0
 * 开单界面
 */
public class OpenOrderActivity extends BaseActivity implements View.OnClickListener, OpenOrderContract.View {
    @Bind(R.id.report_edit)
    EditText reportEdit;
    @Bind(R.id.edit_status)
    TextView editStatus;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.subtotal)
    TextView subtotalTv;
    @Bind(R.id.consultation_fee)
    EditText consultationFeeEdit;
    @Bind(R.id.total)
    TextView totalTv;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.choose_time_btn)
    Button chooseTimeBtn;
    @Bind(R.id.confirm)
    RelativeLayout confirmBtn;

    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String userId;

    String orderId;

    FeesAdapter adapter;

    boolean needReview = true;

    String reviewTime;

    OpenOrderContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("开单");
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
        reportEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = reportEdit.getText().toString();
                editStatus.setText(content.length() + "/"
                        + 300);
            }
        });
        radioGroup.check(R.id.want_checkBox);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.want_checkBox: //需要
                        needReview = true;
                        chooseTimeBtn.setVisibility(View.VISIBLE);
                        break;
                    case R.id.unwanted_checkBox: //不需要
                        needReview = false;
                        chooseTimeBtn.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        consultationFeeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                caculateTotal();
            }
        });
        adapter = new FeesAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        new OpenOrderPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            orderId = getIntent().getStringExtra("orderId");
            userId = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_ID, "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @OnClick({R.id.choose_btn, R.id.choose_time_btn, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_btn:
                Intent intent = new Intent().setClass(this, ChooseMedicineActvity.class);
                startActivityForResult(intent, RESULT_FIRST_USER);
                break;
            case R.id.choose_time_btn:
                String timeStr = chooseTimeBtn.getText().toString();
                Calendar calendar = Calendar.getInstance();
                Date date;
                try {
                    if (!TextUtils.isEmpty(timeStr)) {
                        date = format.parse(timeStr);
                        calendar.setTime(date);
                    }
                } catch (ParseException e) {
                    Logger.e(TAG, "choose time exception：" + e);
                }
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(OpenOrderActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        reviewTime = dateFormat.format(date);
                        chooseTimeBtn.setText(format.format(date));
                    }
                }).setOutSideCancelable(true)
                        .setDate(calendar)
                        .setSubmitText("确定")
                        .setCancelText("取消")
                        .build();
                pvTime.show();
                break;
            case R.id.confirm:
                if(TextUtils.isEmpty(reportEdit.getText().toString())){
                    ToastUtils.showShort("请输入诊断报告");
                    return;
                }
                if(TextUtils.isEmpty(consultationFeeEdit.getText().toString())){
                    ToastUtils.showShort("请输入费用金额");
                    return;
                }

                OpenOrderReq req = new OpenOrderReq();
                req.setUser_id(userId);
                req.setAppoint_id(orderId);
                req.setDiagnostic_report(reportEdit.getText().toString());
                req.setDiagnostic_price(consultationFeeEdit.getText().toString());
                req.setFees_list(adapter.getDataList());
                req.setTotal_price(totalTv.getText().toString().substring(1));
                req.setNeed_review(needReview ? "1" : "0");
                req.setReview_type("2");
                if(needReview){
                    if(TextUtils.isEmpty(reviewTime)){
                        ToastUtils.showShort("请选择复诊日期");
                    }else {
                        req.setReview_time(reviewTime);
                        mPresenter.openOrder(req);
                    }
                }else {
                    mPresenter.openOrder(req);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FeesList feesList = (FeesList) data.getExtras().getSerializable("feesList");
            adapter.setDataList(feesList.getFees_list());
            Utils.calListViewWidthAndHeight(listView);
            float sum = data.getFloatExtra("subtotal", 0f);
            subtotalTv.setText("¥ " + String.format("%.2f",sum));
            caculateTotal();
        }
    }

    void caculateTotal() {
        float consultationFee;
        if (TextUtils.isEmpty(consultationFeeEdit.getText().toString())) {
            consultationFee = 0f;
        } else {
            consultationFee = Float.parseFloat(consultationFeeEdit.getText().toString());
        }
        float subtotal = Float.parseFloat(subtotalTv.getText().toString().substring(1));
        float total = consultationFee + subtotal;
        totalTv.setText("¥ " + total);
    }

    @Override
    public void setPresenter(OpenOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void openOrderSuccess() {
        ToastUtils.showShort("开单成功");
        setResult(RESULT_OK);
        finish();
    }
}
