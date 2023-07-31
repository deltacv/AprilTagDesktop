/*
 * Copyright (c) 2021 Sebastian Erives
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NativeLibLoader {

    private NativeLibLoader() { }

    public enum OperatingSystem {
        WINDOWS,
        LINUX,
        MACOS_X86,
	MACOS_SILICON,
        UNKNOWN
    }

    public static OperatingSystem getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
	String arch = System.getProperty("os.arch");

        if (osName.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (osName.contains("nux")) {
            return OperatingSystem.LINUX;
        } else if (osName.contains("mac") || osName.contains("darwin")) {
	    if(arch.equals("aarch64") || arch.contains("arch") || arch.contains("arm")) {
                return OperatingSystem.MACOS_SILICON;
	    } else {
                return OperatingSystem.MACOS_X86;
	    }
        }

        return OperatingSystem.UNKNOWN;
    }
	
	private static boolean hasBeenLoaded = false;

    public static void load() {
		if(hasBeenLoaded) {
			return;
		}
		
        OperatingSystem OS = getOS();
        String osName = System.getProperty("os.name").toLowerCase();
        String extension = "";

        switch(OS) {
            case WINDOWS:
                extension = ".dll";
                break;
            case LINUX:
                extension = ".so";
                break;
            case MACOS_X86:
                extension = ".dylib";
                break;
            case MACOS_SILICON:
                extension = "_arm.dylib";
                break;
            case UNKNOWN:
                throw new UnsupportedOperationException("The " + osName + " platform couldn't be recognized, therefore the april tag desktop plugin is not supported");
        }

        String name =  "libapriltag" + extension;

        String tmpDir = System.getProperty("java.io.tmpdir");
        File tempFile = new File(tmpDir + File.separator + name);

        try {
            Files.copy(NativeLibLoader.class.getResourceAsStream("/" + name), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch(NullPointerException e) {
            throw new UnsupportedOperationException("A native lib could not be found for the " + osName + " platform, this probably means that the AprilTag plugin is not supported", e);
        } catch(Exception e) {
            throw new RuntimeException("Error while extracting native library", e);
        }

        try {
            System.load(tempFile.getAbsolutePath());
        } catch(Throwable e) {
            throw new UnsupportedOperationException("The native library failed to link, which probably means that the AprilTag plugin is not supported in the " + osName + " platform", e);
        }
		
        hasBeenLoaded = true;
    }

}
