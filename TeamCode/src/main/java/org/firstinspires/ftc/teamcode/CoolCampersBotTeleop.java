package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Camp Bot TeleOp")
public class CoolCampersBotTeleop extends LinearOpMode{

    private DcMotor rightDrive;
    private DcMotor leftDrive;
    private DcMotor intake;
    private CRServo rightServo;
    private CRServo leftServo;
    private boolean prevA;
    private boolean prevB;


    @Override
    public void runOpMode() throws InterruptedException {
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        rightServo.setDirection(DcMotorSimple.Direction.FORWARD);
        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        prevA = gamepad1.a;
        prevB = gamepad1.a;

        waitForStart();

        while(opModeIsActive()) {
            double rDrivePower = (-gamepad1.left_stick_y - gamepad1.left_stick_x);
            double IDrivePower = (-gamepad1.left_stick_y + gamepad1.left_stick_x);


            double maxMagnitude = Math.max(Math.abs(rDrivePower), Math.abs(IDrivePower));

            if (maxMagnitude>1){
                rDrivePower=rDrivePower/maxMagnitude;
                IDrivePower=IDrivePower/maxMagnitude;
            }

            rightDrive.setPower(rDrivePower);
            leftDrive.setPower(IDrivePower);

            if (gamepad1.a && prevA != gamepad1.a) {
                double power = intake.getPower();
                if(power == 0) intake.setPower(1);
                else intake.setPower(0);
            }



            if (gamepad1.b && prevB != gamepad1.b) {
                double power = rightServo.getPower();
                if ( power == 0){
                    rightServo.setPower(1);
                    leftServo.setPower(1);
                }
                else{
                    rightServo.setPower(0);
                    leftServo.setPower(0);
                }
            }
            prevA = gamepad1.a;
            prevB = gamepad1.b;


        }

        rightDrive.setPower(0);
        leftDrive.setPower(0);
        intake.setPower(0);
        rightServo.setPower(0);
        leftServo.setPower(0);
    }
}