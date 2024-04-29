package novel.mcMusicServer.watchdog;

import java.util.concurrent.ConcurrentHashMap;
import novel.mcMusicServer.container.RunningPlayerCurrentIndexMap;

/**
 * RunningPlayerStateMap
 */
public class RunningPlayerStateMap extends ConcurrentHashMap<String, Integer> {
  private static RunningPlayerStateMap runningPlayerStateMap;

  private RunningPlayerStateMap() {
    super();
  }

  private static void init() {
    if (runningPlayerStateMap == null) {
      synchronized (RunningPlayerStateMap.class) {
        if (runningPlayerStateMap == null) {
          runningPlayerStateMap = new RunningPlayerStateMap();
        }
      }
    }
  }

  public static RunningPlayerStateMap getInstance() {
    init();
    return runningPlayerStateMap;
  }

  public void addPlayer(String playerName) {
    runningPlayerStateMap.put(playerName, -1);
  }

  public void updatePlayer(String playerName) {
    if (runningPlayerStateMap.containsKey(playerName)) {
      int cur = runningPlayerStateMap.get(playerName);
      runningPlayerStateMap.put(playerName, cur + 1);
    }
  }

  public Integer getPlayerState(String playerName) {
    if (runningPlayerStateMap.containsKey(playerName)) {
      return runningPlayerStateMap.get(playerName);
    }
    return null;
  }

  public void removePlayer(String playerName) {
    if (runningPlayerStateMap.containsKey(playerName)) {
      runningPlayerStateMap.remove(playerName);
      try {
        RunningPlayerCurrentIndexMap.removePlayer(playerName);
      } catch (Exception e) {
      }
    }
  }

}
