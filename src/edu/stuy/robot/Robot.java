package edu.stuy.robot;

import edu.stuy.robot.subsystems.Drivetrain;
import edu.stuy.util.TegraSocketReader;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

    //public static BlueSignalLight cvSignalLight;

    Command autonomousCommand;

    private static TegraSocketReader tegraReader;
    private static Thread tegraThread;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        try {
            drivetrain = new Drivetrain();
            oi = new OI();
            SmartDashboard.putNumber("maxCV", 1.0);
            SmartDashboard.putNumber("minCV", 0.5);
            SmartDashboard.putBoolean("printTegraData", false);
            drivetrain.setDrivetrainBrakeMode(true);
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

    private void ensureTegraBeingRead() {
        if (tegraThread != null) {
            return;
        }
        System.out.println("Initializing a TegraSocketReader");
        tegraReader = new TegraSocketReader();
        System.out.println("Setting up thread");
        tegraThread = new Thread(tegraReader);
        // Call .start(), rather than .run(), to run it in a separate thread
        tegraThread.start();
        System.out.println("Done!");
    }

    private void dontReadFromTegra() {
        if (tegraThread != null && tegraThread.isAlive()) {
            tegraThread.interrupt();
        }
        tegraReader = null;
        tegraThread = null;
    }

    public static double[] getLatestTegraVector() {
        if (tegraReader == null) {
            System.out.println("TEGRAREADER IS NULL, THOUGH DATA WAS REQUESTED");
            return null;
        }
        return tegraReader.getMostRecent();
    }

    public static boolean tegraIsConnected() {
        return tegraReader != null && tegraReader.isConnected();
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
//        try {
            // This makes sure that the autonomous stops running when
            // teleop starts running. If you want the autonomous to
            // continue until interrupted by another command, remove
            // this line or comment it out.
            if (autonomousCommand != null) {
                autonomousCommand.cancel();
            }
            Robot.drivetrain.resetEncoders();

            // Set up Tegra reading thread
            ensureTegraBeingRead();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (tegraThread != null && tegraThread.isAlive()) {
                        tegraThread.interrupt();
                    }
                }
            });
            System.out.println("Added shutdown hook for Tegra thread interruption");//*/
//        } catch (Exception e) {
//            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopInit: ");
//            e.printStackTrace();
//            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            //        + indentText(e.toString() + e.fillInStackTrace(), "    "));
//        }
    }

    /**
     * This function is called when the disabled button is hit. You can use it
     * to reset subsystems before shutting down.
     */
    public void disabledInit() {
        dontReadFromTegra();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//        try {
            Scheduler.getInstance().run();
//        } catch (Exception e) {
//            System.out.println("AN EXCEPTION WAS CAUGHT IN teleopPeriodic: ");
//            e.printStackTrace();
//            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        }
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
