package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Rev Range Test")
@Disabled
public class RevRangeTest extends LinearOpMode {
    private DistanceSensor snsRange;
    private ColorSensor snsColor;

    @Override public void runOpMode() {
        //snsRange = hardwareMap.get(DistanceSensor.class, "snsColorJewel");
        snsColor = hardwareMap.get(ColorSensor.class, "snsRangeRampBottom");
        waitForStart();

        telemetry.setMsTransmissionInterval(10);

        while (opModeIsActive()) {
            // telemetry.addData("Range", "%.2f", snsRange.getDistance(DistanceUnit.INCH));
            telemetry.addData("Color", "%d %d %d", snsColor.red(), snsColor.green(), snsColor.blue());
            telemetry.update();
        }
    }
}
