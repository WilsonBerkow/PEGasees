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

    public static boolean dontStartCommands;

    private static TegraSocketReader tegraReader;
    private static Thread tegraThread;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        drivetrain = new Drivetrain();
        oi = new OI();
        dontStartCommands = false;
        drivetrain.setDrivetrainBrakeMode(true);


        try {
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
            System.out.println("AN EXCEPTION WAS CAUGHT IN robotInit: "
                    + indentText(e.getStackTrace().toString(), "    "));
        }
    }

    private static String indentText(String txt, String indent) {
        String[] lines = txt.split("\n");
        String result = "";
        for (String ln : lines) {
            result += indent + ln.trim() + "\n";
        }
        return result;
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

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    }

    public void autonomousPeriodic() {
        try {
            Scheduler.getInstance().run();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN autonomousPeriodic: "
                    + indentText(e.getStackTrace().toString(), "    "));
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
            Robot.drivetrain.resetEncoders();
        } catch (Exception e) {
            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopInit: "
                    + indentText(e.getStackTrace().toString(), "    "));
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
            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopPeriodic: "
                    + indentText(e.getStackTrace().toString(), "    "));
        }
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
