public class Rotater {
    
    private int lenX;
    private int lenY;
    
    public Rotater (int lenX, int lenY) {
        this.lenX = lenX;
        this.lenY = lenY;
    }
    
    
    
    public Position contraRotate(int x, int y) {
        Position pos = new Position();
        pos.setX(lenY - y - 1);
        pos.setY(x);
        return pos;
    }
    
    
    public Position rotate(int x, int y) {
        Position pos = new Position();
        pos.setX(y);
        pos.setY(lenX - x - 1);
        return pos;
    }
    
}