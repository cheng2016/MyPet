package com.cds.pet.module.order.transfer;

import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 16:28
 * @Version: 3.0.0
 */
public interface TransferContract {
    interface View extends BaseView<Presenter> {
        void  transferSuccess();

        void  transferFailed();
    }

    interface Presenter extends BasePresenter {
        void  transfer(String orderId,String option_desc);
    }
}
