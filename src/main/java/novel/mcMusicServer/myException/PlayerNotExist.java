package novel.mcMusicServer.myException;

/**
 * PlayerNotExist
 */
public class PlayerNotExist extends MyException {
  private void init() {
    this.name = "Player Not Exist";
  }

  public PlayerNotExist() {
    super();
    init();
  }

  public PlayerNotExist(String msg) {
    super(msg);
    init();
  }

}
