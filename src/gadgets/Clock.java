package gadgets;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Clock extends Application {
	private Canvas canvas;
	private double dragStartX;
	private double dragStartY;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		/* make full screen size canvas */

		canvas = new Canvas(300, 300);
		canvas.setOnMouseClicked(ev -> {
			click(ev.getX(), ev.getY());

		});

		/* make scene from the canvas */
		Group root = new Group();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		scene.setOnMousePressed(e -> {
			dragStartX = e.getSceneX();
			dragStartY = e.getSceneY();
		});
		scene.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - dragStartX);
			stage.setY(e.getScreenY() - dragStartY);
		});

		/* set up stage */

		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.TRANSPARENT);

		stage.show();

		setUpAnimation();
	}

	private void click(double x, double y) {
		// TODO Auto-generated method stub
		System.out.println(x + "," + y);
	}

	public void setUpAnimation() {
		AnimationTimer animationTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				GraphicsContext gc = canvas.getGraphicsContext2D();

				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			}
		};
		animationTimer.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
