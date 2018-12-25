package com.cds.pet.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 15:25
 * @Version: 3.0.0
 */
public class Diagnostics implements Serializable {
    /**
     * result : adfasdasfda
     * pay_state : 1
     * total_price : 444.0
     * doctor_phone_number : 13691912026
     * rating : 5
     * pay_type : 1
     * review_time : 2018-12-05 16:14:05
     * id : 1
     * fees_list : [{"price":"123.0","count":"1","name":"sdfsadf"},{"price":"321.0","count":"2","name":"sdfasdf"}]
     * doctor_name : 张三
     * pay_time : 2018-12-04 16:14:03
     */
    private String no;
    private String id;
    private String doctor_head_img;
    private String doctor_name;
    private String doctor_phone_number;
    private String rating;
    private String result;

    private List<Fees> fees_list;

    private String total_price;//总额
    private String fees_total;//小计
    private String price;//诊断费用

    private int pay_state;
    private String pay_state_value;
    private String pay_time;
    private int pay_type;
    private String pay_type_value;
    private String review_time;
    private String create_time;

    private int  need_review;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_head_img() {
        return doctor_head_img;
    }

    public void setDoctor_head_img(String doctor_head_img) {
        this.doctor_head_img = doctor_head_img;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_phone_number() {
        return doctor_phone_number;
    }

    public void setDoctor_phone_number(String doctor_phone_number) {
        this.doctor_phone_number = doctor_phone_number;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Fees> getFees_list() {
        return fees_list;
    }

    public void setFees_list(List<Fees> fees_list) {
        this.fees_list = fees_list;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getFees_total() {
        return fees_total;
    }

    public void setFees_total(String fees_total) {
        this.fees_total = fees_total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }

    public String getPay_state_value() {
        return pay_state_value;
    }

    public void setPay_state_value(String pay_state_value) {
        this.pay_state_value = pay_state_value;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_type_value() {
        return pay_type_value;
    }

    public void setPay_type_value(String pay_type_value) {
        this.pay_type_value = pay_type_value;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getNeed_review() {
        return need_review;
    }

    public void setNeed_review(int need_review) {
        this.need_review = need_review;
    }
}
