package com.cds.pet.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseFragment;
import com.cds.pet.module.login.LoginActivity;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.cds.pet.util.picasso.PicassoCircleTransform;
import com.cds.pet.view.CustomDialog;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import butterknife.Bind;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/3 17:09
 * @Version: 3.0.0
 */
public class UserFragment extends BaseFragment implements View.OnClickListener, UserContract.View {
    @Bind(R.id.nickname)
    TextView nicknameTv;
    @Bind(R.id.ratingbar)
    ScaleRatingBar ratingbar;
    @Bind(R.id.stop)
    Button stopTv;
    @Bind(R.id.head_img)
    ImageView headImg;

    public static final String UNWORKING = "0";

    public static final String WORKING = "1";

    String work_state;

    UserContract.Presenter mPresenter;

    public static UserFragment newInstance() {
        UserFragment mainFragment = new UserFragment();
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.title)).setText("我的");
        view.findViewById(R.id.logout).setOnClickListener(this);
        view.findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new UserPresenter(this);
        work_state = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.WORK_STATE, "1");
        stopTv.setBackgroundResource(WORKING.equals(work_state) ? R.drawable.shape_work_button : R.drawable.shape_login_button);
        stopTv.setText(WORKING.equals(work_state) ? "暂停接单" : "恢复接单");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        String nickName = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.NICK_NAME, "");
        String rating = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.RATING, "");
        nicknameTv.setText(nickName);
        ratingbar.setRating(Float.parseFloat(rating));
        String headImgStr = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.HEAD_IMG, "");
        if (!TextUtils.isEmpty(headImgStr)) {
            Picasso.with(getActivity())
                    .load(headImgStr)
                    .error(R.mipmap.doctor_loginportraits)
                    .transform(new PicassoCircleTransform())
                    .into(headImg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                showProgressDilog("退出中...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDilog();
                        PreferenceUtils.setPrefString(App.getInstance(), PreferenceConstants.USER_PASSWORD, "");
                        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.NICK_NAME,"");
                        PreferenceUtils.setPrefString(App.getInstance(),PreferenceConstants.RATING,"");
                        PreferenceUtils.setPrefString(App.getInstance(), PreferenceConstants.HEAD_IMG, "");
                        startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                }, 1600);
                break;
            case R.id.stop:
                if (customDialog == null) {
                    customDialog = new CustomDialog(getActivity());
                }
                customDialog.setTitle(WORKING.equals(work_state) ? "确认暂停接单吗？" : "确认恢复接单吗？")
                        .setMessage(WORKING.equals(work_state) ? "确认后，系统将不再派发新的预约订单，请妥当处理好已派发的预约。" : "确认后，系统将会恢复派发新的预约订单。")
                        .setPositiveButton("确认", getResources().getColor(R.color.theme_color), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (WORKING.equals(work_state)) {
                                    mPresenter.updateWorkState(UNWORKING);
                                } else {
                                    mPresenter.updateWorkState(WORKING);
                                }
                            }
                        });
                if (!customDialog.isShowing()) {
                    customDialog.show();
                }
                break;
            default:
                break;
        }
    }

    CustomDialog customDialog;

    @Override
    public void updateWorkStateSuccess() {
        if (WORKING.equals(work_state)) {
            work_state = UNWORKING;
        } else {
            work_state = WORKING;
        }
        PreferenceUtils.setPrefString(App.getInstance(), PreferenceConstants.WORK_STATE, work_state);
        stopTv.setBackgroundResource(WORKING.equals(work_state) ? R.drawable.shape_work_button : R.drawable.shape_login_button);
        stopTv.setText(WORKING.equals(work_state) ? "暂停接单" : "恢复接单");
        ToastUtils.showShort(WORKING.equals(work_state) ? "已恢复接单" : "已暂停接单");
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
