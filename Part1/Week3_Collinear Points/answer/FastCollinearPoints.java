import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
            
    private int pLen;
    private final Point[] points;
    private ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();
    
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null || points.length == 0)
            throw new NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new NullPointerException();            
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();            
            }
        }
                
        this.points = Arrays.copyOf(points, points.length);        
        this.pLen = this.points.length;
        
        findLines();
    } 
    
    // the number of line segments
    public int numberOfSegments() {
        //return lines.length;
        return lineList.size();
    }  
    
    // the line segments
    public LineSegment[] segments() {        
        LineSegment[] temp = new LineSegment[lineList.size()];
        lineList.toArray(temp);
        return temp;
    }    
    
    
    private void findLines() {        
        //Point[] datumPoints = copyPoints(points);
        Point[] datumPoints = Arrays.copyOf(points, points.length);
        
        //ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();
        for (int i = 0; i < pLen; i++) {            
            Arrays.sort(points, datumPoints[i].slopeOrder());
            
            Point endPoint = null;
            double lastSlope = 0;
            double thisSlope;
            int sameCount = 0;
            boolean isDatumFirst = true;
            
            for (int j = 0; j < pLen; j++) {                
                thisSlope = datumPoints[i].slopeTo(points[j]);
                if (j == 0 || thisSlope != lastSlope) {
                    if (sameCount >= 3 && isDatumFirst) {
                        //addLine(datumPoints[i], endPoint);
                        lineList.add(new LineSegment(datumPoints[i], endPoint));
                    }
                    sameCount = 0;
                    isDatumFirst = true;
                    endPoint = points[j];
                } 
                
                if (isDatumFirst) {         
                    isDatumFirst = datumPoints[i].compareTo(points[j]) < 0;
                    if (endPoint.compareTo(points[j]) < 0) 
                        endPoint = points[j];
                }                
                sameCount++;
                lastSlope = thisSlope;
                
                if (j == pLen -1 && sameCount >= 3 && isDatumFirst)
                    //addLine(datumPoints[i], endPoint);
                    lineList.add(new LineSegment(datumPoints[i], endPoint));
            }
        }
        
    }
    
    
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}