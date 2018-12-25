package com.cds.pet.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
import com.cds.pet.data.entity.SMessage;
import com.cds.pet.module.adapter.MessageAdapter;
import com.cds.pet.module.order.diagnostic.DiagnosticReportActivity;
import com.cds.pet.module.order.info.OrderInfoActivity;
import com.cheng.refresh.library.PullToRefreshBase;
import com.cheng.refresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.cds.pet.data.Constant.ORDER_TYPE_OTHER;
import static com.cds.pet.data.Constant.ORDER_TYPE_WAITING_ORDER;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/3 17:09
 * @Version: 3.0.0
 */
public class MessageFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>, MessageContract.View, AdapterView.OnItemClickListener {
    public static final int REQUEST_NUM = 10;
    @Bind(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @Bind(R.id.refresh_listView)
    PullToRefreshListView refreshListView;
    private ListView mListView;

    List<SMessage> mDataList = new ArrayList<>();

    MessageAdapter adapter;

    private int offset = 0;
    private boolean hasMoreData = false;//是否有更多数据
    private boolean isLoadMore = false;//是否加载更多

    MessageContract.Presenter mPresenter;

    public static MessageFragment newInstance() {
        MessageFragment mainFragment = new MessageFragment();
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.title)).setText("消息");
        refreshListView.setPullLoadEnabled(false);//上拉加载是否可用
        refreshListView.setScrollLoadEnabled(true);//判断滑动到底部加载是否可用
        refreshListView.setPullRefreshEnabled(true);//设置是否能下拉
        refreshListView.setOnRefreshListener(this);
        mListView = refreshListView.getRefreshableView();
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        new MessagePresenter(this);
        adapter = new MessageAdapter(getActivity());
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mPresenter.queryMessage(offset);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void queryMessageSuccess(List<SMessage> list) {
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
    public void queryMessage() {
        offset = 0;
        isLoadMore = false;
        mPresenter.queryMessage(offset);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offset = 0;
                isLoadMore = false;
                mPresenter.queryMessage(offset);

            }
        }, 1200);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offset++;
                isLoadMore = true;
                mPresenter.queryMessage(offset);
            }
        }, 1200);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if("10".equals(adapter.getDataList().get(position).getMsgType())
                ||"12".equals(adapter.getDataList().get(position).getMsgType())){
            intent.setClass(getActivity(), OrderInfoActivity.class);
            if("10".equals(adapter.getDataList().get(position).getMsgType())){
                intent.putExtra("type", ORDER_TYPE_WAITING_ORDER);
            }else {
                intent.putExtra("type", ORDER_TYPE_OTHER);
            }
        }else if("11".equals(adapter.getDataList().get(position).getMsgType())){
            intent.setClass(getActivity(), DiagnosticReportActivity.class);
            intent.putExtra("type", ORDER_TYPE_OTHER);
        }
        intent.putExtra("orderId", adapter.getDataList().get(position).getOrderId());
        startActivity (intent);
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
