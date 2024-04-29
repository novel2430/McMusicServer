package novel.mcMusicServer.watchdog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import novel.mcMusicServer.Utils;

/**
 * WatchDogThread
 */
@Component
public class WatchDogThread implements Runnable {
  private Map<String, Integer> playerState = new HashMap<String, Integer>();
  private Integer durationSec = 60;

  private void doCheck() {
    RunningPlayerStateMap currentStateMap = RunningPlayerStateMap.getInstance();
    List<String> record = new ArrayList<String>();
    // Update playerState
    for (Map.Entry<String, Integer> entry : playerState.entrySet()) {
      if (!currentStateMap.containsKey(entry.getKey())) {
        record.add(entry.getKey());
      }
    }
    for (String playerName : record) {
      playerState.remove(playerName);
    }
    // Kill not alive
    record.clear();
    for (Map.Entry<String, Integer> entry : currentStateMap.entrySet()) {
      if (playerState.containsKey(entry.getKey())) {
        if (entry.getValue() <= playerState.get(entry.getKey())) {
          record.add(entry.getKey());
          Utils.printInfoLog("WatchDog", "[" + entry.getKey() + "] not alive");
        } else {
          playerState.put(entry.getKey(), entry.getValue());
        }
      } else {
        playerState.put(entry.getKey(), entry.getValue());
      }
    }
    for (String playerName : record) {
      playerState.remove(playerName);
      currentStateMap.removePlayer(playerName);
    }
  }

  @Override
  public void run() {
    Long startTime = System.currentTimeMillis();
    while (true) {
      if (System.currentTimeMillis() - startTime >= durationSec * 1000) {
        doCheck();
        startTime = System.currentTimeMillis();
      }
    }
  }


}
