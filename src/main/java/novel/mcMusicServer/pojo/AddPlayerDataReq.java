package novel.mcMusicServer.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * AddPlayerDataReq
 */
@Data
public class AddPlayerDataReq {
  @JSONField(name = "PlayerName", ordinal = 1)
  private String playerName;
  @JSONField(name = "GameStartTime", ordinal = 2)
  private String gameStartTime;
  @JSONField(name = "Data", ordinal = 3)
  private OneData data;
}
