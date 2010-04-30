package gameEngine.gameMath;

import java.awt.geom.Point2D;

public class Vector {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x(x);
		this.y(y);
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public void setVector(Point2D p) {
		this.x(p.getX());
		this.y(p.getY());
	}
	
	public void setVector(double x, double y) {
		this.x(x);
		this.y(y);
	}
	
	public Vector(Point2D p) {
		this.x(p.getX());
		this.y(p.getY());
	}
	
	public void normalize(){
		double h = Math.sqrt( Math.pow(x(), 2)+Math.pow(y(),2));
		this.x(x/h);
		this.y(y/h);
	}

	public void x(double x) {
		this.x = x;
	}

	public double x() {
		return x;
	}

	public void y(double y) {
		this.y = y;
	}

	public double y() {
		return y;
	}
	
	public Vector multiply(double scalar){
		return new Vector(x()*scalar,y()*scalar);
	}
}
