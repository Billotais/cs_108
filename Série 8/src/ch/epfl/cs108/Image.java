package ch.epfl.cs108;

@FunctionalInterface
public interface Image {
    public int apply(double x, double y);

    public static final Image RED_CIRCLE = (x, y) -> {
        double r = Math.sqrt(x * x + y * y);
        return r <= 1.0 ? 0xFF_00_00 : 0xFF_FF_FF;
    };

    public static Image mandelbrot(int maxIterations) {
        return (cr, ci) -> {
            double zr = cr, zi = ci;
            int i = 1;
            while (zr * zr + zi * zi <= 4 && i < maxIterations) {
                double zr1 = zr * zr - zi * zi + cr;
                double zi1 = 2d * zr * zi + ci;
                zr = zr1;
                zi = zi1;
                i += 1;
            }
            
            double p = 1d - Math.pow((double)i / (double)maxIterations, 0.25);
            int p1 = (int)(p * 255.9999);
            return (p1 << 16) | (p1 << 8) | p1;
        };
    }
}
