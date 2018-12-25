package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 11:43
 * @Version: 3.0.0
 */
public class OrderInfoReq {
    /**
     * user_id : 用户id
     * appoint_id : ads
     * state : 状态
     * role_type : 角色id
     * session_id : 微信openID
     */

    private String user_id;
    private String appoint_id;
    private String state;
    private String role_type = "2"; //【1：用户，2医生】
    private String session_id;

    public OrderInfoReq() {
    }

    public OrderInfoReq(String user_id, String appoint_id, String state) {
        this.user_id = user_id;
        this.appoint_id = appoint_id;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
