package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "PID Test")
@Disabled
public class PIDTest extends LinearOpMode {
    private DcMotorImpl drvFrontLeft, drvFrontRight, drvBackLeft, drvBackRight;
    private DcMotorImpl[] drivetrain;

    @Override public void runOpMode() {
        drvFrontLeft = (DcMotorImplEx)hardwareMap.dcMotor.get("drvFrontLeft");
        drvFrontRight = (DcMotorImplEx)hardwareMap.dcMotor.get("drvFrontRight");
        drvBackLeft = (DcMotorImplEx)hardwareMap.dcMotor.get("drvBackLeft");
        drvBackRight = (DcMotorImplEx)hardwareMap.dcMotor.get("drvBackRight");
        drivetrain = new DcMotorImpl[] { drvFrontLeft, drvFrontRight, drvBackLeft, drvBackRight };
        waitForStart();

        telemetry.setMsTransmissionInterval(10);

        while (opModeIsActive()) {
            for (DcMotorImpl motor : drivetrain) {
                PIDCoefficients enc = ((DcMotorImplEx)motor).getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
                PIDCoefficients rtp = ((DcMotorImplEx)motor).getPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION);
                telemetry.addData("enc", "k=%.2f i=%.2f d=%.2f", enc.p, enc.i, enc.d);
                telemetry.addData("rtp", "k=%.2f i=%.2f d=%.2f", rtp.p, rtp.i, rtp.d);
            }
            telemetry.update();
        }
    }
}
