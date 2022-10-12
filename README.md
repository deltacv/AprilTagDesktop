# AprilTagDesktop
Port of EOCV-AprilTag-Plugin to the desktop EOCV-Sim

<img src="https://github.com/deltacv/AprilTagDesktop/blob/main/doc/img_apriltag_eocvsim.png?raw=true" width="80%" height="80%"></img>

# Supported platforms

  - Windows x86_64 (tested in Windows 10)
  - MacOS x86_64 (not tested)
  - Linux x86_64 (tested in Ubuntu 20.04)
  
# Usage
See the [example pipeline](https://github.com/deltacv/EOCV-Sim/blob/main/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/AprilTagDetectionPipeline.java) in the EOCV-Sim repo to learn how to use the library.
Some parts have been changed from the original sample to make it work in EOCV-Sim. Make sure to check the [samples folder in the original repo](https://github.com/OpenFTC/EOCV-AprilTag-Plugin/tree/master/examples/src/main/java/org/firstinspires/ftc/teamcode) to learn how to use it within the FTC SDK!

# Adding as a dependency to any project

### Gradle
   ```groovy
   repositories {
       maven { url 'https://jitpack.com' } //add jitpack as a maven repo 
   }
   
   dependencies {
      implementation 'com.github.deltacv:AprilTagDesktop:1.1.0' //add the dependency
   }
   ```
   
### Maven
   
   Adding the jitpack maven repo
   ```xml
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
   ```
   
   Adding the dependecy
   ```xml
    <dependency>
	    <groupId>com.github.deltacv</groupId>
	    <artifactId>AprilTagDesktop</artifactId>
	    <version>1.1.0</version>
	</dependency>
   ```

# Credits
Almost all the source code in this repository was extracted from the [EOCV-AprilTag-Plugin](https://github.com/OpenFTC/EOCV-AprilTag-Plugin) repo, made by Windwoes (NPE) from the OpenFTC team. The Linux & Windows native libraries were also compiled by them. Many thanks for developing the plugin and providing us with the libraries!
