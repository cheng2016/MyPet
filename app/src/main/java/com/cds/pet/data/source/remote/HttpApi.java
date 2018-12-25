package com.cds.pet.data.source.remote;

import com.cds.pet.data.BaseResp;
import com.cds.pet.data.entity.DoctorInfo;
import com.cds.pet.data.entity.Info;
import com.cds.pet.data.entity.Medicine;
import com.cds.pet.data.entity.Order;
import com.cds.pet.data.entity.OrderInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by chengzj on 2017/6/18.
 */

public interface HttpApi {
    //http://gank.io/api/day/2016/10/12
    public static final String base_url = "https://sit.wecarelove.com/api/pet/api/";

    @POST("doctor/login")
    Observable<BaseResp<Info>> login(@Query("content") String json);

    @GET("doctor/info")
    Observable<BaseResp<DoctorInfo>> getDoctorInfo(@Query("content") String json);

    @GET("appoint/list")
    Observable<BaseResp<List<Order>>> getOrderList(@Query("content") String json);

    @GET("appoint/info")
    Observable<BaseResp<OrderInfo>> getOrderInfo(@Query("content") String json);

    @POST("appoint/option")
    Observable<BaseResp> executeOrder(@Query("content") String json);

    @GET("doctor/services")
    Observable<BaseResp<List<Medicine>>> getMedicine(@Query("content") String json);

    @POST("doctor/diagnosticsresult")
    Observable<BaseResp> openOrder(@Query("content") String json);

    @POST("doctor/info")
    Observable<BaseResp> updateWorkState(@Query("content") String json);
}
