package com.cds.pet.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.module.main.MainActivity;
import com.cds.pet.util.Logger;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Author: chengzj
 * @CreateDate: 2018/11/29 15:32
 * @Version: 3.0.0
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    private static final int RC_SIGN_IN = 9001;

    LoginContract.Presenter mPresenter;
    @Bind(R.id.account)
    AppCompatEditText accountView;
    @Bind(R.id.password)
    AppCompatEditText passwordView;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.facebook_login_button)
    LoginButton facebookButton;
    @Bind(R.id.google_login_button)
    SignInButton googleButton;

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
        initFaceBoock();
        initGoogle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        FirebaseAuth.getInstance().signOut();
    }

    @OnClick({R.id.login_button, R.id.facebook_login_button, R.id.google_login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String acount = accountView.getText().toString().trim();
                String password = passwordView.getText().toString().trim();
                if (TextUtils.isEmpty(acount)
                        || TextUtils.isEmpty(password)) {
                    ToastUtils.showShort("账户和密码不能为空！");
                } else {
                    login(acount, password);
                }
                break;
            case R.id.facebook_login_button:
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (!isLoggedIn) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                }
                break;
            case R.id.google_login_button:
                signIn();
                break;
        }
    }

    private FirebaseAuth mAuth;

    void initGoogle(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // ...
        mAuth = FirebaseAuth.getInstance();
    }

    GoogleSignInClient mGoogleSignInClient;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN );
    }

    CallbackManager callbackManager;

    void initFaceBoock() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Logger.e(TAG, "登录成功: " + loginResult.getAccessToken().getToken());
                loginResult.getAccessToken().getApplicationId();
                loginResult.getAccessToken().getUserId();
                ToastUtils.showShort("登录成功");
            }

            @Override
            public void onCancel() {
                Logger.e(TAG, "登录取消");
                ToastUtils.showShort("登录取消");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.e(TAG, "onError", error);
                ToastUtils.showShort("登录错误");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Logger.w(TAG, "Google sign in failed", e);
                ToastUtils.showShort("Google sign in failed");
                // ...
            }
        }
    }

    private String providerId;

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Logger.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            providerId = user.getProviderId();

//                            unlick();

                            ToastUtils.showShort("signInWithCredential success "+ user.getEmail());
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                            ToastUtils.showShort("signInWithCredential failure");
                        }

                        // ...
                    }
                });
    }

    void unlick(){
        mAuth.getCurrentUser().unlink(providerId)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Auth provider unlinked from account
                            // ...
                            ToastUtils.showShort("取消身份验证提供方与用户帐号的关联成功");
                        }else {
                            ToastUtils.showShort("取消身份验证提供方与用户帐号的关联失败");
                        }
                    }
                });
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
