package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/7 14:25
 * @Version: 3.0.0
 */
public class ExecuteOrderReq {
    private String user_id;
    private String appoint_id;
    private String option_type; // 1：确认接单，2：转单
    private String option_desc;

    public ExecuteOrderReq(String user_id, String appoint_id, String option_type) {
        this.user_id = user_id;
        this.appoint_id = appoint_id;
        this.option_type = option_type;
    }

    public ExecuteOrderReq(String user_id, String appoint_id, String option_type, String option_desc) {
        this.user_id = user_id;
        this.appoint_id = appoint_id;
        this.option_type = option_type;
        this.option_desc = option_desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getOption_type() {
        return option_type;
    }

    public void setOption_type(String option_type) {
        this.option_type = option_type;
    }

    public String getOption_desc() {
        return option_desc;
    }

    public void setOption_desc(String option_desc) {
        this.option_desc = option_desc;
    }
}
