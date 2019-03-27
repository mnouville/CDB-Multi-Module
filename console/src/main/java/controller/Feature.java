package controller;

/**
 * Enum class of Controller.
 * 
 * @author mnouville
 * @version 1.0
 */
enum Feature {
  ADD(1), DELETE(2), UPDATE(3), GET(4), GETALL(5), EXIT(6);

  private int code;

  Feature(int code) {
    this.code = code;
  }

  public int getValue() {
    return this.code;
  }
}
