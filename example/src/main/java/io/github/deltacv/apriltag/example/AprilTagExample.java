package io.github.deltacv.apriltag.example;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagDetectorJNI;

import java.io.Reader;
import java.util.List;
import java.util.Scanner;

public class AprilTagExample {
    static {
        OpenCV.loadLocally();
        System.out.println("OpenCV loaded");
    }

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    public static double fx = 578.272;
    public static double fy = 578.272;
    public static double cx = 402.145;
    public static double cy = 221.506;

    // UNITS ARE METERS
    public static double TAG_SIZE = 0.166;

    public static void main(String[] args) {
        long detector = AprilTagDetectorJNI.createApriltagDetector(AprilTagDetectorJNI.TagFamily.TAG_36h11.string, 3, 3);
        System.out.println("Created apriltag detector " + detector);

        VideoCapture camera = new VideoCapture();

        System.out.print("Enter a camera index: ");
        int index = 0;//new Scanner(System.in).nextInt();

        camera.open(index);
        if(!camera.isOpened()) {
            System.out.println("Could not open camera " + index);
            return;
        }

        Mat image = new Mat();
        camera.read(image);
        if(image.empty()) {
            System.out.println("Could not open camera " + index + " (image was empty)");
            return;
        }

        System.out.println("Opened camera " + index);

        while(camera.isOpened()) {
            camera.read(image);
            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);

            List<AprilTagDetection> detections = AprilTagDetectorJNI.runAprilTagDetectorSimple(detector, image, TAG_SIZE, fx, fy, cx, cy);

            for(AprilTagDetection detection : detections) {
                System.out.printf("detected apriltag %d at x=%.2f y=%.2f z=%.2f\n", detection.id, detection.pose.x, detection.pose.y, detection.pose.z);
            }
        }

        // not necessary since our program will exit at this point, just to show off the apriltag api
        AprilTagDetectorJNI.releaseApriltagDetector(detector);
    }
}
