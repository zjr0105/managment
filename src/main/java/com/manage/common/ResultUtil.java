package com.manage.common;

import com.manage.common.enums.ResultUtilCodeEnum;

import java.io.Serializable;

public class ResultUtil<T> implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;


  /**
   * 状态码
   */
  private String code;

  /**
   * 状态
   */
  private Boolean success;

  /**
   * 提示消息
   */
  private String msg;

  /**
   * 数据对象
   */
  private T data;

  /**
   * 无参构造器
   */
  private ResultUtil() {
    super();
  }

  /**
   * 只返回状态，状态码，消息
   *
   * @param success
   * @param code
   * @param msg
   */
  private ResultUtil(Boolean success, String code, String msg) {
    super();
    this.success = success;
    this.code = code;
    this.msg = msg;
  }

  /**
   * 只返回状态，状态码，数据对象
   *
   * @param success
   * @param code
   * @param data
   */
  private ResultUtil(Boolean success, String code, T data) {
    super();
    this.success = success;
    this.code = code;
    this.data = data;
  }

  /**
   * 返回全部信息即状态，状态码，消息，数据对象
   *
   * @param success
   * @param code
   * @param msg
   * @param data
   */
  private ResultUtil(Boolean success, String code, String msg, T data) {
    super();
    this.success = success;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static <T> ResultUtil<T> success(String code, String msg, T data) {
    return new ResultUtil<T>(true, code, msg, data);
  }

  public static <T> ResultUtil<T> success(String msg, T data) {
    return success(ResultUtilCodeEnum.DEFAULT_SUCCESS_CODE.getCode(), msg, data);
  }

  public static <T> ResultUtil<T> success(T data) {
    return success(ResultUtilCodeEnum.DEFAULT_SUCCESS_CODE, data);
  }

  public static <T> ResultUtil<T> success(ResultUtilCodeEnum codeEnum,T data) {
    return success(codeEnum.getCode(), codeEnum.getMsg(), data);
  }
  
  public static ResultUtil<?> success(String msg) {
    return success(msg, null);
  }
  public static ResultUtil<?> success() {
    return success(ResultUtilCodeEnum.DEFAULT_SUCCESS_CODE, null);
  }

  
  public static ResultUtil<?> error(String code, String msg) {
    return new ResultUtil<>(false, code, msg);
  }
  
  public static ResultUtil<?> error(String msg) {
    return error(ResultUtilCodeEnum.DEFAULT_FAILURE_CODE.getCode(), msg);
  }

  public static ResultUtil<?> error(ResultUtilCodeEnum codeEnum) {
    return error(codeEnum.getCode(), codeEnum.getMsg());
  }

  @Override
  public String toString() {
    return "MarsResult{" +
        "code='" + code + '\'' +
        ", success=" + success +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }
}
