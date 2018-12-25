package com.cds.pet.module.order;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.DoctorInfo;
import com.cds.pet.data.entity.Info;
import com.cds.pet.data.source.remote.BaseObserver;
import com.cds.pet.data.source.remote.HttpApi;
import com.cds.pet.data.source.remote.HttpFactory;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 9:54
 * @Version: 3.0.0
 */
public class OrderPresenter implements OrderContract.Presenter {
    public final static String TAG = "OrderPresenter";
    private OrderContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public OrderPresenter(OrderContract.View view) {
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
    public void getDoctorInfo() {
        String userId = PreferenceUtils.getPrefString(App.getInstance(),PreferenceConstants.USER_ID,"");
        Info req = new Info(userId);
        mHttpApi.getDoctorInfo(new Gson().toJson(req))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResp<DoctorInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResp<DoctorInfo> resp) {
                        if ("200".equals(resp.getInfo().getCode())) {
                            view.getDoctorInfoSuccess(resp.getData());
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