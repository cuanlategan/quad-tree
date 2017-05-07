import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    static final int MAX_ITEMS = 5;
    private int branchSize = 0;

    private List<Point2D> points = new ArrayList<>();

    private QuadTree childNW = null;
    private QuadTree childNE = null;
    private QuadTree childSE = null;
    private QuadTree childSW = null;

    public enum Direction {NorthWest, NorthEast, SouthEast, SouthWest}

    private BoundingBox bbox;

    public QuadTree(BoundingBox bbox) {
        this.bbox = bbox;
    }

    public Direction getDirection(Point2D p) {

        // TODO maybe add case for centre position
        if (p.getX() < bbox.getMaxX()/2
                && p.getY() < bbox.getMaxY()/2) {
            return Direction.NorthWest;
        }
        if (p.getX() > bbox.getMaxX()/2
                && p.getY() < bbox.getMaxY()/2) {
            return Direction.NorthEast;
        }
        if (p.getX() > bbox.getMaxX()/2
                && p.getY() > bbox.getMaxY()/2) {
            return Direction.SouthEast;
        }
        if (p.getX() < bbox.getMaxX()/2
                && p.getY() > bbox.getMaxY()/2) {
            return Direction.SouthWest;
        }

        try {
            throw new Exception("direction not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Should not be reached
        return Direction.NorthWest;
    }

    // TODO write tests for this method
    private QuadTree createChildNode(Direction direction) {
        BoundingBox childBox;
        QuadTree result = null;

        switch (direction) {
            case NorthWest:
                childBox = new BoundingBox(bbox.getMinX(),
                        bbox.getMinY(),
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
            case NorthEast:
                childBox = new BoundingBox(bbox.getWidth() / 2,
                        bbox.getMinY(),
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
            case SouthEast:
                childBox = new BoundingBox(bbox.getWidth() / 2,
                        bbox.getHeight() / 2,
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
            case SouthWest:
                childBox = new BoundingBox(bbox.getMinX(),
                        bbox.getHeight() / 2,
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
        }

        return result;
    }

    public boolean addPoint(Point2D p) {
        if (!bbox.contains(p)) {
            // TODO throw exception here
            // System.out.println("addPoint something went wrong, point not within bounds");
            return false;
        }

        if (getNodeSize() < MAX_ITEMS) {
            points.add(p);
            ++branchSize;
            return true;
        }

        //points.add(p);
        boolean result = false;
        if (getNodeSize() == MAX_ITEMS) {
            Direction dir = getDirection(p);
            switch (dir) {
                case NorthWest:
                    if (childNW == null) childNW = createChildNode(dir);
                    if (childNW.addPoint(p)){ result = true; }
                    break;
                case NorthEast:
                    if (childNE == null) childNE = createChildNode(dir);
                    if (childNE.addPoint(p)){ result = true; }
                    break;
                case SouthEast:
                    if (childSE == null) childSE = createChildNode(dir);
                    if (childSE.addPoint(p)){ result = true; }
                    break;
                case SouthWest:
                    if (childSW == null) childSW = createChildNode(dir);
                    if (childSW.addPoint(p)){ result = true; }
                    break;
            }
        }
        if(result) ++branchSize;
        return result;
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

    private BoundingBox getBbox() {
        return bbox;
    }

    public int getNodeSize() {
        return points.size();
    }

    public int getBranchSize() {
        return branchSize;
    }


}
