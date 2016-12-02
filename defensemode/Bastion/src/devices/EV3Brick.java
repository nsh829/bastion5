package devices;

import lejos.hardware.Button;
import devices.LightSensor;
import devices.SonarSensor;
import devices.TouchSensor;
import devices.WheelActuator;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Timer;
import lejos.utility.TimerListener;

public class EV3Brick {
	public static LightSensor light = new LightSensor();
	public static SonarSensor sonar = new SonarSensor();
	public static TouchSensor touch = new TouchSensor();
	public static WheelActuator wheel = new WheelActuator();
	
	public static GameOverListener gameOverListener = new GameOverListener();
	public static Timer gameOverTimer = new Timer(180000, gameOverListener);
	
	public static GoBackToStadiumListener goBackToStadiumListener = new GoBackToStadiumListener();
	public static Timer goBackToStadiumTimer = new Timer(3000, goBackToStadiumListener);
	
	public static RushingListener rushingListener = new RushingListener();
	public static Timer rushingTimer = new Timer(1000, rushingListener);
	
	public static boolean mode = false; // false : attack mode, true : defense mode
	public static boolean goBackToStadium = true;
	
	private static int baseSpeed = 500;
	private static int maxSpeed = 900;
	public static boolean rushing = false;
	
	public static void setBaseSpeed() {
		wheel.setSpeed(baseSpeed);
	}
	
	public static void setMaxSpeed() {
		wheel.setSpeed(maxSpeed);
	}

	public static void main(String[] args) {
		// initialize
		light.setDaemon(true);
		sonar.setDaemon(true);
		touch.setDaemon(true);
		light.start();
		sonar.start();
		touch.start();
		setBaseSpeed();
		
		// start behaviors
		Behavior b1 = new TurnAround();
		Behavior b2 = new GoBackToStadium();
		Behavior b3 = new RunAwayFromEnemy();
		Behavior b4 = new RushToEnemy();
		Behavior b5 = new stopRushToEnemy();
		Behavior[] behaviorList = { b1, b2, b3, b4, b5 };
		Arbitrator arbitrator = new Arbitrator(behaviorList);
		gameOverTimer.start();
		arbitrator.start();
	}

}

class GameOverListener implements TimerListener {

	@Override
	public void timedOut() {
		EV3Brick.gameOverTimer.stop();
		System.exit(0);
	}
	
}

class TurnAround implements Behavior {

	@Override
	public boolean takeControl() {
		if (Button.ESCAPE.isDown() == true) System.exit(1);
		return true;
	}

	@Override
	public void action() {
		EV3Brick.wheel.goFront();
	}

	@Override
	public void suppress() {
	}
	
}

class GoBackToStadiumListener implements TimerListener {

	@Override
	public void timedOut() {
		EV3Brick.goBackToStadium = true;
		EV3Brick.goBackToStadiumTimer.stop();
	}
	
}

class GoBackToStadium implements Behavior {

	@Override
	public boolean takeControl() {
		if(EV3Brick.light.getColor() >= 6 && EV3Brick.goBackToStadium == true) return true;
		return false;
	}

	@Override
	public void action() {
		EV3Brick.wheel.turn(420);
		EV3Brick.goBackToStadium = false;
		EV3Brick.goBackToStadiumTimer.setDelay(3000);
		EV3Brick.goBackToStadiumTimer.start();
	}

	@Override
	public void suppress() {
	}
	
}

class RushToEnemy implements Behavior {

	@Override
	public boolean takeControl() {
		if(EV3Brick.touch.isTouched() == 1 && EV3Brick.rushing == false) return true;
		return false;
	}

	@Override
	public void action() {
		EV3Brick.setMaxSpeed();
		EV3Brick.rushing = true;
	}

	@Override
	public void suppress() {
	}
	
}

class stopRushToEnemy implements Behavior {

	@Override
	public boolean takeControl() {
		if(EV3Brick.touch.isTouched() != 1 && EV3Brick.rushing == true) return true;
		return false;
	}

	@Override
	public void action() {
		EV3Brick.setBaseSpeed();
		EV3Brick.rushing = false;
	}

	@Override
	public void suppress() {	
	}
	
}

class RushingListener implements TimerListener {

	@Override
	public void timedOut() {
		EV3Brick.setBaseSpeed();
		EV3Brick.goBackToStadiumTimer.stop();
	}
	
}

class RunAwayFromEnemy implements Behavior {

	@Override
	public boolean takeControl() {
		if(EV3Brick.sonar.getDistance() < 10 && EV3Brick.rushing == false) return true;
		return false;
	}

	@Override
	public void action() {
		EV3Brick.setMaxSpeed();
		EV3Brick.wheel.turn(420);
		EV3Brick.rushingTimer.setDelay(1000);
		EV3Brick.rushingTimer.start();
	}

	@Override
	public void suppress() {
	}
	
}