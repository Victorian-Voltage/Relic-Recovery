package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Debruyn_854042 on 4/11/2018.
 */

@TeleOp(name = "PIDTester")
@Disabled
public class PIDTester extends OpMode {
    private DcMotorEx dtFrontLeft, dtFrontRight, dtBackLeft, dtBackRight;
    private DcMotorEx[] driveMotors;
    private double[] pidCoefficients = new double[] {10,0,0};
    private ElapsedTime runtime = new ElapsedTime();
    private int pidIndex = 0;
    private final double DRIVE_CAP = 0.6;
    private double LFP, LRP, RFP, RRP, driveAngle, DRIVE_MOD = DRIVE_CAP;
    private boolean limiter, cTLimiter, cTIncrease, cTDecrease;

    @Override
    public void init() {
        dtFrontLeft = (DcMotorEx)hardwareMap.dcMotor.get("drvFrontLeft");
        dtFrontRight = (DcMotorEx)hardwareMap.dcMotor.get("drvFrontRight");
        dtBackLeft = (DcMotorEx)hardwareMap.dcMotor.get("drvBackLeft");
        dtBackRight = (DcMotorEx)hardwareMap.dcMotor.get("drvBackRight");

        dtFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dtFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dtBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dtBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dtFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        dtBackLeft.setDirection(DcMotor.Direction.REVERSE);


        driveAngle = Math.PI/4;

        driveMotors = new DcMotorEx[] { dtFrontLeft, dtFrontRight, dtBackLeft, dtBackRight };

        cTIncrease = true;
        cTDecrease = true;

        telemetry.addData("Status", "Hardware initialized");
    }

    @Override
    public void start() { runtime.reset(); }

    @Override
    public void loop() {
        setPIDCoefficients();
        setLimiter();
        victorianDrive();
        setDrivePower();
        sendTelemetry();
    }

    @Override
    public void stop() {
        dtFrontLeft.setPower(0);
        dtFrontRight.setPower(0);
        dtBackLeft.setPower(0);
        dtBackRight.setPower(0);
    }

    // Analog Controls for setting correct drive train power
    private void victorianDrive() {
        // Collect controller inputs
        double lsx = gamepad1.left_stick_x, lsy = gamepad1.left_stick_y;
        double rsx = gamepad1.right_stick_x;
        double epsilon = Math.sqrt(2);
        double r = Math.hypot(lsx, lsy);
        double stickAngle = Math.atan2(lsy, -lsx);
        // Set "front" of the robot
        if(dPadPressed())
            setDriveAngle();
        // Calculate power angle derived from analog stick angle
        double powerAngle = stickAngle - driveAngle;
        // Calculate dual zone analog turn power
        double turn = getDualZonePower(rsx);
        // Calculate sin and cos power values
        double cos = r*Math.cos(powerAngle);
        double sin = r*Math.sin(powerAngle);
        // Ramp up power values
        double cosPow = epsilon*cos;
        double sinPow = epsilon*sin;
        // Calculate individual motor power values
        LFP = Range.clip(cosPow-turn, -1.0, 1.0);
        LRP = Range.clip(sinPow-turn, -1.0, 1.0);
        RFP = Range.clip(sinPow+turn, -1.0, 1.0);
        RRP = Range.clip(cosPow+turn, -1.0, 1.0);
        // Set power limiter
        LFP*=DRIVE_MOD;
        LRP*=DRIVE_MOD;
        RFP*=DRIVE_MOD;
        RRP*=DRIVE_MOD;
    }
    // Sets the drive train power
    private void setDrivePower() {
        dtFrontLeft.setPower(LFP);
        dtFrontRight.setPower(RFP);
        dtBackLeft.setPower(LRP);
        dtBackRight.setPower(RRP);
    }
    // Returns proper turn power using dual zone
    private double getDualZonePower(double t) {
        if(Math.abs(t) < .5)
            return .8*t;
        else
            return Math.signum(t)*(1.2*Math.abs(t)-0.2);
    }
    // Returns Boolean (determine if D-Pad is being pressed)
    private boolean dPadPressed() {
        if (!gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_left && !gamepad1.dpad_right)
            return false;
        return true;
    }
    // Sets the "front" of the robot
    private void setDriveAngle() {
        if(gamepad1.dpad_up)
            driveAngle = Math.PI/4;
        else if(gamepad1.dpad_left)
            driveAngle = 3*(Math.PI/4);
        else if(gamepad1.dpad_down)
            driveAngle = -3*(Math.PI/4);
        else if(gamepad1.dpad_right)
            driveAngle = -(Math.PI/4);
    }
    // Sets the PID coefficients
    private void setPIDCoefficients() {
        // Determine which coefficient to change
        boolean x = gamepad1.x, y = gamepad1.y, b = gamepad1.b;
        if(x)
            pidIndex = 0;
        if(y)
            pidIndex = 1;
        if(b)
            pidIndex = 2;

        // Change PID values
        boolean lb = gamepad1.left_bumper, rb = gamepad1.right_bumper;
        if(lb && cTIncrease) {
            cTIncrease = false;
            pidCoefficients[pidIndex]-=1;
        }
        else if(!lb)
            cTIncrease = true;

        if(rb && cTDecrease) {
            cTDecrease = false;
            pidCoefficients[pidIndex]+=1;
        }
        else if(!rb)
            cTDecrease = true;

        // Set PID coefficients
        for(DcMotorEx motor : driveMotors)
            motor.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(pidCoefficients[0],pidCoefficients[1],pidCoefficients[2]));

    }

    // Sets thee motor restrictions of the robot's motors
    private void setLimiter() {
        boolean ls = gamepad1.left_stick_button, rs = gamepad1.right_stick_button;
        // Both stick buttons toggle intake mods
        if((ls || rs) && cTLimiter) {
            limiter = !limiter;
            cTLimiter = false;
            // Set Restrictions
            DRIVE_MOD = limiter ? DRIVE_CAP : 1;
        }
        else if(!ls && !rs)
            cTLimiter = true;
    }
    // Send Telemetry
    private void sendTelemetry() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front Motors", "Left (%.2f) | Right (%.2f)", LFP, RFP);
        telemetry.addData("Rear Motors", "Left (%.2f) | Right (%.2f)", LRP, RRP);
        telemetry.addLine()
                .addData("Motors","")
                .addData("P",dtBackLeft.getPIDCoefficients((DcMotor.RunMode.RUN_USING_ENCODER)).p)
                .addData("I",dtBackLeft.getPIDCoefficients((DcMotor.RunMode.RUN_USING_ENCODER)).i)
                .addData("D",dtBackLeft.getPIDCoefficients((DcMotor.RunMode.RUN_USING_ENCODER)).d);
        telemetry.addData("Limiter",limiter);
        telemetry.update();
    }
}
