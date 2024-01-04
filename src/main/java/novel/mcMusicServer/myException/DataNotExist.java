package novel.mcMusicServer.myException;

/**
 * DataNotExist
 */
public class DataNotExist extends MyException {
  private void init() {
    this.name = "Data Not Exist";
  }

  public DataNotExist() {
    super();
    init();
  }

  public DataNotExist(String msg) {
    super(msg);
    init();
  }

}
