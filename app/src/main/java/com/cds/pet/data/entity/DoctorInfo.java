package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/5 16:01
 * @Version: 3.0.0
 */
public class DoctorInfo {
    /**
     * user_id : 2
     * name : 李四
     * rating : 5
     * head_img : https://sit.wecarelove.com/opt//usr/data/opt/3a8f4c4a3803c74f0e714b029c891992.png
     *
     * worker_state":1
     */

    private int user_id;
    private String name;
    private String rating;
    private String head_img;
    private String worker_state;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getWorker_state() {
        return worker_state;
    }

    public void setWorker_state(String worker_state) {
        this.worker_state = worker_state;
    }
}
