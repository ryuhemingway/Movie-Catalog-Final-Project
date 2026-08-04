package student.view;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class shows the average rating statistics at the bottom of the GUI window.
 */
public class StatisticsPanel extends JPanel {

  /** How tall this panel should be, in pixels, to leave room for a chart. */
  private static final int PANEL_HEIGHT = 200;
  /** Shows the current statistics text. */
  private JLabel statsLabel;
  /**
   * This method builds an empty statistics panel.
   */
  public StatisticsPanel() {
    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));
    setPreferredSize(new java.awt.Dimension(0, PANEL_HEIGHT));

    JLabel titleLabel = new JLabel("StatisticsPanel", SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    statsLabel = new JLabel("Average Rating By Genre etc.(Place Holder)", SwingConstants.CENTER);
    add(statsLabel, BorderLayout.CENTER);
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
