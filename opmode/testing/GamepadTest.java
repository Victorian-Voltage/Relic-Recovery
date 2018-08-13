package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.vv7797.opmode.control.VictorianGamepad;

@TeleOp(name = "Gamepad Test")
@Disabled
public class GamepadTest extends OpMode {
    VictorianGamepad pad1, pad2;

    @Override public void init() {
        pad1 = new VictorianGamepad(gamepad1);
        pad2 = new VictorianGamepad(gamepad2);

        telemetry.setAutoClear(false);
    }

    @Override public void loop() {
        pad1.update();
        pad2.update();

        if (pad1.a_pressed)
            telemetry.addData("a@1", "tapped");
        else if (pad1.a_released)
            telemetry.addData("a@1", "released");

        if (pad2.a_pressed)
            telemetry.addData("a@2", "tapped");
        else if (pad2.a_released)
            telemetry.addData("a@2", "released");

        telemetry.update();
    }
}
