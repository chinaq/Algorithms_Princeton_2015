import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the rectangle to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        private boolean isByX;
        private int N;
        
        public Node(Point2D p, RectHV rect, boolean isByX, int N) {
            this.p = p;
            this.rect = rect;
            this.isByX = isByX;
            this.N = N;
        }
    }
    
    
    private Node root;
    
    // construct an empty set of points
    public KdTree() {
    }       
    
    
    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }                       
   
    // number of points in the set
    public int size() {
        return size(root);
    }
    
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }
    
    
    
    
    
    
    ////////////////// insert //////////////////////////
    
    // add the point to the set (if it is not already in the set)
    
    public void insert(Point2D p) {
        ifNullThrowException(p);        
        if (root == null) {
            RectHV rect = new RectHV(0, 0, 1, 1);
            Node newNode = new Node(p, rect, true, 1);
            root = newNode;
        } else {
            put(null, root, p);  
        }        
    }              
    
    
    
    private Node put(Node father, Node thisNode, Point2D p) {        
        
        //添加新结点
        if (thisNode == null) {
            Node newNode = createNewNode(father, p);
            return newNode;
        }
        
        int cmp = compare(p, thisNode.p, thisNode.isByX);
        if (cmp == 0) {
            return thisNode;  
        } else if (cmp < 0) {
            thisNode.left = put(thisNode, thisNode.left, p);
        } else {           
            thisNode.right = put(thisNode, thisNode.right, p);
        }
        
        thisNode.N = 1 + size(thisNode.left) + size(thisNode.right);
        return thisNode;        
    }
    
    
    private Node createNewNode(Node father, Point2D p) {
            double xmin = father.rect.xmin();
            double ymin = father.rect.ymin();
            double xmax = father.rect.xmax();
            double ymax = father.rect.ymax();
            double xf = father.p.x();
            double yf = father.p.y();
            
            RectHV rect;            
            int cmp = compare(p, father.p, father.isByX);
            if (cmp < 0) {
                if (father.isByX) 
                    rect = new RectHV(xmin, ymin, xf, ymax); 
                else 
                    rect = new RectHV(xmin, yf, xmax, ymax); 
            } else {
                if (father.isByX) 
                    rect = new RectHV(xf, ymin, xmax, ymax); 
                else 
                    rect = new RectHV(xmin, ymin, xmax, yf);  
            }            
            Node newNode = new Node(p, rect, !father.isByX, 1);
            return newNode;
    }
    
    
    
    
    ////////////////// contain /////////////////////
    
    // does the set contain point p?
    public boolean contains(Point2D p) {        
        ifNullThrowException(p);
        Node temp = new Node(p, null, false, 0);
        return get(temp) != null;
    }     
    
    
    private Node get(Node node) {
        return get(root, node);
    }

    private Node get(Node father, Node node) {
        if (father == null) return null;
        //node.isByX = father.isByX;
        
        int cmp = compare(node.p, father.p, father.isByX);
        if      (cmp < 0) return get(father.left, node);
        else if (cmp > 0) return get(father.right, node);
        else              return father;
    }
    
    
    
    
    
    ////////////// draw ///////////////////////
    
    // draw all points to standard draw
    public void draw() {
        draw(root);
    }              
    
    private void draw(Node node) {
        if (node == null)
            return;
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();
        
        double xmin = node.rect.xmin();
        double ymin = node.rect.ymin();
        double xmax = node.rect.xmax();
        double ymax = node.rect.ymax();
        double xp = node.p.x();
        double yp = node.p.y();
        
        
        StdDraw.setPenRadius();
        if (node.isByX) {
            StdDraw.setPenColor(StdDraw.RED);        
            StdDraw.line(xp, ymin, xp, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, yp, xmax, yp);
        }
        
        draw(node.left);
        draw(node.right);
            
    }
    
    
    
    
    
    ////////// range ///////////////////
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ifNullThrowException(rect);
        Queue<Point2D> queue = new Queue<Point2D>();     
        if (root != null)
            getPoints(queue, root, rect);        
        return queue;
    }
    
    
    private void getPoints(Queue<Point2D> queue, Node node, RectHV aim) {
        if (aim.contains(node.p)) 
            queue.enqueue(node.p);            
        if (node.left != null && aim.intersects(node.left.rect)) 
            getPoints(queue, node.left, aim);
        if (node.right != null && aim.intersects(node.right.rect))
            getPoints(queue, node.right, aim);
    }
    
    
    
    
    
    
    //////////////// nearest ////////////////
        
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {        
        ifNullThrowException(p);
        Point2D result = null;
        double minDistance = 1/0.0;
        
        if (root != null)
            result = getNearest(root, p, minDistance, result);
        return result;
    }              

    private Point2D getNearest(Node node, Point2D aim, 
                             double minDistance, Point2D lastResult) { 
        if (node == null)
            return lastResult;
            
        Point2D result = lastResult;       
        double nowDistance = node.p.distanceSquaredTo(aim);
        if (nowDistance < minDistance) {
            result = node.p;
            minDistance = nowDistance;
        }        
        
        //如果在此矩形内
        Node outerLeft = node.left;
        Node outerRight = node.right;        
        if (node.left != null && node.left.rect.contains(aim)) {
            result = getNearest(node.left, aim, minDistance, result); 
            outerLeft = null;
        } else if (node.right != null && node.right.rect.contains(aim)) {
            result = getNearest(node.right, aim, minDistance, result);
            outerRight = null;
        }        
        minDistance = result.distanceSquaredTo(aim);
        
        //如果不在此矩形内
        if (outerLeft != null 
                && outerLeft.rect.distanceSquaredTo(aim) < minDistance) {
            result = getNearest(node.left, aim, minDistance, result);
            minDistance = result.distanceSquaredTo(aim);
        }
        if (outerRight != null 
                && outerRight.rect.distanceSquaredTo(aim) < minDistance) {
            result = getNearest(node.right, aim, minDistance, result);
            //minDistance = result.distanceSquaredTo(aim);
        }        

        return result;        
    }
    
    
    
    // unit testing of the methods (optional)
//    public static void main(String[] args) {}       
    
    
    private void ifNullThrowException(Object para) {
        if (para == null)
            throw new NullPointerException();
    }
    
    
    
    private int compare(Point2D p1, Point2D p2, boolean isByX) {        
        if (p1.x() == p2.x() && p1.y() == p2.y())
                                  return 0;
        if (isByX) {
            if (p1.x() <= p2.x()) return -1;
            else                  return 1;                
        } else {
            if (p1.y() >= p2.y()) return -1;
            else                  return 1;    
        } 
    }
    
}