package vn.com.irtech.sys.controller;

import cn.hutool.core.date.DateUtil;
import vn.com.irtech.sys.common.AppFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:  Admin
 * @Date: 2019/12/15 23:46
 */
@RestController
@RequestMapping("file")
public class FileController {

    /**
     * File Upload
     * @param mf
     * @return
     */
    @RequestMapping("uploadFile")
    public Map<String,Object> uploadFile(MultipartFile mf) {
        //1.Get file name
        String oldName = mf.getOriginalFilename();
        //2.Generate a new file name based on the old file name
        String newName=AppFileUtils.createNewFileName(oldName);
        //3.Get string of current date
        String dirName= DateUtil.format(new Date(), "yyyy-MM-dd");
        //4.Constructing folders
        File dirFile=new File(AppFileUtils.UPLOAD_PATH,dirName);
        //5.Determine if the current folder exists
        if(!dirFile.exists()) {
            //Create new folder if it doesn't exist
            dirFile.mkdirs();
        }
        //6.Constructing file objects
        File file=new File(dirFile, newName+"_temp");
        //7.Write the picture information in mf to file
        try {
            mf.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("path",dirName+"/"+newName+"_temp");
        return map;
    }

    /**
     * Image download
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path){
        return AppFileUtils.createResponseEntity(path);
    }

}
