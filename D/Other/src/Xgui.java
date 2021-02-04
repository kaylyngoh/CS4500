public class Xgui {

  public static void main(String[] args) {

    if (args.length == 1) {
      try {
        int size = Integer.parseInt(args[0]);
        if (size >= 1 && size <= 1000) {
          View view = new View(size);
          Controller controller = new Controller(view);
          controller.play();
        } else {
          throw new IllegalArgumentException("Only accepts integers 1 to 1000");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Only accepts integers");
      }
    } else {
      throw new IllegalArgumentException("Should only have 1 argument which represents the size of the hexagon in pixels");
    }
  }
}
