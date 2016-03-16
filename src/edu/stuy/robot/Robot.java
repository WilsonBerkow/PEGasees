package edu.stuy.robot;

import static edu.stuy.robot.RobotMap.JONAH_ID;
import static edu.stuy.robot.RobotMap.SHOOTER_SPEED_LABEL;
import static edu.stuy.robot.RobotMap.YUBIN_ID;

import edu.stuy.robot.subsystems.Drivetrain;
import edu.stuy.util.BlueSignalLight;
import edu.stuy.util.TegraSocketReader;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static Drivetrain drivetrain;
    public static OI oi;

    public static BlueSignalLight cvSignalLight;

    Command autonomousCommand;

    public static SendableChooser debugChooser;
    public static SendableChooser autonChooser;
    public static SendableChooser operatorChooser;
    public static SendableChooser autonPositionChooser;
    public static SendableChooser autonShootChooser;

    public static boolean dontStartCommands;

    private static TegraSocketReader tegraReader;
    private static Thread tegraThread;
    private double autonStartTime;
    private boolean debugMode;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        try {

            setupDebugChooser();
            debugMode = (Boolean) debugChooser.getSelected();

            // GyroPID
            SmartDashboard.putNumber("Gyro P", 0);
            SmartDashboard.putNumber("Gyro I", 0);
            SmartDashboard.putNumber("Gyro D", 0);

            // Start the operator chooser before anything else
            chooseOperator();

            // Initialize all the subsystems
            drivetrain = new Drivetrain();

            oi = new OI();

            dontStartCommands = false;

            drivetrain.setDrivetrainBrakeMode(true);

            SmartDashboard.putNumber(SHOOTER_SPEED_LABEL, 0.0);

            // Auton Distances:
            SmartDashboard.putNumber("Rock", 0);
            SmartDashboard.putNumber("Moat", 0);
            SmartDashboard.putNumber("Rough", 0);
            SmartDashboard.putNumber("Ramparts", 0);
            SmartDashboard.putNumber("Draw", 0); // complex
            SmartDashboard.putNumber("Cheval", 0);
            SmartDashboard.putNumber("Portcullis", 0); // complex

            // Potentiometer
            double initialVoltage = 93.5;
            double finalVoltage = 170;
            SmartDashboard.putNumber("Initial Voltage", initialVoltage);
            SmartDashboard.putNumber("Final Voltage", finalVoltage);
            SmartDashboard.putNumber("Conversion Factor", 90.0 / (finalVoltage - initialVoltage));

            // Set up the auton chooser
            setupAutonChooser();
            setupAutonPositionChooser();

            // Set up Tegra reading thread
            startTegraReadingThread();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (tegraThread != null && tegraThread.isAlive()) {
                        tegraThread.interrupt();
                    }
                }
            });
            System.out.println("Added shutdown hook for Tegra thread interruption");
        } catch (Exception e) {
            // Let's hope it was near the end
            System.out.println("AN EXCEPTION WAS CAUGHT IN robotInit: " + e);
        }
    }

    private void startTegraReadingThread() {
        System.out.println("Initializing a TegraSocketReader");
        tegraReader = new TegraSocketReader();
        System.out.println("Setting up thread");
        tegraThread = new Thread(tegraReader);
        // Call .start(), rather than .run(), to run it in a separate thread
        tegraThread.start();
        System.out.println("Done!");
    }

    public static double[] getLatestTegraVector() {
        return tegraReader.getMostRecent();
    }

    public static boolean tegraIsConnected() {
        return tegraReader.isConnected();
    }

    /**
     * Maps the relative locations of the obstacles
     */
    private void setupAutonPositionChooser() {
        autonPositionChooser = new SendableChooser();
        SmartDashboard.putString("1", "The low bar is in position 1, not a valid autonomous choice");
        autonPositionChooser.addObject("2", 2);
        autonPositionChooser.addDefault("3", 3);
        autonPositionChooser.addObject("4", 4);
        autonPositionChooser.addObject("5", 5);
        SmartDashboard.putData("Auton Position", autonPositionChooser);
    }

    private void chooseOperator() {
        operatorChooser = new SendableChooser();
        operatorChooser.addDefault("Jonah", JONAH_ID);
        operatorChooser.addObject("Yubin", YUBIN_ID);
        SmartDashboard.putData("Choose thy Operator Knight!", operatorChooser);
    }

    private void setupAutonChooser() {
        autonShootChooser = new SendableChooser();
        autonShootChooser.addDefault("Yes", true);
        autonShootChooser.addObject("No", false);
        SmartDashboard.putData("Shoot during auton?", autonShootChooser);

        autonChooser = new SendableChooser();
    }

    private void setupDebugChooser() {
        debugChooser = new SendableChooser();
        debugChooser.addDefault("Competition Mode", false);
        debugChooser.addObject("Debug Mode", true);
        SmartDashboard.putData("SmartDashboard Mode", debugChooser);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        try {
            debugMode = (Boolean) debugChooser.getSelected();
            Command selectedCommand = (Command) autonChooser.getSelected();
            boolean shootAfter = (Boolean) autonShootChooser.getSelected();
            int autonPosition = (Integer) autonPositionChooser.getSelected();
            autonomousCommand = selectedCommand;
            autonomousCommand.start();
            Robot.drivetrain.resetEncoders();
            autonStartTime = Timer.getFPGATimestamp();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN autonomousInit: " + e);
        }
    }

    public void autonomousPeriodic() {
        try {
            Scheduler.getInstance().run();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN autonomousPeriodic: " + e);
        }
    }

    public void teleopInit() {
        try {
            // This makes sure that the autonomous stops running when
            // teleop starts running. If you want the autonomous to
            // continue until interrupted by another command, remove
            // this line or comment it out.
            if (autonomousCommand != null) {
                autonomousCommand.cancel();
            }
            debugMode = (Boolean) debugChooser.getSelected();
            Robot.drivetrain.resetEncoders();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopInit: " + e);
        }
    }

    /**
     * This function is called when the disabled button is hit. You can use it
     * to reset subsystems before shutting down.
     */
    public void disabledInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        try {
            Scheduler.getInstance().run();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopPeriodic: " + e);
        }
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
