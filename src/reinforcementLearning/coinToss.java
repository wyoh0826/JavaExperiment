package reinforcementLearning;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class coinToss  extends Application{
	AnimationTimer animationTimer;
	
	private Canvas canvas;
	
	private double[] vic = new double[101];
	private double p = 0.4;
	private int[] pi = new int[101];
	
	private boolean running = false;
	
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
		
		setUpAnimation();
	}
	
	private void click(double x, double y) {
		// TODO Auto-generated method stub
		if(running) return;
		
		running = true;
		setUpData();
        animationTimer.start();
	}

	private void setUpData() {
		// TODO Auto-generated method stub
		for(int i = 0; i < vic.length; i++){
			vic[i] = 0;
			pi[i] = 0;
		}
		vic[0] = 0;
		vic[100] = 1;
	}

	public void updata(){
		
		if(!running)return;
		
		double[] prev = new double[vic.length];
		for(int i = 0 ; i < vic.length; i++) prev[i] = vic[i];
		boolean updata = false;
		
		for(int coin = 1; coin < 100; coin++){
			double f = 0.0;
			int n = 0;
			
			for(int bet = 1; bet <= coin && bet <= 100-coin; bet++){
				double v = p*prev[coin+bet]+(1.0-p)*prev[coin-bet];
				if( v > f ){
					n = bet;
					f = v;
				}
			}
			
			if(Math.abs(vic[coin]-f) > 0.00001){
				vic[coin] = f;
				pi[coin] = n;
				updata = true;
			}
		}
		
		if(!updata){
			running = false;
		}
	}
	
	public void setUpAnimation(){
		animationTimer = new AnimationTimer() {

			
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();

                
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
                for(int i = 0; i < vic.length; i++){
                	gc.fillRect(50+i*2, 250-2*pi[i], 2, 2*pi[i]);
                }
                updata();
            }
        };
	}
	
	
	public static void main(String[] args) {
        launch(args);
    }
}
