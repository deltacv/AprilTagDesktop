package io.github.deltacv.apriltag;

class SystemUtil {

    public enum OperatingSystem {
        WINDOWS, LINUX, MACOS, UNKNOWN
    }

    public enum Architecture {
        AMD64("amd64"), ARM64("arm64"), UNKNOWN("");

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
            return Architecture.ARM64;
        } else if(arch.equals("amd64") || arch.contains("64") || arch.contains("x86_64")) {
            return Architecture.AMD64;
        } else {
            return Architecture.UNKNOWN;
        }
    }

}
