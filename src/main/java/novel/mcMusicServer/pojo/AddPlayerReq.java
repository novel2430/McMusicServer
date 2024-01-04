package novel.mcMusicServer.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * AddPlayerReq
 */
@Data
public class AddPlayerReq {
  @JSONField(name = "PlayerName", ordinal = 1)
  private String playerName;
}
