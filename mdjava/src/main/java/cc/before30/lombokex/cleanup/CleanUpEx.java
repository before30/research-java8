package cc.before30.lombokex.cleanup;

import lombok.Cleanup;

import java.io.*;

/**
 * Created by before30 on 15/01/2017.
 */
public class CleanUpEx {

    public static void main(String[] args) throws IOException {
        @Cleanup InputStream in = new FileInputStream("mdjava/build.gradle");
        @Cleanup OutputStream out = new FileOutputStream("/tmp/copied_build_gradle");

        byte[] b = new byte[1024];
        while(true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }
}
