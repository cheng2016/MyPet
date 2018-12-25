package com.cds.pet.module.order.diagnostic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.data.entity.Diagnostics;
import com.cds.pet.data.entity.OrderInfo;

import java.util.List;

import butterknife.Bind;


/**
 * @Author: chengzj
 * @CreateDate: 2018/12/14 16:14
 * @Version: 3.0.0
 * <p>
 * 诊断报告详情
 */
public class DiagnosticReportActivity extends BaseActivity implements View.OnClickListener, DiagnosticReportContract.View {
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    String type;
    String orderId;

    DiagnosticReportContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_diagnostic_report;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("订单");
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new DiagnosticReportPresenter(this);
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

    @Override
    public void setPresenter(DiagnosticReportContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getOrderInfoSuccess(OrderInfo resp) {
        mLoadingView.showContent();
        if (resp != null) {
            List<Diagnostics> list = resp.getDiagnostics();
            if (list != null && list.size() > 0) {
                viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), new Fragment[list.size()], resp));
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int arg0) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
            }
        }
    }

    @Override
    public void getOrderInfoFailed() {
        mLoadingView.showError();
    }

    @Override
    public void confirmReceiptSuccess() {

    }


    public class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments;

        private OrderInfo orderInfo;

        public PagerAdapter(FragmentManager fm, Fragment[] fragments, OrderInfo orderInfo) {
            super(fm);
            this.mFragments = fragments;
            this.orderInfo = orderInfo;
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                mFragments[position] = DiagnosticReportFragment.newInstance(orderInfo, position);
            }
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}
