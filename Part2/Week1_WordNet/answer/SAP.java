import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class SAP {
    
    private class Cache {
        private int len = -1;
        private int ancestor = -1;    
     
        public void setLen(int value)
        {
            len = value;
        }
        public int getLen() {
            return len;
        }
        
        public void setAncestor(int value) {
            ancestor = value;
        }
        public int getAncestor() {
            return ancestor;
        }        
    }
    
    private class OneCache extends Cache {
        private int v = -1;
        private int w = -1;
        
        public void setV(int value)
        {
            v = value;
        }
        public int getV() {
            return v;
        }
        
        public void setW(int value) {
            w = value;
        }
        public int getW() {
            return w;
        }    
    }
    
    
    private class GroupCache extends Cache {
        private ArrayList<Integer> vs = new ArrayList<Integer>();
        private ArrayList<Integer> ws = new ArrayList<Integer>();
        
        public void setVs(ArrayList<Integer> value)
        {
            vs = value;
        }
        public ArrayList<Integer> getVs() {
            return vs;
        }
        
        public void setWs(ArrayList<Integer> value) {
            ws = value;
        }
        public ArrayList<Integer> getWs() {
            return ws;
        }    
    }
    
    
    private OneCache oneCache;
    private GroupCache groupCache;
    private Digraph G;
    
    
    //构造函数
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        ifNullThrowException(G);
        this.G = new Digraph(G);
        this.oneCache = new OneCache();
        this.groupCache = new GroupCache();
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        ifNullThrowException(v);
        ifNullThrowException(w);
        ifOutOfRangeThrowException(v);
        ifOutOfRangeThrowException(w);
        
        if (!existOneCache(v, w))
            setOneCache(v, w);        
        return oneCache.getLen();
    }
    
    
   // a common ancestor of v and w that participates in 
   //a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        ifNullThrowException(v);
        ifNullThrowException(w);
        ifOutOfRangeThrowException(v);
        ifOutOfRangeThrowException(w);
        
        if (!existOneCache(v, w))
            setOneCache(v, w);        
        return oneCache.getAncestor();
    }

    
    private boolean existOneCache(int v, int w) {
        if ((oneCache.v == v && oneCache.w == w) ||
           (oneCache.v == w && oneCache.w == v))
            return true;
        return false;
    }
    
    
    
    private void setOneCache(int v, int w) {
        DeluxeBFS vBfs = new DeluxeBFS(G, v);
        DeluxeBFS wBfs = new DeluxeBFS(G, w);
        
        oneCache.v = v;
        oneCache.w = w;          
        setCache(vBfs, wBfs, oneCache);
    }
    
    
    
    
    
   // length of shortest ancestral path between any vertex 
    //in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {   
        ifNullThrowException(v);
        ifNullThrowException(w);
        
        ArrayList<Integer> vs = copyIterable(v);
        ArrayList<Integer> ws = copyIterable(w);
        if (!isGroupCacheSuit(vs, ws))
            setGroupCache(vs, ws);
        return groupCache.getLen();
    }

    
    
    
    
   // a common ancestor that participates in shortest ancestral path; 
    //-1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        ifNullThrowException(v);
        ifNullThrowException(w);        
        
        ArrayList<Integer> vs = copyIterable(v);
        ArrayList<Integer> ws = copyIterable(w);
        if (!isGroupCacheSuit(vs, ws))
            setGroupCache(vs, ws);
        return groupCache.getAncestor();
    }

    
    
    
    private boolean isGroupCacheSuit(ArrayList<Integer> vs, 
                                     ArrayList<Integer> ws) {        
        if (suit(groupCache.vs, vs) && suit(groupCache.ws, ws) ||
           suit(groupCache.vs, ws) && suit(groupCache.ws, vs))
            return true;
        return false;       
    }
    
    
    private boolean suit(ArrayList<Integer> thisList, 
                         ArrayList<Integer> thatList) {
        if (thisList.size() != thatList.size())
            return false;
        
        Iterator<Integer> thisIter = thisList.iterator();
        Iterator<Integer> thatIter = thatList.iterator();
        int tempThis;
        int tempThat;        
        
        while (thisIter.hasNext()) { 
            tempThis = thisIter.next();
            tempThat = thatIter.next();            
            ifOutOfRangeThrowException(tempThis);
            ifOutOfRangeThrowException(tempThat);
            
            if (tempThis != tempThat) {
                return false;
            } 
        }
        return true;
    }
    
    
    
    
    
    private void setGroupCache(ArrayList<Integer> vs, 
                               ArrayList<Integer> ws) {        
        DeluxeBFS vBfs = new DeluxeBFS(G, vs);
        DeluxeBFS wBfs = new DeluxeBFS(G, ws);
        
        groupCache.vs = vs;
        groupCache.ws = ws;
        setCache(vBfs, wBfs, groupCache);
    }
    
    
    private void setCache(DeluxeBFS vBfs, DeluxeBFS wBfs, Cache cache) { 
        boolean[] vFathers = vBfs.fathers();
        boolean[] wFathers = wBfs.fathers();        
        
        int len = -1;
        int ancestor = -1;
        for (int i = 0; i < vFathers.length; i++) {
            if (vFathers[i] && wFathers[i]) {
                int vLen = vBfs.distTo(i);
                int wLen = wBfs.distTo(i);
                int tempLen = vLen + wLen;
                if (len == -1 || tempLen < len) {
                    len = tempLen;
                    ancestor = i;
                }
            }
        }
        cache.len = len;
        cache.ancestor = ancestor;        
    }
    
    
    
    
    private ArrayList<Integer>  copyIterable(Iterable<Integer> iter) {
        ArrayList<Integer> copy = new ArrayList<Integer>();
        for (int t: iter) {
            copy.add(t);
        }
        Collections.sort(copy);
        return copy;
    }
    
    
    private void ifNullThrowException(Object ob) {
        if (ob == null) 
            throw new NullPointerException();
    }
    
    private void ifOutOfRangeThrowException(int i) {
        if (i < 0 || i > G.V() -1)
            throw new IndexOutOfBoundsException();
    }
    
    
    
   // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
        
        while (!StdIn.isEmpty()) {
            ArrayList<Integer> vs = new ArrayList<Integer>();
            for (int i = 0; i < 3; i++) {
                vs.add(StdIn.readInt());
            }            
            ArrayList<Integer> ws = new ArrayList<Integer>();
            for (int i = 0; i < 3; i++) {
                ws.add(StdIn.readInt());
            }            
            int length   = sap.length(vs, ws);
            int ancestor = sap.ancestor(vs, ws);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

        
    }
}