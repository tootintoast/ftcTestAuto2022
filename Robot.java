package org.firstinspires.ftc.robotcontroller.Auto;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot{

public DcMotor frontRight; // setting the dcMotors to private since this is going to be our object
public DcMotor backRight;
public DcMotor frontLeft;
public DcMotor backLeft;
public DcMotor pulleyLeft;
public DcMotor pulleyRight;
private Servo arm;
public ColorSensor color; // Setting up a color sensor we may use



public void init(HardwareMap hwMap) { // mapping each motor so the phone can read it
        frontLeft = hwMap.dcMotor.get("front_left");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight = hwMap.dcMotor.get("front_right");
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft = hwMap.dcMotor.get("back_left");
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight = hwMap.dcMotor.get("back_right");
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pulleyLeft = hwMap.dcMotor.get("pulley_Left");
        pulleyLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pulleyRight = hwMap.dcMotor.get("pulleyRight");
        pulleyRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm = hwMap.servo.get("arm");

        color = hwMap.colorSensor.get("color");


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // setting the motors so the code works and the motors don't spin out
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        }

        void MecDrive(double horizontal, double vertical, double turn){ // setting the mecdrive code so the robot can drive
// we can use void to add a section onto our code, just like how we did constructors before
        frontRight.setPower(vertical - turn - horizontal); //using the variables we had before, we can use math to figure out the power to give the motors.
        frontLeft.setPower(vertical + turn + horizontal);
        backLeft.setPower(vertical + turn - horizontal);
        backRight.setPower(vertical - turn + horizontal);
        }
        void PulleySystem(double lPower, double rPower, double motorSpeed) {
        if (lPower == rPower) {
        pulleyRight.setPower(motorSpeed);
        pulleyLeft.setPower(motorSpeed);
        }
        }
        void armSystem(double servoPosition, double servoSpeed) {
        }
        private void autoDrive(double power, double rightInches, double leftInches){

        double wheelDiameterMM = 75;
        double circumferenceMM = wheelDiameterMM * Math.PI;
        double driveGearReduction = 30.21;
        double ticksPerMotorRev = 28;
        double ticksPerWheelRev = ticksPerMotorRev * driveGearReduction;
        double ticksPerMM = ticksPerWheelRev / circumferenceMM;
        double ticksPerInch = ticksPerMM * 25.4;

        int frontRightTarget = (int)(rightInches * ticksPerInch); // turns distance into encoder values, the inches is the 30
        int backRightTarget = (int)((rightInches - 11.5) * ticksPerInch);

        int frontLeftTarget = (int)(leftInches * ticksPerInch);
        int backLeftTarget = (int)((leftInches - 11.5) * ticksPerInch);

        frontLeft.setTargetPosition(frontLeftTarget);
        frontRight.setTargetPosition(frontRightTarget);
        backLeft.setTargetPosition(backLeftTarget);
        backRight.setTargetPosition(backRightTarget);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        }
}