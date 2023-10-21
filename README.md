# Controls Test

Hi!

The comments in the example subsystem mainly focus on why each function exists, and gives a basic description. This is because the why is the most important part of code. There are some basic comments on the what, but those are less important

## Functions

There are two main functions that are used, `Run` and `manageTalon`. These functions are the main logic of the file

### Run

The main logic, running the launcher and other funtions. Controls the launcher and the launch setup code.

### Manage Talon

Manages the talon motor. Runs the talon when there is no ball or when launching, and stops the talon when it is not needed.