package com.driveable.driveable.Utils;

public class CustomError {
  private String msg;
  private int errorCode;

  public CustomError(int errorCode, String msg) {
    this.errorCode = errorCode;
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
