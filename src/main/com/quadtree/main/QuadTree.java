package com.quadtree.main;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;



public class QuadTree {

    class Point2D implements Comparable<Point2D>{
        private float x, y;

        public Point2D(float x,
                       float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        @Override
        public String toString(){
            return "point x: " + getX() + " y: " + getY();
        }

        @Override
        public int compareTo(Point2D other) {

            final int  xDiff = Float.compare(this.x, other.x);
            final int  yDiff = Float.compare(this.y, other.y);

            if(yDiff < 0)                { return -1; }
            if(yDiff > 0)                { return 1; }
            if(xDiff < 0)                { return -1; }
            if(xDiff > 0)                { return 1; }

            return 0;
        }
    }

    class Node{

        final float R_HEIGHT, R_WIDTH;
        final int DEPTH;
        final Point2D centre;

        private List<Point2D> points = new ArrayList<>(6);

        private Node childNW = null;
        private Node childNE = null;
        private Node childSE = null;
        private Node childSW = null;

        Node(Point2D centre,
                     float R_WIDTH,
                     float R_HEIGHT,
                     int DEPTH) {

            this.centre = centre;
            this.R_WIDTH = R_WIDTH;
            this.R_HEIGHT = R_HEIGHT;
            this.DEPTH = DEPTH;

        }

        @Override
        public String toString(){
            return  "Node num-points: " + points.size() +
                    " centreX:"  + centre.getX() +
                    " centreY:"  + centre.getY() +
                    " r_width:"  + R_WIDTH +
                    " r_height:" + R_HEIGHT +
                    " depth:"    + DEPTH;
        }

    }


    private final Node root;
    private final int NODE_MAX_SIZE;

    QuadTree(float WIDTH, float HEIGHT) {
        this(5, WIDTH, HEIGHT);
    }

    QuadTree(int NODE_MAX_SIZE, float WIDTH, float HEIGHT) {

        this.NODE_MAX_SIZE = NODE_MAX_SIZE;
        Point2D centre = new Point2D(WIDTH/2, HEIGHT/2);
        this.root = new Node(centre, WIDTH/2, HEIGHT/2, 1);
    }


    public int depth(){
        return depth(root);
    }

