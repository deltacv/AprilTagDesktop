/*
 * Copyright (c) 2023 Sebastian Erives
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.deltacv.apriltag;

class SystemUtil {

    public enum OperatingSystem {
        WINDOWS, LINUX, MACOS, UNKNOWN
    }

    public enum Architecture {
        AMD64("amd64"),
        ARM64("arm64"),
        AAARCH64("aarch64"), // uh, same thing as ARM64, but linux uses this
        UNKNOWN("");

        public final String suffix;

        Architecture(String suffix) {
            this.suffix = suffix;
        }
    }

    private SystemUtil() { }

    public static OperatingSystem getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (osName.contains("nux")) {
            return OperatingSystem.LINUX;
        } else if (osName.contains("mac") || osName.contains("darwin")) {
            return OperatingSystem.MACOS;
        }

        return OperatingSystem.UNKNOWN;
    }

    public static Architecture getArch() {
        String arch = System.getProperty("os.arch").toLowerCase();

        if(arch.equals("aarch64") || arch.contains("arch") || arch.contains("arm")) {
            if(SystemUtil.getOS() == OperatingSystem.LINUX) {
                return Architecture.AAARCH64;
            } else {
                return Architecture.ARM64;
            }
        } else if(arch.equals("amd64") || arch.contains("64") || arch.contains("x86_64")) {
            return Architecture.AMD64;
        } else {
            return Architecture.UNKNOWN;
        }
    }

}
