package com.cds.pet.module.splash;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.cds.pet.App;
import com.cds.pet.R;
import com.cds.pet.base.BaseActivity;
import com.cds.pet.module.login.LoginActivity;
import com.cds.pet.module.main.MainActivity;
import com.cds.pet.util.DeviceUtils;
import com.cds.pet.util.Logger;
import com.cds.pet.util.MD5Utils;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.cds.pet.util.ResourceUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//import com.vondear.rxtools.RxBarTool;

public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler();

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected int getLayoutId() {
//        RxBarTool.hideStatusBar(this);//隐藏状态栏 并 全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        RxToast.showToast(this, "正在检查版本更新...", 500);
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },2000);*/
    }

    @Override
    protected void initData() {
//        ToastUtils.showShort(this, "正在检查版本更新");
        String value = ResourceUtils.getProperties(this, "packagename");
//        ToastUtils.showShort(this, "获取配置数据：packagename = " + value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent().setClass(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * @param permissions
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
                            int.class});
                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                } else {
                    //权限都开发直接跳转
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String userid = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_ID, "");
                            String pwd = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_PASSWORD, "");
                            if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(pwd)) {
                                startActivity(new Intent().setClass(SplashActivity.this, LoginActivity.class));
                            } else {
                                String token = MD5Utils.md5(userid + DeviceUtils.getDeviceIMEI(App.getInstance()));
                                Logger.i(TAG, "token：" + token);
                                PreferenceUtils.setPrefString(SplashActivity.this, PreferenceConstants.ACCESS_TOKEN, token);
                                startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
                            }
                            finish();
                        }
                    }, 800);
                    Logger.i(TAG, "checkPermissions startActivity");
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
