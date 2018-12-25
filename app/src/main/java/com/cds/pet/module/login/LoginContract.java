package com.cds.pet.module.login;

import com.cds.pet.module.BasePresenter;
import com.cds.pet.module.BaseView;

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();

        void loginFailed();
    }

    interface Presenter extends BasePresenter {
        void login(String name, String pwd);
    }
}
