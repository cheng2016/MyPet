package com.cds.pet.service.message;

public class ContentMsg {
  private String title;

  private DetailMsg detail;

  private String tail;
  /**
   * appoint_id : 44
   * opt_time : 1544511184472
   * details : 您有新的预约订单，请及时处理~！
   */

  private String appoint_id;
  private long opt_time;
  private String details;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DetailMsg getDetail() {
    return detail;
  }

  public void setDetail(DetailMsg detail) {
    this.detail = detail;
  }

  public String getTail() {
    return tail;
  }

  public void setTail(String tail) {
    this.tail = tail;
  }

  public String getAppoint_id() {
    return appoint_id;
  }

  public void setAppoint_id(String appoint_id) {
    this.appoint_id = appoint_id;
  }

  public long getOpt_time() {
    return opt_time;
  }

  public void setOpt_time(long opt_time) {
    this.opt_time = opt_time;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
