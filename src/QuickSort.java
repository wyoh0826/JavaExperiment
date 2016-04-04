import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class QuickSort extends Application{

	private Canvas canvas;
	
	private int size = 100;
	private int[] array = new int[size];

    private ArrayList<QS> qsq = new ArrayList<QS>();
    
    int count = 0;
    
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
		if( qsq.size() == 0 ){
			setUp();
			setUpAnimation();
		}
	}
	
	private void setUp(){
		for( int i = 0 ; i < size; i++){
			array[i] = i;
		}
		Random rand = new Random();
		
		for( int i = 0 ; i < size-1; i++){
			swap( array, i,i+1+rand.nextInt(size-i-1));
		}
		qsq.add(new QS(array,0,size-1));
		count = 0;
	}
	

	public void setUpAnimation(){
		AnimationTimer animationTimer = new AnimationTimer() {

			
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				if( qsq.size() == 0)
					gc.setFill(Color.RED);
				else
					gc.setFill(Color.BLUE);
                
				for(int i = 0; i < size; i++){
                	int x = 50+i*2;
                	int y = 250-array[i]*2;
                	gc.fillOval(x-1, y-1, 2, 3);
                }
				gc.fillText("count "+count,10,20);
				
				if( qsq.size() == 0){
					this.stop();
					return;
				}

                /* quick sort */
                ArrayList<QS> temp = qsq;
                qsq = new ArrayList<QS>();
                for(QS qs: temp){
                	qs.execute( qsq );
                	count++;
                }
                try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
        animationTimer.start();
	}
	
	private void swap(int[] array, int n, int m){
		int temp = array[n];
		array[n] = array[m];
		array[m] = temp;
	}
	
	
	public static void main(String[] args) {
        launch(args);
    }
}

class QS{
	int[] array;
	int start;
	int end;
	
	QS(int[] a, int s, int e){
		array = a;
		start = s;
		end = e;
	}
	
	void execute(ArrayList<QS> qsq){
		
		
		int index = (end+start)/2;
		if(index == start)index++;
		int pivot = array[index];

		int i = start;
		int j = end;
		while( true ){
			while( array[i] < pivot)
				i++;
			while( pivot < array[j])
				j--;
			
			if( i >= j )break;
			swap(array,i,j);
		}
		if( start < i-1)
			qsq.add(new QS(array,start,i-1));
		if( j+1 < end )
			qsq.add(new QS(array,j+1,end));

	}
	
	private void swap(int[] array, int n, int m){
		int temp = array[n];
		array[n] = array[m];
		array[m] = temp;
	}
	
	int length(){
		return end - start+1;
	}
}
