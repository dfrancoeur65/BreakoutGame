import java.awt.Color;

import acm.graphics.*;

public class Ball extends GOval {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Ball(double ballSize){
		super(ballSize,ballSize);
		r = ballSize/2;
		setFilled(true);
		setFillColor(Color.red);
	}

	public GPoint getTop(){
		double x = getX()+r;
		double y = getY()-.1;
		GPoint top = new GPoint(x,y);
		return top;
	}

	public GPoint getRightEdge(){
		double x = getX()+2*r+.1;
		double y = getY()+r;
		GPoint rightEdge = new GPoint(x,y);
		return rightEdge;
	}

	public GPoint getBottom(){
		double x = getX()+r;
		double y = getY()+2*r+.1;
		GPoint bottom = new GPoint(x,y);
		return bottom;
	}

	public GPoint getLeftEdge(){
		double x = getX()-.1;
		double y = getY()+r;
		GPoint leftEdge = new GPoint(x,y);
		return leftEdge;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}



	private double dx = 1;
	private double dy = -1;
	private double r;



}
