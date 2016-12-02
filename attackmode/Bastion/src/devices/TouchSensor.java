package devices;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3TouchSensor;

public class TouchSensor extends Thread {
	private EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	private SensorMode touch = touchSensor.getTouchMode();
	private float[] sample = new float[touch.sampleSize()];
	
	public TouchSensor() {
	}
	
	public int isTouched() {
		return (int)sample[0];
	}
	
	public void run() {
		while(true) {
			touch.fetchSample(sample, 0);
		}
	}
}