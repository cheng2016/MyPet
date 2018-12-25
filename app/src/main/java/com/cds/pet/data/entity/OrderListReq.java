package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 15:51
 * @Version: 3.0.0
 */
public class OrderListReq {

    /**
     * user_id : 用户id
     * state : 状态
     * role_type : 角色id
     * session_id : 微信openID
     */

    private String user_id;
    private String state;   // 1：待接单，2：待上门，3：已上门，5：其他
    private String role_type = "2"; //【1：用户，2:医生】
    private String session_id;
    private int page_no;

    public OrderListReq(String user_id, String state) {
        this.user_id = user_id;
        this.state = state;
    }

    public OrderListReq(String user_id, String state, int page_no) {
        this.user_id = user_id;
        this.state = state;
        this.page_no = page_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }
}
