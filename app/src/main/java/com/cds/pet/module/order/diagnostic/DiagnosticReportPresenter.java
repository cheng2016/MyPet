package com.cds.pet.module.order.diagnostic;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.ConfirmReceiptReq;
import com.cds.pet.data.entity.OrderInfo;
import com.cds.pet.data.entity.OrderInfoReq;
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
 * @CreateDate: 2018/12/14 16:34
 * @Version: 3.0.0
 */
public class DiagnosticReportPresenter implements DiagnosticReportContract.Presenter {
    public final static String TAG = "DiagnosticReportPresenter";
    private DiagnosticReportContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public DiagnosticReportPresenter(DiagnosticReportContract.View view) {
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
    public void getOrderInfo(String orderId, String type) {
        String userId = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_ID, "");
        OrderInfoReq req = new OrderInfoReq(userId, orderId, type);
        mHttpApi.getOrderInfo(new Gson().toJson(req))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResp<OrderInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResp<OrderInfo> resp) {
                        if ("200".equals(resp.getInfo().getCode())) {
                            view.getOrderInfoSuccess(resp.getData());
                        } else {
                            view.getOrderInfoFailed();
                            ToastUtils.showShort(resp.getInfo().getInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getOrderInfoFailed();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void confirmReceipt(String orderId, String diagnostic_id) {
        ConfirmReceiptReq req = new ConfirmReceiptReq(orderId, diagnostic_id, "3");
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
                            view.confirmReceiptSuccess();
                        } else {
                            ToastUtils.showShort(resp.getInfo().getInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
