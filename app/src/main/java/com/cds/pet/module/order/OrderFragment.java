package com.cds.pet.module.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
import com.cds.pet.data.entity.DoctorInfo;
import com.cds.pet.module.order.list.OrderListFragment;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.cds.pet.util.picasso.PicassoCircleTransform;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import butterknife.Bind;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/3 17:08
 * @Version: 3.0.0
 */
public class OrderFragment extends BaseFragment implements OrderContract.View {
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tab)
    TabLayout tabLayout;
    @Bind(R.id.nickname)
    TextView nicknameTv;
    @Bind(R.id.job)
    TextView jobTv;
    @Bind(R.id.ratingbar)
    ScaleRatingBar ratingbar;
    @Bind(R.id.head_img)
    ImageView headImg;

    OrderContract.Presenter mPresenter;

    public static OrderFragment newInstance() {
        OrderFragment orderFragment = new OrderFragment();
        return orderFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.title)).setText("宠物服务");
    }

    @Override
    protected void initData() {
        new OrderPresenter(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            Fragment[] mFragments = new Fragment[3];
            String[] titles = new String[]{"待上门订单", "已派订单", "其他"};

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        if (mFragments[0] == null) {
                            mFragments[0] = OrderListFragment.newInstance(0);
                        }
                        return mFragments[0];
                    case 1:
                        if (mFragments[1] == null) {
                            mFragments[1] = OrderListFragment.newInstance(1);
                        }
                        return mFragments[1];
                    case 2:
                        if (mFragments[2] == null) {
                            mFragments[2] = OrderListFragment.newInstance(2);
                        }
                        return mFragments[2];
                    default:
                        return null;
                }
            }

            /**
             * //此方法用来显示tab上的名字
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mPresenter.getDoctorInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getDoctorInfoSuccess(DoctorInfo info) {
        nicknameTv.setText(info.getName());
        ratingbar.setRating(Float.parseFloat(info.getRating()));
        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.NICK_NAME,info.getName());
        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.RATING,info.getRating());
        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.WORK_STATE,info.getWorker_state());
        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.HEAD_IMG,info.getHead_img());
        if (!TextUtils.isEmpty(info.getHead_img())) {
            Picasso.with(getActivity())
                    .load(info.getHead_img())
                    .error(R.mipmap.doctor_loginportraits)
                    .transform(new PicassoCircleTransform())
                    .into(headImg);
        }
    }
}
