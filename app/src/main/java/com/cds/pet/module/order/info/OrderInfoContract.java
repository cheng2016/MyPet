package com.cds.pet.module.order.info;

import com.cds.pet.data.entity.OrderInfo;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 11:40
 * @Version: 3.0.0
 */
public interface OrderInfoContract {
    interface View extends BaseView<Presenter> {
        void getOrderInfoSuccess(OrderInfo resp);

        void getOrderInfoFailed();

        void acceptOrderSuccess();

        void acceptOrderFailed();
    }

    interface Presenter extends BasePresenter {
        void getOrderInfo(String orderNo,String type);

        void acceptOrder(String orderId);

        void billing();
    }
}
