import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    private BoundingBox bbox;
    private QuadTree quadTree;

    @Before
    public void buildQuadTree(){
        bbox = new BoundingBox(0,0,200,200);
        quadTree = new QuadTree(bbox);
    }

    @After
    public void deleteQuadTree(){
        bbox = null;
        quadTree = null;
    }

    @Test
    public void addBadPoint(){
        quadTree.addPoint(new Point2D(400,400));
        System.out.println(quadTree.getTotalSize());
        assert (quadTree.getTotalSize() == 0);
    }


    @Test
    public void addGoodPoint(){
        quadTree.addPoint(new Point2D(100,100));
        assert( quadTree.getTotalSize() == 1 );
    }

    @Test
    public void createChild(){

        for (int i = 0; i < QuadTree.MAX_ITEMS + 3; i++) {

            quadTree.addPoint(new Point2D(50,50));
        }
        assert( quadTree.getNodeSize() == QuadTree.MAX_ITEMS );
        assert( quadTree.getTotalSize() == 8 );
    }
}
