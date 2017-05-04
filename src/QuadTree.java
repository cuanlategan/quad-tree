import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    static final int MAX_ITEMS = 5;

    private ArrayList<Point> points = new ArrayList<>();

    private QuadTree childNW = null;
    private QuadTree childNE = null;
    private QuadTree childSE = null;
    private QuadTree childSW = null;


    private Rectangle rect;

    private enum Direction{NorthWest, NorthEast, SouthEast, SouthWest}

    public QuadTree(Rectangle rect){
        this.rect = rect;
    }

    private Direction getDirection(Point p){
        if(p.getX() < rect.getCenterX() && p.getY() < rect.getCenterY()){
            return Direction.NorthWest;
        }
        if(p.getX() > rect.getCenterX() && p.getY() < rect.getCenterY()){
            return Direction.NorthEast;
        }
        if(p.getX() > rect.getCenterX() && p.getY() > rect.getCenterY()){
            return Direction.SouthEast;
        }
        if(p.getX() < rect.getCenterX() && p.getY() > rect.getCenterY()){
            return Direction.SouthWest;
        }
        // Should not be reached
        return Direction.NorthWest;
    }

    public void addPoint(Point p){
        if (!rect.contains(p)){
            // TODO throw exception here
            System.out.println("addPoint something went wrong");
            return;
        }

        if(getNumItems() <= MAX_ITEMS){
            points.add(p);
            return;
        }

        if(getNumItems() == MAX_ITEMS){
            switch (getDirection(p)) {
                case NorthWest:
                    if(childNW == null){
                        //TODO Continue working here
                        //Rectangle r = new Rectangle()
                    }
                    break;
                case NorthEast:
                    break;
                case SouthEast:
                    break;
                case SouthWest:
                    break;
            }
        }

    }


    public int getNumItems(){
        return points.size();
    }



}
