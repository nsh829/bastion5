package devices;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightSensor extends Thread {
	private EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	private SensorMode color = colorSensor.getColorIDMode();
	private float[] sample = new float[color.sampleSize()];
	
	public LightSensor() {
	}
	
	public int getColor() {
		return (int)sample[0];
	}
	
	public void run() {
		while(true) {
			color.fetchSample(sample, 0);
		}
	}
}
