package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Sensor Test")
@Disabled
public class UltrasonicTest extends LinearOpMode {
    private UltrasonicSensor sensor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override public void runOpMode() {
        sensor = hardwareMap.get(UltrasonicSensor.class, "snsLego");

        waitForStart();
        telemetry.setAutoClear(false);

        double last = 0;

        while (opModeIsActive()) {
            if (runtime.time()-last > 0.125) {
                double reading;
                do {
                    reading = sensor.getUltrasonicLevel();
                } while (reading <= 0);
                telemetry.addData("Reading", "%.4f", reading);
                telemetry.update();
                last = runtime.time();
            }
        }
    }
}
