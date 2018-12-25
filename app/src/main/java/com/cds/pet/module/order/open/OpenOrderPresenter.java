package com.cds.pet.module.order.open;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.OpenOrderReq;
import com.cds.pet.data.source.remote.BaseObserver;
import com.cds.pet.data.source.remote.HttpApi;
import com.cds.pet.data.source.remote.HttpFactory;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 17:07
 * @Version: 3.0.0
 */
public class OpenOrderPresenter implements OpenOrderContract.Presenter{
    public final static String TAG = "OpenOrderPresenter";
    private OpenOrderContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public OpenOrderPresenter(OpenOrderContract.View view) {
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

    @Override
    public void openOrder(OpenOrderReq req) {
        mHttpApi.openOrder(new Gson().toJson(req))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if ("200".equals(resp.getInfo().getCode())) {
                            view.openOrderSuccess();
                        } else {
                            ToastUtils.showShort(resp.getInfo().getInfo());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
