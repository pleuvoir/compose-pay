package io.github.pleuvoir.pay.common.test.builder;

import io.github.pleuvoir.pay.common.builder.FtpUploadBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FtpUploadBuilderTest {

    @Test
    public void testUpload() throws FileNotFoundException, IOException {

        FtpUploadBuilder.create("ip", 21).
                setUser("ftptest", "f!kxboeWneg8*D1C").
                setRemoteDirectory("/ftp/test")
                .upload(new File("/Users/pleuvoir/Desktop/test.txt"));

    }
}
