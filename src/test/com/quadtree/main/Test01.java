package com.quadtree.main;

import com.quadtree.main.QuadTree.Point2D;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


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
    public void testBoundsPredicates() {
        QuadTree.Point2D point = quadTree.new Point2D(100, 100);
        QuadTree.Point2D center = quadTree.new Point2D(87, 87);
        QuadTree.Node node = quadTree.new Node(center, 12, 12, 7);

        assertFalse("point should not of lied in node", QuadTree.liesNW(node).test(point));
        assertFalse("point should not of lied in node", QuadTree.liesNE(node).test(point));
        assertFalse("point should not of lied in node", QuadTree.liesSE(node).test(point));
        assertFalse("point should not of lied in node", QuadTree.liesSW(node).test(point));
        assertFalse("point should not of lied in node", QuadTree.checkBounds(node, point));
    }

    @Test
    public void testPointComparable(){
        Point2D p1 = quadTree.new Point2D(0,0);
        Point2D p2 = quadTree.new Point2D(1,0);
        Point2D p3 = quadTree.new Point2D(0,1);
        Point2D p4 = quadTree.new Point2D(1,1);
        Point2D p5 = quadTree.new Point2D(1,2);
        Point2D p6 = quadTree.new Point2D(1,2);

        ArrayList<Point2D> list =  new ArrayList<>(Arrays.asList(p6, p5, p4, p3, p2, p1));
        Collections.shuffle(list);
        list.sort(Comparator.naturalOrder());

        assertTrue("Point returned from list out of order: " +p1, p1 == list.get(0));
        assertTrue("Point returned from list out of order: " +p2, p2 == list.get(1));
        assertTrue("Point returned from list out of order: " +p3, p3 == list.get(2));
        assertTrue("Point returned from list out of order: " +p4, p4 == list.get(3));
        assertTrue("Point returned from list out of order: " +p5, p5 == list.get(4) || p5 == list.get(5));
        assertTrue("Point returned from list out of order: " +p6, p6 == list.get(5) || p6 == list.get(4));
        assertTrue("Point comparison should return 0", p5.compareTo(p6) == 0);
    }

}
