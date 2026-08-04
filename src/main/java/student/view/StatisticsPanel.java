package student.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class shows the average rating statistics at the bottom of the GUI window.
 */
public class StatisticsPanel extends JPanel {

  /** Shows the current statistics text. */
  private JLabel statsLabel;

  /**
   * This methodd builds an empty statistics panel.
   */
  public StatisticsPanel() {
    add(new JLabel("StatisticsPanel"));
    statsLabel = new JLabel("Average Rating By Genre etc..");
    add(statsLabel);
  }

  /**
   * This method updates the text showing the average rating.
   *
   * @param averageRating the average rating to display
   */
  public void setAverageRating(double averageRating) {
    statsLabel.setText(String.format("Average Rating: %.1f", averageRating));
  }
}
