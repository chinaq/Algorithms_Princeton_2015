import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;


public class SeamCarver {
    
    private boolean isInArrayOrig;
    private boolean isInArrayTrans;
    private boolean isInPic;
    
    private int[] horizontalSeamCache;
    private int[] verticalSeamCache;
    
    private Picture pic;
    //private double[][] energiesOrig;
    //private double[][] energiesTrans;

    private double[][] energiesOrig;
    private double[][] energiesTrans;
//    private Color[][] colorsOrig;
//    private Color[][] colorsTrans;
    private byte[][] origR;
    private byte[][] origG;
    private byte[][] origB;
    private byte[][] transR;
    private byte[][] transG;
    private byte[][] transB;
    
    private int height;
    private int width;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        ifNullThrowEx(picture);
        
        this.pic = new Picture(picture);
        this.isInPic = true;
        this.isInArrayOrig = false;
        this.isInArrayTrans = false;  
        
        horizontalSeamCache = null;
        verticalSeamCache = null;
        
        this.height = pic.height();
        this.width = pic.width();
        
        setToOrig();
    }                
    
    private void setToOrig() {
        if (isInArrayOrig)
            return;
        else if (isInArrayTrans) {
            setFromTransToOrig();
        } else {
            setFromPicToOrig();
        }
    }
    
    
    
    private void setToTrans() {
        if (isInArrayTrans)
            return;
        else if (isInArrayOrig)
            setFromOrigToTrans();
        else {
            setFromPicToOrig();
            setFromOrigToTrans();
        }
    }
    
    
    private void setToPic() {
        if (isInPic)
            return;
        else if (isInArrayOrig) {
            setFromOrigToPic();
        } else {
            setFromTransToOrig();
            setFromOrigToPic();
        }
    }
    
    
    private void setFromOrigToPic() {
        pic = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //pic.set(i, j, colorsOrig[i][j]);
                pic.set(i, j, new Color(0xff&origR[i][j], 0xff&origG[i][j], 0xff&origB[i][j]));
            }
        }            
        isInPic = true;
    }
    
    
    
    
    private void setFromTransToOrig() {
        energiesOrig = new double[width][height];
        //colorsOrig = new Color[width][height];
        origR = new byte[width][height];
        origG = new byte[width][height];
        origB = new byte[width][height];
        
        
        Rotater r = new Rotater(height, width);            
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) { 
                Position p = r.rotate(i, j);
                energiesOrig[p.getX()][p.getY()] = energiesTrans[i][j];
                //colorsOrig[p.getX()][p.getY()] = colorsTrans[i][j];
                origR[p.getX()][p.getY()] = transR[i][j];
                origG[p.getX()][p.getY()] = transG[i][j];
                origB[p.getX()][p.getY()] = transB[i][j];
            }
        }
        isInArrayOrig = true;
    }
    
    
    private void setFromPicToOrig() {
            energiesOrig = new double[width][height];
            //colorsOrig = new Color[width][height];
            origR = new byte[width][height];
            origG = new byte[width][height];
            origB = new byte[width][height];
            
            
            
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {                
                    //colorsOrig[i][j] = pic.get(i, j);
                    origR[i][j] = (byte)pic.get(i, j).getRed();
                    origG[i][j] = (byte)pic.get(i, j).getGreen();
                    origB[i][j] = (byte)pic.get(i, j).getBlue();
                    
                    
                    if (i == 0 || j == 0 || i == width -1 || j == height -1) {
                        energiesOrig[i][j] = 1000f;
                    } else {
                        Color up = pic.get(i-1, j);
                        Color down = pic.get(i+1, j);
                        Color left = pic.get(i, j-1);
                        Color right = pic.get(i, j+1);
                        double rx = up.getRed()-down.getRed();
                        double gx = up.getGreen()-down.getGreen();
                        double bx = up.getBlue()-down.getBlue();
                        double ry = left.getRed()-right.getRed();
                        double gy = left.getGreen()-right.getGreen();
                        double by = left.getBlue()-right.getBlue();
                        energiesOrig[i][j] = 
                            Math.sqrt(rx*rx + gx*gx + bx*bx 
                                          + ry*ry + gy*gy + by*by); 
                    }
                }
            }
            isInArrayOrig = true;
            
            pic = null;
            isInPic = false;
    }
    
    
    
    private void setFromOrigToTrans() {
        energiesTrans = new double[height][width];
        //colorsTrans = new Color[height][width];
        transR = new byte[height][width];
        transG = new byte[height][width];
        transB = new byte[height][width];
        
        Rotater r = new Rotater(width, height);            
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) { 
                Position p = r.contraRotate(i, j);
                energiesTrans[p.getX()][p.getY()] = energiesOrig[i][j];
                //colorsTrans[p.getX()][p.getY()] = colorsOrig[i][j];
                transR[p.getX()][p.getY()] = origR[i][j];
                transG[p.getX()][p.getY()] = origG[i][j];
                transB[p.getX()][p.getY()] = origB[i][j];            
            }
        }
        isInArrayTrans = true;
    }
        
    
    // current picture
    public Picture picture() {
        setToPic();
        return new Picture(pic);
    }                          
    
    // width of current picture
    public int width() {
        return width;
    }                            
    
    // height of current picture
    public int height() {
        return height;
    }                           
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        ifOutOfRangeThrowEx(x, y);
        
        setToOrig();
        return energiesOrig[x][y];
    }               
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (horizontalSeamCache == null) {
            setToOrig();
            AcyclicSp sp = new AcyclicSp(energiesOrig, width, height);
            Stack<Position> poses = sp.pathToTail();
            int size = poses.size();
            int [] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = poses.pop().getY();
            }
            horizontalSeamCache = result;
        }
        
        int[] tempSeam = new int[horizontalSeamCache.length];
        System.arraycopy(horizontalSeamCache, 0, 
                         tempSeam, 0, horizontalSeamCache.length);
        return tempSeam;
    }               
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (verticalSeamCache == null) {
            setToTrans();
            AcyclicSp sp = new AcyclicSp(energiesTrans, height, width);
            Stack<Position> poses = sp.pathToTail();
            int size = poses.size();
            int [] result = new int[size];
            for (int i = size - 1; i >= 0; i--) {
                result[i] = poses.pop().getY();
            }
            verticalSeamCache = result;
        }                 
        
        int[] tempSeam = new int[verticalSeamCache.length];
        System.arraycopy(verticalSeamCache, 0, 
                         tempSeam, 0, verticalSeamCache.length);
        return tempSeam;
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) { 
        ifNullThrowEx(seam);
        ifHorizotalSeamArgErrorThrowEx(seam);
        
        setToOrig();
        setNewOrig(seam);
        updateOrigEnergies(seam);
        
        clearState();
        isInArrayOrig = true;
    }   
    
    
    private void setNewOrig(int[] seam) {
        int[] removes = seam;        
        int newHeight = height - 1;        
        double[][] tempEnergies = new double[width][newHeight];
        //Color[][] tempColors = new Color[width][newHeight];
        byte[][] tempR = new byte[width][newHeight];
        byte[][] tempG = new byte[width][newHeight];
        byte[][] tempB = new byte[width][newHeight];
        
        for (int i = 0; i < width; i++) {
            int middle = removes[i];
            //int middle = removes[width - 1 - i];
            if (middle > 0) {
                System.arraycopy(energiesOrig[i], 0, tempEnergies[i], 0, middle);
                //System.arraycopy(colorsOrig[i], 0, tempColors[i], 0, middle); 
                System.arraycopy(origR[i], 0, tempR[i], 0, middle);
                System.arraycopy(origG[i], 0, tempG[i], 0, middle);
                System.arraycopy(origB[i], 0, tempB[i], 0, middle);
                
            } 
            if (middle < height - 1) {
                System.arraycopy(energiesOrig[i], middle + 1, tempEnergies[i], middle, height - 1 - middle);
                //System.arraycopy(colorsOrig[i], middle + 1, tempColors[i], middle, height - 1 - middle);
                System.arraycopy(origR[i], middle + 1, tempR[i], middle, height - 1 - middle);
                System.arraycopy(origG[i], middle + 1, tempG[i], middle, height - 1 - middle);
                System.arraycopy(origB[i], middle + 1, tempB[i], middle, height - 1 - middle);
            }
        }
        
        energiesOrig = tempEnergies;
        //colorsOrig = tempColors;
        origR = tempR;
        origG = tempG;
        origB = tempB;
        
        height = newHeight;        
    }
    
    
    private void updateOrigEnergies(int[] seam) {        
        int[] removes = seam;  
        for (int i = 0; i < width; i++) {
//            int y1 = removes[width - 1 - i] - 1;
//            int y2 = removes[width - 1 - i];
            int y1 = removes[i] - 1;
            int y2 = removes[i];            
//            updateEnergyPoint(i, y1, width, height, energiesOrig, colorsOrig);
//            updateEnergyPoint(i, y2, width, height, energiesOrig, colorsOrig);
              updateEnergyPoint(i, y1, width, height, energiesOrig, 
                                origR, origG, origB);
              updateEnergyPoint(i, y2, width, height, energiesOrig, 
                                origR, origG, origB);
        }
    }
    
    
    
