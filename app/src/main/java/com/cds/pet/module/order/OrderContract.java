package com.cds.pet.module.order;

import com.cds.pet.data.entity.DoctorInfo;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 9:54
 * @Version: 3.0.0
 */
public interface OrderContract {
    interface View extends BaseView<Presenter> {
       void  getDoctorInfoSuccess(DoctorInfo info);
    }

    interface Presenter extends BasePresenter {
        void  getDoctorInfo();
    }
}
