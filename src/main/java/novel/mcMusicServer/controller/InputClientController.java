package novel.mcMusicServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import novel.mcMusicServer.pojo.AddPlayerDataReq;
import novel.mcMusicServer.pojo.AddPlayerReq;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.InputDataService;

/**
 * InputClientController
 */
@RestController
public class InputClientController {
  @Autowired
  private InputDataService inputService;

  @PostMapping("/api/input/add-player")
  public Response addPlayer(@RequestBody AddPlayerReq addplayer) {
    return inputService.playerJoin(addplayer.getPlayerName());
  }

  @PostMapping("/api/input/add-player-data")
  public Response addPlayerData(@RequestBody AddPlayerDataReq addPlayerData) {
    return inputService.playerAddData(addPlayerData.getPlayerName(),
        addPlayerData.getGameStartTime(), addPlayerData.getData());
  }

  @PostMapping("/api/input/remove-player")
  public Response removePlayer(@RequestBody AddPlayerReq removeplayer) {
    return inputService.playerLeave(removeplayer.getPlayerName());
  }

}
