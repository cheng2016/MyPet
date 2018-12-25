package com.cds.pet.module.order.list;

import com.cds.pet.data.entity.Order;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 11:03
 * @Version: 3.0.0
 */
public class OrderListContract {
    interface View extends BaseView<Presenter> {
        void getOrderListSuccess(List<Order> list);

        void getOrderListFailed();
    }

    interface Presenter extends BasePresenter {
        void getOrderList(String type,int pageNo);
    }
}
