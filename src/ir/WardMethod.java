package ir;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WardMethod extends Application {
private Canvas canvas;

	Node[] nodes;
	
	int size = 100;
	int group = 1;
	int step = 1;
	
	int dataSet = 1;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		/* make full screen size canvas */
		
		canvas = new Canvas(900,900);
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
		if( nodes == null || nodes.length == group ){ 
		}else{
			return;
		}
		
		setUpData();
		setUpAnimation();
	}
	
	public void setUpData(){
		
		Random rand = new Random();
		
		
		
		switch(dataSet){
		case 0:
			nodes = new DataSetMaker().random(size,1);
			break;
		case 1:
			group = rand.nextInt(5)+2;
			nodes = new DataSetMaker().shooting(size, group);
			break;
		}
	}

	public void setUpAnimation(){
		AnimationTimer animationTimer = new AnimationTimer() {

			
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
                
                for(Node node: nodes){
                	draw(gc,node);
                	
                }
                
                if( nodes.length == group ){
            
                	gc.setStroke(Color.BLACK);
            		int r = 5;
                	
                	for(Node node:nodes){
                		Point2D p = node.vector();
                    	double x = convX(p.getX());
                    	double y = convY(p.getY());
                		gc.strokeOval(x-r, y-r, 2*r, 2*r);
                	}
                	stop();
                	return;
                }
                
                for(int i = 0; i < step && nodes.length > 1; i++)
                	clustering();
                
            }

			private void clustering() {
				// TODO Auto-generated method stub
				int n = nodes.length;
				double saveD = -1;
                int index0 = 0;
                int index1 = 0;
                
                for(int i = 0; i < n; i++){
                	for(int j = i+1; j < n; j++){
                		
                		Point2D uab = new Node(nodes[i],nodes[j]).vector();
                		Point2D ua = nodes[i].vector();
                		Point2D ub = nodes[j].vector();
                		
                		
                		double d = 0.0;
                		
                		d = new Node(nodes[i],nodes[j]).s()
                				- nodes[i].s()
                				- nodes[j].s();
                		
                		/*
                		d = -1.0;
                		for(Point2D pa:nodes[i].points()){
                			for(Point2D pb: nodes[j].points()){
                				double dd = pa.distance(pb);
                				if( d < 0 || d > dd){
                					d = dd;
                				}
                			}
                		}*/
                		
                		if(saveD < 0 || saveD > d){
                			index0 = i;
                			index1 = j;
                			saveD = d;
                		}
                	}
                }
                
                Node[] temp = nodes;
                nodes = new Node[temp.length-1];
                int index = 0;
                for(int i = 0; i < temp.length; i++){
                	if(i == index0 || i == index1)
                		continue;
                	nodes[index] = temp[i];
                	index++;
                }
                nodes[nodes.length-1] = new Node(temp[index0],temp[index1]);
			}

			private void draw(GraphicsContext gc, Node node) {
				// TODO Auto-generated method stub
                gc.setFill(node.getColor());
                gc.setStroke(Color.BLUE);
       
				if( node.isLeaf() ){
                	int r = 5;
                	double x = convX(node.getP().getX());
                	double y = convY(node.getP().getY());
                    gc.fillOval(x-r, y-r, 2*r, 2*r);
            	}else{
            		Point2D p0 = node.getN0().vector();
            		Point2D p1 = node.getN1().vector();

                	double x0 = convX(p0.getX());
                	double y0 = convY(p0.getY());
                	double x1 = convX(p1.getX());
                	double y1 = convY(p1.getY());
            		
            		gc.strokeLine(x0,y0,x1,y1);
            		draw(gc,node.getN0());
            		draw(gc,node.getN1());
            	}
			}
        };
        animationTimer.start();
	}

	double convX(double x){
		return x*canvas.getWidth()*0.45+canvas.getWidth()/2;
	}
	double convY(double y){
		return y*canvas.getHeight()*0.45+canvas.getHeight()/2;
	}
	public static void main(String[] args) {
        launch(args);
    }
}

