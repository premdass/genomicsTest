package genomics.coordinates;

public class CoOrdinates {
  String name;
  long position;

  public CoOrdinates(String name, long position) {
    this.name = name;
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }



}