//    private void updateEnergyPoint(int x, int y, int maxX, int maxY, 
//                                   double[][] energies, Color[][] colors) {
        private void updateEnergyPoint(int x, int y, int maxX, int maxY, 
                                   double[][] energies, 
                                       byte[][] R,  byte[][] G,  byte[][] B) {
        if (x < 0 || y < 0 || x > maxX - 1 || y > maxY - 1)
            return;
        
        if (x == 0 || y == 0 || x == maxX -1 || y == maxY - 1) {
            energies[x][y] = 1000;
        } else {
//            Color up = colors[x-1][y];
//            Color down = colors[x+1][y];
//            Color left = colors[x][y-1];
//            Color right = colors[x][y+1];
            byte upR = R[x-1][y];
            byte upG = G[x-1][y];
            byte upB = B[x-1][y];
            byte downR = R[x+1][y];
            byte downG = G[x+1][y];
            byte downB = B[x+1][y];
            byte leftR = R[x][y-1];
            byte leftG = G[x][y-1];
            byte leftB = B[x][y-1];
            byte rightR = R[x][y+1];
            byte rightG = G[x][y+1];
            byte rightB = B[x][y+1];
            
//            double rx = up.getRed()-down.getRed();
//            double gx = up.getGreen()-down.getGreen();
//            double bx = up.getBlue()-down.getBlue();
//            double ry = left.getRed()-right.getRed();
//            double gy = left.getGreen()-right.getGreen();
//            double by = left.getBlue()-right.getBlue();
            
            double rx = upR - downR;
            double gx = upG - downG;
            double bx = upB - downB;
            double ry = leftR - rightR;
            double gy = leftG - rightG;
            double by = leftB - rightB;
            
            energies[x][y] = Math.sqrt(rx*rx + gx*gx + bx*bx 
                                           + ry*ry + gy*gy + by*by); 
        }
    }
    
    
    
    private void clearState() {
        isInPic = false;
        isInArrayOrig = false;
        isInArrayTrans = false;
        horizontalSeamCache = null;
        verticalSeamCache = null;
    }
    
    
    
    
    
    
    
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        ifNullThrowEx(seam);
        ifVerticalSeamArgErrorThrowEx(seam);
        
        setToTrans();
        setNewTrans(seam);
        updateTransEnergies(seam);
        
        clearState();
        isInArrayTrans = true;
    } 
    
    
    
    private void setNewTrans(int[] seam) {        
        int[] removes = seam;        
        int newWidth = width - 1;        
        double[][] tempEnergies = new double[height][newWidth];
        //Color[][] tempColors = new Color[height][newWidth];
        byte[][] tempR = new byte[height][newWidth];
        byte[][] tempG = new byte[height][newWidth];
        byte[][] tempB = new byte[height][newWidth];
        
        for (int i = 0; i < height; i++) {
            //int middle = removes[i];
            int middle = removes[height - 1 - i];
            if (middle > 0) {
                System.arraycopy(energiesTrans[i], 0, tempEnergies[i], 0, middle);
                //System.arraycopy(colorsTrans[i], 0, tempColors[i], 0, middle);                
                System.arraycopy(transR[i], 0, tempR[i], 0, middle);
                System.arraycopy(transG[i], 0, tempG[i], 0, middle);
                System.arraycopy(transB[i], 0, tempB[i], 0, middle);
            } 
            if (middle < width - 1) {
                System.arraycopy(energiesTrans[i], middle + 1, tempEnergies[i], middle, width - 1 - middle);
                //System.arraycopy(colorsTrans[i], middle + 1, tempColors[i], middle, width - 1 - middle);
                System.arraycopy(transR[i], middle + 1, tempR[i], middle, width - 1 - middle);
                System.arraycopy(transG[i], middle + 1, tempG[i], middle, width - 1 - middle);
                System.arraycopy(transB[i], middle + 1, tempB[i], middle, width - 1 - middle);
            }
        }
        
        energiesTrans = tempEnergies;
        //colorsTrans = tempColors;
        transR = tempR;
        transG = tempG;
        transB = tempB;
        
        width = newWidth;   
    }
    
    
   private void updateTransEnergies(int[] seam) {        
        int[] removes = seam;  
        for (int i = 0; i < height; i++) {
            //int y1 = removes[i] - 1;
            //int y2 = removes[i];            
            int y1 = removes[height - 1 - i] - 1;
            int y2 = removes[height - 1 - i];            
//            updateEnergyPoint(i, y1, height, width, energiesTrans, colorsTrans);
//            updateEnergyPoint(i, y2, height, width, energiesTrans, colorsTrans);
            updateEnergyPoint(i, y1, height, width, energiesTrans, transR, transG, transB);
            updateEnergyPoint(i, y2, height, width, energiesTrans, transR, transG, transB);
        }
    }
   
   
   private void ifOutOfRangeThrowEx(int x, int y) {
       if (x < 0 || y < 0 || x > width - 1 || y > height - 1) {
           throw new IndexOutOfBoundsException();
       }
   }
    
   private void ifNullThrowEx(Object obj) {
       if (obj == null)
           throw new NullPointerException();           
   }
   
   private void ifVerticalSeamArgErrorThrowEx(int[] seam) {
       if (seam.length != height)
           throw new IllegalArgumentException();
       
       int last = seam[0]; 
       for (int w: seam) {           
           if (w < 0 || w > width - 1)
               throw new IllegalArgumentException();
           if (w - last > 1 || w - last < -1)
               throw new IllegalArgumentException();
           last = w;
       }
       
       if (width <= 1)
           throw new IllegalArgumentException();
   }
   
   
   private void ifHorizotalSeamArgErrorThrowEx(int[] seam) {
       if (seam.length != width)
           throw new IllegalArgumentException();
       
       int last = seam[0]; 
       for (int h: seam) {           
           if (h < 0 || h > height - 1)
               throw new IllegalArgumentException();
           if (h - last > 1 || h - last < -1)
               throw new IllegalArgumentException();
           last = h;
       }
       
       if (height <= 1)
           throw new IllegalArgumentException();
   }
}