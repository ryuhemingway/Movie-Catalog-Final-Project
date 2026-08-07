package student.view;

/**
 * This interface is implemented by anything that can be started as the
 * user-facing part of CineVault, whether that's the GUI window or the
 * command line menu.
 */
public interface IView {

  /**
   * This starts the view, showing it to the user.
   */
  void start();
}
