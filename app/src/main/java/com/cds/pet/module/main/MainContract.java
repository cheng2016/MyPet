package com.cds.pet.module.main;

import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 16:50
 * @Version: 3.0.0
 */
public interface MainContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
