package com.cds.pet.data.entity;

import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 16:18
 * @Version: 3.0.0
 */
public class Order {
    /**
     * appoint_no : 201811300001
     * phone_number : 13691912026
     * symptom : [{"name":"症状1","id":"1"},{"name":"症状2","id":"2"}]
     * address : 深圳市福田区花木成畦手自栽asf
     * appoint_id : 1
     * user_name : 测试
     * reservation_time : 2018-11-30 15:04:34
     * pet_nickname : 二狗子
     * state : 1
     * pet_ varieties : 巴基度
     */
    private String appoint_no;
    private String phone_number;
    private String address;
    private String appoint_id;
    private String user_name;
    private String reservation_time;
    private String pet_nickname;
    private String state;
    private String pet_varieties;
    private List<Symptom> symptom;
    /**
     * state_key : 3
     * pet_head_img : https://sit.wecarelove.com/opt/pet/headImg/d6497b94c69853c474c22c939d7267de.png
     * is_pay_value : 已付款
     * state_value : 待上门
     * is_pay : 1
     */

    private String state_key;
    private String pet_head_img;
    private String is_pay_value;
    private String state_value;
    private int is_pay;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getPet_nickname() {
        return pet_nickname;
    }

    public void setPet_nickname(String pet_nickname) {
        this.pet_nickname = pet_nickname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPet_varieties() {
        return pet_varieties;
    }

    public void setPet_varieties(String pet_varieties) {
        this.pet_varieties = pet_varieties;
    }

    public List<Symptom> getSymptom() {
        return symptom;
    }

    public void setSymptom(List<Symptom> symptom) {
        this.symptom = symptom;
    }

    public String getAppoint_no() {
        return appoint_no;
    }

    public void setAppoint_no(String appoint_no) {
        this.appoint_no = appoint_no;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getState_key() {
        return state_key;
    }

    public void setState_key(String state_key) {
        this.state_key = state_key;
    }

    public String getPet_head_img() {
        return pet_head_img;
    }

    public void setPet_head_img(String pet_head_img) {
        this.pet_head_img = pet_head_img;
    }

    public String getIs_pay_value() {
        return is_pay_value;
    }

    public void setIs_pay_value(String is_pay_value) {
        this.is_pay_value = is_pay_value;
    }

    public String getState_value() {
        return state_value;
    }

    public void setState_value(String state_value) {
        this.state_value = state_value;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }
}
