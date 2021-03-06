import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    public final int MAX_ITEMS;

    private List<Point2D> points = new ArrayList<>();

    private QuadTree childNW = null;
    private QuadTree childNE = null;
    private QuadTree childSE = null;
    private QuadTree childSW = null;

    public enum Direction {NorthWest, NorthEast, SouthEast, SouthWest}

    private BoundingBox bbox;

    public QuadTree(BoundingBox bbox) {
        this.bbox = bbox;
        MAX_ITEMS = 5;
    }

    public QuadTree(BoundingBox bbox, int maxItems) {
        this.bbox = bbox;
        MAX_ITEMS = maxItems;
    }


    /**
     * Finds bearing that point falls under eg NorthWest, NorthEast.
     * Western quadrants are divided by [0,max-width/2)
     * North quadrants are divided by [0,max-height/2)
     *
     * @param p Point that falls in domain of branch
     * @return Direction enumeration of where point lies.
     */
    public Direction getDirection(Point2D p) {

        final double x = p.getX();
        final double y = p.getY();
        final double bboxMaxX = bbox.getMaxX();
        final double bboxMaxY = bbox.getMaxY();
        final double bboxMinX = bbox.getMinX();
        final double bboxMinY = bbox.getMinY();


        if (x >= bboxMinX && x < bboxMaxX/2 &&
            y >= bboxMinY && y < bboxMaxY/2) {
            return Direction.NorthWest;
        }
        if (x >= bboxMaxX/2 && x <= bboxMaxX &&
            y >= bboxMinY && y < bboxMaxY/2) {
            return Direction.NorthEast;
        }
        if (x >= bboxMaxX/2 && x <= bboxMaxX &&
            y >= bboxMaxY/2 && y <= bboxMaxY ){
            return Direction.SouthEast;
        }
        if (x >= bboxMinX && x < bboxMaxX/2 &&
            y >= bboxMaxY/2 && y <= bboxMaxY ){
            return Direction.SouthWest;
        }

        throw new RuntimeException("getDirection: direction not found");

    }


    public boolean hasPoint(Point2D p){
        if(points.contains(p)) return true;
        else if(childNW != null && childNW.hasPoint(p)) return true;
        else if(childNE != null && childNE.hasPoint(p)) return true;
        else if(childSE != null && childSE.hasPoint(p)) return true;
        else if(childSW != null && childSW.hasPoint(p)) return true;

        return false;
    }

    public Point2D getPoint(Point2D p){
        if(points.contains(p)) return points.get(points.indexOf(p));
        switch (getDirection(p)) {
            case NorthWest:
                if(childNW != null && childNW.hasPoint(p)) return childNW.getPoint(p);
                break;
            case NorthEast:
                if(childNE != null && childNE.hasPoint(p)) return childNE.getPoint(p);
                break;
            case SouthEast:
                if(childSE != null && childSE.hasPoint(p)) return childSE.getPoint(p);
                break;
            case SouthWest:
                if(childSW != null && childSW.hasPoint(p)) return childSW.getPoint(p);
                break;
        }

        return null;
    }

    public boolean addPoint(Point2D p) {

        if (!bbox.contains(p)) {
            String error = "addPoint: point(x:" + p.getX() + " y:" + p.getY() + ") ";
            error += "not within bounds minX:" + bbox.getMinX() + " maxX" + bbox.getMaxX();
            error += " minY:" + bbox.getMinY() + " maxY:" + bbox.getMaxY() + "\n";
            throw new RuntimeException(error);
        }

        if (getNodeSize() < MAX_ITEMS) {
            points.add(p);
            //if(points.size() == MAX_ITEMS) balanceDown();
            return true;
        }

        boolean result = false;
        Direction dir = getDirection(p);
        if (getNodeSize() >= MAX_ITEMS) {
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


        return result;
    }


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
                childBox = new BoundingBox(bbox.getMinX() + bbox.getWidth() / 2,
                        bbox.getMinY(),
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
            case SouthEast:
                childBox = new BoundingBox(bbox.getMinX() + bbox.getWidth() / 2,
                        bbox.getMinY() + bbox.getHeight() / 2,
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
            case SouthWest:
                childBox = new BoundingBox(bbox.getMinX(),
                        bbox.getMinY() + bbox.getHeight() / 2,
                        bbox.getWidth() / 2,
                        bbox.getHeight() / 2);
                result = new QuadTree(childBox);
                break;
        }

        return result;
    }

    public List<Point2D> getAllPoints(){
        List<Point2D> result = points;
        if(childNW != null) result.addAll(childNW.getAllPoints());
        if(childNE != null) result.addAll(childNE.getAllPoints());
        if(childSE != null) result.addAll(childSE.getAllPoints());
        if(childSW != null) result.addAll(childSW.getAllPoints());
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


    public int getNodeSize() {
        return points.size();
    }

    public int getBranchSize() {
        int result = points.size();
        if(childNW != null) result += childNW.getBranchSize();
        if(childNE != null) result += childNE.getBranchSize();
        if(childSE != null) result += childSE.getBranchSize();
        if(childSW != null) result += childSW.getBranchSize();

        return result;
    }


}
