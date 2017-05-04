import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Created by cuan on 5/4/17.
 */
public class Test01 {

    @Test
    public void addBadPoint(){
        Rectangle rectangle = new Rectangle(0,0,200,200);
        QuadTree quadTree = new QuadTree(rectangle);
        quadTree.addPoint(new Point(400,400));

        if ((quadTree.getNumItems() == 0)) {
            return;
        }
        throw new AssertionError();
    }


    @Test
    public void addGoodPoint(){
        Rectangle rectangle = new Rectangle(0,0,200,200);
        QuadTree quadTree = new QuadTree(rectangle);
        quadTree.addPoint(new Point(100,100));

        if ((quadTree.getNumItems() == 1)) {
            return;
        }
        throw new AssertionError();
    }
}
