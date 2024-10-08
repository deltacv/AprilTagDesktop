# cmake -G "MinGW Makefiles" -D CMAKE_C_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/gcc.exe" -D CMAKE_CXX_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/g++.exe" .

# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.5)
set(CMAKE_CXX_STANDARD 11)

project("apriltag")

if(NOT DEFINED ARCH)
    if(DEFINED CMAKE_OSX_ARCHITECTURES AND NOT "${CMAKE_OSX_ARCHITECTURES}" STREQUAL "")
	set(ARCH ${CMAKE_OSX_ARCHITECTURES})
    else()
    	set(ARCH ${CMAKE_SYSTEM_PROCESSOR})
    endif()
endif()

string(TOLOWER ${ARCH} ARCH)

if("${ARCH}" STREQUAL "x86_64")
    set(ARCH "amd64")
endif()

message(STATUS "Target architecture: ${ARCH}")

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

             apriltag/common/g2d.c
             apriltag/common/getopt.c
             apriltag/common/homography.c
             apriltag/common/image_u8.c
             apriltag/common/image_u8x3.c
             apriltag/common/image_u8x4.c
             apriltag/common/matd.c
             apriltag/common/pam.c
             apriltag/common/pjpeg.c
             apriltag/common/pjpeg-idct.c
             apriltag/common/pnm.c
             apriltag/common/string_util.c
             apriltag/common/svd22.c
             apriltag/common/time_util.c
             apriltag/common/unionfind.c
             apriltag/common/pthreads_cross.c # EOCV-AprilTag-Plugin does not add this, but it is needed for windows...
             apriltag/common/workerpool.c
             apriltag/common/zarray.c
             apriltag/common/zhash.c
             apriltag/common/zmaxheap.c
             apriltag/apriltag.c
             apriltag/apriltag_pose.c
             apriltag/apriltag_quad_thresh.c
             apriltag/tag16h5.c
             apriltag/tag25h9.c
             apriltag/tag36h11.c
             apriltag/tagCircle21h7.c
             apriltag/tagStandard41h12.c)


# VROOOOOOOM
target_compile_options(apriltag PRIVATE
        "$<$<CONFIG:RELEASE>:-O3>"
        "$<$<CONFIG:DEBUG>:-O3>"
        )

set_target_properties(apriltag PROPERTIES OUTPUT_NAME "apriltag_${ARCH}")

include_directories(
    apriltag
    apriltag/common
    ${JNI_INCLUDE_DIRS}
)