# cmake -G "MinGW Makefiles" -D CMAKE_C_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/gcc.exe" -D CMAKE_CXX_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/g++.exe" .

# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.5)
set(CMAKE_CXX_STANDARD 20)

if(NOT DEFINED ARCH)
    if(DEFINED CMAKE_OSX_ARCHITECTURES)
	set(ARCH CMAKE_OSX_ARCHITECTURES)
    else()
    	set(ARCH CMAKE_SYSTEM_PROCESSOR)
     endif()
else()
    string(APPEND "_" ARCH)
endif()

project("apriltag$ARCH")

message(STATUS $ENV{JAVA_HOME})

set(JAVA_AWT_LIBRARY NotNeeded)
set(JAVA_JVM_LIBRARY NotNeeded)
set(JAVA_AWT_INCLUDE_PATH NotNeeded)

find_package(JNI REQUIRED)

add_library( # Specifies the name of the library.
             apriltag

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/AprilTagDetectorJNI.cpp
             src/main/cpp/AprilTagDetectionJNI.cpp

             src/main/cpp/apriltag/common/g2d.c
             src/main/cpp/apriltag/common/getopt.c
             src/main/cpp/apriltag/common/homography.c
             src/main/cpp/apriltag/common/image_u8.c
             src/main/cpp/apriltag/common/image_u8x3.c
             src/main/cpp/apriltag/common/image_u8x4.c
             src/main/cpp/apriltag/common/matd.c
             src/main/cpp/apriltag/common/pam.c
             src/main/cpp/apriltag/common/pjpeg.c
             src/main/cpp/apriltag/common/pjpeg-idct.c
             src/main/cpp/apriltag/common/pnm.c
             src/main/cpp/apriltag/common/string_util.c
             src/main/cpp/apriltag/common/pthreads_cross.cpp
             src/main/cpp/apriltag/common/svd22.c
             src/main/cpp/apriltag/common/time_util.c
             src/main/cpp/apriltag/common/unionfind.c
             src/main/cpp/apriltag/common/workerpool.c
             src/main/cpp/apriltag/common/zarray.c
             src/main/cpp/apriltag/common/zhash.c
             src/main/cpp/apriltag/common/zmaxheap.c
             src/main/cpp/apriltag/apriltag.c
             src/main/cpp/apriltag/apriltag_pose.c
             src/main/cpp/apriltag/apriltag_quad_thresh.c
             src/main/cpp/apriltag/tag16h5.c
             src/main/cpp/apriltag/tag25h9.c
             src/main/cpp/apriltag/tag36h11.c
             src/main/cpp/apriltag/tagCircle21h7.c
             src/main/cpp/apriltag/tagStandard41h12.c
)


# set(CMAKE_SHARED_LINKER_FLAGS "-static-libgcc -static-libstdc++ -static")
			 
# Specifies a path to native header files.
include_directories(
                     src/main/cpp/apriltag
                     src/main/cpp/apriltag/common
		     ${JNI_INCLUDE_DIRS}
)
