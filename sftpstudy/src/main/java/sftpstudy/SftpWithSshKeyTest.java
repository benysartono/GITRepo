package sftpstudy;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.vfs2.FileSystemException;

/**
 * Sample usage of {@link SftpWithSshKey} class
 */
class SftpWithSshKeyTest {

    /**
     * Test that all files in movies folder are listed.
     *
     * @throws URISyntaxException
     * @throws FileSystemException
     */
    public void listAllFiles() throws URISyntaxException, FileSystemException {


        File privateKey = new File("~/.ssh/ida_rsa");
        List<String> files = SftpWithSshKey.connect("localhost", 22, "admin", privateKey, "passphrase")
                .cd("./documents")
                .listFiles();
        Assertions.assertEquals(2, files.size());
    }
}
