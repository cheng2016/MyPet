package com.cds.pet.module.order.choose;

import com.cds.pet.data.entity.Medicine;
import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 17:26
 * @Version: 3.0.0
 */
public interface ChooseMedicineContract {
    interface View extends BaseView<Presenter> {
        void getMedicineSuccess(List<Medicine> list);
    }

    interface Presenter extends BasePresenter {
        void getMedicine();
    }
}
