# cmake -G "MinGW Makefiles" -D CMAKE_C_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/gcc.exe" -D CMAKE_CXX_COMPILER="C:/Program Files/mingw-w64/x86_64-8.1.0-posix-seh-rt_v6-rev0/mingw64/bin/g++.exe" .

# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

project("AprilTagDesktop")

set (CMAKE_CXX_STANDARD 11)

if(NOT DEFINED ENV{OPENCV452_BUILD_TREE})
    message( FATAL_ERROR "The OPENCV452_BUILD_TREE env var is not set!" )
endif()

string(FIND "$ENV{OPENCV452_BUILD_TREE}" "\\" "STRFIND_OUT")
if(${STRFIND_OUT} GREATER "-1")
    message( FATAL_ERROR "ERROR: env var OPENCV452_BUILD_TREE is defined using '\\' path separator, must use '/' " )
endif()

if(NOT DEFINED ENV{OPENCV452_SOURCE_TREE})
    message( FATAL_ERROR "The OPENCV452_SOURCE_TREE env var is not set!" )
endif()

string(FIND "$ENV{OPENCV452_SOURCE_TREE}" "\\" "STRFIND_OUT")
if(${STRFIND_OUT} GREATER "-1")
    message( FATAL_ERROR "ERROR: env var OPENCV452_SOURCE_TREE is defined using '\\' path separator, must use '/' " )
endif()

if(NOT DEFINED ENV{JAVA_HOME_CMAKE})
    message( FATAL_ERROR "The JAVA_HOME_CMAKE env var is not set!" )
endif()

string(FIND "$ENV{JAVA_HOME_CMAKE}" "\\" "STRFIND_OUT")
if(${STRFIND_OUT} GREATER "-1")
    message( FATAL_ERROR "ERROR: env var JAVA_HOME_CMAKE is defined using '\\' path separator, must use '/' " )
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
			 
# The shared library extension we need is platform specific
if (WIN32)
    set(SHARED_LIB_EXTENSION "dll.a") # .dll.a is (I think?) equivalent to .lib, which is a "helper" thing required for linking to a dll on windows because reasons...
    set(IMPORT_TYPE "IMPORTED_IMPLIB")
    set(WIN32_OPENCV_VERSION "452")
    set(JNI_PLATFORM_INCL "win32")
    set(CMAKE_SHARED_LINKER_FLAGS  "-static-libgcc -static-libstdc++ -static") # Statically link mingw32 libs so the user doesn't need to have them installed

elseif (APPLE)
    set(SHARED_LIB_EXTENSION "dylib")
    set(IMPORT_TYPE "IMPORTED_LOCATION")
    set(WIN32_OPENCV_VERSION "")
	set(JNI_PLATFORM_INCL "darwin")

elseif (UNIX)
    set(SHARED_LIB_EXTENSION "so")
    set(IMPORT_TYPE "IMPORTED_LOCATION")
    set(WIN32_OPENCV_VERSION "")
	set(JNI_PLATFORM_INCL "linux")

endif (WIN32)


add_library(opencv_core SHARED IMPORTED)
set_target_properties(opencv_core PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_core${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

add_library(opencv_imgproc SHARED IMPORTED)
set_target_properties(opencv_imgproc PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_imgproc${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

add_library(opencv_imgcodecs SHARED IMPORTED)
set_target_properties(opencv_imgcodecs PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_imgcodecs${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

add_library(opencv_highgui SHARED IMPORTED)
set_target_properties(opencv_highgui PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_highgui${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

add_library(opencv_videoio SHARED IMPORTED)
set_target_properties(opencv_videoio PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_videoio${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

add_library(opencv_video SHARED IMPORTED)
set_target_properties(opencv_video PROPERTIES ${IMPORT_TYPE}
        $ENV{OPENCV452_BUILD_TREE}/lib/libopencv_video${WIN32_OPENCV_VERSION}.${SHARED_LIB_EXTENSION})

# Specifies a path to native header files.
include_directories(
                     src/main/cpp/apriltag/include
                     src/main/cpp/apriltag_common/include
                     #${CMAKE_JAVA_}
                     $ENV{JAVA_HOME_CMAKE}/include
                     $ENV{JAVA_HOME_CMAKE}/include/${JNI_PLATFORM_INCL}
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
        opencv_imgcodecs
)