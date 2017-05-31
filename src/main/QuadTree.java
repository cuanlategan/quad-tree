import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {

    class Point{
        int x,y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    class Node{

        final int HEIGHT, WIDTH;
        final Point centre;

        private List<Point> points = new ArrayList<>(5);

        private Optional<Node> childNW = Optional.empty();
        private Optional<Node> childNE = Optional.empty();
        private Optional<Node> childSE = Optional.empty();
        private Optional<Node> childSW = Optional.empty();

        private Node(Point centre, int WIDTH, int HEIGHT){
            this.centre = centre;
            this.WIDTH = WIDTH;
            this.HEIGHT = HEIGHT;
        }
    }


    private final Node root;
    private final int NODE_SIZE;

    QuadTree(int width, int height){
        this(5, width, height);
    }


    QuadTree(int NODE_SIZE, int width, int height){
        this.NODE_SIZE = NODE_SIZE;
        Point centre = new Point(width/2, height/2);
        this.root = new Node(centre, width, height);
    }


    public void addPoint(Point point){
        addPoint(root, point);
    }


    private void addPoint(Node node, Point point){

        if(node.points.size() < 5) {
            node.points.add(point);
            return;
        }

        if(!node.childNW.isPresent()){
            createNodeChildren(node);
        }

    }


    private void createNodeChildren(Node node) {

        final int xPos = node.centre.getX();
        final int yPos = node.centre.getY();
        final int height = node.HEIGHT/2;
        final int width = node.WIDTH/2;

        Point c_nw = new Point(xPos-width/2, yPos-height/2);
        Node nw = new Node(c_nw, width, height);

        Point c_ne = new Point(xPos+width/2, yPos-height/2);
        Node ne = new Node(c_ne, width, height);

        Point c_se = new Point(xPos+width/2, yPos+height/2);
        Node se = new Node(c_se, width, height);

        Point c_sw = new Point(xPos-width/2, yPos+height/2);
        Node sw = new Node(c_sw, width, height);

        node.childNW = Optional.of(nw);
        node.childNE = Optional.of(ne);
        node.childSE = Optional.of(se);
        node.childSW = Optional.of(sw);

    }

    Predicate<Point> liesNW(Node node){

        int

        return point -> {
            node.point.x
        };
    }

}
