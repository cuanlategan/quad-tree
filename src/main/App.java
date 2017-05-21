import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;


public class App extends Application {
    private final double width = 600;
    private final double height = 800;
    private final QuadTree quadTree = new QuadTree(new BoundingBox(0,0, width, height));

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Quadtree Demo");
        primaryStage.setResizable(false);

        Group circles = new Group();
        Group scene = new Group(new Group(new Rectangle(width,height,Color.WHITE), circles));
        scene.setTranslateX(25);
        scene.setTranslateY(25);
        scene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("mouse");
            }
        });

        Button btn = new Button();
        btn.setText("Add Point");
        btn.setTranslateX(width/2 - btn.getWidth()/2);
        btn.setTranslateY(height+50);
        btn.setOnAction(event -> {
            double x = Math.floor(Math.random()*width);
            double y = Math.floor(Math.random()*height);
            Point2D p = new Point2D(x, y);
            quadTree.addPoint(p);
            System.out.println(p.getX() + "  " + p.getY() + "\n");
            Point2D pointAdded = quadTree.getPoint(p); // Use quadTree for getting point position
            Circle circle = new Circle(5, Color.web("black"));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStrokeWidth(4);
            circle.setCenterX(pointAdded.getX());
            circle.setCenterY(pointAdded.getY());
            circles.getChildren().add(circle);
        });

        Pane root = new Pane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, width+50, height+100));


        root.getChildren().add(scene);

        primaryStage.show();
    }

}

