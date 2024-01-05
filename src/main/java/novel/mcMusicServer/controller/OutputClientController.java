package novel.mcMusicServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.OutputDataService;

/**
 * OutputClientController
 */
@RestController
public class OutputClientController {
  @Autowired
  private OutputDataService outputService;

  @GetMapping("/api/output/get-old-data")
  public Response getFile(@RequestParam(value = "playerName") String playerName,
      @RequestParam(value = "gameStartTime") String gameStartTime,
      @RequestParam(value = "index") int index) {
    return outputService.getFile(playerName, gameStartTime, index);
  }

}
