import java.util.*;

public class Quadrilateral implements TwoDShape, Positionable, Comparable<TwoDShape> {

    List<TwoDPoint> vertices;

    public Quadrilateral(List<TwoDPoint> vertices) {
        this.vertices = vertices;
        setPosition(this.vertices);
    }

    /**
     * Sets the position of this quadrilateral according to the first four elements in the specified list of points. The
     * quadrilateral is formed on the basis of these four points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than four elements, the subsequent elements are ignored.
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
        TwoDPoint p4 = vertices.get(3);
        l.add(p1);
        l.add(p2);
        l.add(p3);
        l.add(p4);
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
        double m3 = getSlope(first, l.get(3));

        if(m1>=m2 & m1>=m3){
            res.add(l.get(1));
            if(m2>m3){
                res.add(l.get(2));
                res.add(l.get(3));
            }
            else{
                res.add(l.get(3));
                res.add(l.get(2));
            }
        }
        else if(m2>=m1 & m2>=m3){
            res.add(l.get(2));
            if(m1>m3){
                res.add(l.get(1));
                res.add(l.get(3));
            }
            else{
                res.add(l.get(3));
                res.add(l.get(1));
            }
        }
        else{
            res.add(l.get(3));
            if(m2>m1){
                res.add(l.get(2));
                res.add(l.get(1));
            }
            else{
                res.add(l.get(1));
                res.add(l.get(2));
            }
        }
        this.vertices = res;
    }

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

    public boolean compare(TwoDPoint p1, TwoDPoint p2){
        if((p1.getX() == p2.getX()) & (p1.getY() == p2.getY())){
            return false;
        }
        return true;
    }

    public double getSlope(TwoDPoint p1, TwoDPoint p2){
        if(p1.getX() == p2.getX()) {
            if (p1.getY() < p2.getY()) {
                return 1 * Double.POSITIVE_INFINITY;
            } else {
                return -1*Double.POSITIVE_INFINITY;
            }
        }
        return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
    }

    /**
     * @return the number of sides of this quadrilateral, which is always set to four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The <i>trivial</i> quadrilateral, where all
     * four corner vertices are the same point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a quadrilateral, and
     * <code>false</code> otherwise. For example, if three of the four vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        List<TwoDPoint> l1 = new ArrayList<>();
        l1.add((TwoDPoint) vertices.get(0));
        l1.add((TwoDPoint) vertices.get(1));
        l1.add((TwoDPoint) vertices.get(2));

        List<TwoDPoint> l2 = new ArrayList<>();
        l2.add((TwoDPoint) vertices.get(1));
        l2.add((TwoDPoint) vertices.get(2));
        l2.add((TwoDPoint) vertices.get(3));

        Triangle t1 = new Triangle(l1);
        double a1 = t1.checkarea(l1);
        Triangle t2 = new Triangle(l2);
        double a2 = t2.checkarea(l2);
        if(a1>0 & a2>0){
            return true;
        }
        return false; // TODO
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * quadrilateral becoming invalid (e.g., all four corners collapse to a single point), then it is left unchanged.
     * Snapping is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        List<TwoDPoint> res = null;
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
        // TODO
    }

    /**
     * @return the area of this quadrilateral
     */
    public double area() {
        List<TwoDPoint> l1 = new ArrayList<>();
        l1.add(this.vertices.get(0));
        l1.add(this.vertices.get(1));
        l1.add(this.vertices.get(2));

        List<TwoDPoint> l2 = new ArrayList<>();
        l2.add(this.vertices.get(1));
        l2.add(this.vertices.get(2));
        l2.add(this.vertices.get(3));

        Triangle t1 = new Triangle(l1);
        double a1 = t1.area();
        Triangle t2 = new Triangle(l2);
        double a2 = t2.area();
        return a1 + a2;
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
     */
    public double perimeter() {
        double p = 0;
        for(int i=0;i<this.vertices.size()-1;i++){
            p += length((TwoDPoint) this.vertices.get(i), (TwoDPoint) this.vertices.get(i+1));
        }
        p += length(this.vertices.get(0), this.vertices.get(3));
        return p; // TODO
    }

    public double length(TwoDPoint p1, TwoDPoint p2){
        double x1 = p1.getX() - p2.getX();
        double y1 = p1.getY() - p2.getY();
        double d = x1*x1 + y1*y1;
        return Math.sqrt(d);
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

    @Override
    public String toString() {
        double x1 = this.vertices.get(0).getX();
        double y1 = this.vertices.get(0).getY();
        double x2 = this.vertices.get(1).getX();
        double y2 = this.vertices.get(1).getY();
        double x3 = this.vertices.get(2).getX();
        double y3 = this.vertices.get(2).getY();
        double x4 = this.vertices.get(3).getX();
        double y4 = this.vertices.get(3).getY();
        return "Quadrilateral[(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "), (" + x3 + ", " + y3 + "), (" + x4 + ", " + y4 +")]";
    }

}
