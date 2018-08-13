package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Autonomous(name = "Touch Sensor Test")
@Disabled
public class TouchSensorTest extends LinearOpMode {
    private DigitalChannel snsTouchProbe;

    @Override public void runOpMode() {
        snsTouchProbe = hardwareMap.digitalChannel.get("snsTouchProbe");
        snsTouchProbe.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("State", "" + snsTouchProbe.getState());
            telemetry.update();
        }
    }
}
