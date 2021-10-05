# AprilTagDesktop
Port of EOCV-AprilTag-Plugin to the desktop EOCV-Sim

# Supported platforms
For now, the only supported platform consists of

  - Linux x86_64 (tested in Ubuntu 20.04)
  
We are working to support the rest of the platforms in the future.

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
      implementation 'com.github.deltacv:AprilTagDesktop:1.0.0' //add the dependency
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
	    <version>1.0.0</version>
	</dependency>
   ```

# Credits
Almost all the source code in this repository was extracted from the [EOCV-AprilTag-Plugin](https://github.com/OpenFTC/EOCV-AprilTag-Plugin) repo, made by Windwoes (NPE) from the OpenFTC team. The Linux native library was also compiled by them. Many thanks to them for developing the plugin and providing us with the library!
