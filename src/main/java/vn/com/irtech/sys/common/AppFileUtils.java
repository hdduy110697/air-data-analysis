package vn.com.irtech.sys.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author:  Admin
 * @Date: 2019/12/15 23:44
 */
public class AppFileUtils {

    /**
     * File upload save path Default value
     */
    public static String UPLOAD_PATH="D:/upload/";

    static {
        //Read the storage address of the configuration file
        InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties=new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property = properties.getProperty("filepath");
        if(null!=property) {
            UPLOAD_PATH=property;
        }
    }

    /**
     * Get the new name based on the old name of the file
     * @param oldName Old file name
     * @return The new name consists of a 32-bit random number plus a picture suffix
     */
    public static String createNewFileName(String oldName) {
        //Get file name suffix
        String stuff=oldName.substring(oldName.lastIndexOf("."), oldName.length());
        //The UUID is spliced ​​with the file name suffix to generate a new file name. The generated UUID is 32 bits.
        return IdUtil.simpleUUID().toUpperCase()+stuff;
    }

    /**
     * document dowload
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1,Constructing file objects
        File file=new File(UPLOAD_PATH, path);
        if(file.exists()) {
            //The file to be downloaded, encapsulated in byte []
            byte[] bytes=null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Create an object that encapsulates the response headers
            HttpHeaders header=new HttpHeaders();
            //Package response content type (APPLICATION_OCTET_STREAM response content is not limited)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //Create ResponseEntity object
            ResponseEntity<Object> entity= new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }

    /**
     * Change the name of this picture. Remove _temp
     * @param goodsimg
     * @return
     */
    public static String renameFile(String goodsimg) {
        File file = new File(UPLOAD_PATH,goodsimg);
        String replace = goodsimg.replace("_temp","");
        if (file.exists()){
            file.renameTo(new File(UPLOAD_PATH,replace));
        }
        return replace;
    }

    /**
     * Delete picture based on old path
     * @param oldPath
     */
    public static void removeFileByPath(String oldPath) {
        //The path of the picture is not the path of the default picture
        if (!oldPath.equals(Constast.DEFAULT_IMG_GOODS)){
            File file = new File(UPLOAD_PATH,oldPath);
            if (file.exists()){
                file.delete();
            }
        }
    }


}
