package com.manage.common.enums;

public enum ResultUtilCodeEnum {

  DEFAULT_SUCCESS_CODE("0000","操作成功"),

  DEFAULT_FAILURE_CODE("1000","操作失败"),

  /**
   * 账户不存在
   */
  USERNAME_NOT_FOUND("1001", "账户不存在。"),


  /**
   * 用户名或密码不正确
   */
  BAD_CREDENTIALS("1002", "用户名或密码不正确。"),

  /**
   * 账户过期
   */
  ACCOUNT_EXPIRED("1003", "账户已过期。"),

  /**
   * 账户锁定
   */
  ACCOUNT_LOCKED("1004", "账户已锁定。"),

  /**
   * 账户不可用
   */
  ACCOUNT_DISABLED("1005", "账户不可用。"),

  /**
   * 证书过期
   */
  CREDENTIALS_EXPIRED("1006", "证书已过期。"),

  /**
   * 没有权限
   */
  NOT_PERMISSION("1007", "没有权限。"),

  /**
   * 未登录
   */
  NOT_LOGIN("1008", "未登录。"),

  /**
   * 系统错误
   */
  SYSTEM_ERROR("9999", "系统错误。");

  private String code ;

  private String msg;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  ResultUtilCodeEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ResultUtilCodeEnum getEnumByCode(String code) {
    for (ResultUtilCodeEnum value : values()) {
      if (code != null && code.equals(value.getCode())) {
        return value;
      }
    }
    return null;
  }
}
