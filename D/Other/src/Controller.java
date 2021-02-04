import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter {

  private View view;

  Controller(View view){
    this.view = view;
  }

  public void play() {
    this.view.setMouseAdapter(this);
    this.view.makeVisible();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.view.doesHexagonContainView(e.getX(), e.getY())) {
      this.view.dispose();
    }
  }
}
