import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;


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

    @Test
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

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(0,0));
        }
        assert( quadTree.getNodeSize() == QuadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildNW02(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(99,99));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createGoodChildNE01(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(100,0));
        }
        assert( quadTree.getNodeSize() == QuadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildNE02(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(200,99));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createGoodChildSE01(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(100,100));
        }
        assert( quadTree.getNodeSize() == QuadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildSE02(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(200,200));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createGoodChildSW01(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(0,100));
        }
        assert( quadTree.getNodeSize() == QuadTree.MAX_ITEMS );
    }

    @Test
    public void createGoodChildSW02(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(99,200));
        }
        assert( quadTree.getBranchSize() == 8 );
    }

    @Test
    public void createChildOfChild(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 6; i++) {

            quadTree.addPoint(new Point2D(50,50));
        }
        assert( quadTree.getBranchSize() == 11 );
    }
}
