package pl.dernovyi.picture_hosting.service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StorageService {
    @Value("${endpoint}")
    private String endpoint;
    @Value("${nameLibrary}")
    private String nameLibrary;
    public URI saveToStorage(MultipartFile multipartFile){
        URI uri;
        CloudBlobContainer image = getCloudBlobContainer();

        StringBuilder name = new  StringBuilder()
                .append(UUID.randomUUID().toString())
                .append(".")
                .append(getTypeFile(multipartFile));

        CloudBlockBlob blockBlobReference = null;
        try {
            blockBlobReference = image.getBlockBlobReference(Objects.requireNonNull( name.toString()));
            blockBlobReference.upload(multipartFile.getInputStream(), -1);
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }

        uri = blockBlobReference.getUri();
        return uri;
    }
    private CloudBlobContainer getCloudBlobContainer()  {
        CloudStorageAccount account = null;
        try {
            account = CloudStorageAccount
                    .parse(endpoint);
        } catch (InvalidKeyException | URISyntaxException e) {
            e.printStackTrace();
        }
        CloudBlobClient client = account.createCloudBlobClient();
        try {
            return client.getContainerReference(nameLibrary);
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTypeFile(@RequestParam("file") MultipartFile multipartFile) {
        Pattern pattern = Pattern.compile("[^\\/]*$");
        Matcher matcher = pattern.matcher(multipartFile.getContentType());

        String ext = null;
        if (matcher.find()) {
            ext = matcher.group();

        }
        return ext;
    }
}
