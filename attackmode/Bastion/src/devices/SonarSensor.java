package devices;

import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class SonarSensor extends Thread {
	private EV3UltrasonicSensor ir = new EV3UltrasonicSensor(SensorPort.S2);
	private SampleProvider sonar = ir.getDistanceMode();
	private float[] sample = new float[sonar.sampleSize()];
	
	public SonarSensor() {
		ir.enable();
	}
	
	public int getDistance() {
		return (int)(sample[0]*100);
	}
	
	public void run() {
		while(true) {
			sonar.fetchSample(sample, 0);
		}
	}
}
