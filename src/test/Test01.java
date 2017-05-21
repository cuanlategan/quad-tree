import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;


/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    private QuadTree quadTree;

    @Before
    public void buildQuadTree(){
        quadTree = new QuadTree(new BoundingBox(0,0,200,200));
    }

    @Test
    public void getDirectionNW(){
        QuadTree.Direction dir =  quadTree.getDirection(new Point2D(0,0));
        assert (dir == QuadTree.Direction.NorthWest);
    }

    @Test
    public void getDirectionNE(){
        QuadTree.Direction dir =  quadTree.getDirection(new Point2D(200,0));
        assert (dir == QuadTree.Direction.NorthEast);
    }

    @Test
    public void getDirectionSE(){
        QuadTree.Direction dir =  quadTree.getDirection(new Point2D(200,200));
        assert (dir == QuadTree.Direction.SouthEast);
    }

    @Test
    public void getDirectionSW(){
        QuadTree.Direction dir =  quadTree.getDirection(new Point2D(0,200));
        assert (dir == QuadTree.Direction.SouthWest);
    }

    @Test(expected = RuntimeException.class)
    public void getBadDirection(){
        QuadTree.Direction dir =  quadTree.getDirection(new Point2D(400,400));
        assert (dir == null);
    }

    @Test(expected = RuntimeException.class)
    public void addBadPoint(){
        quadTree.addPoint(new Point2D(400,400));
        assert (quadTree.getBranchSize() == 0);
    }


    @Test
    public void addGoodPoint(){
        quadTree.addPoint(new Point2D(100,100));
        assert( quadTree.getBranchSize() == 1 );
    }

    @Test
    public void createGoodChildNW01(){
        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(0,0));
        }
        assert( quadTree.getNodeSize() == quadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildNW02(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(99,99));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    // Tests rebalancing of tree as MAX_ITEMS is reached
    @Test
    public void createGoodChildOfChildNW01(){
        QuadTree qt = new QuadTree(new BoundingBox(0,0, 200,200),8);
        for (int i = 0; i < qt.MAX_ITEMS * 5; i++) {
            qt.addPoint(new Point2D(99,99));
            //assert( quadTree.getBranchSize() == i+1 );
        }
        System.out.println("expecting branch size: "+ qt.MAX_ITEMS * 5 + " got: " + qt.getBranchSize());
        assert( qt.getBranchSize() == qt.MAX_ITEMS * 5 );
    }

    @Test
    public void createGoodChildNE01(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(100,0));
        }
        assert( quadTree.getNodeSize() == quadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildNE02(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(200,99));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createGoodChildSE01(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(100,100));
        }
        assert( quadTree.getNodeSize() == quadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildSE02(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(200,200));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createGoodChildSW01(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(0,100));
        }
        assert( quadTree.getNodeSize() == quadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildSW02(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(99,200));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createChildOfChild(){

        for (int i = 0; i < quadTree.MAX_ITEMS + 6; i++) {

            quadTree.addPoint(new Point2D(50,50));
        }
        assert( quadTree.getBranchSize() == 11 );
    }

    @Test
    public void hasPointRoot(){
        quadTree.addPoint(new Point2D(50,50));
        assert (quadTree.hasPoint(new Point2D(50,50)));
    }

    @Test
    public void hasPointNW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(50,50));
        assert (quadTree.hasPoint(new Point2D(50,50)));
    }

    @Test
    public void notHasPointNW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(50,50));
        assertFalse (quadTree.hasPoint(new Point2D(55,55)));
    }

    @Test
    public void hasPointNE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(100,50));
        assert (quadTree.hasPoint(new Point2D(100,50)));
    }
    @Test
    public void notHasPointNE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(100,50));
        assertFalse (quadTree.hasPoint(new Point2D(105,55)));
    }

    @Test
    public void hasPointSE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(50,50));
        }
        quadTree.addPoint(new Point2D(100,150));
        assert (quadTree.hasPoint(new Point2D(100,150)));
    }

    @Test
    public void notHasPointSE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(50,50));
        }
        quadTree.addPoint(new Point2D(100,150));
        assertFalse(quadTree.hasPoint(new Point2D(105,155)));
    }

    @Test
    public void hasPointSW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(50,150));
        assert (quadTree.hasPoint(new Point2D(50,150)));
    }

    @Test
    public void notHasPointSW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(50,150));
        assertFalse(quadTree.hasPoint(new Point2D(55,155)));
    }

    @Test
    public void getPointRoot(){
        Point2D p = new Point2D(50,50);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(p);
        assert (p == result);
    }

    @Test
    public void getBadPointRoot(){
        Point2D p = new Point2D(50,50);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(100,100));
        assert (result == null);
    }

    @Test
    public void getPointNW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        Point2D p = new Point2D(50,50);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(50,50));
        assert (p == result);
    }

    @Test
    public void notGetPointNW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        quadTree.addPoint(new Point2D(50,50));
        assertFalse (quadTree.hasPoint(new Point2D(55,55)));
    }

    @Test
    public void getPointNE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        Point2D p = new Point2D(100,50);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(100,50));
        assert (p == result);
    }
    @Test
    public void notGetPointNE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        Point2D p = new Point2D(100,50);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(105,55));
        assert (result == null);
    }

    @Test
    public void getPointSE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(50,50));
        }
        Point2D p = new Point2D(100,150);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(100,150));
        assert (p == result);
    }

    @Test
    public void notGetPointSE(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(50,50));
        }
        Point2D p = new Point2D(100,150);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(105,155));
        assert (result == null);
    }

    @Test
    public void getPointSW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        Point2D p = new Point2D(50,150);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(50,150));
        assert (p == result);
    }

    @Test
    public void notGetPointSW(){
        for (int i = 0; i <= quadTree.MAX_ITEMS; i++) {
            quadTree.addPoint(new Point2D(200,200));
        }
        Point2D p = new Point2D(50,150);
        quadTree.addPoint(p);
        Point2D result = quadTree.getPoint(new Point2D(55,155));
        assert (result == null);
    }

}
