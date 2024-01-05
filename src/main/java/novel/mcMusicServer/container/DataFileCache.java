package novel.mcMusicServer.container;

import java.util.Collections;
import java.util.Map;

/**
 * DataFileCache
 */
public class DataFileCache {
  private static Map<String, String> cache;

  private DataFileCache() {}

  private static void init() {
    if (cache == null) {
      synchronized (DataFileCache.class) {
        if (cache == null) {
          int cacheSize = 20;
          cache = Collections.synchronizedMap(new LruCache(cacheSize));
        }
      }
    }
  }

  public static String getFileContent(String fileName) {
    init();
    return cache.get(fileName);
  }

  public static void addFile(String fileName, String content) {
    init();
    cache.put(fileName, content);
  }
}
