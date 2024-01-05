package novel.mcMusicServer.service;

import org.springframework.stereotype.Repository;
import novel.mcMusicServer.pojo.Response;

/**
 * OutputDataService
 */
@Repository
public interface OutputDataService {
  public Response getFile(String playerName, String gameStartTime, int index);

}
