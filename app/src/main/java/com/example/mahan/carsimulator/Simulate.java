package com.example.mahan.carsimulator;

import android.util.Log;

/**
 * Created by mahan on 11/6/2017.
 */

public class Simulate extends Thread {

    private static final double TIME_TICK = 1;

    private VehicleParams params;
    private boolean status;
    private UpdateI context;

    private long getStartTime() {
        return params.getStartTime();
    }

    public Simulate(VehicleParams params, UpdateI context, boolean status) {
        this.params = params;
        this.context = context;
        this.status = status;
    }

    public void setTime(double time) {
        this.params.setTime(time);
    }

    public void setAcceleration(double acceleration) {
        this.params.setAcceleration(acceleration);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void brake() {
        this.params.setBrake();
    }

    public boolean isStatus()  {
        return this.status;
    }

    public void setDirection(int control) {
        this.params.setDirection(control);
    }

    public void run() {
        double start = System.currentTimeMillis();
        while(status) {
            try {
                double time = System.currentTimeMillis()/ 1000;
                double t = time - params.getTime();
                double v = params.getVelocity() + params.getAcceleration() * t;
                double s = (params.getVelocity() * t) + (params.getAcceleration() * t * t / 2);
                params.setVelocity(v);
                params.setDistance(s);
                params.setPosition(s);
                params.setTime(time);

                if((System.currentTimeMillis() - start) / 1000 >= TIME_TICK) {
                    params.setAcceleration(-params.getBrake());
                    start = System.currentTimeMillis();
                }
                context.updateUI(params);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStartTime(long startTime) {
        this.params.setStartTime(startTime);
    }


    public interface UpdateI {
        void updateUI(VehicleParams params);
    }
}
