import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    final double WIDTH = 200;
    final double HEIGHT = 200;
    final BoundingBox bbox = new BoundingBox(0,0,WIDTH, HEIGHT);
    QuadTree qt;

    @Before
    public void buildEmpptyQuadtree(){
        qt = new QuadTree(bbox);
    }

    @Test
    public void addTwoPoint(){
        qt.addPoint(new Point2D(WIDTH,HEIGHT));
        qt.addPoint(new Point2D(WIDTH/2,HEIGHT/2));
        qt.addPoint(new Point2D(0,0));
        assertEquals("Added one point then checked size ",3, qt.size());

    }

    @Test
    public void addTenPoints(){
        for(int i=0; i<10; ++i){
            qt.addPoint(new Point2D(i,i));
        }
        assertEquals("Added ten points then checked size ",10, qt.size());
    }

}
