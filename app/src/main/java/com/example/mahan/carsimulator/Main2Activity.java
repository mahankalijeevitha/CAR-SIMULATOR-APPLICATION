package com.example.mahan.carsimulator;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements Simulate.UpdateI {

    private static final double ACCELERATION_INC = 5;

    private VehicleParams params;
    private Simulate simulator;

    private ImageView engine, track, vehicle;
    private Button left, right, brake, accelerate;

    private RelativeLayout mainLayout;
    private RelativeLayout.LayoutParams vehicleparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainLayout = (RelativeLayout ) this.getLayoutInflater().inflate(R.layout.activity_main2, null);

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        engine = (ImageView) findViewById(R.id.engine);
                        track = (ImageView) findViewById(R.id.track);
                        vehicle = (ImageView) findViewById(R.id.vehicle);
                        left = (Button) findViewById(R.id.left);
                        right = (Button) findViewById(R.id.right);
                        brake = (Button) findViewById(R.id.brake);
                        accelerate = (Button) findViewById(R.id.accelerate);

                        params = new VehicleParams();

                        Rect rect = new Rect();
                        RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) vehicle.getLayoutParams();
                        vehicleparams = layout;
                        rect.set(layout.leftMargin, layout.topMargin + 335, layout.rightMargin, layout.bottomMargin);
                        params.setPosition(rect);
                        Log.d("Sample", "Vehicle Left: " + rect.left + ", Top: " + rect.top + ", Right: " + rect.right + ", Bottom: " + rect.bottom);

                        layout = (RelativeLayout.LayoutParams) track.getLayoutParams();
                        rect = new Rect();
                        rect.set(layout.leftMargin + 21, layout.topMargin + 93, layout.rightMargin + track.getWidth()- 85, layout.bottomMargin + track.getHeight() - 70);
                        params.setBoundary(rect);
                        Log.d("Sample", "Track Left: " + rect.left + ", Top: " + rect.top + ", Right: " + rect.right + ", Bottom: " + rect.bottom);

                        simulator = new Simulate(params, Main2Activity.this, false);

                        engine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(simulator.isStatus()) {
                                    simulator.setStatus(false);
                                } else {
                                    simulator.setStatus(true);
                                    simulator.setTime(System.currentTimeMillis() / 1000);
                                    simulator.setStartTime(System.currentTimeMillis());
                                    simulator.start();
                                }
                            }
                        });

                        accelerate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                simulator.setAcceleration(ACCELERATION_INC);
                            }
                        });

                        brake.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                simulator.brake();
                            }
                        });

//                        right.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                simulator.setDirection(1);
//                            }
//                        });

                    }
                }
        );

        setContentView(mainLayout);

    }

    @Override
    public void updateUI(final VehicleParams params) {
        this.params =  params;
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(vehicle.getLayoutParams());
                if(params.getDirection() == 0) {
                    layoutParams.setMargins(params.getPosition().left, params.getPosition().top, params.getPosition().right, params.getPosition().bottom);
                } else if(params.getDirection() == 1) {
                    layoutParams.setMargins(params.getPosition().left + params.getPosition().right, params.getPosition().top, params.getPosition().right, params.getPosition().bottom);
                } else if(params.getDirection() == 2) {
                    layoutParams.setMargins(params.getPosition().left + params.getPosition().right, params.getPosition().top + params.getPosition().bottom, params.getPosition().right, params.getPosition().bottom);
                } else if(params.getDirection() == 3) {
                    layoutParams.setMargins(params.getPosition().left, params.getPosition().top + params.getPosition().bottom, params.getPosition().right, params.getPosition().bottom);
                }
                vehicle.setLayoutParams(layoutParams);
//                ((TextView) findViewById(R.id.accelerated)).setText("Accelration: " + params.getAcceleration());
//                ((TextView) findViewById(R.id.veloc)).setText("Velocity: " + params.getVelocity());
//                ((TextView) findViewById(R.id.tim)).setText("Time: " + ((System.currentTimeMillis() - params.getStartTime()) / 1000));
                ((TextView) findViewById(R.id.pos)).setText("X: (" + (params.getPosition().left + params.getPosition().right) + ", " + (params.getPosition().top + params.getPosition().bottom) + ")");
//                ((TextView) findViewById(R.id.dist)).setText("Distance: " + params.getDistance());
            }
        }));
        Log.d("Sample", params.toString());
    }
}
