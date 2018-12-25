package com.cds.pet.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 14:49
 * @Version: 3.0.0
 */
public class OrderInfo implements Serializable {
    /**
     * contact_name : 测试
     * contact_phone : 13691912026
     * create_time : 2018-11-30 15:04:34
     * symptom_desc : asdfasdfsfasdf
     * reservation_time : 2018-11-30 15:04:34
     * pay_type : 1
     * pay_time : 2018-12-03 15:34:15
     * order_cost : 100.0
     * symptom : [{"name":"症状1","id":"1"},{"name":"症状2","id":"2"}]
     * diagnostics : []
     * appoint_id : 1
     * pet_nickname : 二狗子
     * state : 1
     * contact_address : 深圳市福田区花木成畦手自栽asf
     * pet_id : 1
     * pay_state : 1
     * diagnostics : [{"result":"adfasdasfda","pay_state":1,"total_price":"444.0","doctor_phone_number":"13691912026","rating":"5","pay_type":1,"review_time":"2018-12-05 16:14:05","id":"1","fees_list":[{"price":"123.0","count":"1","name":"sdfsadf"},{"price":"321.0","count":"2","name":"sdfasdf"}],"doctor_name":"张三","pay_time":"2018-12-04 16:14:03"},{"result":"sdfasdf","pay_state":1,"doctor_phone_number":"13691912026","rating":"5","pay_type":1,"review_time":"2018-12-03 16:14:43","id":"2","doctor_name":"张三","pay_time":"2018-12-03 16:14:41"}]

     */

    private String contact_name;
    private String contact_phone;
    private String create_time;
    private String symptom_desc;
    private String reservation_time;
    private int pay_state;
    private String pay_time;
    private String pay_type;
    private String order_cost;
    private String appoint_id;
    private String pet_nickname;
    private String pet_varieties;
    private String state;
    private String contact_address;
    private String pet_id;
    private List<Symptom> symptom;
    private List<Diagnostics> diagnostics;
    /**
     * state_key : 3
     * pet_head_img : https://sit.wecarelove.com/opt/pet/headImg/d6497b94c69853c474c22c939d7267de.png
     * pay_type_value : 微信支付
     * diagnostics : [{"pay_state":1,"total_price":"444.0","doctor_phone_number":"13691912026","pay_type_value":"微信支付","rating":"5","fees_list":[{"price":"123.0","count":"1","name":"sdfsadf"},{"price":"321.0","count":"2","name":"sdfasdf"}],"pay_time":"2018-12-04 16:14:03","result":"adfasdasfda","doctor_head_img":"https://sit.wecarelove.com/opt/pet/headImg/3a8f4c4a3803c74f0e714b029c891992.png","pay_state_value":"已付款","pay_type":1,"review_time":"2018-12-05 16:14:05","id":"1","doctor_name":"张三"},{"result":"sdfasdf","pay_state":1,"doctor_phone_number":"13691912026","doctor_head_img":"https://sit.wecarelove.com/opt/pet/headImg/3a8f4c4a3803c74f0e714b029c891992.png","pay_state_value":"已付款","pay_type_value":"微信支付","rating":"5","pay_type":1,"review_time":"2018-12-03 16:14:43","id":"2","doctor_name":"张三","pay_time":"2018-12-03 16:14:41"},{"total_price":"296.6","doctor_phone_number":"13691912026","doctor_head_img":"https://sit.wecarelove.com/opt/pet/headImg/3a8f4c4a3803c74f0e714b029c891992.png","rating":"5","review_time":"","id":"5","fees_list":[{"price":"158.3","count":"1","name":"诊费"},{"price":"12.8","count":"1","name":"疫苗1"},{"price":"125.5","count":"1","name":"疫苗2"}],"doctor_name":"张三","pay_time":""},{"result":"qsqeqw","total_price":"296.6","doctor_phone_number":"13691912026","doctor_head_img":"https://sit.wecarelove.com/opt/pet/headImg/3a8f4c4a3803c74f0e714b029c891992.png","rating":"5","review_time":"","id":"6","fees_list":[{"price":"158.3","count":"1","name":"诊费"},{"price":"12.8","count":"1","name":"疫苗1"},{"price":"125.5","count":"1","name":"疫苗2"}],"doctor_name":"张三","pay_time":""}]
     * appoint_no : 201811300001
     * pay_state_value : 已付款
     * pay_type : 1
     * state_value : 待上门
     */

    private String state_key;
    private String pet_head_img;
    private String pay_type_value;
    private String appoint_no;
    private String pay_state_value;
    private String state_value;


    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSymptom_desc() {
        return symptom_desc;
    }

    public void setSymptom_desc(String symptom_desc) {
        this.symptom_desc = symptom_desc;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getOrder_cost() {
        return order_cost;
    }

    public void setOrder_cost(String order_cost) {
        this.order_cost = order_cost;
    }

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getPet_nickname() {
        return pet_nickname;
    }

    public void setPet_nickname(String pet_nickname) {
        this.pet_nickname = pet_nickname;
    }

    public String getPet_varieties() {
        return pet_varieties;
    }

    public void setPet_varieties(String pet_varieties) {
        this.pet_varieties = pet_varieties;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getPet_id() {
        return pet_id;
    }

    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }

    public List<Symptom> getSymptom() {
        return symptom;
    }

    public void setSymptom(List<Symptom> symptom) {
        this.symptom = symptom;
    }

    public List<Diagnostics> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(List<Diagnostics> diagnostics) {
        this.diagnostics = diagnostics;
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

    public String getPay_type_value() {
        return pay_type_value;
    }

    public void setPay_type_value(String pay_type_value) {
        this.pay_type_value = pay_type_value;
    }

    public String getAppoint_no() {
        return appoint_no;
    }

    public void setAppoint_no(String appoint_no) {
        this.appoint_no = appoint_no;
    }

    public String getPay_state_value() {
        return pay_state_value;
    }

    public void setPay_state_value(String pay_state_value) {
        this.pay_state_value = pay_state_value;
    }

    public String getState_value() {
        return state_value;
    }

    public void setState_value(String state_value) {
        this.state_value = state_value;
    }
}
