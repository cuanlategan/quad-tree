import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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

        Button btn = new Button();
        btn.setText("Add Point");
        btn.setTranslateX(width/2 - btn.getWidth()/2);
        btn.setTranslateY(height+50);
        btn.setOnAction(event -> quadTree.addPoint(new Point2D(Math.random()*600, Math.random()*800)));

        Pane root = new Pane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, width, height+100));

        primaryStage.show();
    }

}

