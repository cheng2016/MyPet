package com.cds.pet.data.entity;

import java.io.Serializable;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 16:19
 * @Version: 3.0.0
 */
public class Symptom implements Serializable{
    /**
     * name : 症状1
     * id : 1
     */

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
