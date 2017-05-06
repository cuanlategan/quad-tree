import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.ArrayList;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    static final int MAX_ITEMS = 5;
    static private int totalSize = 0;

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
        BoundingBox childBox = null;
        QuadTree result = null;

        switch (direction) {
            case NorthWest:
                childBox = new BoundingBox( bbox.getMinX(),
                                        bbox.getMinY(),
                                  bbox.getWidth()/2,
                                  bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case NorthEast:
                childBox = new BoundingBox( bbox.getWidth()/2,
                                        bbox.getMinY(),
                                  bbox.getWidth()/2,
                                  bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case SouthEast:
                childBox = new BoundingBox( bbox.getWidth()/2,
                                        bbox.getHeight()/2,
                                        bbox.getWidth()/2,
                                        bbox.getHeight()/2 );
                result = new QuadTree(bbox);
                break;
            case SouthWest:
                childBox = new BoundingBox(      bbox.getMinX(),
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
            System.out.println("addPoint something went wrong, point not within bounds");
            return;
        }

        if(getNodeSize() < MAX_ITEMS){
            points.add(p);
            totalSize++;
            return;
        }

        //points.add(p);

        if(getNodeSize() == MAX_ITEMS){
            Direction dir = getDirection(p);
            switch (dir) {
                case NorthWest:
                    if(childNW == null){
                        childNW = createChildNode(dir);
                        childNW.addPoint(p);
                        //balanceDownwards(childNW);
                    } else childNW.addPoint(p);
                    break;
                case NorthEast:
                    if(childNE == null){
                        childNE = createChildNode(dir);
                        childNE.addPoint(p);
                        //balanceDownwards(childNE);
                    } else childNE.addPoint(p);
                    break;
                case SouthEast:
                    if(childSE == null){
                        childSE = createChildNode(dir);
                        childSE.addPoint(p);
                        //balanceDownwards(childSE);
                    } else childSE.addPoint(p);
                    break;
                case SouthWest:
                    if(childSW == null){
                        childSW = createChildNode(dir);
                        childSW.addPoint(p);
                        //balanceDownwards(childSW);
                    } else childSW.addPoint(p);
                    break;
            }
        }

    }

    /*
    private boolean balanceDownwards(QuadTree child){
        boolean result = false;
        BoundingBox childBbox = child.getBbox();
        for (Point2D p: points) {
            if(childBbox.contains(p)) {
                child.addPoint(p);
                points.remove(p);
                result = true;
            }
        }
        return result;
    }
    */

    private BoundingBox getBbox(){
        return bbox;
    }

    public int getNodeSize(){
        return points.size();
    }

    public int getTotalSize(){
        return totalSize;
    }

    private enum Direction{NorthWest, NorthEast, SouthEast, SouthWest}



}
