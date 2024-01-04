package novel.mcMusicServer.myException;

import novel.mcMusicServer.Utils;

public class MyException extends Exception {
  private String message = "";
  protected String name = "";

  public MyException() {};

  public MyException(String message) {
    this.message = message;
  }

  public void printLog() {
    Utils.printErrorLog(name, message);
  }

  public String getName() {
    return this.name;
  }

  public String getMessage() {
    return this.message;
  }

}
