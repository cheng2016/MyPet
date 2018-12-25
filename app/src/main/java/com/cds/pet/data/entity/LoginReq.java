package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/3 16:44
 * @Version: 3.0.0
 */
public class LoginReq {
    /**
     * user_id : 医生id
     * login_pwd : 医生密码
     */
    private String user_id;
    private String login_pwd;

    public LoginReq(String user_id, String login_pwd) {
        this.user_id = user_id;
        this.login_pwd = login_pwd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }
}
