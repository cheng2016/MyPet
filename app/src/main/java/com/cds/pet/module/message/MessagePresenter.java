package com.cds.pet.module.message;

import com.cds.pet.App;
import com.cds.pet.data.entity.SMessage;
import com.cds.pet.data.source.local.SMessageDaoUtils;
import com.cds.pet.data.source.local.greendao.SMessageDao;
import com.cds.pet.data.source.remote.HttpApi;
import com.cds.pet.data.source.remote.HttpFactory;
import com.cds.pet.util.Logger;
import com.cds.pet.util.PreferenceConstants;
import com.cds.pet.util.PreferenceUtils;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 14:12
 * @Version: 3.0.0
 */
public class MessagePresenter implements MessageContract.Presenter {
    public final static String TAG = "MessagePresenter";
    private MessageContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;
    private SMessageDaoUtils daoUtils;
    private String userId;

    public MessagePresenter(MessageContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
        daoUtils = new SMessageDaoUtils(App.getInstance());
        userId = PreferenceUtils.getPrefString(App.getInstance(), PreferenceConstants.USER_ID, "");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void queryMessage(int offset) {
        Logger.i(TAG, "queryMessage  offset：" + offset);
        List<SMessage> messageList = daoUtils.querySMessage("USER_ID = ?", new String[]{userId}, SMessageDao.Properties.Id, offset, MessageFragment.REQUEST_NUM);//条件查询
        Logger.i(TAG, "queryMessage messageList：" + new Gson().toJson(messageList));
        view.queryMessageSuccess(messageList);
    }
}
