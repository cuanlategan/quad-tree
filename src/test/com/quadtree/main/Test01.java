package com.quadtree.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    QuadTree quadTree;

    @Before
    public void runBefore(){
        quadTree = new QuadTree(5,600, 800);
    }

    @Test
    public void addPoint(){
        QuadTree.Point p1 = quadTree.new Point(100, 400);
        QuadTree.Point p2 = quadTree.new Point(200, 400);
        QuadTree.Point p3 = quadTree.new Point(300, 400);
        QuadTree.Point p4 = quadTree.new Point(400, 400);
        QuadTree.Point p5 = quadTree.new Point(400, 100);

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
            QuadTree.Point p1 = quadTree.new Point(100, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point p1 = quadTree.new Point(400, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point p1 = quadTree.new Point(400, 400);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

        for(int i = 0; i < 50; i++){
            ++count;
            QuadTree.Point p1 = quadTree.new Point(100, 400);
            quadTree.addPoint(p1);
            assert(quadTree.size() == count);
        }

    }

    @Test
    public void addPoint3(){
        for(int i = 0; i < 26; i++){
            QuadTree.Point p1 = quadTree.new Point(100, 100);
            quadTree.addPoint(p1);
            assert(quadTree.size() == i+1);
        }
    }

}
