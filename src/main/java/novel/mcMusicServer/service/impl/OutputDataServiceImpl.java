package novel.mcMusicServer.service.impl;

import org.springframework.stereotype.Service;
import novel.mcMusicServer.module.OutputModule;
import novel.mcMusicServer.pojo.Response;
import novel.mcMusicServer.service.OutputDataService;

/**
 * OutputDataServiceImpl
 */
@Service
public class OutputDataServiceImpl implements OutputDataService {

  @Override
  public Response getFile(String playerName, String gameStartTime, int index) {
    return OutputModule.getOldData(playerName, gameStartTime, index);
  }

}
