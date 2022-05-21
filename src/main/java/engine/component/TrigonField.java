package engine.component;

/**
 * @author lbaudenb
 */
public class TrigonField extends Field {

  private final int isRight;

  public TrigonField(int x, int y, int isRight) {
    super(x, y);
    this.isRight = isRight;
  }

  public int getIsRight() {
    return this.isRight;
  }
}
