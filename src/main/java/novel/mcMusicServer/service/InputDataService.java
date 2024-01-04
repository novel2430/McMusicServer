package novel.mcMusicServer.service;

import org.springframework.stereotype.Repository;
import novel.mcMusicServer.pojo.OneData;
import novel.mcMusicServer.pojo.Response;

/**
 * InputDataService
 */
@Repository
public interface InputDataService {
  Response playerJoin(String playerName);

  Response playerAddData(String playerName, String gameStartTime, OneData data);

  Response playerLeave(String playerName);

}
