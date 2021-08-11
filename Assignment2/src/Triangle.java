import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Triangle implements TwoDShape, Comparable<TwoDShape> {

    List<TwoDPoint> vertices;

    public Triangle(List<TwoDPoint> vertices) {
        this.vertices = vertices;
        setPosition(this.vertices);
    }

    /**
     * Sets the position of this triangle according to the first three elements in the specified list of points. The
     * triangle is formed on the basis of these three points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than three elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {

        List<TwoDPoint> l = new ArrayList<>();
        List<TwoDPoint> res = new ArrayList<>();
        TwoDPoint p1 = vertices.get(0);
        TwoDPoint p2 = vertices.get(1);
        TwoDPoint p3 = vertices.get(2);
        l.add(p1);
        l.add(p2);
        l.add(p3);
        Collections.sort(l, new Comparator<TwoDPoint>() {
            @Override
            public int compare(TwoDPoint p1, TwoDPoint p2){
                if(p1.getX() > p2.getX()){
                    return 1;
                }
                else if(p1.getX() < p2.getX()){
                    return -1;
                }
                else{
                    if(p1.getY() > p2.getY()){
                        return 1;
                    }
                    else{
                        return -1;
                    }
                }
            }
        });
        TwoDPoint first = l.get(0);
        res.add(first);
        double m1 = getSlope(first, l.get(1));
        double m2 = getSlope(first, l.get(2));
        if(m1>m2){
            res.add(l.get(1));
            res.add(l.get(2));
        }
        else{
            res.add(l.get(2));
            res.add(l.get(1));
        }
        this.vertices = res;
    }
        // TODO

    /**
     * Retrieve the position of an object as a list of points. The points are be retrieved and added to the returned
     * list in a clockwise manner on the two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends Point> getPosition() {
        return this.vertices;
    }

    public double getSlope(TwoDPoint p1, TwoDPoint p2) {
        if (p1.getX() == p2.getX()) {
            if (p1.getY() < p2.getY()) {
                return Double.POSITIVE_INFINITY;
            }
            else {
                return -1 * Double.POSITIVE_INFINITY;
            }
        }
        return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
    }
    /**
     * @return the number of sides of this triangle, which is always set to three
     */
    @Override
    public int numSides() {
        return 3;
    }

    /**
     * Checks whether or not a list of vertices forms a valid triangle. The <i>trivial</i> triangle, where all three
     * corner vertices are the same point, is considered to be an invalid triangle.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a triangle, and
     * <code>false</code> otherwise. For example, three vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        double area = checkarea(vertices);
        if (area > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        double x1 = this.vertices.get(0).getX();
        double y1 = this.vertices.get(0).getY();
        double x2 = this.vertices.get(1).getX();
        double y2 = this.vertices.get(1).getY();
        double x3 = this.vertices.get(2).getX();
        double y3 = this.vertices.get(2).getY();
        return "Triangle[(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "), (" + x3 + ", " + y3 + ")]";
    }

    public double checkarea(List<? extends Point> vertices) {
        TwoDPoint p1 = (TwoDPoint) vertices.get(0);
        TwoDPoint p2 = (TwoDPoint) vertices.get(1);
        TwoDPoint p3 = (TwoDPoint) vertices.get(2);
        double s1 = getDistance(p1, p2);
        double s2 = getDistance(p1, p3);
        double s3 = getDistance(p2, p3);
        double s = perimeter()/2;
        double area = s * (s-s1) * (s-s2) * (s-s3);
        if(area<=0){
            return 0;
        }
        area = Math.sqrt(area);
        return area; // TODO
    }

    /**
     * This method snaps each vertex of this triangle to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant triangle will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * triangle becoming invalid (e.g., all corners collapse to a single point), then it is left unchanged. Snapping is
     * an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        List<TwoDPoint> res = null; //= new ArrayList<>();
        for(int i=0; i<this.vertices.size(); i++){
            TwoDPoint curPoint = this.vertices.get(i);
            double x = Math.round(curPoint.getX());
            double y = Math.round(curPoint.getY());
            TwoDPoint newPoint = new TwoDPoint(x,y);
            res.set(i, newPoint);
        }
        if(isMember(res)){
            setPosition(res);
        }
    }

    /**
     * @return the area of this triangle
     */
    public double area() {
        TwoDPoint p1 = this.vertices.get(0);
        TwoDPoint p2 = this.vertices.get(1);
        TwoDPoint p3 = this.vertices.get(2);
        double s1 = getDistance(p1, p2);
        double s2 = getDistance(p1, p3);
        double s3 = getDistance(p2, p3);
        double s = perimeter()/2;
        double area = s * (s-s1) * (s-s2) * (s-s3);
        if(area <= 0){
            return 0;
        }
        area = Math.sqrt(area);
        return area; // TODO
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this triangle
     */
    public double perimeter() {
        TwoDPoint p1 = this.vertices.get(0);
        TwoDPoint p2 = this.vertices.get(1);
        TwoDPoint p3 = this.vertices.get(2);
        double s1 = getDistance(p1, p2);
        double s2 = getDistance(p1, p3);
        double s3 = getDistance(p2, p3);
        double s = s1 + s2 + s3;
        return s; // TODO
    }

    public double getDistance(TwoDPoint p1, TwoDPoint p2) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        double d = ((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2));
        d = Math.sqrt(d);
        return d;
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
