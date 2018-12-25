package com.cds.pet.module.order.list;

import com.blankj.utilcode.util.ToastUtils;
import com.cds.pet.App;
import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.Order;
import com.cds.pet.data.entity.OrderListReq;
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
 * @CreateDate: 2018/12/4 11:02
 * @Version: 3.0.0
 */
public class OrderListPresenter implements OrderListContract.Presenter{
    public final static String TAG = "OrderListPresenter";
    private OrderListContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public OrderListPresenter(OrderListContract.View view) {
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
    public void getOrderList(String type,int pageNo) {
        String userId = PreferenceUtils.getPrefString(App.getInstance(),PreferenceConstants.USER_ID,"");
        OrderListReq req = new OrderListReq(userId,type,pageNo);
        mHttpApi.getOrderList(new Gson().toJson(req))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResp<List<Order>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResp<List<Order>> resp) {
                        if ("200".equals(resp.getInfo().getCode())) {
                            view.getOrderListSuccess(resp.getData());
                        } else {
                            view.getOrderListFailed();
                            ToastUtils.showShort(resp.getInfo().getInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getOrderListFailed();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
