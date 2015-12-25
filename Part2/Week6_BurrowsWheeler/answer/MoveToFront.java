import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.ArrayList;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard 
    // input and writing to standard output
    
    private static class Node {    
        private char val;
        private Node next = null;
            
        private Node(char val) {
            this.val = val;
        }        
    }
    
    
    private static Node root = null;
    
    
    public static void encode() {        
        root = new Node((char)0);        
        Node nowNode = root;
        for (char i = 1; i < 256; i++) {
            nowNode.next = new Node(i);    
            nowNode = nowNode.next;
        }
        
        while(!BinaryStdIn.isEmpty()) {
            char in = BinaryStdIn.readChar();            
            Node lastNode = null;
            Node thisNode = root;
            char no = 0;
            while (thisNode != null) {
                if (thisNode.val == in) {
                    BinaryStdOut.write(no);
                    if (lastNode != null) {
                        lastNode.next = thisNode.next;
                        thisNode.next = root;
                        root = thisNode;
                    }                    
                    break;
                } else {
                    lastNode = thisNode;
                    thisNode = thisNode.next;
                    no++;
                }
            }            
        }        
        BinaryStdOut.close();               
    }
    

    // apply move-to-front decoding, reading from standard 
    // input and writing to standard output
    public static void decode() {
        
        root = new Node((char)0);        
        Node nowNode = root;
        for (char i = 1; i < 256; i++) {
            nowNode.next = new Node(i);    
            nowNode = nowNode.next;
        }        
        while(!BinaryStdIn.isEmpty()) {
            char inNo = BinaryStdIn.readChar();            
            Node lastNode = null;
            Node thisNode = root;
            char nowNo = 0;
            while (thisNode != null) {
                if (inNo == nowNo) {
                    BinaryStdOut.write(thisNode.val);
                    if (lastNode != null) {
                        lastNode.next = thisNode.next;
                        thisNode.next = root;
                        root = thisNode;
                    }                    
                    break;
                } else {
                    lastNode = thisNode;
                    thisNode = thisNode.next;
                    nowNo++;
                }
            }            
        }        
        BinaryStdOut.close();                
    }
    

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
    }
}