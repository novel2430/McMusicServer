package novel.mcMusicServer.container;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LruCache
 */
public class LruCache extends LinkedHashMap<String, String> {
  private int capacity;

  public LruCache(int capacity) {
    super(16, 0.75f, true);
    this.capacity = capacity;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
    return size() > capacity;
  }
}
