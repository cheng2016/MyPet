package com.cds.pet.module.order.transfer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 16:04
 * @Version: 3.0.0
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener, TransferContract.View {
    @Bind(R.id.edit_text)
    EditText editText;
    @Bind(R.id.edit_status)
    TextView editStatus;
    @Bind(R.id.confirm)
    RelativeLayout confirm;

    String orderId;
    TransferContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("订单详情");
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = editText.getText().toString();
                editStatus.setText(content.length() + "/"
                        + 300);
            }
        });
    }

    @Override
    protected void initData() {
        new TransferPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            orderId = getIntent().getStringExtra("orderId");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @OnClick(R.id.confirm)
    public void onViewClicked() {
        showProgressDilog();
        String content = editText.getText().toString();
        if(TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请填写转单说明");
        }else {
            mPresenter.transfer(orderId, content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }

    @Override
    public void transferSuccess() {
        hideProgressDilog();
        ToastUtils.showShort("转交订单成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void transferFailed() {
        hideProgressDilog();
    }

    @Override
    public void setPresenter(TransferContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
