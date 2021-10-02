# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

project("AprilTagDesktop")

set (CMAKE_CXX_STANDARD 11)

if(NOT DEFINED ENV{OPENCV452_BUILD_TREE})
    message( FATAL_ERROR "The OPENCV452_BUILD_TREE env var is not set!" )
endif()

if(NOT DEFINED ENV{OPENCV452_SOURCE_TREE})
    message( FATAL_ERROR "The OPENCV452_SOURCE_TREE env var is not set!" )
endif()

if(NOT DEFINED ENV{JAVA_HOME})
    message( FATAL_ERROR "The JAVA_HOME env var is not set!" )
endif()

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
             apriltag

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/AprilTagDetectorJNI.cpp
             src/main/cpp/AprilTagDetectionJNI.cpp
             src/main/cpp/apriltag_common/src/g2d.c
             src/main/cpp/apriltag_common/src/getopt.c
             src/main/cpp/apriltag_common/src/homography.c
             src/main/cpp/apriltag_common/src/image_u8.c
             src/main/cpp/apriltag_common/src/image_u8x3.c
             src/main/cpp/apriltag_common/src/image_u8x4.c
             src/main/cpp/apriltag_common/src/matd.c
             src/main/cpp/apriltag_common/src/pam.c
             src/main/cpp/apriltag_common/src/pjpeg.c
             src/main/cpp/apriltag_common/src/pjpeg-idct.c
             src/main/cpp/apriltag_common/src/pnm.c
             src/main/cpp/apriltag_common/src/string_util.c
             src/main/cpp/apriltag_common/src/svd22.c
             src/main/cpp/apriltag_common/src/time_util.c
             src/main/cpp/apriltag_common/src/unionfind.c
             src/main/cpp/apriltag_common/src/workerpool.c
             src/main/cpp/apriltag_common/src/zarray.c
             src/main/cpp/apriltag_common/src/zhash.c
             src/main/cpp/apriltag_common/src/zmaxheap.c
             src/main/cpp/apriltag/src/apriltag.c
             src/main/cpp/apriltag/src/apriltag_pose.c
             src/main/cpp/apriltag/src/apriltag_quad_thresh.c
             src/main/cpp/apriltag/src/tag16h5.c
             src/main/cpp/apriltag/src/tag25h9.c
             src/main/cpp/apriltag/src/tag36h11.c
             src/main/cpp/apriltag/src/tagCircle21h7.c
             src/main/cpp/apriltag/src/tagStandard41h12.c)

add_library(opencv_core SHARED IMPORTED)
set_target_properties(opencv_core PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_core.so)

add_library(opencv_imgproc SHARED IMPORTED)
set_target_properties(opencv_imgproc PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_imgproc.so)

add_library(opencv_highgui SHARED IMPORTED)
set_target_properties(opencv_highgui PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_highgui.so)

add_library(opencv_videoio SHARED IMPORTED)
set_target_properties(opencv_videoio PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_videoio.so)

add_library(opencv_video SHARED IMPORTED)
set_target_properties(opencv_video PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_video.so)

add_library(cv2 SHARED IMPORTED)
set_target_properties(cv2 PROPERTIES IMPORTED_LOCATION
        $ENV{OPENCV452_BUILD_TREE}/lib/cv2.so)

# Specifies a path to native header files.
include_directories(
                     src/main/cpp/apriltag/include
                     src/main/cpp/apriltag_common/include
                     ${CMAKE_JAVA_}
                     $ENV{JAVA_HOME}/include
                     $ENV{JAVA_HOME}/include/linux
                     $ENV{OPENCV452_SOURCE_TREE}/modules/cv2/include
                     $ENV{OPENCV452_SOURCE_TREE}/modules/highgui/include
                     $ENV{OPENCV452_SOURCE_TREE}/modules/imgproc/include
                     $ENV{OPENCV452_SOURCE_TREE}/modules/videoio/include
                     $ENV{OPENCV452_SOURCE_TREE}/modules/video/include
                     $ENV{OPENCV452_SOURCE_TREE}/modules/core/include
                     $ENV{OPENCV452_BUILD_TREE})

# Specify path to prebuilt object

# linking libs
target_link_libraries(
        apriltag
        opencv_core
        opencv_video
        opencv_videoio
        opencv_imgproc
        opencv_highgui
        cv2
)
