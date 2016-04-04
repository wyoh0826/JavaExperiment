package ir;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Node {


	
	private Color color = Color.BLUE;
	private boolean leaf;
	private Point2D p;
	private Node n0;
	private Node n1;
	
	private double s = -1.0;
	
	Node(Node n0, Node n1){
		this.n0 = n0;
		this.n1 = n1;
		leaf = false;
	}
	Node(Point2D p){
		this.p = p;
		leaf = true;
	}
	
	int weight(){
		if(leaf) return 1;
		return n0.weight()+n1.weight();
	}
	
	Point2D vector(){
		if(leaf) return p;
		
		Point2D v0 = n0.vector();
		Point2D v1 = n1.vector();
		int w0 = n0.weight();
		int w1 = n1.weight();
		int w = w0+w1;
		
		return new Point2D( ( v0.getX()*w0+v1.getX()*w1 )/w, 
							( v0.getY()*w0+v1.getY()*w1 )/w );
	}
	
	double s(){
		if( s < 0 ){
			Point2D u = vector();
			s = 0;
			
			for(Point2D x: points()){
				s += Math.pow( u.distance(x), 2 );
			}
		}
		
		return s;
	}
	
	ArrayList<Point2D> points(){
		ArrayList<Point2D> ans = new ArrayList<Point2D>();
		
		if(leaf){
			ans.add(p);
		}else{
			ans.addAll( n0.points() );
			ans.addAll( n1.points() );
		}
		
		return ans;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public Point2D getP() {
		return p;
	}
	public Node getN0() {
		return n0;
	}
	public Node getN1() {
		return n1;
	}
	public void setP(Point2D p){
		this.p = p;
	}
	
}
