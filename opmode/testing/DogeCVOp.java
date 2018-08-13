package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.CryptoboxDetector;
import com.disnodeteam.dogecv.detectors.GlyphDetector;
import java.util.Arrays;

@Autonomous(name = "CV Test")
@Disabled
public class DogeCVOp extends LinearOpMode {
    private CryptoboxDetector cbox;
    private GlyphDetector glyph;

    @Override public void runOpMode() throws InterruptedException {
        cbox = new CryptoboxDetector();
        cbox.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        cbox.downScaleFactor = 0.5;
        cbox.detectionMode = CryptoboxDetector.CryptoboxDetectionMode.RED;
        cbox.speed = CryptoboxDetector.CryptoboxSpeed.SLOW;
        cbox.rotateMat = true;
        cbox.enable();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Column left", cbox.getCryptoBoxLeftPosition());
            telemetry.addData("Column center", cbox.getCryptoBoxCenterPosition());
            telemetry.addData("Column right", cbox.getCryptoBoxRightPosition());

            int sep = -1;

            try {
                int total = 0;
                int[] positions = cbox.getDividerPositions();
                for (int i = 0; i < positions.length - 1; i++)
                    total += positions[i + 1] - positions[i];
                sep = total / (positions.length - 1);

                telemetry.addData("Dividers", Arrays.toString(positions));
            } catch (NullPointerException e) {}

            telemetry.addData("Separation", sep);
            telemetry.update();
        }
    }
}
