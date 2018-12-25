package com.cds.pet.module.main;

import com.cds.pet.data.source.remote.HttpApi;
import com.cds.pet.data.source.remote.HttpFactory;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 16:51
 * @Version: 3.0.0
 */
public class MainPresenter implements MainContract.Presenter{
    public final static String TAG = "MainPresenter";
    private MainContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
