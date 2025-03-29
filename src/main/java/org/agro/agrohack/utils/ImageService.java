package org.agro.agrohack.utils;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFSBucket;

    public String uploadImageToStorage(MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        ObjectId fileId = gridFsTemplate.store(image.getInputStream(), fileName, MediaType.IMAGE_JPEG_VALUE);

        return fileId.toString();
    }

    public Resource getImage(String fileId) throws FileNotFoundException {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (file != null) {
            return new InputStreamResource(gridFSBucket.openDownloadStream(file.getId()));
        }
        throw new FileNotFoundException("File not found with ID: " + fileId);
    }
    public Resource getImages(List<String> fileIds) throws FileNotFoundException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);

            for (String fileId : fileIds) {
                GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
                if (file == null) {
                    continue; // or throw an exception
                }

                try (var inputStream = gridFSBucket.openDownloadStream(file.getObjectId())) {
                    ZipEntry zipEntry = new ZipEntry(file.getFilename());
                    zipOut.putNextEntry(zipEntry);

                    inputStream.transferTo(zipOut);
                    zipOut.closeEntry();
                }
            }

            zipOut.finish();
            return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            throw new FileNotFoundException("Error fetching image files");
        }
    }
}
