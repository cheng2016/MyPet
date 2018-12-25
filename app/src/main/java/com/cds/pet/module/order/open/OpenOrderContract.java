package com.cds.pet.module.order.open;

import com.cds.pet.data.entity.OpenOrderReq;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 17:07
 * @Version: 3.0.0
 */
public interface OpenOrderContract {
    interface View extends BaseView<Presenter> {
        void openOrderSuccess();
    }

    interface Presenter extends BasePresenter {
        void openOrder(OpenOrderReq req);
    }
}
