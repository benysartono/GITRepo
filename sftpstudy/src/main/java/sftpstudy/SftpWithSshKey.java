package sftpstudy;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.IdentityInfo;
import org.apache.commons.vfs2.provider.sftp.IdentityProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

/**
 * This class uses Apache's VFS project to connect to remote SFTP server using
 * username and SSH key based authentication.
 * <p>
 * Maven Dependency: https://mvnrepository.com/artifact/org.apache.commons/commons-vfs2
 * Apache Commons-VFS Project: https://commons.apache.org/proper/commons-vfs/
 */
public class SftpWithSshKey {

    public static final String SFTP = "sftp";

    /**
     * Holds instance of remote SFTP folder.
     */
    private FileObject fileObject;

    /**
     * Direct instantiation not allowed. Use {@link #connect(String, Integer, String, File, String)}
     * method to initiate connection.
     *
     * @param fileObject
     */
    private SftpWithSshKey(FileObject fileObject) {
        this.fileObject = fileObject;
    }

    /**
     * Initiate connection to remote SFTP server
     *
     * @param host
     * @param port
     * @param userName
     * @param sshKey     - file containing the SSH private Key
     * @param passphrase - Passphrase of SSH private key
     * @return
     */
    public static SftpWithSshKey connect(String host,
                                         Integer port,
                                         String userName,
                                         File sshKey,
                                         String passphrase) throws URISyntaxException, FileSystemException {
        URI sftpUri = createSftpUri(host, port, userName);

        FileSystemOptions options = new FileSystemOptions();
        SftpFileSystemConfigBuilder sftpConfigBuilder = SftpFileSystemConfigBuilder.getInstance();
        IdentityProvider identityInfo = new IdentityInfo(sshKey, passphrase.getBytes());
        sftpConfigBuilder.setIdentityProvider(options, identityInfo);

        return new SftpWithSshKey(VFS.getManager().resolveFile(sftpUri.toString(), options));
    }



    /**
     * Navigate to specific folder in SFTP
     *
     * @param folderPath
     * @return
     */
    public SftpWithSshKey cd(String folderPath) throws FileSystemException {
        if (this.fileObject == null) {
            throw new RuntimeException("SFTP connection not initiated.");
        }
        FileObject newFolder = this.fileObject.resolveFile(folderPath);
        if (!newFolder.exists() || !newFolder.isFolder()) {


            throw new RuntimeException("Path does not exist or is not a folder.");
        }
        this.fileObject = newFolder;
        return this;
    }

    /**
     * Create SFTP URI
     *
     * @param host
     * @param port
     * @param userName
     * @return
     */
    private static URI createSftpUri(String host,
                                     Integer port,
                                     String userName) throws URISyntaxException {
        return new URI(SFTP, userName, host, Objects.requireNonNullElse(port, -1), null, null, null);
    }
    
    /**
     * List all file names. Only file names are listed, not the folders.
     *
     * @return
     */
    public List<String> listFiles() throws FileSystemException {
        return ofNullable(this.fileObject.findFiles(SELECT_FILES))
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(FileObject::getName)
                .map(FileName::getBaseName)
                .collect(Collectors.toList());
    }    
}


