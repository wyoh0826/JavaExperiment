package ir;

import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class DataSetMaker {
	Color[] colors = {Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW,
			Color.PINK,Color.SKYBLUE,Color.DARKGRAY};


	public Node[] random(int n,int group){

		Node[] nodes = new Node[n];

		Random rand = new Random();
		
		for(int i = 0; i < nodes.length; i++){

			double x = rand.nextDouble() * 2 - 1.0;
			double y = rand.nextDouble() * 2 - 1.0;
			
			nodes[i] = new Node( new Point2D(x,y) ) ;
		}
		
		return normalize(nodes);
	}
	
	public Node[] shooting(int n, int group){

		Node[] nodes = new Node[n];
		Random rand = new Random();
		
		Point2D[] centors = new Point2D[group];
		
		group = centors.length;
		double maxR = 0.4;
		
		for(int i = 0; i < centors.length; i++){
			
			double x = rand.nextDouble() * (1.0 - maxR) *2 -1.0;
			double y = rand.nextDouble() * (1.0 - maxR) *2 -1.0;
			
			centors[i] = new Point2D(x,y);
			
			for(int j = 0; j < i; j++){
				if( centors[j].distance(centors[i]) < maxR/3){
					i--;
					break;
				}
			}
		}
		
		for(int i = 0; i < nodes.length; i++){
			int index = rand.nextInt(centors.length);
			Point2D c = centors[ index ];
			double r = 1*maxR * rand.nextDouble() * rand.nextDouble();
			double theta = Math.PI * 2 * rand.nextDouble();
			
			double x = c.getX() + r*Math.cos(theta);
			double y = c.getY() + r*Math.sin(theta); 

			nodes[i] = new Node( new Point2D(x,y) ) ;
			nodes[i].setColor( colors[index] );
		}
		

		return normalize(nodes);
	}
	
	private Node[] normalize(Node[] src){
		Point2D ave = average(src);
		
		for(Node n: src){
			n.setP( n.getP().subtract(ave) );
		}
		double max = 0.0;
		
		for(Node n:src){
			if( n.getP().getX() > max )
				max = n.vector().getX();
			if( n.getP().getY() > max )
				max = n.vector().getY();
		}
		
		for(Node n: src)
			n.setP( n.getP().multiply(1.0/max) );
		
		return src;
		
	}

	private Point2D average(Node[] src) {
		// TODO Auto-generated method stub
		double x = 0;
		double y = 0;
		
		for(Node n:src){
			x += n.getP().getX();
			y += n.getP().getY();
		}
		
		return new Point2D( x/src.length, y/src.length);
	}
}
