package novel.mcMusicServer.watchdog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

/**
 * WatchDogService
 */
@Service
public class WatchDogService {
  @Autowired
  private WatchDogThread watchdog;

  @PostConstruct
  public void startThread() {
    new Thread(watchdog).start();
  }

}
