package com.cds.pet.data.entity;

import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/10 11:38
 * @Version: 3.0.0
 */
public class OpenOrderReq {

    /**
     * appoint_id : 订单号
     * user_id : 医生id
     * diagnostic_report : 诊断报告
     * diagnostic_price : 诊断费用
     * fees_list : [{"count":"数量","id":"产品id","price":"价格","name":"name"}]
     * total_price : 总额
     * need_review : 是否需要复诊
     * review_time : 复诊时间
     * review_type : 复诊类型
     */

    private String appoint_id;
    private String user_id;
    private String diagnostic_report;
    private String diagnostic_price;
    private String total_price;
    private String need_review;
    private String review_time;
    private String review_type;
    private List<Fees> fees_list;

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDiagnostic_report() {
        return diagnostic_report;
    }

    public void setDiagnostic_report(String diagnostic_report) {
        this.diagnostic_report = diagnostic_report;
    }

    public String getDiagnostic_price() {
        return diagnostic_price;
    }

    public void setDiagnostic_price(String diagnostic_price) {
        this.diagnostic_price = diagnostic_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getNeed_review() {
        return need_review;
    }

    public void setNeed_review(String need_review) {
        this.need_review = need_review;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }

    public String getReview_type() {
        return review_type;
    }

    public void setReview_type(String review_type) {
        this.review_type = review_type;
    }

    public List<Fees> getFees_list() {
        return fees_list;
    }

    public void setFees_list(List<Fees> fees_list) {
        this.fees_list = fees_list;
    }
}
