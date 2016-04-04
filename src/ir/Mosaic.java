package ir;

import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mosaic extends Application{

	private Canvas canvas;
	private Image img = null;
	private Stage stage;
	private int SamplingInterval = 16;
	private int num = 3;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		/* make full screen size canvas */
	
		this.stage = stage;
		
		canvas = new Canvas(300,300);
		
		canvas.setOnDragOver( event ->{
			Dragboard board = event.getDragboard();
			if(board.hasFiles()) {  
				event.acceptTransferModes(TransferMode.COPY);
			}
		});
		canvas.setOnDragDropped(event ->{
	        /* data dropped */
	        /* if there is a string data on dragboard, read it and use it */
	        Dragboard db = event.getDragboard();

	        if(db.hasFiles()) {
	    		try {
					img = new Image(new FileInputStream(db.getFiles().get(0)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		event.setDropCompleted(true);
		        makeMosaic();
	    	} else {	
	    		event.setDropCompleted(false);
	    	}
		     
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
	
	private void makeMosaic() {
		// TODO Auto-generated method stub
		canvas.setWidth(img.getWidth());
		canvas.setHeight(img.getHeight());
		
		stage.setWidth(img.getWidth());
		stage.setHeight(img.getHeight());

		ArrayList<ColorNode> cns = new ArrayList<ColorNode>();
        
		/* setup nodes */
        for(int i = 0; i < (int)canvas.getWidth()/SamplingInterval-1; i++){
        	for(int j = 0; j < (int)canvas.getHeight()/SamplingInterval-1; j++){
        		
        		Color c = img.getPixelReader().getColor(i*SamplingInterval+SamplingInterval/2, j*SamplingInterval+SamplingInterval/2);
        		if(c.getOpacity() != 0.0f){
        			cns.add(new ColorNode(c,i,j));
        		}
        	}
        }
        
        /* clustering */
        while(cns.size() >= num){
    		ColorNode n0 = null;
    		ColorNode n1 = null;
    		int distance = 0;
    		
        	for(int i = 0; i < cns.size(); i++){
        		for(int j = i+1; j < cns.size(); j++){

        			int d = cns.get(i).distance(cns.get(j));
        			if(n0 == null || distance >= d){
        				distance = d;
        				n0 = cns.get(i);
        				n1 = cns.get(j);
        			}
        		}
        	}
        	
        	cns.remove(n0);
        	cns.remove(n1);
        	cns.add(new ColorNode(n0,n1));
        }

        /* draw */
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        for(ColorNode cn: cns){
        	drawCN(cn,gc);
        }
        
	}

	
	private void drawCN(ColorNode cn, GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(cn.leaf){
			gc.setFill(cn.getColor());
			int x = cn.x*SamplingInterval+SamplingInterval/2;
			int y = cn.y*SamplingInterval+SamplingInterval/2;
			gc.fillOval(x,y,SamplingInterval,SamplingInterval);
		}else{
			drawCN(cn.n0,gc);
			drawCN(cn.n1,gc);
		}
	}

	public static void main(String[] args) {
        launch(args);
    }
}

class ColorNode{
	
	boolean leaf;
	private Color color;
	ColorNode n0;
	ColorNode n1;
	int x;
	int y;
	
	ColorNode(Color c, int x, int y){
		leaf = true;
		color = c;
		this.x = x;
		this.y = y;
	}
	ColorNode(ColorNode n0, ColorNode n1){
		leaf = false;
		this.n0 = n0;
		this.n1 = n1;
	}
	
	void setColor(Color c){
		color = c;
		if(!leaf){
			n0.setColor(c);
			n1.setColor(c);
		}
	}
	Color getColor(){
		if(color == null){
			if( n0.weight() >= n1.weight() ){
				color =  n0.getColor();
				n1.setColor(color);
			}else{
				color =  n1.getColor();
				n0.setColor(color);
			}
		}
		return color;
	}
	int distance(ColorNode cn){
		Color c0 = cn.getColor();

		getColor();
		
		int ans = 0;
		ans += Math.abs(255*c0.getRed()-255*color.getRed());
		ans += Math.abs(255*c0.getBlue()-255*color.getBlue());
		ans += Math.abs(255*c0.getGreen()-255*color.getGreen());
		
		return ans;
	}
	
	int weight(){
		if(leaf) return 1;
		else return n0.weight()+n1.weight();
	}
}
