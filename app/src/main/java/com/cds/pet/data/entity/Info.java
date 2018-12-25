package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/4 11:39
 * @Version: 3.0.0
 */
public class Info {

    /**
     * user_id : 医生id
     */

    private String user_id;

    public Info(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
