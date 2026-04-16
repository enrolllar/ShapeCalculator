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

  @Override
  public double area() {
    return length * width;
  }

  @Override
  public double perimeter() {
    return 2 * (length + width);
  }

  @Override
  public double getRadiusExternal() {
    radiusExternal = Math.sqrt(length * length + width * width) / 2;
    return radiusExternal;
  }

  @Override
  public double getRadiusInternal() {
    if (length == width) {
      radiusInternal = length / 2;
      return radiusInternal;
    }
    return -1;
  }
}