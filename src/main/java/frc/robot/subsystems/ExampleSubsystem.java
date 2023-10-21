// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import javax.swing.text.StyledEditorKit.BoldAction;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.commands.ExampleCommand;

public class ExampleSubsystem extends SubsystemBase {

  private final XboxController controller;

  private final CANSparkMax motor1;
  private final CANSparkMax motor2;

  private final WPI_TalonSRX talon;

  private final AnalogPotentiometer sensor3;

  private Boolean started = false;
  private final Timer timer;

  private final double distance = 0.266;
  
  public ExampleSubsystem() {
    controller = new XboxController(0);

    motor1 = new CANSparkMax(3, MotorType.kBrushless);
    motor2 = new CANSparkMax(16, MotorType.kBrushless);

    motor2.follow(motor1);

    talon = new WPI_TalonSRX(8);
    talon.configFactoryDefault();

    sensor3 = new AnalogPotentiometer(2);

    timer = new Timer();
  }

  @Override
  public void periodic() {
    Run();
  }

  /*
   * If the a button is pressed, it starts the motor and talon
   * If it has been running for more than a second, it stops because
   * one second is enough to launch a ball
   */
  private void Run() {
    manageTalon();

    if (controller.getAButton()) {
      started = true;
      timer.start();
    }

    if (timer.get() >= 1) {
      started = false;
      timer.stop();
      timer.reset();
      motor1.set(0);
    }

    if (!started) return;

    motor1.set(-0.3);
  }

  /*
   * Manages the talon motor
   * You must set it to speed 0 to stop it
   * Runs the talon untill it detects a ball at the launcher
   * It needs to run when launching because the ball is not close enough for it to be launched,
   * and the launcher can only launch the ball while it is spinning
   * This gives it enough of a delay to launch the ball
   */
  private void manageTalon() {
    if (sensor3.get() <= distance || started) {
      RunTalon(-0.3);
      return;
    }

    RunTalon(0);
  }

  private void RunTalon(double speed) {
    talon.set(speed);
  }

  @Override
  public void simulationPeriodic() {}
  public boolean exampleCondition() { return true; }
  public CommandBase exampleMethodCommand() { return runOnce(() -> {}); }
}
