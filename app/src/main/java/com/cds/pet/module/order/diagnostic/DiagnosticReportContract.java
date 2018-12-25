package com.cds.pet.module.order.diagnostic;

import com.cds.pet.data.entity.OrderInfo;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/14 16:34
 * @Version: 3.0.0
 */
public interface DiagnosticReportContract {
    interface View extends BaseView<Presenter> {
        void getOrderInfoSuccess(OrderInfo resp);

        void getOrderInfoFailed();

        void confirmReceiptSuccess();
    }

    interface Presenter extends BasePresenter {
        void getOrderInfo(String orderNo,String type);

        void confirmReceipt(String orderId,String diagnostic_id);
    }
}
