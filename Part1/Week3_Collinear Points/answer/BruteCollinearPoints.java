import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {
    
    private int pLen;
    private Point[] points;
    private LineSegment[] lines;
    
    // finds all line segments containing 4 points
    // 找出所有4点线段
    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length == 0)
            throw new NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new NullPointerException();            
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].toString().equals(points[j].toString()))
                    throw new IllegalArgumentException();            
            }
        }
                
//        this.points = new Point[points.length];
//        for (int i = 0; i < points.length; i++) {
//            this.points[i] = points[i];
//        }
        
        this.points = Arrays.copyOf(points, points.length);
        
        this.pLen = points.length;
        this.lines = new LineSegment[0];
        
        findLines();
    }   
    
    // 线段总数
    // the number of line segments
    public int numberOfSegments() {        
        return lines.length;
    }        
    
    
    // 线段集合
    // the line segments
    public LineSegment[] segments() {        
//        LineSegment[] temp = new LineSegment[lines.length];
//        for (int i = 0; i < lines.length; i++) {
//            temp[i] = lines[i];
//        }
        LineSegment[] temp = Arrays.copyOf(lines, lines.length);
        return temp;
    }               
    
    
    
    
    
    private void findLines() {
        
        ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();
        for (int i = 0; i < pLen; i++) {
            for (int j = 0; j < pLen; j++) {
                for (int k = 0; k < pLen; k++) {
                    for (int l = 0; l < pLen; l++) {
                        
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[l]);
                        
                        int cmp1 = points[i].compareTo(points[j]);
                        int cmp2 = points[j].compareTo(points[k]);
                        int cmp3 = points[k].compareTo(points[l]);
                        
                        if (slope1 == slope2 && slope1 == slope3
                                && cmp1 == -1 && cmp2 == -1 && cmp3 == -1) {
                            lineList.add(new LineSegment(points[i], points[l]));
                            //addLine(points[i], points[l]);
                        }
                    }
                }
            }
        }
        if (lineList.size() > 0) {
            lines = new LineSegment[lineList.size()];
            lineList.toArray(lines);
        }
    }
    
    
//    private void addLine(Point first, Point last) {
//        LineSegment newLine = new LineSegment(first, last);
//
//        LineSegment[] tempLines = new LineSegment[lines.length + 1];
//        for (int i = 0; i < lines.length; i++) {
//            tempLines[i] = lines[i];
//        }
//        tempLines[lines.length] = newLine;
//        lines = tempLines;            
//    }
    
    
    
    
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
    
    
}