package novel.mcMusicServer.module;

import novel.mcMusicServer.Utils;
import novel.mcMusicServer.container.DataFileCache;
import novel.mcMusicServer.container.RunningPlayerCurrentIndexMap;
import novel.mcMusicServer.container.RunningPlayerWebsocketClientMap;
import novel.mcMusicServer.dataStruct.FileData;
import novel.mcMusicServer.myException.DataNotExist;
import novel.mcMusicServer.myException.MyException;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.websocket.WebSocketServer;

/**
 * OutputModule
 */
public class OutputModule {
  private static Response getNewestData(String playerName) throws MyException {
    FileData fileData = RunningPlayerCurrentIndexMap.getData(playerName);
    if (fileData == null || fileData.getIndex() == -1) {
      throw new DataNotExist("From Output Client");
    }
    return Utils.buildSuccessResponseWithData(fileData.getData());
  }

  public static Response getOldData(String playerName, String gameStartTime, int index) {
    String fileName = Utils.buildFileName(playerName, gameStartTime, index);
    String data = DataFileCache.getFileContent(fileName);
    if (data == null) {
      // find disk file
      data = Utils.readFromDisk(fileName);
      if (data == null) {
        return Utils.buildFailedResponse("File Not Exist");
      }
      // store in cache
      DataFileCache.addFile(fileName, data);
    }
    return Utils.buildSuccessResponse(data);
  }

  public static Response addNewWebSocketClient(String playerName, WebSocketServer client)
      throws MyException {
    RunningPlayerWebsocketClientMap.addWebSocket(playerName, client);
    Response resp = getNewestData(playerName);
    return resp;
  }

}
