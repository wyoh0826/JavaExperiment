import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MonteCarlo extends Application {

	private Canvas canvas;
	private AnimationTimer animationTimer;
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	private double pi = 0.0;
	private Point2D centor;
	private int radius = 100;
	private int count = 0;
	private int num = 10_000;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		/* make full screen size canvas */
		canvas = new Canvas(radius*3,radius*3);
		canvas.setOnMouseClicked(ev -> {
			click(ev.getX(), ev.getY());

		});

		/* make scene from the canvas */
		Group root = new Group();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);

		/* set up stage */

		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
		
		/* setup */
		centor = new Point2D(radius*1.5 ,radius*1.5);

		setUpAnimation();
	}

	private void click(double x, double y) {
		// TODO Auto-generated method stub
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		points = new  ArrayList<Point2D>();
		count = 0;
		animationTimer.start();
	}

	public void setUpAnimation() {
		animationTimer = new AnimationTimer() {

			Random rand = new Random();
			
			@Override
			public void handle(long now) {
				GraphicsContext gc = canvas.getGraphicsContext2D();
				/* drawing */
				
				if(points.size() >= num) stop();
				
				for(int i = 0; i < num/100; i++){
					Point2D p = generatePoint();
					if( p.distance(centor) <= radius ) 
						gc.setFill(Color.BLUE);
					else
						gc.setFill(Color.RED);
					int r = 1;
					gc.fillOval(p.getX()-r, p.getY()-r, 2*r, 2*r);
				}
				pi = (double)count/(double)points.size()*4.0;
				
				gc.clearRect(0, 0, canvas.getWidth(), radius*0.45);
				gc.setFill(Color.BLUE);
				gc.fillText("pi = "+pi, 10, 20);
				
				/*
				gc.setFill(Color.RED);
				gc.setStroke(Color.RED);
				gc.strokeOval(centor.getX()-radius, centor.getY()-radius, 2*radius, 2*radius);
				int r = 1;
				gc.fillOval(-r+centor.getX(), -r+centor.getY(), 2*r, 2*r);
				*/
			}

			private Point2D generatePoint() {
				// TODO Auto-generated method stub
				double r1 = rand.nextDouble();
				double r2 = rand.nextDouble();
				//r1 = (int)(r1*10)/10.0+0.05;
				//r2 = (int)(r2*10)/10.0+0.05;
				Point2D p = new Point2D(r1 * radius*2-radius+centor.getX(),
										r2 * radius*2-radius+centor.getY());
				points.add(p);
				if(p.distance(centor) <= radius) count++;
				
				return p;
			}
			
		};

	}

	public static void main(String[] args) {
		launch(args);
	}
}
