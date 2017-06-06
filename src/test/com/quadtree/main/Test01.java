package com.quadtree.main;

import org.junit.Before;
import org.junit.Test;


public class Test01 {

    private QuadTree quadTree;

    @Before
    public void runBefore(){
        quadTree = new QuadTree(5,600, 800);
    }

    @Test
    public void addPoint(){
        QuadTree.Point2D p1 = quadTree.new Point2D(100, 400);
        QuadTree.Point2D p2 = quadTree.new Point2D(200, 400);
        QuadTree.Point2D p3 = quadTree.new Point2D(300, 400);
        QuadTree.Point2D p4 = quadTree.new Point2D(400, 400);
        QuadTree.Point2D p5 = quadTree.new Point2D(400, 100);

        assert(quadTree.size() == 0);
        quadTree.addPoint(p1);
        assert(quadTree.size() == 1);
        quadTree.addPoint(p2);
        assert(quadTree.size() == 2);
        quadTree.addPoint(p3);
        assert(quadTree.size() == 3);
        quadTree.addPoint(p4);
        assert(quadTree.size() == 4);
        quadTree.addPoint(p5);
        assert(quadTree.size() == 5);
    }

    @Test
    public void addPoint2(){

        int count = 0;

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point2D p1 = quadTree.new Point2D(100, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point2D p1 = quadTree.new Point2D(400, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point2D p1 = quadTree.new Point2D(400, 400);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point2D p1 = quadTree.new Point2D(100, 400);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

    }

    @Test
    public void addPoint3(){
        quadTree = new QuadTree(5, 800, 800);
        for(int i = 0; i < 500; i++){
            QuadTree.Point2D p1 = quadTree.new Point2D(100, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == i+1);
        }
    }

    @Test
    public void testBoundsPredicates(){
        QuadTree.Point2D point = quadTree.new Point2D(100, 100);
        QuadTree.Point2D center = quadTree.new Point2D(87, 87);
        QuadTree.Node node = quadTree.new Node(center, 12,12,7);

        if(QuadTree.liesNW(node).test(point)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        if(QuadTree.liesNE(node).test(point)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        if(QuadTree.liesSE(node).test(point)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        if(QuadTree.liesSW(node).test(point)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        if(QuadTree.checkBounds(node, point)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

}
