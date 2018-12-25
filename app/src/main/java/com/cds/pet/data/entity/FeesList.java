package com.cds.pet.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/10 11:31
 * @Version: 3.0.0
 */
public class FeesList implements Serializable {
    List<Fees> fees_list;

    public FeesList(List<Fees> fees_list) {
        this.fees_list = fees_list;
    }

    public List<Fees> getFees_list() {
        return fees_list;
    }

    public void setFees_list(List<Fees> fees_list) {
        this.fees_list = fees_list;
    }
}
