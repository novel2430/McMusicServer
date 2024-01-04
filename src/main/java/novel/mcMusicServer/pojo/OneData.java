package novel.mcMusicServer.pojo;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * OneData
 */
@Getter
@Setter
public class OneData {
  @JSONField(name = "Index", ordinal = 1)
  private int index;
  @JSONField(name = "Biome", ordinal = 2)
  private Map<String, Double> biome = new HashMap<String, Double>();
  @JSONField(name = "Time", ordinal = 3)
  private Map<String, Double> time = new HashMap<String, Double>();
  @JSONField(name = "Climate", ordinal = 4)
  private Map<String, Double> climate = new HashMap<String, Double>();
  @JSONField(name = "Temperature", ordinal = 5)
  private Map<String, Double> temperature = new HashMap<String, Double>();
  @JSONField(name = "Health", ordinal = 6)
  private Map<String, Double> health = new HashMap<String, Double>();
  @JSONField(name = "Hunger", ordinal = 7)
  private Map<String, Double> hunger = new HashMap<String, Double>();
  @JSONField(name = "Status", ordinal = 8)
  private Map<String, Double> status = new HashMap<String, Double>();
  @JSONField(name = "Motion", ordinal = 9)
  private Map<String, Double> motion = new HashMap<String, Double>();
  @JSONField(name = "Placing", ordinal = 10)
  private Map<String, Double> placing = new HashMap<String, Double>();
}
