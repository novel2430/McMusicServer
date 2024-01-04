package novel.mcMusicServer.myException;

/**
 * PlayerExist
 */
public class PlayerExist extends MyException {
  private void init() {
    this.name = "Player Exist";
  }

  public PlayerExist() {
    super();
    init();
  }

  public PlayerExist(String msg) {
    super(msg);
    init();
  }

}
