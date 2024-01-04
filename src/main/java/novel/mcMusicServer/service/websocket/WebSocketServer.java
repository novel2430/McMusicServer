package novel.mcMusicServer.service.websocket;

import java.io.IOException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import novel.mcMusicServer.Utils;
import novel.mcMusicServer.module.OutputModule;
import novel.mcMusicServer.myException.DataNotExist;
import novel.mcMusicServer.myException.PlayerNotExist;
import novel.mcMusicServer.pojo.Response;

/**
 * WebSocketServer
 */
@ServerEndpoint("/get/new-data/{playerName}")
@Component
public class WebSocketServer {
  private Session session;
  private String playerName;
  private Boolean isActive = false;

  public void sendResponse(Response resp) throws IOException {
    this.session.getBasicRemote().sendText(JSON.toJSONString(resp));
  }

  // connect success
  @OnOpen
  public void onOpen(Session session, @PathParam("playerName") String playerName) {
    this.session = session;
    this.playerName = playerName;
    Response resp;
    this.isActive = true;
    Utils.printInfoLog("Client Join", "get [" + playerName + "] data");
    try {
      resp = OutputModule.addNewWebSocketClient(playerName, this);
      this.sendResponse(resp);
    } catch (PlayerNotExist e) {
      e.printLog();
      try {
        this.sendResponse(Utils.buildFailedResponse("Player is not playing"));
      } catch (IOException ee) {
        ee.printStackTrace();
      }
    } catch (DataNotExist e) {
      try {
        this.sendResponse(Utils.buildFailedResponse("Player data still not exist"));
      } catch (IOException ee) {
        ee.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        session.close();
      } catch (IOException ee) {
        ee.printStackTrace();
      }
    }
  }

  // connect close
  @OnClose
  public void onClose() {
    this.isActive = false;
    Utils.printInfoLog("Client Leave", "no get [" + this.playerName + "] data");
  }

  // connect error
  @OnError
  public void onError(Session session, Throwable error) {

  }

  // client send message to server
  @OnMessage
  public void onMessage(String message, Session session) {

  }

  public Boolean isActive() {
    return this.isActive;
  }

  public void closeSession() {
    if (!this.session.isOpen())
      return;
    try {
      this.session.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
