package com.cds.pet.module.order.list;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
import com.cds.pet.data.Constant;
import com.cds.pet.data.entity.Order;
import com.cds.pet.module.adapter.OrderAdapter;
import com.cds.pet.module.order.diagnostic.DiagnosticReportActivity;
import com.cds.pet.module.order.info.OrderInfoActivity;
import com.cds.pet.util.PermissionHelper;
import com.cds.pet.util.PhoneUtils;
import com.cds.pet.view.CustomDialog;
import com.cheng.refresh.library.PullToRefreshBase;
import com.cheng.refresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 10:10
 * @Version: 3.0.0
 */
public class OrderListFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>, OrderListContract.View, AdapterView.OnItemClickListener, OrderAdapter.OnPhoneClickListener {
    public static final int REQUEST_NUM = 10;
    @Bind(R.id.refresh_listView)
    PullToRefreshListView refreshListView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;

    ListView mListView;

    OrderAdapter adapter;

    List<Order> mDataList = new ArrayList<>();

    private boolean hasMoreData = false;//是否有更多数据
    private boolean isLoadMore = false;//是否加载更多

    private int index = -1;
    private String type;

    private int offset = 0;

    OrderListContract.Presenter mPresenter;

    public static OrderListFragment newInstance(int index) {
        OrderListFragment orderFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        refreshListView.setPullLoadEnabled(false);//上拉加载是否可用
        refreshListView.setScrollLoadEnabled(true);//判断滑动到底部加载是否可用
        refreshListView.setPullRefreshEnabled(true);//设置是否能下拉
        refreshListView.setOnRefreshListener(this);
        mListView = refreshListView.getRefreshableView();
        adapter = new OrderAdapter(getActivity());
        mListView.setAdapter(adapter);
        adapter.setListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        new OrderListPresenter(this);
        index = getArguments().getInt("index");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        getData();
    }

    private void getData() {
        switch (index) {
            case 0:
                type = Constant.ORDER_TYPE_ORDER_RECEIVED;
                break;
            case 1:
                type = Constant.ORDER_TYPE_WAITING_ORDER;
                break;
            case 2:
                type = Constant.ORDER_TYPE_OTHER;
                break;
        }
        mPresenter.getOrderList(type, offset);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        offset = 0;
        isLoadMore = false;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        offset++;
        isLoadMore = true;
        getData();
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getOrderListSuccess(List<Order> list) {
        if (!isLoadMore) {
            mDataList.clear();
            if (list.isEmpty()) {
                emptyLayout.setVisibility(View.VISIBLE);
                refreshListView.setScrollLoadEnabled(false);
            } else {
                emptyLayout.setVisibility(View.GONE);
                refreshListView.setScrollLoadEnabled(true);
            }
        }
        if (list.size() == REQUEST_NUM) {
            hasMoreData = true;
        } else {
            hasMoreData = false;
        }
        mDataList.addAll(list);
        adapter.setDataList(mDataList);
        refreshListView.onPullDownRefreshComplete();
        refreshListView.onPullUpRefreshComplete();
        refreshListView.setHasMoreData(hasMoreData);
    }

    @Override
    public void getOrderListFailed() {
        refreshListView.onPullDownRefreshComplete();
        refreshListView.onPullUpRefreshComplete();
        refreshListView.setHasMoreData(hasMoreData);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if (index == 2 && ("4".equals(adapter.getDataList().get(position).getState_key())
                || "5".equals(adapter.getDataList().get(position).getState_key()))) {
            intent.setClass(getActivity(), DiagnosticReportActivity.class);
        } else {
            intent.setClass(getActivity(), OrderInfoActivity.class);
        }
        intent.putExtra("type", type);
        intent.putExtra("orderId", adapter.getDataList().get(position).getAppoint_id());
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            offset = 0;
            getData();
        }
    }


    /**
     * 授权处理
     */
    private PermissionHelper mHelper;

    @Override
    public void onPhoneClick(final String phone) {
        if (mHelper == null) {
            mHelper = new PermissionHelper(this);
        }
        mHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        new CustomDialog(getActivity()).setTitle(phone)
                                .setPositiveButton("呼叫",getResources().getColor(R.color.theme_color), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PhoneUtils.callDial(getActivity(), phone);
                                    }
                                }).show();
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        Toast.makeText(getActivity(), "请授权,否则无法进行拨号", Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.CALL_PHONE);
    }
}
