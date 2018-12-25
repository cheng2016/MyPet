package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/19 16:43
 * @Version: 3.0.0
 */
public class ConfirmReceiptReq {
    private String appoint_id;
    private String diagnose_id;
    private String pay_type;

    public ConfirmReceiptReq(String appoint_id, String diagnose_id, String pay_type) {
        this.appoint_id = appoint_id;
        this.diagnose_id = diagnose_id;
        this.pay_type = pay_type;
    }

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getDiagnose_id() {
        return diagnose_id;
    }

    public void setDiagnose_id(String diagnose_id) {
        this.diagnose_id = diagnose_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }
}
