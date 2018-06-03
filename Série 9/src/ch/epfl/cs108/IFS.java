package ch.epfl.cs108;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class IFS {
    private final double minX, minY, width;
    private final List<UnaryOperator<Point>> functions;
    private final int functionsCount;

    public IFS(double minX, double minY, double width, List<UnaryOperator<Point>> functions) {
        if (! (functions.size() > 0))
            throw new IllegalArgumentException("no functions given");
        if (! (width > 0))
            throw new IllegalArgumentException("non-positive width");

        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.functions = Collections.unmodifiableList(new ArrayList<>(functions));
        this.functionsCount = functions.size();
    }

    public double minX() { return minX; }
    public double minY() { return minY; }
    public double width() { return width; }

   /* public List<Point> points(long count) {
        
        Random random = new Random(12);
        return Stream.iterate(new Point(0, 0), p -> apply(random.nextInt(), p))
                .skip(20)
                .limit(count)
                .collect(Collectors.toList());    
    }*/
    public List<Point> points(long count)
    {
        return Stream.iterate(new Pair<> (new Point(0, 0),new PCG(12))
                , p -> new Pair<>(apply(p.second().get(), p.first()),p.second().next()))
                .map(Pair::first)
                .skip(20)
                .limit(count)
                
                .collect(Collectors.toList());    
    }

    // Apply the n-th function (modulo the number of functions) to the point p.
    private Point apply(int n, Point p) {
        return functions.get(Math.floorMod(n, functionsCount)).apply(p);
    }
    
    public static final class Builder {
        private double minX = 0, minY = 1, width = 1;
        private List<UnaryOperator<Point>> functions = new ArrayList<>();
        
        public Builder setMinX(double newMinX) {
            minX = newMinX;
            return this;
        }
        
        public Builder setMinY(double newMinY) {
            minY = newMinY;
            return this;
        }
        
        public Builder setWidth(double newWidth) {
            width = newWidth;
            return this;
        }
        
        public Builder addFunction(UnaryOperator<Point> f) {
            functions.add(f);
            return this;
        }
        
        public IFS build() {
            return new IFS(minX, minY, width, functions);
        }
    }
}
