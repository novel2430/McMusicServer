package novel.mcMusicServer.container;

import java.util.concurrent.ConcurrentHashMap;
import novel.mcMusicServer.dataStruct.FileData;
import novel.mcMusicServer.myException.PlayerExist;
import novel.mcMusicServer.myException.PlayerNotExist;

/**
 * RunningPlayerCurrentIndexMap
 */
public class RunningPlayerCurrentIndexMap extends ConcurrentHashMap<String, FileData> {
  private static RunningPlayerCurrentIndexMap runningPlayerCurrentIndexMap;

  private RunningPlayerCurrentIndexMap() {
    super();
  }

  private static void init() {
    if (runningPlayerCurrentIndexMap == null) {
      synchronized (RunningPlayerCurrentIndexMap.class) {
        if (runningPlayerCurrentIndexMap == null) {
          runningPlayerCurrentIndexMap = new RunningPlayerCurrentIndexMap();
        }
      }
    }
  }

  public static void addData(String playerName, FileData data) throws PlayerNotExist {
    init();
    if (runningPlayerCurrentIndexMap.containsKey(playerName)) {
      runningPlayerCurrentIndexMap.get(playerName).update(data);
    } else {
      // Throw Player Not Exist
      throw new PlayerNotExist("From Input Client");
    }
  }

  public static FileData getData(String playerName) throws PlayerNotExist {
    init();
    if (runningPlayerCurrentIndexMap.containsKey(playerName)) {
      return runningPlayerCurrentIndexMap.get(playerName);
    } else {
      // Throw Player Not Exist
      throw new PlayerNotExist("From Output Client");
    }
  }

  public static Boolean isPlayerExist(String playerName) {
    init();
    return runningPlayerCurrentIndexMap.containsKey(playerName);
  }

  public static void removePlayer(String playerName) throws PlayerNotExist {
    init();
    if (runningPlayerCurrentIndexMap.containsKey(playerName)) {
      runningPlayerCurrentIndexMap.remove(playerName);
    } else {
      throw new PlayerNotExist();
    }
  }

  public static void addPlayer(String playerName) throws PlayerExist {
    init();
    if (!runningPlayerCurrentIndexMap.containsKey(playerName)) {
      runningPlayerCurrentIndexMap.put(playerName, new FileData(null, -1));
    } else {
      // Throw Player Exist
      throw new PlayerExist("From Input Client");
    }
  }
}
