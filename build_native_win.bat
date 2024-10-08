@echo off
mkdir native_build
cd native_build
cmake -T ClangCL ..
cmake --build .