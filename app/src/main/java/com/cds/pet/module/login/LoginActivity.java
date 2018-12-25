package com.cds.pet.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.Button;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.module.main.MainActivity;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Author: chengzj
 * @CreateDate: 2018/11/29 15:32
 * @Version: 3.0.0
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    LoginContract.Presenter mPresenter;
    @Bind(R.id.account)
    AppCompatEditText accountView;
    @Bind(R.id.password)
    AppCompatEditText passwordView;
    @Bind(R.id.login_button)
    Button loginButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        new LoginPresenter(this);
        String name = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_NAME, "");
        String password = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_PASSWORD, "");
        accountView.setText(name);
        passwordView.setText(password);
        if (!TextUtils.isEmpty(name)) {
            accountView.setSelection(name.length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @OnClick(R.id.login_button)
    public void onViewClicked() {
        String acount = accountView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();
        if (TextUtils.isEmpty(acount)
                || TextUtils.isEmpty(password)) {
            ToastUtils.showShort("账户和密码不能为空！");
        } else {
            login(acount, password);
        }
    }

    private void login(String account, String password) {
        KeyboardUtils.hideSoftInput(this);
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("账户不能为空");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
        } else {
            showProgressDilog();
            mPresenter.login(account, password);
        }
    }


    @Override
    public void loginSuccess() {
        hideProgressDilog();
        ToastUtils.showShort("登录成功！");
        Intent intent = new Intent().setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        hideProgressDilog();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
