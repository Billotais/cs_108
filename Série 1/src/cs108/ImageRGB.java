package cs108;

import java.awt.image.BufferedImage;

/**
 * Une image continue et infinie, représentée par une fonction associant une
 * couleur à chaque point du plan.
 */
@FunctionalInterface
public interface ImageRGB {
    public ColorRGB apply(double x, double y);
    
    public static final ImageRGB RED_CIRCLE = (x, y) -> {
        double r = Math.sqrt(x * x + y * y);
        return r <= 1.0 ? ColorRGB.RED : ColorRGB.WHITE;
    };
    
    public static ImageRGB chessboard(double size, ColorRGB c1, ColorRGB c2)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException();
        }
        return (x,y) ->
        {
            double nX = Math.floor(x / size);
            double nY = Math.floor(y / size);
            
            return (((int) nX+nY) % 2 == 0 ? c1 : c2);
            
        };
        
    }
    
    public static ImageRGB target(double size,ColorRGB c1, ColorRGB c2)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException();
        }
        
        return (x,y) ->
        {
            double n = Math.floor(Math.sqrt(x*x + y*y) / size);
            return n % 2 == 0 ? c1 : c2;
        };
    }
    
    public static ImageRGB linearHorizontalGradient(double x1, double x2, ColorRGB c1, ColorRGB c2)
    {
        if (x1 > x1)
        {
            throw new IllegalArgumentException();
        }
        
        return (x,y) ->
        {
            if (x <= x1)
            {
                return c1;
            }
            else if (x >= x2)
            {
                return c2;
            }
            else 
            {
                double proportion = (x-x1)/(x2-x1);
                return c1.mixWith(c2, proportion);
            }
            
        };
    }

    public default ImageRGB flattened() {
        return (x, y) -> apply(x, y * 2d);
    }
    public default ImageRGB rotated(double a) {
        double cosInvA = Math.cos(-a);
        double sinInvA = Math.sin(-a);
        return (x, y) -> {
          double rX = x * cosInvA - y * sinInvA;
          double rY = x * sinInvA + y * cosInvA;
          return apply(rX, rY);
       };
    }
    
    public static ImageRGB ofDiscreteImage(ColorRGB bg, BufferedImage i) 
    {
        double h = i.getHeight();
        double w = i.getWidth();
        
        return (x, y) -> {
            int iX = (int)Math.round( x * (w/2d) + w / 2d);
            int iY = (int)Math.round(-y * (w/2d) + h / 2d);
            
            if (iX >= 0 && iY >= 0 && iY < h && iX < w)
            {
                return new ColorRGB(i.getRGB(iX,iY));
            }
            else
            {
                return bg;
            }
        };
    }
    
    

    // TODO: à compléter
}
