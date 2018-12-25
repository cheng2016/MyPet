package com.cds.pet.module.main;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.module.adapter.MainPagerAdapter;
import com.cds.pet.module.message.MessageFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainContract.View {
    public static final String MESSAGE_BROADCAST_ACTION = "com.cds.pet.action.message";
    @Bind(R.id.vp_horizontal_ntb)
    ViewPager viewPager;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.message_tip)
    View messageTipImg;

    MessageReceiver messageReceiver;

    MainPagerAdapter mMainPagerAdapter;

    MainContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mMainPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.home);
                        break;
                    case 1:
                        radioGroup.check(R.id.message);
                        messageTipImg.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        radioGroup.check(R.id.me);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        radioGroup.check(R.id.home);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.home: //设备
                        checkedId = 0;
                        break;
                    case R.id.message: //消息
                        checkedId = 1;
                        messageTipImg.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.me: //我的
                        checkedId = 2;
                        break;
                    default:
                        break;
                }
                viewPager.setCurrentItem(checkedId);
            }
        });
    }

    @Override
    protected void initData() {
        new MainPresenter(this);
        //注册广播
        registerReceiver();
        //请求连接tcp服务器
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSocketService != null) {
                    mSocketService.initPushService();
                }
            }
        }, 300);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
        closeNetty();
        //程序退出前清除所有推送
        clearNotificaction();
        mPresenter.unsubscribe();
    }


    private void clearNotificaction() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    private void registerReceiver() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(MESSAGE_BROADCAST_ACTION);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, iFilter);
    }

    /**
     * 构造消息广播监听类，收到消息后刷新该页面
     */
    public class MessageReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_BROADCAST_ACTION)
                    && mMainPagerAdapter.getmFragments().length > 0
                    && mMainPagerAdapter.getmFragments()[1] instanceof MessageFragment) {
                MessageFragment fragment = (MessageFragment) mMainPagerAdapter.getmFragments()[1];
                fragment.queryMessage();
                messageTipImg.setVisibility(View.VISIBLE);
            }
        }
    }

    //双击返回键 退出
    //----------------------------------------------------------------------------------------------
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ToastUtils.showShort("再次点击返回键退出");
        }
        mBackPressed = System.currentTimeMillis();
    }

    void closeNetty() {
        if (null != mSocketService) {
            mSocketService.shutdownNetty();
        }
    }
}
