package novel.mcMusicServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import novel.mcMusicServer.pojo.OneData;
import novel.mcMusicServer.pojo.Response;

/**
 * Utils
 */
public class Utils {
  private static String jarPath = buildJarPath();

  private static String buildJarPath() {
    ApplicationHome home = new ApplicationHome(McMusicServerApplication.class);
    return (home.getSource().getParentFile().getPath());
  }

  public static void writtingToDisk(String fileName, String content) throws Exception {
    BufferedWriter write = new BufferedWriter(new FileWriter(fileName));
    write.write(content);
    write.close();
  }

  public static String readFromDisk(String fileName) {
    File file = new File(fileName);
    if (file.exists() && !file.isDirectory()) {
      Long fileLength = file.length();
      byte[] content = new byte[fileLength.intValue()];
      try (InputStream in = new FileInputStream(file)) {
        in.read(content);
        return new String(content);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static void printErrorLog(String name, String msg) {
    LoggerFactory.getLogger(name).error(msg);
  }

  public static void printInfoLog(String name, String text) {
    LoggerFactory.getLogger(name).info(text);
  }

  public static String getSavePath() {
    return jarPath;
  }

  public static String buildFileName(String playerName, String gameStartTime, int index) {
    String fileName = Utils.getSavePath() + "/" + playerName + "_" + gameStartTime + "_"
        + Integer.toString(index) + ".json";
    return fileName;
  }

  public static Response buildFailedResponse(String msg) {
    return new Response(0, msg);
  }

  public static Response buildFailedResponse() {
    return new Response(0, "Failed");
  }

  public static Response buildSuccessResponse(String msg) {
    return new Response(1, msg);
  }

  public static Response buildSuccessResponseWithData(OneData data) {
    return new Response(1, data);
  }
}
