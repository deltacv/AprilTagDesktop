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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class AprilTagLibLoader {

    private AprilTagLibLoader() {
    }

    private static boolean hasBeenLoaded = false;

    public static void load() {
        if (hasBeenLoaded) {
            return;
        }

        SystemUtil.OperatingSystem OS = SystemUtil.getOS();
        String osName = System.getProperty("os.name").toLowerCase();

        String prefix = "lib";
        String extension = "";

        String arch = "";

        if(SystemUtil.getArch() != SystemUtil.Architecture.UNKNOWN) {
            arch = "_" + SystemUtil.getArch().suffix;
        }

        switch (OS) {
            case WINDOWS:
                extension = arch + ".dll";
                prefix = ""; // windows dlls do not start by "lib"
                break;
            case LINUX:
                extension = arch + ".so";
                break;
            case MACOS:
                extension = arch + ".dylib";
                break;
            case UNKNOWN:
                throw new UnsupportedOperationException("The " + osName + " platform couldn't be recognized, therefore the april tag desktop plugin is not supported");
        }

        String osArch = osName + " (" + arch + ")";

        String name = prefix + "apriltag" + extension;
        String versionedName = Integer.toUnsignedString(Build.versionString.hashCode()) +  name;

        String tmpDir = System.getProperty("java.io.tmpdir");

        File tempDeltacv = new File(tmpDir + File.separator + "deltacv");
        File tempFile = new File(tempDeltacv, versionedName);

        if(!tempDeltacv.exists()) {
            tempDeltacv.mkdir();
        }

        if(!tempFile.exists()) {
            try {
                Files.copy(Objects.requireNonNull(AprilTagLibLoader.class.getResourceAsStream("/" + name)), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (NullPointerException e) {
                throw new UnsupportedOperationException("Library could not be found for the " + osArch + " platform, AprilTag plugin is not supported. Tried " + name, e);
            } catch (Exception e) {
                throw new RuntimeException("Error while extracting library", e);
            }
        }

        try {
            System.load(tempFile.getAbsolutePath());
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Library failed to link, AprilTag plugin is not supported in the " + osArch + " platform", e);
        }

        hasBeenLoaded = true;
    }

}
