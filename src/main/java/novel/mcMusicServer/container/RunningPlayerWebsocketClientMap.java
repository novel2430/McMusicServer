package novel.mcMusicServer.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import novel.mcMusicServer.myException.MyException;
import novel.mcMusicServer.myException.PlayerExist;
import novel.mcMusicServer.myException.PlayerNotExist;
import novel.mcMusicServer.service.websocket.WebSocketServer;

/**
 * RunningPlayerWebsocketClientMap
 */
public class RunningPlayerWebsocketClientMap
    extends ConcurrentHashMap<String, CopyOnWriteArrayList<WebSocketServer>> {
  private static RunningPlayerWebsocketClientMap runningPlayerWebsocketClientMap;

  private RunningPlayerWebsocketClientMap() {
    super();
  }

  private static void init() {
    if (runningPlayerWebsocketClientMap == null) {
      synchronized (RunningPlayerWebsocketClientMap.class) {
        if (runningPlayerWebsocketClientMap == null) {
          runningPlayerWebsocketClientMap = new RunningPlayerWebsocketClientMap();
        }
      }
    }
  }

  public static void addPlayer(String playerName) throws MyException {
    init();
    if (!runningPlayerWebsocketClientMap.containsKey(playerName)) {
      runningPlayerWebsocketClientMap.put(playerName, new CopyOnWriteArrayList<WebSocketServer>());
    } else {
      throw new PlayerExist("From Input Client");
    }
  }

  public static void removePlayer(String playerName) throws MyException {
    init();
    if (runningPlayerWebsocketClientMap.containsKey(playerName)) {
      runningPlayerWebsocketClientMap.remove(playerName);
    } else {
      throw new PlayerNotExist("From Input Client");
    }
  }

  public static CopyOnWriteArrayList<WebSocketServer> getPlayerWebSocketList(String playerName)
      throws MyException {
    init();
    if (runningPlayerWebsocketClientMap.containsKey(playerName)) {
      return runningPlayerWebsocketClientMap.get(playerName);
    } else {
      throw new PlayerNotExist("From Input Client");
    }
  }

  public static void addWebSocket(String playerName, WebSocketServer client) throws MyException {
    init();
    if (!runningPlayerWebsocketClientMap.containsKey(playerName)) {
      runningPlayerWebsocketClientMap.put(playerName, new CopyOnWriteArrayList<WebSocketServer>());
    }
    runningPlayerWebsocketClientMap.get(playerName).add(client);
    if (!RunningPlayerCurrentIndexMap.isPlayerExist(playerName)) {
      throw new PlayerNotExist("From Output Client");
    }
  }

  public static void removeWebSocket(String playerName, WebSocketServer client) {
    init();
    if (!runningPlayerWebsocketClientMap.containsKey(playerName)) {
      runningPlayerWebsocketClientMap.get(playerName).remove(client);
    }
  }

  public static Boolean isPlayerExist(String playerName) {
    init();
    return runningPlayerWebsocketClientMap.containsKey(playerName);
  }

}
