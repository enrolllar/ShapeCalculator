package classes;

public class Rhombus extends Shape {
  private double length;
  private double height;
  private double radiusExternal;
  private double radiusInternal;

  public Rhombus(double length, double height) {
    this.length = length;
    this.height = height;
  }
  // зачетное задание
  public Rhombus(double length, double angleDegrees, boolean byAngle) {
    this.length = length;
    this.height = length * Math.sin(Math.toRadians(angleDegrees));
  }

  @Override
  public double area() { return length * height; }

  @Override
  public double perimeter() { return 4 * length; }

  @Override
  public double getRadiusInternal() {
    radiusInternal = height / 2;
    return radiusInternal;
  }

  @Override
  public double getRadiusExternal() {
    if (Math.abs(length - height) < 1e-9) {
      radiusExternal = length * Math.sqrt(2) / 2;
      return radiusExternal;
    }
    return -1;
  }
}