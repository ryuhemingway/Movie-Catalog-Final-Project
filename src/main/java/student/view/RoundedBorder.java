package student.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * This class is a small custom border that draws a rounded rectangle instead
 * of a plain straight line, so our panels look softer like the wireframe draft.
 */
public class RoundedBorder extends AbstractBorder {

  /** How rounded the corners should be. */
  private static final int CORNER_RADIUS = 15;
  /** The color to draw the border in. */
  private final Color color;

  /**
   * This method makes a rounded border using the given color.
   *
   * @param color the color to draw the border in
   */
  public RoundedBorder(Color color) {
    this.color = color;
  }

  @Override
  public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
    g.setColor(color);
    g.drawRoundRect(x, y, width - 1, height - 1, CORNER_RADIUS, CORNER_RADIUS);
  }

  @Override
  public Insets getBorderInsets(java.awt.Component c) {
    return new Insets(10, 10, 10, 10);
  }
}