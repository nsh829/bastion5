package devices;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class WheelActuator {
	private RegulatedMotor leftFront = Motor.A;
	private RegulatedMotor rightFront = Motor.D;
	private RegulatedMotor leftBack = Motor.B;
	private RegulatedMotor rightBack = Motor.C;
	
	public WheelActuator() {
	}
	
	public void goFront() {
		leftFront.forward();
		leftBack.backward();
		rightFront.forward();
		rightBack.backward();
	}
	
	public void goBack() {
		leftFront.backward();
		leftBack.forward();
		rightFront.backward();
		rightBack.forward();
	}
	
	public void stop() {
		leftFront.stop();
		leftBack.stop();
		rightFront.stop();
		rightBack.stop();
	}
	
	public void move(int rotate) {
		leftFront.rotate(rotate, true);
		leftBack.rotate(rotate, true);
		rightFront.rotate(rotate, true);
		rightBack.rotate(rotate);
		//rightBack.waitComplete();
	}
	public void keepTurn() {
		leftFront.forward();
		leftBack.backward();
		rightFront.backward();
		rightBack.forward();
	}
	
	public void turn(int rotate) {
		leftFront.rotate(rotate, true);
		leftBack.rotate(-rotate, true);
		rightFront.rotate(-rotate, true);
		rightBack.rotate(rotate);
		//rightBack.waitComplete();
	}
	
	public void setSpeed(int speed) {
		leftFront.setSpeed(speed);
		leftBack.setSpeed(speed);
		rightFront.setSpeed(speed);
		rightBack.setSpeed(speed);
	}
}
