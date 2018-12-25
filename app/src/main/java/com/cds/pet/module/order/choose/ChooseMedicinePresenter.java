package com.cds.pet.module.order.choose;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.Info;
import com.cds.pet.data.entity.Medicine;
import com.cds.pet.data.source.remote.BaseObserver;
import com.cds.pet.data.source.remote.HttpApi;
import com.cds.pet.data.source.remote.HttpFactory;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 17:26
 * @Version: 3.0.0
 */
public class ChooseMedicinePresenter implements ChooseMedicineContract.Presenter{
    public final static String TAG = "ChooseMedicinePresenter";
    private ChooseMedicineContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public ChooseMedicinePresenter(ChooseMedicineContract.View view) {
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
    public void getMedicine() {
        String userId = PreferenceUtils.getPrefString(App.getInstance(),PreferenceConstants.USER_ID,"");
        Info req = new Info(userId);
        mHttpApi.getMedicine(new Gson().toJson(req))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResp<List<Medicine>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResp<List<Medicine>> resp) {
                        if ("200".equals(resp.getInfo().getCode())) {
                            view.getMedicineSuccess(resp.getData());
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
