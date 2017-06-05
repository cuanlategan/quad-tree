package com.quadtree.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;


public class QuadTree {

    class Point {
        private int x, y;

        public Point(int x,
                     int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString(){
            return "point x: " + getX() + " y: " + getY();
        }
    }

    class Node {

        final int R_HEIGHT, R_WIDTH, DEPTH;
        final Point centre;

        private List<Point> points = new ArrayList<>(6);

        private Node childNW = null;
        private Node childNE = null;
        private Node childSE = null;
        private Node childSW = null;

        private Node(Point centre,
                     int R_WIDTH,
                     int R_HEIGHT,
                     int DEPTH) {

            this.centre = centre;
            this.R_WIDTH = R_WIDTH;
            this.R_HEIGHT = R_HEIGHT;
            this.DEPTH = DEPTH;
            //System.out.println(DEPTH);
        }

        @Override
        public String toString(){
            return ("Node num-points: " + points.size()) +
                    " centreX:" + centre.getX() +
                    " centreY:" + centre.getY() +
                    " r_width:" + R_WIDTH +
                    " r_height:" + R_HEIGHT;
        }
    }


    private final Node root;
    private final int NODE_MAX_SIZE;

    QuadTree(int width, int height) {
        this(5, width, height);
    }

    QuadTree(int NODE_MAX_SIZE, int width, int height) {
        this.NODE_MAX_SIZE = NODE_MAX_SIZE;
        Point centre = new Point(width / 2, height / 2);
        this.root = new Node(centre, width, height, 1);
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


    public void addPoint(Point point) {
        addPoint(root, point);
    }


    private void addPoint(Node node, Point point) {

        if(!checkBounds(node, point)){
            System.out.println("not within bounds");
            System.out.println(node);
            System.out.println(point);
            int dep = depth();
            System.out.println("Depth: "+ dep);
            System.out.println("Depth: "+ node.DEPTH);
            System.out.println();
        }

        node.points.add(point);

        if (node.points.size() <= NODE_MAX_SIZE) return;

        if (node.childNW == null) { createNodeChildren(node); }

        //balance downwards
        int max_moved = NODE_MAX_SIZE/2;
        for(int i=0; i < node.points.size() && max_moved > 0; i++){

            Point p = node.points.get(i);

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

    private boolean checkBounds(Node node, Point point) {

        int minBoundX = node.centre.getX() - node.R_WIDTH;
        int maxBoundX = node.centre.getX() + node.R_WIDTH;
        int minBoundY = node.centre.getY() - node.R_HEIGHT;
        int maxBoundY = node.centre.getY() + node.R_HEIGHT;

        int pX = point.getX();
        int pY = point.getY();

        return minBoundX <= pX && pX <= maxBoundX &&
               minBoundY <= pY && pY <= maxBoundY;

    }


    private void createNodeChildren(Node node) {

        final int xPos = node.centre.getX();
        final int yPos = node.centre.getY();
        final int rHeight = node.R_HEIGHT / 2;
        final int rWidth = node.R_WIDTH / 2;

        Point c_nw = new Point(xPos - rWidth, yPos - rHeight);
        node.childNW = new Node(c_nw, rWidth, rHeight, node.DEPTH+1);

        Point c_ne = new Point(xPos + rWidth, yPos - rHeight);
        node.childNE = new Node(c_ne, rWidth, rHeight, node.DEPTH+1);

        Point c_se = new Point(xPos + rWidth, yPos + rHeight);
        node.childSE = new Node(c_se, rWidth, rHeight, node.DEPTH+1);

        Point c_sw = new Point(xPos - rWidth, yPos + rHeight);
        node.childSW = new Node(c_sw, rWidth, rHeight, node.DEPTH+1);

    }


    private static Predicate<Point> liesNW(Node node) {

        /*
        int minBoundX = node.centre.getX() - node.R_WIDTH;
        int maxBoundX = node.centre.getX();
        int minBoundY = node.centre.getY() - node.R_HEIGHT;
        int maxBoundY = node.centre.getY();


        return point -> minBoundX <= point.getX() && point.getX() <= maxBoundX &&
                        minBoundY <= point.getY() && point.getY() <= maxBoundY;
        */

        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x < nodeX && point.y < nodeY;


    }

    private static Predicate<Point> liesNE(Node node) {

        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x >= nodeX && point.y < nodeY;
    }

    private static Predicate<Point> liesSE(Node node) {

        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x >= nodeX && point.y >= nodeY;
    }

    private static Predicate<Point> liesSW(Node node) {

        int nodeX = node.centre.x;
        int nodeY = node.centre.y;

        return point -> point.x < nodeX && point.y >= nodeY;
    }

}
