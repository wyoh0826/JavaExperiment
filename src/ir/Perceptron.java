package ir;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Perceptron extends Application{

	private Canvas canvas;
	
	int size = 100;
	Node[] nodes;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		/* make full screen size canvas */
		
		canvas = new Canvas(300,300);
		canvas.setOnMouseClicked( ev ->{
			click(ev.getX(),ev.getY());
			
		});
		
		/* make scene from the canvas */
		Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        
        /* set up stage */

        stage.setScene(scene);
		stage.setResizable(false);
        
		stage.show();
		
	}
	
	private void click(double x, double y) {
		// TODO Auto-generated method stub
		nodes = new DataSetMaker().shooting(size, 2);
		setUpAnimation();
	}

	public void setUpAnimation(){
		AnimationTimer animationTimer = new AnimationTimer() {

			
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();

                
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
            }
        };
        animationTimer.start();
	}
	
	
	public static void main(String[] args) {
        launch(args);
    }
}
