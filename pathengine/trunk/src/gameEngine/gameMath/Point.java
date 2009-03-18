package gameEngine.gameMath;

public class Point {
	private static final double POINT_PRECISION = 0.0000001;
	private double x;
	private double y;

	public Point() {
		x = 0;
		y = 0;
	}

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public Point copy(){
		return new Point(getX(),getY());
	}

	public double distance(final double p2x,final double p2y){
		final double xComponent = p2x - getX();
		final double yComponent = p2y - getY();
		return Math.sqrt(Math.pow(xComponent,2)+Math.pow(yComponent,2));
	}

	public double distance(final Point p){
		final double xComponent = p.getX() - getX();
		final double yComponent = p.getY() - getY();
		return Math.sqrt(Math.pow(xComponent,2)+Math.pow(yComponent,2));
	}

	@Override
	public boolean equals(final Object obj) {
		if(!(obj instanceof Point)) {
			return false;
		}
		final Point p = (Point) obj;
		return Eval.equals(p.getX(),getX(),POINT_PRECISION) && Eval.equals(p.getY(),getY(),POINT_PRECISION);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	public void setLocation(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	public void setLocation(final Point point) {
		setLocation(point.x, point.y);
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}

}
