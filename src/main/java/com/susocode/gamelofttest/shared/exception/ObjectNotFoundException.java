package com.susocode.gamelofttest.shared.exception;

public class ObjectNotFoundException extends BaseException {

  public ObjectNotFoundException(String key, String message) {
    super(key, message, 404);
  }

}
