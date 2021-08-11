import java.util.ArrayList;
import java.util.List;

public class Circle implements TwoDShape, Positionable, Comparable<TwoDShape> {

    private TwoDPoint center;
    private double radius;

    public Circle(double x, double y, double r) {
        this.center = new TwoDPoint(x, y);
        this.radius = r;
    }

    /**
     * Sets the position of this circle to be centered at the first element in the specified list of points.
     *
     * @param points the specified list of points.
     * @throws IllegalArgumentException if the input does not consist of {@link TwoDPoint} instances
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        for(int i=0; i<points.size(); i++) {
            boolean b =  points.get(i) instanceof TwoDPoint;
            if (!b){
                throw new IllegalArgumentException();
            }
        }
        this.center = (TwoDPoint) points.get(0);
    }

    /**
     * @return the center of this circle as an immutable singleton list
     */
    @Override
    public List<? extends Point> getPosition() {
        List<TwoDPoint> pos = new ArrayList<TwoDPoint>();
        pos.add(this.center);
        return pos;
    }

    /**
     * @return the number of sides of this circle, which is always set to positive infinity
     */
    @Override
    public int numSides() {
        return (int) Double.POSITIVE_INFINITY;
    }

    /**
     * Checks whether or not a list of vertices is a valid collection of vertices for the type of two-dimensional shape.
     *
     * @param centers the list of vertices to check against, where each vertex is a <code>Point</code> type. For
     *                the Circle object, this list is expected to contain only its center.
     * @return <code>true</code> if and only if <code>centers</code> is a single point, and the radius of this circle is
     * a positive value.
     */
    @Override
    public boolean isMember(List<? extends Point> centers) {
        return centers.size() == 1 && radius > 0;
    }

    @Override
    public String toString() {
        double x = this.center.getX();
        double y = this.center.getY();
        double r = this.radius;
        String s = "Circle[center: " + x + "," + y + "; radius: " + r + "]";
        return s;
    }

    /**
     * @return the area of this circle
     */
    public double area() {
        double a = 3.14159*this.radius*this.radius;
        return a; // TODO
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
     */
    public double perimeter() {
        return 2*3.14159*this.radius; // TODO
    }

    @Override
    public int compareTo(TwoDShape o) {
        double a1 = this.area();
        double a2 = o.area();
        if(a1>a2){
            return 1;
        }
        else{
            return -1;
        }
    }

}
