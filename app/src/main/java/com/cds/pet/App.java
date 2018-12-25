package com.cds.pet;

import android.app.Application;
import android.os.Environment;

import com.blankj.utilcode.util.Utils;
import com.cds.pet.util.CrashHandler;
import com.cds.pet.util.DeviceUtils;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * @Author: chengzj
 * @CreateDate: 2018/11/29 15:23
 * @Version: 3.0.0
 */
public class App extends Application {
    public static final  String TAG = "App";

    public String HOST = "sit.wecarelove.com";

    public int PORT = 85;

    private String imageCacheDir;

    private String appCacheDir;

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //初始化工具类
        Utils.init(this);
        initPicasoConfig();
        CrashHandler.getInstance().init(this);
    }

    private void initPicasoConfig() {
        if (DeviceUtils.isSDCardEnable()) {
            appCacheDir = Environment.getExternalStorageDirectory() + "/wecare/pet/";
        } else {
            appCacheDir = getCacheDir().getAbsolutePath() + "/wecare/pet/";
        }
        File directory = new File(appCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        PreferenceUtils.setPrefString(this,PreferenceConstants.APP_CACHE_DIR,appCacheDir);
        imageCacheDir = appCacheDir + "image/";
        File file = new File(imageCacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        PreferenceUtils.setPrefString(this,PreferenceConstants.APP_IMAGE_CACHE_DIR,imageCacheDir);
        //设置图片内存缓存大小为运行时内存的八分之一
        long l = Runtime.getRuntime().maxMemory();
        OkHttpClient client = new OkHttpClient();
        client.setCache(new Cache(file, l / 8));
        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache((int) (l / 8)))
                .downloader(new OkHttpDownloader(client))
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .loggingEnabled(false)//picasso log日志
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
