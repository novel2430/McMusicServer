package novel.mcMusicServer.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Response
 */
@Getter
@Setter
public class Response {
  private int state = 0;
  private Object data;

  public Response() {}

  public Response(int state, Object data) {
    this.state = state;
    this.data = data;
  }
}
