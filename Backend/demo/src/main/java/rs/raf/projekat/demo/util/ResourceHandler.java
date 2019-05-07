package rs.raf.projekat.demo.util;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class ResourceHandler {

    //TODO: change my full path.
    private static final String FULL_PATH = "C:\\Users\\Strahinja\\Desktop\\demo\\src\\main\\resources\\images\\";

    public String uploadImage(String imageAsBase64String, String imageName) {
        try {
            byte[] imageByte= Base64.decodeBase64(imageAsBase64String);
            String name = generateImageName(imageName);

            String imageUrl = name;

            String fullPath;
            fullPath = FULL_PATH + name;

            new FileOutputStream(fullPath).write(imageByte);
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateImageName (String imageName) {
        imageName = imageName.replaceAll(" ","_");
        return imageName + "_" + System.currentTimeMillis() + ".png";
    }

}

