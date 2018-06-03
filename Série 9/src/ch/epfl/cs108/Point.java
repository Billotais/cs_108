package ch.epfl.cs108;

public final class Point {
    private final double x, y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double x() { return x; }
    public double y() { return y; }

    public Point scaled(double sX, double sY) {
        return new Point(x() * sX, y() * sY);
    }
    
    public Point scaled(double s) {
        return scaled(s, s);
    }
    
    public Point translated(double dX, double dY) {
        return new Point(x() + dX, y() + dY);
    }
    
    @Override
    public String toString() {
        return String.format("(%f,%f)", x(), y());
    }
}
