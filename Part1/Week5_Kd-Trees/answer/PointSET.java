import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;


public class PointSET {
    
    private SET<Point2D> set;
    
    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }       
    
    
    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }                       
   
    // number of points in the set
    public int size() {
        return set.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        ifNullThrowException(p);
        set.add(p);
    }              
    
    
    // does the set contain point p?
    public boolean contains(Point2D p) {        
        ifNullThrowException(p);
        return set.contains(p);
    }     
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D p: set) {
            p.draw();
        }
    }              
    
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ifNullThrowException(rect);
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D p: set) {
            if (rect.contains(p))
                queue.enqueue(p);
        }
        return queue;
    }
    
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {        
        ifNullThrowException(p);
        Point2D result = null;
        double minDistance = 1/0.0;
        for (Point2D thisP: set) {
            if (result == null || p.distanceSquaredTo(thisP) < minDistance) {
                result = thisP;                
                minDistance = p.distanceSquaredTo(thisP);
            }
        }
        return result;
    }              

    
//    // unit testing of the methods (optional)
//    public static void main(String[] args) {}       
    
    
    private void ifNullThrowException(Object para) {
        if (para == null)
            throw new NullPointerException();
    }
    
}