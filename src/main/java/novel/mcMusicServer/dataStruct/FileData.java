package novel.mcMusicServer.dataStruct;

import lombok.Getter;
import lombok.Setter;
import novel.mcMusicServer.pojo.OneData;

/**
 * FileData
 */
@Getter
@Setter
public class FileData {
  private OneData data;
  private int index;

  public FileData() {};

  public FileData(OneData data, int index) {
    this.data = data;
    this.index = index;
  }

  public void update(FileData data) {
    synchronized (this) {
      if (data.getIndex() > this.getIndex()) {
        this.index = data.getIndex();
        this.data = data.getData();
      }
    }
  }
}
