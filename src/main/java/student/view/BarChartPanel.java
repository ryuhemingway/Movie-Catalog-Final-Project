package student.view;

import java.awt.Color;
import java.awt.Dimension;
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
  /** The space between bars, in pixels. This needs to be at least as
   *  wide as NAME_LABEL_EXTRA_WIDTH, or else neighboring name labels
   *  run into each other. */
  private static final int BAR_GAP = 45;
  /** The starting x position for the first bar, in pixels. */
  private static final int START_X = 10;
  /** How tall the value label above each bar is, in pixels. */
  private static final int VALUE_LABEL_HEIGHT = 15;
  /** How much extra width the value label gets past the bar, in pixels. */
  private static final int VALUE_LABEL_EXTRA_WIDTH = 20;
  /** How much extra width the name label gets past the bar, in pixels. */
  private static final int NAME_LABEL_EXTRA_WIDTH = 40;
  /** Font size for the genre/title label below each bar, in points. */
  private static final float NAME_LABEL_FONT_SIZE = 10f;
  /** How much empty space we leave above the tallest bar, in pixels,
   *  so even that bar's value label has room and never gets cut off. */
  private static final int TOP_MARGIN = 15;
  /** How far below the bar the name label sits, in pixels. */
  private static final int NAME_LABEL_GAP = 5;
  /** The smallest value we allow when scaling bars, so we never divide by zero. */
  private static final double MIN_VALUE = 0.1;
  /** The color we fill each bar in with. */
  private static final Color BAR_COLOR = new Color(100, 149, 237);

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
    // We start at MIN_VALUE instead of 0 so we never divide by zero below.
    double maxValue = MIN_VALUE;
    for (Double value : data.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }

    // We use null layout here so we can place each label at an exact
    // x, y spot lined up with its bar, which paintComponent draws below.
    setLayout(null);
    int x = START_X;
    for (Map.Entry<String, Double> entry : data.entrySet()) {
      String label = entry.getKey();
      double value = entry.getValue();
      int barHeight = (int) ((value / maxValue) * MAX_BAR_HEIGHT);

      // This is the y position every bar sits on, like a floor. We
      // leave room above it (TOP_MARGIN) so even the tallest bar's
      // value label has space and never gets cut off at the top.
      int baselineY = TOP_MARGIN + MAX_BAR_HEIGHT;
      int barTop = baselineY - barHeight;

      // This label shows the number above the bar, like "8.7". We
      // format it to one decimal place, since raw averages can come
      // out with lots of extra digits, like 7.958823529411764.
      String roundedValue = String.format("%.1f", value);
      JLabel valueLabel = new JLabel(roundedValue);
      valueLabel.setBounds(x, barTop - VALUE_LABEL_HEIGHT,
          BAR_WIDTH + VALUE_LABEL_EXTRA_WIDTH, VALUE_LABEL_HEIGHT);
      add(valueLabel);

      // This label shows the genre or movie title below the bar. We
      // use a smaller font here than the default, so longer genre and
      // movie names have a better chance of fitting without getting
      // cut off
      JLabel nameLabel = new JLabel(label);
      nameLabel.setFont(nameLabel.getFont().deriveFont(NAME_LABEL_FONT_SIZE));
      nameLabel.setBounds(x, baselineY + NAME_LABEL_GAP,
          BAR_WIDTH + NAME_LABEL_EXTRA_WIDTH, VALUE_LABEL_HEIGHT);
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

    int x = START_X;
    for (Double value : data.values()) {
      // We recompute maxValue here too, since paintComponent can be
      // called on its own without setData running again first.
      double maxValue = MIN_VALUE;
      for (Double v : data.values()) {
        if (v > maxValue) {
          maxValue = v;
        }
      }

      int barHeight = (int) ((value / maxValue) * MAX_BAR_HEIGHT);

      // Same baseline math as setData(), so the bar lines up with
      // where we placed its labels
      int baselineY = TOP_MARGIN + MAX_BAR_HEIGHT;
      int barTop = baselineY - barHeight;

      // fill the bar in with color first, then draw the outline on
      // top of it so the edges still look crisp
      g.setColor(BAR_COLOR);
      g.fillRect(x, barTop, BAR_WIDTH, barHeight);

      g.setColor(Color.BLACK);
      g.drawRect(x, barTop, BAR_WIDTH, barHeight);

      x += BAR_WIDTH + BAR_GAP;
    }
  }

  /**
   * This tells Swing how big this panel wants to be, based on how many
   * bars we have to draw. Without this, the panel does not reserve any
   * space for itself, so the bars never actually show up on screen.
   *
   * @return the size this panel needs to show all its bars and labels
   */
    @Override
    public Dimension getPreferredSize() {
      int numberOfBars = data.size();
      int width = START_X + (numberOfBars * (BAR_WIDTH + BAR_GAP));

      // this adds up the margin above the bars, the tallest a bar can
      // be, and room below for the name label
      int height = TOP_MARGIN + MAX_BAR_HEIGHT + NAME_LABEL_GAP + VALUE_LABEL_HEIGHT;

      return new Dimension(width, height);
    }
}
