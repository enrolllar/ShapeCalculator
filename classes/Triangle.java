package classes;

public class Triangle extends Shape {
  private double base;
  private double height;
  private double lengthSide1;
  private double lengthSide2;
  private double lengthSide3;
  private double radiusExternal;
  private double radiusInternal;

  public Triangle(double base, double height,
                  double lengthSide1, double lengthSide2, double lengthSide3) {
    this.base = base;
    this.height = height;
    this.lengthSide1 = lengthSide1;
    this.lengthSide2 = lengthSide2;
    this.lengthSide3 = lengthSide3;
  }
  // зачетное задание
  public Triangle(double side1, double side2, double angleDegrees, boolean byAngle) {
    double rad = Math.toRadians(angleDegrees);
    this.lengthSide1 = side1;
    this.lengthSide2 = side2;
    this.lengthSide3 = Math.sqrt(side1 * side1 + side2 * side2 - 2 * side1 * side2 * Math.cos(rad));
    double area = 0.5 * side1 * side2 * Math.sin(rad);
    this.base   = side1;
    this.height = (2 * area) / side1;
  }

  public boolean exists() {
    return (lengthSide1 + lengthSide2 > lengthSide3) &&
            (lengthSide1 + lengthSide3 > lengthSide2) &&
            (lengthSide2 + lengthSide3 > lengthSide1);
  }

  @Override
  public double area() { return (base * height) / 2; }

  @Override
  public double perimeter() { return lengthSide1 + lengthSide2 + lengthSide3; }

  private double heronArea() {
    double s = (lengthSide1 + lengthSide2 + lengthSide3) / 2;
    return Math.sqrt(s * (s - lengthSide1) * (s - lengthSide2) * (s - lengthSide3));
  }

  @Override
  public double getRadiusInternal() {
    if (!exists()) return -1;
    double s = (lengthSide1 + lengthSide2 + lengthSide3) / 2;
    radiusInternal = heronArea() / s;
    return radiusInternal;
  }

  @Override
  public double getRadiusExternal() {
    if (!exists()) return -1;
    radiusExternal = (lengthSide1 * lengthSide2 * lengthSide3) / (4 * heronArea());
    return radiusExternal;
  }
}