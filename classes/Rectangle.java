package classes;

public class Rectangle extends Shape {
  private double length;
  private double width;
  private double radiusExternal;
  private double radiusInternal;

  public Rectangle(double length, double width) {
    this.length = length;
    this.width = width;
  }
// зачетное задание
  public Rectangle(double diagonal, double angleDegrees, boolean byAngle) {
    double rad = Math.toRadians(angleDegrees);
    this.length = diagonal * Math.cos(rad);
    this.width  = diagonal * Math.sin(rad);
  }

  @Override
  public double area() { return length * width; }

  @Override
  public double perimeter() { return 2 * (length + width); }

  @Override
  public double getRadiusExternal() {
    radiusExternal = Math.sqrt(length * length + width * width) / 2;
    return radiusExternal;
  }

  @Override
  public double getRadiusInternal() {
    if (Math.abs(length - width) < 1e-9) { radiusInternal = length / 2; return radiusInternal; }
    return -1;
  }

  public double getLength() { return length; }
  public double getWidth()  { return width; }
}