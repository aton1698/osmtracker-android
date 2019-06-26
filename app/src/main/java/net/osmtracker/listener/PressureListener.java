package net.osmtracker.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Listener for pressure sensor.
 *
 * Register the listener with your context using the register/unregister functions
 *
 * Should be possible to enable/disable via preferences due to power consumption.
 *
 */
public class PressureListener implements SensorEventListener {

    /**
     * TAG for this class
     */
    private static final String TAG = SensorListener.class.getSimpleName();

    private SensorManager sensorService;
    private boolean use_pressure;
    private float last_atmospheric_pressure_hPa = 0;

    public PressureListener(boolean use_pressure) {
        this.use_pressure = use_pressure;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        last_atmospheric_pressure_hPa = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean register (Context context) {

        boolean result = true;

        if (use_pressure) {
            sensorService = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

            Sensor pressureSens = sensorService.getDefaultSensor(Sensor.TYPE_PRESSURE);

            if (pressureSens != null) {
                sensorService.registerListener(this,
                        pressureSens,
                        SensorManager.SENSOR_DELAY_NORMAL);

                Log.i(TAG, "Registerered for pressure Sensor");
                result = true;
            } else {
                Log.w(TAG, "Pressure sensor not found");
                result = false;
            }
        }

        return result;
    }

    public void unregister () {
        // stop sensors TODO: is this good if sensors registration failed?
        if (sensorService != null) {
            sensorService.unregisterListener(this);
            sensorService = null;
            Log.v(TAG, "unregistered");
        }
    }

    public float getPressure () {
        return last_atmospheric_pressure_hPa;
    }
}
