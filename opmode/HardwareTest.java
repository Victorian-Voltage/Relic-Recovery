package org.firstinspires.ftc.teamcode.vv7797.opmode;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "Hardware Test")
public class HardwareTest extends LinearOpMode {
    private ColorSensor snsColorJewel;
    private DistanceSensor snsRangeLatchWall, snsRangeLatchDivider, snsRangeBack, snsRangeFront, snsRangeRampBottom, snsRangeRampTop;
    private BNO055IMU gyro;
    private Orientation orientation;

    @Override public void runOpMode() {
        telemetry.setMsTransmissionInterval(10);
        telemetry.addData("Initialization", "Contacting sensors...");
        telemetry.update();

        snsColorJewel = hardwareMap.colorSensor.get("snsColorJewel");

        snsRangeRampBottom = hardwareMap.get(DistanceSensor.class, "snsColorRampBottom");
        snsRangeRampTop = hardwareMap.get(DistanceSensor.class, "snsColorRampTop");
        snsRangeBack = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "snsRangeBack");
        snsRangeFront = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "snsRangeFront");
        snsRangeLatchWall = hardwareMap.get(DistanceSensor.class, "snsRangeLatchWall");
        snsRangeLatchDivider = hardwareMap.get(DistanceSensor.class, "snsRangeLatchDivider");

        telemetry.addData("Initialization", "Contacting gyro...");
        telemetry.update();

        BNO055IMU.Parameters gyroParams = new BNO055IMU.Parameters();
        gyroParams.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyroParams.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        gyroParams.calibrationDataFile = "BNO055IMUCalibration.json";
        gyroParams.loggingEnabled = true;
        gyroParams.loggingTag = "IMU";
        gyroParams.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(gyroParams);

        telemetry.addData("Initialization", "Done");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            update();
        }
    }

    private void update() {
        telemetry.addData("JEWEL COLOR", "<%d, %d, %d>", snsColorJewel.red(), snsColorJewel.green(), snsColorJewel.blue());
        telemetry.addData("RAMP BOT RANGE", "%.6f in.", snsRangeRampBottom.getDistance(DistanceUnit.INCH));
        telemetry.addData("RAMP TOP RANGE", "%.6f in.", snsRangeRampTop.getDistance(DistanceUnit.INCH));
        telemetry.addData("LATCH WALL RANGE", "%.6f in.", snsRangeLatchWall.getDistance(DistanceUnit.INCH));
        telemetry.addData("LATCH DIV RANGE", "%.6f in.", snsRangeLatchDivider.getDistance(DistanceUnit.INCH));
        telemetry.addData("BACK RANGE", "%.6f in.", snsRangeBack.getDistance(DistanceUnit.INCH));
        telemetry.addData("FRONT RANGE", "%.6f in.", snsRangeFront.getDistance(DistanceUnit.INCH));
        telemetry.addData("ORIENTATION", "<%.2f, %.2f, %.2f>", orientation.firstAngle, orientation.secondAngle, orientation.thirdAngle);
        telemetry.update();
    }
}
