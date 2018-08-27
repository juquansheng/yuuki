package com.malaxiaoyugan.yuukiadmin.controller;



import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping(value = "/file")
public class UploadController {

    private final FastFileStorageClient fastFileStorageClient;

    @Autowired
    public UploadController(FastFileStorageClient fastFileStorageClient) {
        this.fastFileStorageClient = fastFileStorageClient;
    }


    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/imgUpload")
    @ResponseBody
    public ResponseVO upload(MultipartFile file) {
        try {


            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file
                            .getSize(),
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf
                            (".") + 1, file.getOriginalFilename().length()), null);
            System.out.println(storePath.getFullPath());
            String flieName = storePath.getFullPath().substring(17, storePath.getFullPath().length());
            System.out.println(flieName);
            String filePath = "http://file.malaxiaoyugan.com/group1/M00/00/00/" + flieName;
            return TTBFResultUtil.success( "图片上传成功", filePath);
        } catch (IOException e) {
            return TTBFResultUtil.error("服务器异常");
        }
    }

}
