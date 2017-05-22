import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cuan on 5/4/17.
 */
public class QuadTree {


    private class Node {
        public Node() {
        }

        public Node(BoundingBox bbox, Point2D point) {
            this.bbox = bbox;
            this.point = point;
        }

        private BoundingBox bbox;
        private Point2D point;
        private Node childNW, childNE, childSE, childSW;
    }

    private Node root;
    private BoundingBox bbox;
    private int size = 0;

    public QuadTree(BoundingBox bbox) {
        this.bbox = bbox;
    }


    public boolean hasPoint(Point2D p) {
        if (root.point == null) return false;
        else if (root.point == p) return true;
        else if (root.childNW != null) {
            if (hasPoint(root.childNW, p)) return true;
            if (hasPoint(root.childNE, p)) return true;
            if (hasPoint(root.childSE, p)) return true;
            if (hasPoint(root.childSW, p)) return true;
        }
        return false;
    }

    private boolean hasPoint(Node child, Point2D p) {
        if (child.point == null) return false;
        else if (child.point == p) return true;
        else if (child.childNW == null) return false;
        else if (hasPoint(child.childNW, p)) return true;
        else if (hasPoint(child.childNE, p)) return true;
        else if (hasPoint(child.childSE, p)) return true;
        else if (hasPoint(child.childSW, p)) return true;

        return false;
    }

    public void addPoint(Point2D p) {
        if (root == null) {
            root = new Node(bbox, p);
            ++size;
        } else if (addPoint(root, p)) ++size;
    }

    private boolean addPoint(Node node, Point2D p) {
        if (node.point == null) {
            node.point = p;
            return true;
        }

        if (node.childNW == null) createChildren(node);

        if (node.childNW.bbox.contains(p)) return addPoint(node.childNW, p);
        else if (node.childNE.bbox.contains(p)) return addPoint(node.childNE, p);
        else if (node.childSE.bbox.contains(p)) return addPoint(node.childSE, p);
        else if (node.childSW.bbox.contains(p)) return addPoint(node.childSW, p);

        return false;
    }

    private void createChildren(Node node) {
        node.childNW = new Node();
        node.childNW.bbox = new BoundingBox(
                node.bbox.getMinX(),
                node.bbox.getMinY(),
                node.bbox.getWidth() / 2,
                node.bbox.getHeight() / 2
        );

        node.childNE = new Node();
        node.childNE.bbox = new BoundingBox(
                node.bbox.getMinX() + node.bbox.getWidth()/2,
                node.bbox.getMinY(),
                node.bbox.getWidth()/2,
                node.bbox.getHeight()/2
        );
        node.childSE = new Node();
        node.childSE.bbox = new BoundingBox(
                node.bbox.getMinX() + node.bbox.getWidth()/2,
                node.bbox.getMinY() + node.bbox.getHeight()/2,
                node.bbox.getWidth()/2,
                node.bbox.getWidth()/2
        );

        node.childSW = new Node();
        node.childSW.bbox = new BoundingBox(
                node.bbox.getMinX(),
                node.bbox.getMinY() + node.bbox.getHeight()/2,
                node.bbox.getWidth()/2,
                node.bbox.getWidth()/2
        );

    }

    public int size() {
        return size;
    }
}
