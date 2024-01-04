package novel.mcMusicServer.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import novel.mcMusicServer.Utils;
import novel.mcMusicServer.module.InputModule;
import novel.mcMusicServer.myException.MyException;
import novel.mcMusicServer.myException.PlayerExist;
import novel.mcMusicServer.myException.PlayerNotExist;
import novel.mcMusicServer.pojo.OneData;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.InputDataService;

/**
 * InputDataServiceImpl
 */
@Service
public class InputDataServiceImpl implements InputDataService {

  private Response handleMyException(MyException e) {
    e.printLog();
    return Utils.buildFailedResponse(e.getName());
  }

  @Override
  public Response playerJoin(String playerName) {
    try {
      Response res = InputModule.playerJoin(playerName);
      InputModule.sendPlayerJoinToWebSocket(playerName);
      Utils.printInfoLog("Player Join", "[" + playerName + "]" + " join seccess");
      return res;
    } catch (PlayerExist e) {
      return handleMyException(e);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Utils.buildFailedResponse();
  }

  @Override
  public Response playerAddData(String playerName, String gameStartTime, OneData data) {
    try {
      Response res = InputModule.addPlayingData(playerName, data);
      // do writting file
      Utils.writtingToDisk(Utils.buildFileName(playerName, gameStartTime, data.getIndex()),
          JSON.toJSONString(data, true));
      Utils.printInfoLog("Update Data", "[" + playerName + "]" + " update data sucess");
      // send new data to websocket
      InputModule.sendUpdateToWebSocket(playerName);
      return res;
    } catch (PlayerNotExist e) {
      return handleMyException(e);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Utils.buildFailedResponse();
  }

  @Override
  public Response playerLeave(String playerName) {
    try {
      Response res = InputModule.playerLeave(playerName);
      Utils.printInfoLog("Player Leave", "[" + playerName + "]" + " leave sucess");
      return res;
    } catch (PlayerNotExist e) {
      return Utils.buildSuccessResponse("ok");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Utils.buildFailedResponse();
  }


}
