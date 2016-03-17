package edu.stuy.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap extends PhysicalConstants {

    // Gamepad ports
    int DRIVER_GAMEPAD = 0;
    int OPERATOR_GAMEPAD = 1;

    // Drivetrain CANTalon channels
    int FRONT_RIGHT_MOTOR_CHANNEL = 1;
    int REAR_RIGHT_MOTOR_CHANNEL = 2;
    int REAR_LEFT_MOTOR_CHANNEL = 3;
    int FRONT_LEFT_MOTOR_CHANNEL = 4;

    // CANTalon channels for other subsystems
    int HOPPER_MOTOR_CHANNEL = 5;
    int ACQUIRER_MOTOR_CHANNEL = 6;
    int SHOOTER_MOTOR_CHANNEL = 7;
    int DROPDOWN_MOTOR_CHANNEL = 8;

    // Solenoid ports
    int HOOD_SOLENOID_CHANNEL = 6;
    int GEAR_SHIFT_CHANNEL = 3;

    // Tuneable threshold for current (used in gear shifting for drivetrain)
    int GEAR_SHIFT_THRESHOLD = 40;

    // DropDown Constants
    double DROP_DOWN_DEADBAND = 0.01;

    // Hood Constants
    boolean HOOD_UP_POSITION = false;
    boolean HOOD_DOWN_POSITION = true;

    // Hopper Constants
    double HOPPER_SENSOR_THRESHOLD = 1.0;

    // Digital IO Ports
    int LEFT_ENCODER_CHANNEL_A = 0;
    int LEFT_ENCODER_CHANNEL_B = 1;
    int RIGHT_ENCODER_CHANNEL_A = 2;
    int RIGHT_ENCODER_CHANNEL_B = 3;
    int SHOOTER_ENCODER_A_CHANNEL = 4;
    int SHOOTER_ENCODER_B_CHANNEL = 5;
    int LIMIT_SWITCH_CHANNEL = 6;
    int FLASHLIGHT_CHANNEL = 7;

    // Analog ports
    int ACQUIRER_POTENTIOMETER_CHANNEL = 0;
    int HOPPER_SENSOR_CHANNEL = 3;

    // Potentiometer
    double INITIAL_VOLTAGE = 287.5; // Equal to 0 degrees
    double CONVERSION_FACTOR = 11.25;
    double EPSILON = 0.0005;

    // Physical constants
    int SHOOTER_WHEEL_DIAMETER = 4;
    double SHOOTER_ENCODER_MAXSPEED = 3600.0;
    int DIO_ENCODER_PULSES_PER_REVOLUTION = 360;
    int DRIVETRAIN_WHEEL_DIAMETER = 8;
    double DISTANCE_BETWEEN_SONAR = 1.0;

    // Sonar
    int SONAR_ERROR_MARGIN = 5;
    double DISTANCE_TO_WALL = 14 * 12; // 14ft in inches

    // PID tuning values
    double PID_MAX_ROBOT_SPEED = 0.75;
    double GYRO_P = 1.0;
    double GYRO_I = 1.0;
    double GYRO_D = 1.0;

    // Smart Dashboard
    String SHOOTER_SPEED_LABEL = "Shooter Speed";

    // Auton
    double ROCK_WALL_CURRENT_THRESHOLD = 0.0;
    int ARM_CROSSING_OBSTACLE_ANGLE = 50;

    double SLOT_ANGLE_TO_GOAL_1 = 39.614; // UNUSED (lowbar)
    double SLOT_ANGLE_TO_GOAL_2 = 29.535;
    double SLOT_ANGLE_TO_GOAL_3 = 16.987;
    double SLOT_ANGLE_TO_GOAL_4 = -12.228;
    double SLOT_ANGLE_TO_GOAL_5 = -25.539;

    // CV
    double MAX_DEGREES_OFF_AUTO_AIMING = 2;
    int CAMERA_FRAME_PX_WIDTH = 1280;
    int CAMERA_FRAME_PX_HEIGHT = 720;
    int CAMERA_VIEWING_ANGLE_X = 60;
    double IDEAL_VERTICAL_OFFSET_AUTO_AIMING = 70;
    double MAX_VERTICAL_PX_OFF_AUTO_AIMING = 20;

    // Signal Lights
    int SIGNAL_LIGHT_RED_PORT = 4;
    int SIGNAL_LIGHT_YELLOW_PORT = 5;
    int SIGNAL_LIGHT_BLUE_PORT = 6;

    // Operator IDs
    int JONAH_ID = 0;
    int YUBIN_ID = 1;


    // PCM IDs
    int PCM_1 = 0;
    int PCM_2 = 1;

    // CAN device IDs
    int DRIVE_FRONT_LEFT_ID = 1;
    int DRIVE_REAR_LEFT_ID = 2;
    int DRIVE_FRONT_RIGHT_ID = 3;
    int DRIVE_REAR_RIGHT_ID = 4;
    int ACQUIRER_LEFT_ROLLER_ID = 5;
    int ACQUIRER_RIGHT_ROLLER_ID = 6;
    int LIFT_MOTOR_ID = 7;

    // Solenoid Ports
    int SOLENOID_ARMS_LONG_OUT = 0;
    int SOLENOID_ARMS_LONG_IN = 1;
    int SOLENOID_ARMS_SHORT_OUT = 3;
    int SOLENOID_ARMS_SHORT_IN = 2;
    int LIFT_SOLENOID_BRAKE = 5;
    int CANNER_SOLENOID_OPEN = 1;
    int CANNER_SOLENOID_CLOSE = 2;
    int TOTE_KNOCKER_SOLENOID_ID = 0;

    // Joystick Ports
    int DRIVER_PAD_PORT = 0;
    int OPERATOR_PAD_PORT = 1;

    // Digital IO Ports
    int DRIVETRAIN_ENCODER_LEFT_CHANNEL_A = 2;
    int DRIVETRAIN_ENCODER_LEFT_CHANNEL_B = 3;
    int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_A = 0;
    int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_B = 1;
    int LIFT_LIMIT_SWITCH_CHANNEL = 4;
    int LIFT_ENCODER_CHANNEL_A = 5;
    int LIFT_ENCODER_CHANNEL_B = 6;

    // Analog Channels
    int DRIVETRAIN_GYRO_CHANNEL = 0;

    // Auton Constants
    double DRIVETRAIN_ROTATE_THRESHOLD_DEGREES = 5.0;
    double AUTON_DRIVETRAIN_TIMEOUT = 15.0;
    double AUTON_DRIVETRAIN_TOTE_WIDTH_TIMEOUT = 3.0;
    double AUTON_LIFT_TIMEOUT = 3.5;
    double AUTON_LIFT_DISTANCE = 10.0;
    double AUTON_TOTE_COLLISION_AVOIDANCE_OFFSET = 9.0;

    // Auton Mobility DriveForward
    double AUTON_DRIVE_BACKWARD_SCORING_PLATFORM_INCHES = AUTON_ZONE_WIDTH / 2 + 12;
    double AUTON_DRIVE_BACKWARD_SCORING_PLATFORM_TIMEOUT = 5.2;
    double AUTON_DRIVE_FORWARD_DRIVER_SIDE_INCHES = TOTE_SET_TO_LANDMARK_INCHES + TOTE_WIDTH + 12;
    double AUTON_DRIVE_FORWARD_DRIVER_SIDE_TIMEOUT = 15;

    // Displayed on Smart Dashboard
    String INCHES_LABEL = "If you are using Setting 4 or 5, then input number of inches";

    // Encoder Distances
    double LIFT_ENCODER_RECYCLING_BIN_HEIGHT = RECYCLING_BIN_HEIGHT + TOTE_HEIGHT + 4.0;
    double LIFT_ENCODER_MAX_HEIGHT = 50.0;
    double LIFT_SPROCKET_CIRCUMFERENCE = 3 * Math.PI;
    double DRIVETRAIN_WHEEL_CIRCUMFERENCE = 6 * Math.PI;
    double PULSES_PER_REVOLUTION = 250;
    double LIFT_ENCODER_INCHES_PER_PULSE = LIFT_SPROCKET_CIRCUMFERENCE / PULSES_PER_REVOLUTION;
    double DRIVETRAIN_ENCODER_INCHES_PER_PULSE = DRIVETRAIN_WHEEL_CIRCUMFERENCE / PULSES_PER_REVOLUTION;
    double DRIVETRAIN_ENCODER_ROTATE_CIRCUMFERENCE = 2 * Math.PI * ROBOT_FRONT_WHEEL_TO_WHEEL_DISTANCE;

    // PID Constants
    // MAYBE WE'LL BE NICE AND USE THESE??? UNTESTED!!!
    double DRIVE_ROTATE_P = .01;
    double DRIVE_ROTATE_I = 0;
    double DRIVE_ROTATE_D = 0;

    // Tank drive squares the inputs given to Robot.drivetrain.tankDrive in
    // DrivetrainTankDriveCommand, so to produce 0.5 as the speed multiplier we
    // take the square root of 0.5
    double DRIVETRAIN_SLOWNESS_FACTOR = Math.sqrt(0.5);
    double ACQUIRER_ROLLER_SPEED = 0.75;
    double RAMP_CONSTANT = 0.5;
}
