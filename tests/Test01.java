import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    @Test
    public void addBadPoint(){
        BoundingBox bbox = new BoundingBox(0,0,200,200);
        QuadTree quadTree = new QuadTree(bbox);
        quadTree.addPoint(new Point2D(400,400));

        if ((quadTree.getNumItems() == 0)) {
            return;
        }
        throw new AssertionError();
    }


    @Test
    public void addGoodPoint(){
        BoundingBox bbox = new BoundingBox(0,0,200,200);
        QuadTree quadTree = new QuadTree(bbox);
        quadTree.addPoint(new Point2D(100,100));

        if ((quadTree.getNumItems() == 1)) {
            return;
        }
        throw new AssertionError();
    }
}
