package novel.mcMusicServer.module;

import java.io.IOException;
import novel.mcMusicServer.Utils;
import novel.mcMusicServer.container.RunningPlayerCurrentIndexMap;
import novel.mcMusicServer.container.RunningPlayerWebsocketClientMap;
import novel.mcMusicServer.dataStruct.FileData;
import novel.mcMusicServer.myException.MyException;
import novel.mcMusicServer.pojo.OneData;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.websocket.WebSocketServer;

/**
 * InputModule
 */
public class InputModule {
  public static void sendUpdateToWebSocket(String playerName) throws MyException, IOException {
    OneData data = getNewestPlayerData(playerName);
    if (RunningPlayerWebsocketClientMap.isPlayerExist(playerName)) {
      for (WebSocketServer client : RunningPlayerWebsocketClientMap
          .getPlayerWebSocketList(playerName)) {
        if (client.isActive()) {
          client.sendResponse(Utils.buildSuccessResponseWithData(data));
          Utils.printInfoLog("Client Send", "send [" + playerName + "] update data");
        } else {
          client.closeSession();
          RunningPlayerWebsocketClientMap.removeWebSocket(playerName, client);
        }
      }
    }
  }

  public static void sendPlayerJoinToWebSocket(String playerName) {
    try {
      for (WebSocketServer client : RunningPlayerWebsocketClientMap
          .getPlayerWebSocketList(playerName)) {
        if (client.isActive()) {
          client.sendResponse(Utils.buildFailedResponse("Player data still not exist"));
        } else {
          client.closeSession();
          RunningPlayerWebsocketClientMap.removeWebSocket(playerName, client);
        }
      }
    } catch (Exception e) {
    }
  }

  private static void closeAllWebSocket(String playerName) {
    try {
      if (RunningPlayerWebsocketClientMap.isPlayerExist(playerName)) {
        for (WebSocketServer client : RunningPlayerWebsocketClientMap
            .getPlayerWebSocketList(playerName)) {
          client.closeSession();
        }
        RunningPlayerWebsocketClientMap.removePlayer(playerName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Response playerJoin(String playerName) throws MyException {
    RunningPlayerCurrentIndexMap.addPlayer(playerName);
    return Utils.buildSuccessResponse("ok");
  }

  public static Response addPlayingData(String playerName, OneData data) throws MyException {
    if (data == null) {
      System.out.println("OneData is NULL");
    }
    int index = data.getIndex();
    RunningPlayerCurrentIndexMap.addData(playerName, new FileData(data, index));
    return Utils.buildSuccessResponse("ok");
  }

  public static Response playerLeave(String playerName) throws MyException {
    RunningPlayerCurrentIndexMap.removePlayer(playerName);
    closeAllWebSocket(playerName);
    return Utils.buildSuccessResponse("ok");
  }

  public static OneData getNewestPlayerData(String playerName) throws MyException {
    return RunningPlayerCurrentIndexMap.getData(playerName).getData();
  }

}
