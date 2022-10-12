package org.firstinspires.ftc.robotcontroller.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.math.*;

@Autonomous(name = "testAuto", group = "Autonomous")
public class testAuto extends LinearOpMode {

    public Robot robot = new Robot();
    private ColorSensor color2;

    private void autoDrive(double power, double rightInches, double leftInches){

        double wheelDiameterMM = 75;
        double circumferenceMM = wheelDiameterMM * Math.PI;
        double driveGearReduction = 30.21;
        double ticksPerMotorRev = 28;
        double ticksPerWheelRev = ticksPerMotorRev * driveGearReduction;
        double ticksPerMM = ticksPerWheelRev / circumferenceMM;
        double ticksPerInch = ticksPerMM * 25.4;

        int frontRightTarget = (int)(rightInches * ticksPerInch); // turns distance into encoder values, the inches is the 30
        int backRightTarget = (int)(rightInches * ticksPerInch);

        int frontLeftTarget = (int)(leftInches * ticksPerInch);
        int backLeftTarget = (int)(leftInches * ticksPerInch);

        robot.frontLeft.setTargetPosition(frontLeftTarget);
        robot.frontRight.setTargetPosition(frontRightTarget);
        robot.backLeft.setTargetPosition(backLeftTarget);
        robot.backRight.setTargetPosition(backRightTarget);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRight.setPower(power);
        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(power);

        while (opModeIsActive() && (robot.frontLeft.isBusy() || robot.backRight.isBusy())) {
            telemetry.addData("EncoderFL: ", robot.frontLeft.getCurrentPosition());
            telemetry.addData("EncoderFR: ", robot.frontRight.getCurrentPosition());
            telemetry.addData("EncoderBL: ", robot.backLeft.getCurrentPosition());
            telemetry.addData("EncoderBR: ", robot.backRight.getCurrentPosition());
        }

        // set motor power back to 0
        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);


        int red = color2.red();
        int blue = color2.blue();
        int green = color2.green();

//      Red side (Joe) , Blue side (katie, micheal and seven)

        waitForStart();

        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        color2.enableLed(false);

        autoDrive(.7, 24.25, 24.25);

        color2.enableLed(true);

        sleep(2000);

        if ((red > blue) & (red > green)){ //local 3
            robot.frontLeft.setPower(1);
            robot.backLeft.setPower(1);
            robot.frontRight.setPower(-1);
            robot.backRight.setPower(-1);

            sleep(1000);

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);
            robot.backLeft.setPower(0);

            autoDrive(.8, 23.5, 23.5);
        }
        if ((blue > red) & (blue > green)){ // local 2
            autoDrive(.7, 20, 20);
        }
        if ((green > red) & (green > blue)){ // local 1
            robot.frontLeft.setPower(-1);
            robot.backLeft.setPower(-1);
            robot.frontRight.setPower(1);
            robot.backRight.setPower(1);

            sleep(1000);

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);
            robot.backLeft.setPower(0);

            autoDrive(.8, 23.5, 23.5);
        }

    }
}

