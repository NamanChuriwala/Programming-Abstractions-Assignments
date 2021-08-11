import java.util.ArrayList;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point, Comparable<TwoDPoint> {

    private double x;
    private double y;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        double[] point = {this.x, this.y};
        return point;
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {
        List<TwoDPoint> TwoDPointList = new ArrayList<TwoDPoint>();
        int length = coordinates.length;
        if (length%2 == 0){
            throw new IllegalArgumentException();
        }
        else {
            for (int i=0; i< length; i=i+2) {
                TwoDPoint newPoint = new TwoDPoint(coordinates[i], coordinates[i+1]);
                TwoDPointList.add(newPoint);
            }
        }
        return TwoDPointList; // TODO
    }

    public double getDistance() {
        double d = Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()));
        return d;
    }

    @Override
    public int compareTo(TwoDPoint o) {
        double a1 = this.getDistance();
        double a2 = o.getDistance();
        if(a1>a2){
            return 1;
        }
        else{
            return -1;
        }
    }
}
