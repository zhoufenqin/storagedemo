package com.example;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import com.azure.storage.file.share.ShareServiceClientBuilder;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class fileController {
    @Autowired
    private ApplicationContext applicationContext;
    @GetMapping
    public String writeFile() throws IOException {
        // if the file content length([offset, offset + len)) is shorter than the data length,
        // then the "The range specified is invalid for the current size of the resource." will occurred
        String data = "test file storage";

        ShareServiceClientBuilder shareStorageClientBuilder = applicationContext
            .getBean(ShareServiceClientBuilder.class);
        ShareServiceClient shareServiceClient = shareStorageClientBuilder.buildClient();
        // TODO: update your own container and file here.
        ShareClient shareClient = shareServiceClient.getShareClient("fenzhofile");
        ShareFileClient shareFileClient = shareClient.getFileClient("file.txt");
        OutputStream outputStream = shareFileClient.getFileOutputStream();
        // I write from offset = 0
        // For example, if the file content in portal file share is "hello", current data is "test file storage", then the error will occur
        outputStream.write(data.getBytes());
        return "file updated";
    }

}
