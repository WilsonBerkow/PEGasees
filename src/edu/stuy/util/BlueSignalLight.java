package edu.stuy.util;

import static edu.stuy.robot.RobotMap.SIGNAL_LIGHT_BLUE_PORT;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BlueSignalLight {

    //private DigitalOutput redLight;

    public BlueSignalLight() {
        //redLight = new DigitalOutput(SIGNAL_LIGHT_BLUE_PORT);
    }

    public void set(boolean on) {
        //redLight.set(on);
        System.out.println("Blue signal light set to: " + on);
        SmartDashboard.putBoolean("CV Signal Light On?", on);
    }

    public void setOn() {
        set(true);
    }

    public void setOff() {
        set(false);
    }
}

