package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "MR Color Test")
@Disabled
public class MRColorTest extends LinearOpMode {
    private ColorSensor snsColor;

    @Override public void runOpMode() {
        //snsRange = hardwareMap.get(DistanceSensor.class, "snsColorJewel");
        snsColor = hardwareMap.colorSensor.get("snsColorJewel");
        waitForStart();

        telemetry.setMsTransmissionInterval(10);

        while (opModeIsActive()) {
            // telemetry.addData("Range", "%.2f", snsRange.getDistance(DistanceUnit.INCH));
            telemetry.addData("Color", "r=%d b=%d g=%d", snsColor.red(), snsColor.green(), snsColor.blue());
            telemetry.update();
        }
    }
}
