package org.firstinspires.ftc.teamcode.vv7797.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Vuforia Test")
@Disabled
public class VuforiaTest extends LinearOpMode {
    private OpenGLMatrix lastLocation = null;
    private VuforiaTrackable relicTemplate;
    private VuforiaTrackables relicTrackables;
    private VuforiaLocalizer vuforia;

    @Override public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters vufParameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        vufParameters.vuforiaLicenseKey = "AQvrVpP/////AAAAGauRujoAs0gLpZI/MZh9rk1y2u54G7gQhPA5nvoC4Qit6yi5xypatqoAZ2Gdu3xd/2h+b9LshTtiv4yz9Acm7xgXseQR/GF/wAB3SBfEGXSCE5QFkSft5kizzRw7I5S89pULXjxHFkZFSn/Yb5Dlb/IdG7IhIF9FqGw93TLFalkw3BDjY1dsZ51+nPcXKfDWYBX10UzLyDrjlUiChvtbMT67kCDFWsUUsUxaSbexM6rbK/eBR+nFRcEh5KMs7PHNCAgssnwdpkxYa2s5XqEvXIXAZiIiGJO9xKrHSYSrSrMvoTx3b7KQsSbwNdsyJMfEuwficrD2fDbSEGGcqGmScHRCnLLbmXNFzNENNm9hObsU";
        vufParameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.createVuforiaLocalizer(vufParameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);

        waitForStart();
        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            telemetry.addData("Saw", "" + vuMark);
            telemetry.update();
        }
    }
}
