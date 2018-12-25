package com.cds.pet.module.user;

import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/13 11:48
 * @Version: 3.0.0
 */
public interface UserContract {
    interface View extends BaseView<Presenter> {

        void  updateWorkStateSuccess();

    }

    interface Presenter extends BasePresenter {

        void updateWorkState(String state);

    }
}
