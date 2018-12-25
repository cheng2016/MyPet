package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/13 11:51
 * @Version: 3.0.0
 */
public class WorkStateReq {
    /**
     * user_id : 医生id
     * work_state : 工作状态
     */

    private String user_id;
    private String work_state;

    public WorkStateReq() {
    }

    public WorkStateReq(String user_id, String work_state) {
        this.user_id = user_id;
        this.work_state = work_state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWork_state() {
        return work_state;
    }

    public void setWork_state(String work_state) {
        this.work_state = work_state;
    }
}
