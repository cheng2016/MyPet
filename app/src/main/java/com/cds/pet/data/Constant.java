package com.cds.pet.data;

/**
 * App 常数变量
 */
public interface Constant {
    String OS_IS_DEBUG = "os_is_debug";

    String HTTP_URL = "url";

    // 拍照回传码
    int PHOTO_REQUEST_CAMERA = 100;
    // 相册选择回传吗
    int PHOTO_REQUEST_GALLERY = 200;
    //裁剪程序请求吗
    int PHOTO_REQUEST_CROP_PHOTO = 300;

    /**
     * 消息类型
     */
    //用户设备添加申请
    int MESSAGE_TYPE_USER_ADD_APPLY = 30;
    //用户设备添加申请被拒绝
    int MESSAGE_TYPE_USER_ADD_APPLY_REFUSE = 31;
    //用户设备添加申请被同意
    int MESSAGE_TYPE_USER_ADD_APPLY_AGREE = 32;
    //管理员解绑用户的设备
    int MESSAGE_TYPE_ADMIN_REMOVE_USER_DEVICE = 33;
    //用户解绑设备
    int MESSAGE_TYPE_USER_REMOVE_DEVICE = 34;
    //管理员解绑自己
    int MESSAGE_TYPE_ADMIN_REMOVE_DEVICE = 35;
    //管理员更换，新管理员收到消息
    int MESSAGE_TYPE_ADMIN_REPLACE = 36;
    //管理员更换,用户收到数据
    int MESSAGE_TYPE_ADMIN_REPLACE_NOTIFY_USER = 37;

    //已接订单
    String ORDER_TYPE_ORDER_RECEIVED = "3";
    //待接订单
    String ORDER_TYPE_WAITING_ORDER = "2";
    //其他订单
    String ORDER_TYPE_OTHER = "5";
    //预约成功
    String ORDER_TYPE_APPOINTMENT_SUCCESS = "7";
}
