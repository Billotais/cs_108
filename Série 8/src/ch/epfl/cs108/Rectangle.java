package ch.epfl.cs108;

public final class Rectangle {
    private final double minX, maxX, minY, maxY;
    
    public Rectangle(double minX, double maxX, double minY, double maxY) {
        if (! (minX < maxX))
            throw new IllegalArgumentException("non-positive width");
        if (! (minY < maxY))
            throw new IllegalArgumentException("non-positive height");
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public double minX() { return minX; }
    public double maxX() { return maxX; }
    public double minY() { return minY; }
    public double maxY() { return maxY; }
    
    public double width() { return maxX - minX; }
    public double height() { return maxY - minY; }
    
    public Rectangle translatedBy(double dX, double dY) {
        return new Rectangle(minX + dX, maxX + dX, minY + dY, maxY + dY);
    }
    
    public Rectangle scaledBy(double sX, double sY) {
        return new Rectangle(minX, minX + (maxX - minX) * sX, minY, minY + (maxY - minY) * sY);
    }
}