    private int depth(Node node) {

        if(node.childNW == null){
            return 1;
        }

        final int nw = depth(node.childNW);
        final int ne = depth(node.childNE);
        final int se = depth(node.childSE);
        final int sw = depth(node.childSW);

        final int max = Math.max(nw,
                                 Math.max(ne,
                                    Math.max(se, sw)));

        return 1 + max;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {

        int result = 0;
        if (node.childNW != null) {
            result += size(node.childNW);
            result += size(node.childNE);
            result += size(node.childSE);
            result += size(node.childSW);
        }
        return result + node.points.size();
    }


    public void addPoint(Point2D point) {
        addPoint(root, point);
    }


    private void addPoint(Node node, Point2D point) {

        if(!checkBounds(node, point)){
            System.out.println("not within bounds");
            System.out.println(node);
            System.out.println(point);
            System.out.println();
        }

        node.points.add(point);

        if (node.points.size() <= NODE_MAX_SIZE) return;

        if (node.childNW == null) { createNodeChildren(node); }

        //balance downwards
        int max_moved = NODE_MAX_SIZE/2;
        for(int i=0; i < node.points.size() && max_moved > 0; i++){

            Point2D p = node.points.get(i);

            if(liesNW(node).test(p)){
                addPoint(node.childNW, p);
                node.points.remove(i);
                --max_moved;
            }
            else if(liesNE(node).test(p)){
                addPoint(node.childNE, p);
                node.points.remove(i);
                --max_moved;
            }
            else if(liesSE(node).test(p)){
                addPoint(node.childSE, p);
                node.points.remove(i);
                --max_moved;
            }
            else if(liesSW(node).test(p)){
                addPoint(node.childSW, p);
                node.points.remove(i);
                --max_moved;
            }
        }


    }

    static boolean checkBounds(Node node, Point2D point) {

        float minBoundX = node.centre.getX() - node.R_WIDTH;
        float maxBoundX = node.centre.getX() + node.R_WIDTH;
        float minBoundY = node.centre.getY() - node.R_HEIGHT;
        float maxBoundY = node.centre.getY() + node.R_HEIGHT;

        float pX = point.getX();
        float pY = point.getY();

        return minBoundX <= pX && pX <= maxBoundX &&
               minBoundY <= pY && pY <= maxBoundY;

    }


    private void createNodeChildren(Node node) {

        final float xPos = node.centre.getX();
        final float yPos = node.centre.getY();
        final float rHeight = node.R_HEIGHT / 2;
        final float rWidth = node.R_WIDTH / 2;

        Point2D c_nw = new Point2D(xPos - rWidth, yPos - rHeight);
        node.childNW = new Node(c_nw, rWidth, rHeight, node.DEPTH+1);

        Point2D c_ne = new Point2D(xPos + rWidth, yPos - rHeight);
        node.childNE = new Node(c_ne, rWidth, rHeight, node.DEPTH+1);

        Point2D c_se = new Point2D(xPos + rWidth, yPos + rHeight);
        node.childSE = new Node(c_se, rWidth, rHeight, node.DEPTH+1);

        Point2D c_sw = new Point2D(xPos - rWidth, yPos + rHeight);
        node.childSW = new Node(c_sw, rWidth, rHeight, node.DEPTH+1);

    }


    static Predicate<Point2D> liesNW(Node node) {


        float minBoundX = node.centre.getX() - node.R_WIDTH;
        float maxBoundX = node.centre.getX();
        float minBoundY = node.centre.getY() - node.R_HEIGHT;
        float maxBoundY = node.centre.getY();

        // TODO use Float.compareTo to prevent edge case issues
        return point -> minBoundX <= point.getX() && point.getX() <= maxBoundX &&
                        minBoundY <= point.getY() && point.getY() <= maxBoundY;

        /*
        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x < nodeX && point.y < nodeY;
        */

    }

    static Predicate<Point2D> liesNE(Node node) {

        float minBoundX = node.centre.getX();
        float maxBoundX = node.centre.getX() + node.R_WIDTH;
        float minBoundY = node.centre.getY() - node.R_HEIGHT;
        float maxBoundY = node.centre.getY();

        // TODO use Float.compareTo to prevent edge case issues
        return point -> minBoundX < point.getX()  && point.getX() <= maxBoundX &&
                        minBoundY <= point.getY() && point.getY() <= maxBoundY;

        /*
        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x >= nodeX && point.y < nodeY;
        */
    }

    static Predicate<Point2D> liesSE(Node node) {

        float minBoundX = node.centre.getX();
        float maxBoundX = node.centre.getX() + node.R_WIDTH;
        float minBoundY = node.centre.getY();
        float maxBoundY = node.centre.getY() + node.R_HEIGHT;

        // TODO use Float.compareTo to prevent edge case issues
        return point -> minBoundX < point.getX() && point.getX() <= maxBoundX &&
                        minBoundY < point.getY() && point.getY() <= maxBoundY;

        /*
        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x >= nodeX && point.y >= nodeY;
        */
    }

    static Predicate<Point2D> liesSW(Node node) {

        float minBoundX = node.centre.getX() - node.R_WIDTH;
        float maxBoundX = node.centre.getX();
        float minBoundY = node.centre.getY();
        float maxBoundY = node.centre.getY() + node.R_HEIGHT;

        // TODO use Float.compareTo to prevent edge case issues
        return point -> minBoundX <= point.getX() && point.getX() <= maxBoundX &&
                minBoundY < point.getY() && point.getY() <= maxBoundY;

        /*
        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x < nodeX && point.y >= nodeY;
        */
    }

}
