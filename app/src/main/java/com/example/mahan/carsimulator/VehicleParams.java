package com.example.mahan.carsimulator;

import android.graphics.Rect;

/**
 * Created by mahan on 11/6/2017.
 */

public class VehicleParams {
    // 0 => top, 1 => left, 2 => bottom, 3 => right
    private Rect position;
    private Rect boundary;
    private double distance;
    private double acceleration;
    private double velocity;
    private double time;
    private double brake;
    private int direction;
    private long startTime;

    public VehicleParams() {
        distance = velocity = acceleration = time = direction = 0;
        brake = 2;
    }

    public void setDirection(int control) {
        direction = (4 + direction + control) % 4;
    }

    public int getDirection() {
        return direction;
    }

    public Rect getPosition() {
        return position;
    }

    public void setPosition(Rect  position){
        this.position = position;
    }

    public void setPosition(double offset) {
        double left, right, top, bottom;
        if(direction == 0) {
            top = position.bottom + position.top - offset;
            position.bottom = 0;
            if(top >= boundary.top) {
                position.top = (int) top;
            } else {
                position.top = boundary.top;
            }
        } else if(direction == 1) {
            right = position.right + offset;
            if(right <= boundary.right) {
                position.right = (int) right;
            } else {
                position.right = boundary.right;
            }
        } else if(direction == 2) {
            bottom = position.bottom + offset;
            if(bottom <= boundary.bottom) {
                position.bottom = (int) bottom;
            } else {
                position.bottom = boundary.bottom;
            }
        } else if(direction == 3) {
            left = position.right + position.left + - offset;
            position.right = 0;
            if (left >= boundary.left) {
                position.left = (int) left;
            } else {
                position.left = boundary.left;
            }
        }
    }

    public Rect getBoundary() {
        return boundary;
    }

    public void setBoundary(Rect boundary) {
        this.boundary = boundary;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if(distance  <= 0) {
            return;
        }
        this.distance += distance;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        if(this.acceleration == 0 && this.velocity == 0 && acceleration < 0) {
            return;
        }
        this.brake = 2;
        this.acceleration += acceleration;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
        if(this.velocity < 0) {
            this.velocity = 0;
            this.acceleration = 0;
        }
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getBrake() {
        return brake;
    }

    public void setBrake() {
        this.brake = 3;
    }

    @Override
    public String toString() {
        return "VehicleParams{" +
                "position=" + position +
                ", boundary=" + boundary +
                ", distance=" + distance +
                ", acceleration=" + acceleration +
                ", velocity=" + velocity +
                ", time=" + time +
                ", brake=" + brake +
                '}';
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
