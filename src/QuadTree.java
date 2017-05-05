import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.ArrayList;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    static final int MAX_ITEMS = 5;

    private ArrayList<Point2D> points = new ArrayList<Point2D>();

    private QuadTree childNW = null;
    private QuadTree childNE = null;
    private QuadTree childSE = null;
    private QuadTree childSW = null;


    private BoundingBox bbox;

    public QuadTree(BoundingBox bbox){
        this.bbox = bbox;
    }

    private Direction getDirection(Point2D p){
        if(p.getX() < bbox.getMaxX() - bbox.getWidth()
                && p.getX() < bbox.getMaxY() - bbox.getHeight()){
            return Direction.NorthWest;
        }
        if(p.getX() > bbox.getMaxX() - bbox.getWidth()
                && p.getX() < bbox.getMaxY() - bbox.getHeight()){
            return Direction.NorthEast;
        }
        if(p.getX() > bbox.getMaxX() - bbox.getWidth()
                && p.getX() > bbox.getMaxY() - bbox.getHeight()){
            return Direction.SouthEast;
        }
        if(p.getX() < bbox.getMaxX() - bbox.getWidth()
                && p.getX() > bbox.getMaxY() - bbox.getHeight()){
            return Direction.SouthWest;
        }

        // Should not be reached
        return Direction.NorthWest;
    }

    private QuadTree createChildNode(Direction direction){
        BoundingBox bbox = null;
        QuadTree result = null;

        switch (direction) {
            case NorthWest:
                bbox = new BoundingBox( bbox.getMinX(),
                                        bbox.getMinY(),
                                  bbox.getWidth()/2,
                                  bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case NorthEast:
                bbox = new BoundingBox( bbox.getWidth()/2,
                                        bbox.getMinY(),
                                  bbox.getWidth()/2,
                                  bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case SouthEast:
                bbox = new BoundingBox( bbox.getWidth()/2,
                                        bbox.getHeight()/2,
                                        bbox.getWidth()/2,
                                        bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case SouthWest:
                bbox = new BoundingBox(      bbox.getMinX(),
                                        bbox.getHeight()/2,
                                        bbox.getWidth()/2,
                                        bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
        }

        return result;
    }

    public void addPoint(Point2D p){
        if (!bbox.contains(p)){
            // TODO throw exception here
            System.out.println("addPoint something went wrong");
            return;
        }

        if(getNumItems() <= MAX_ITEMS){
            points.add(p);
            return;
        }

        if(getNumItems() == MAX_ITEMS){
            Direction dir = getDirection(p);
            switch (dir) {
                case NorthWest:
                    if(childNW == null){
                        childNW = createChildNode(dir);
                    }
                    break;
                case NorthEast:
                    if(childNE == null){
                        childNE = createChildNode(dir);
                    }
                    break;
                case SouthEast:
                    if(childSE == null){
                        childSE = createChildNode(dir);
                    }
                    break;
                case SouthWest:
                    if(childSW == null){
                        childSW = createChildNode(dir);
                    }
                    break;
            }
        }

    }

    public int getNumItems(){
        return points.size();
    }


    private enum Direction{NorthWest, NorthEast, SouthEast, SouthWest}



}
