package jnishell32;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@TargetClass(className = "jnishell32.Shell32")
@Platforms({Platform.DARWIN.class, Platform.LINUX.class})
final class Shell32Substitutions {

    @Substitute
    public static String knownFolderPath(String rfid) {
        throw new RuntimeException("Not available on this platform");
    }

}
