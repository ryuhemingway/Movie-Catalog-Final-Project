package student.view;

import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class draws a simple bar chart from a set of labels and values, like
 * genre names paired with average ratings, or movie titles paired with
 * their rating.
 */
public class BarChartPanel extends JPanel {

  /** How tall the tallest bar is allowed to get, in pixels. */
  private static final int MAX_BAR_HEIGHT = 90;
  /** How wide each bar is, in pixels. */
  private static final int BAR_WIDTH = 28;
  /** The space between bars, in pixels. */
  private static final int BAR_GAP = 28;

  /** The current data being charted, label mapped to its value. */
  private Map<String, Double> data;

  /**
   * This builds an empty chart with no data yet.
   */
  public BarChartPanel() {
    data = new LinkedHashMap<>();
  }

  /**
   * This sets the data to chart and redraws the panel.
   *
   * @param newData the labels and values to draw as bars
   */
  public void setData(Map<String, Double> newData) {
    this.data = newData;

    // This method will clear out any old value/name labels from the last chart before
    // we add new ones for the data.
    removeAll();

    // Find the biggest value so we can scale every bar against it.
    // We start at 0.1 instead of 0 so we never divide by zero below.
    double maxValue = 0.1;
    for (Double value : data.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }

    // We use null layout here so we can place each label at an exact
    // x, y spot lined up with its bar, which paintComponent draws below.
    setLayout(null);
    int x = 10;
    for (Map.Entry<String, Double> entry : data.entrySet()) {
      String label = entry.getKey();
      double value = entry.getValue();
      int barHeight = (int) ((value / maxValue) * MAX_BAR_HEIGHT);

      // This label shows the number above or at the bar, like "8.7"
      JLabel valueLabel = new JLabel(String.valueOf(value));
      valueLabel.setBounds(x, MAX_BAR_HEIGHT - barHeight - 20, BAR_WIDTH + 20, 15);
      add(valueLabel);

      // This label shows the genre or movie title below the bar
      JLabel nameLabel = new JLabel(label);
      nameLabel.setBounds(x, MAX_BAR_HEIGHT + 5, BAR_WIDTH + 40, 15);
      add(nameLabel);

      x += BAR_WIDTH + BAR_GAP;
    }

    revalidate();
    repaint();
  }

  /**
   * This draws the actual bar rectangles for the rating. Swing calls this on its own
   * whenever the panel needs to be redrawn.
   *
   * @param g the graphics object we use to draw the bars
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int x = 10;
    for (Double value : data.values()) {
      // We recompute maxValue here too, since paintComponent can be
      // called on its own without setData running again first.
      double maxValue = 0.1;
      for (Double v : data.values()) {
        if (v > maxValue) {
          maxValue = v;
        }
      }
      int barHeight = (int) ((value / maxValue) * MAX_BAR_HEIGHT);
      int barTop = MAX_BAR_HEIGHT - barHeight;

      g.drawRect(x, barTop, BAR_WIDTH, barHeight);
      x += BAR_WIDTH + BAR_GAP;
    }
  }
}