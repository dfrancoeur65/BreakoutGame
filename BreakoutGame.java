import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.*;
import acm.program.*;

public class BreakoutGame extends GraphicsProgram {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void init(){
		player = new Player(PLAYER_WIDTH *getWidth(),PLAYER_HEIGHT);
		add(player,getWidth()/2,getHeight()-getHeight()*PLAYER_Y);
		createRows();
		bouncingBall = new Ball(5);
		add(bouncingBall,0,getWidth()*.6);
		message = new GLabel(" ");
		add(message, getWidth()/2,getHeight()/2);
		message.setVisible(false);
		addKeyListeners();

	}

	public void run(){
		message.setVisible(true);
		pause(1000);
		message.setLabel("New Game");
		pause(3000);
		message.setLabel("You get 3 lives!");
		pause(1000);
		message.setLabel("Start");
		pause(500);
		message.setVisible(false);
		newBall();
	}

	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: player.move(-playerSpeed, 0); break;
		case KeyEvent.VK_RIGHT: player.move(playerSpeed, 0); break;
		}
	}




	private void createRows(){
		double blockWidth = getWidth()/10;
		double topRowY = getHeight()*TOP_ROW_Y;
		for(int i = 0;i<ROW_COLORS.length;i++){
			for (int j = 0;j<COLUMNS;j++){
			add(new Block(ROW_COLORS[i],blockWidth,BLOCK_HEIGHT),j*blockWidth,topRowY+i*BLOCK_HEIGHT);
			blockCount+=1;
			}
		}
	}



	private void newBall(){
		bouncingBall.setLocation(1,getHeight()*.6);
		while(gameRunning){

			checkOnBallMovement();
			bouncingBall.move(bouncingBall.getDx(),bouncingBall.getDy());
			pause(SPEED);
		}

	}


	private void checkOnBallMovement(){
		updateEdges();
		handleBoundaryHit();
		handleBlockHit();
		handleLowerBoundaryHit();
		handlePlayerHit();
		checkBlockCount();
		System.out.println(blockCount);

	}


	private void handlePlayerHit(){
		GPoint playerHit = checkBallEdgesforPlayerHit();
		if(playerHit!=null){
			bounce(playerHit);
		}
	}
	private void handleBoundaryHit(){
		GPoint boundaryHit = checkBallEdgesForBoundaryHit();
		if(boundaryHit!=null){
			bounce(boundaryHit);
		}
	}

	private void handleLowerBoundaryHit(){
	  GPoint lowerBoundaryHit = checkBallEdgesForLowerBoundaryHit();
	  if(lowerBoundaryHit!=null){
		  ballLost();
	  }
	}

	private void ballLost(){
		pause(1000);
		message.setLabel("Ball Lost");
		message.setVisible(true);
		turns--;
		pause(500);
		if(turns !=0){
			message.setLabel("Next Ball");
			pause(500);
			message.setVisible(false);
			pause(2000);
			newBall();
		}
		if(turns==0){
			message.setLabel("GAME OVER");
			message.setVisible(true);
			pause(1000);
			gameRunning = false;
		}
	}


	private GPoint checkBallEdgesForBoundaryHit(){
		if(top.getY()<=0){return top;}
		else if(rightEdge.getX()>=getWidth()){return rightEdge;}
		else if(leftEdge.getX()<=0){return leftEdge;}
		else {return null;}
	}
	private GPoint checkBallEdgesForLowerBoundaryHit(){
		if(bottom.getY()>=getHeight()){return bottom;}
		else{return null;}
	}
	private void handleBlockHit(){
		GPoint blockHit = checkBallEdgesForBlockHit();
		if(blockHit!=null){
			bounce(blockHit);
			removeBlock((Block) getElementAt(blockHit));
		}
	}

	private GPoint checkBallEdgesforPlayerHit(){
		if(checkPointForPlayerHit(top)){return top;}

		else if(checkPointForPlayerHit(bottom)){return bottom;}

		else if(checkPointForPlayerHit(rightEdge)){return rightEdge;}

		else if(checkPointForPlayerHit(leftEdge)){return leftEdge;}

		return null;
	}

	private GPoint checkBallEdgesForBlockHit(){

		if(checkPointForBlockHit(top)){return top;}

		else if(checkPointForBlockHit(bottom)){return bottom;}

		else if(checkPointForBlockHit(rightEdge)){return rightEdge;}

		else if(checkPointForBlockHit(leftEdge)){return leftEdge;}

		return null;
	}

	private void bounce(GPoint bounceOff){
		if(bounceOff==top||bounceOff==bottom){
			yBounce();
		} else if(bounceOff ==rightEdge||bounceOff==leftEdge){
			xBounce();
		}

	}

	private void xBounce(){
		double dx = bouncingBall.getDx();
		bouncingBall.setDx(-dx);
	}

	private void yBounce(){
		double dy = bouncingBall.getDy();
		bouncingBall.setDy(-dy);
	}

	private void updateEdges(){
		top = bouncingBall.getTop();
		rightEdge = bouncingBall.getRightEdge();
		bottom = bouncingBall.getBottom();
		leftEdge = bouncingBall.getLeftEdge();

	}


	private boolean checkPointForBlockHit(GPoint edge){
		System.out.println(getElementAt(edge));
		if(getElementAt(edge) instanceof Block){
			return true;

		} else {return false;}

	}

	private boolean checkPointForPlayerHit(GPoint edge){
		if(getElementAt(edge) instanceof Player){
			return true;
		} else {return false;}
	}
	private void removeBlock(Block block){
		block.setFillColor(Color.red);
		pause(50);
		remove(block);
		blockCount--;
	}
	private void checkBlockCount(){
		if(blockCount==0){
			gameRunning=false;
			message.setVisible(true);
			message.setLabel("You Won");
		}
	}




	/*private constant variables */
	private static final int COLUMNS = 10;

	private static final Color[] ROW_COLORS = {Color.red,Color.red,Color.orange,Color.orange,Color.green, Color.green, Color.yellow,Color.yellow};

	private static final double PLAYER_WIDTH = .2;

	private static final double PLAYER_Y = .15;

	private static final double BLOCK_HEIGHT = 5;

	private static final double PLAYER_HEIGHT = 5;

	private static final double TOP_ROW_Y = .2;

	private static final double SPEED = 10;

	private static Runtime rtime = Runtime.getRuntime();


	/* private instance variables */
	private int blockCount = 0;
	private int turns = 3;
	private int playerSpeed = 10;
	private boolean gameRunning = true;
	private Player player;
	private Ball bouncingBall;
	private GLabel message;
	private GPoint top;
	private GPoint rightEdge;
	private GPoint bottom;
	private GPoint leftEdge;

	public enum Edge{
		TOP,RIGHT_EDGE,BOTTOM,LEFT_EDGE

	}
}


class Player extends GRect{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Player(double width,double height){
		super(width,height);
		setFillColor(Color.black);
		setFilled(true);
	}

}

class Block extends GRect{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Block(Color color, double width,double height){
		super(width,height);
		setFilled(true);
		setFillColor(color);
	}
	public String toString(){
		return "Block";
	}


}
