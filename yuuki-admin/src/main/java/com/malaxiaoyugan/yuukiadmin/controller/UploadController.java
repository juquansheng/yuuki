package com.malaxiaoyugan.yuukiadmin.controller;



import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.malaxiaoyugan.yuukiadmin.utils.Result;
import com.malaxiaoyugan.yuukiadmin.utils.TTBFHttpUtils;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping(value = "/file")
public class UploadController {

    private  FastFileStorageClient fastFileStorageClient;

    @Autowired
    public UploadController(FastFileStorageClient fastFileStorageClient) {
        this.fastFileStorageClient = fastFileStorageClient;
    }



    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/fileupload")
    @ResponseBody
    public ResponseVO upload(MultipartFile file) {
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file
                            .getSize(),
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf
                            (".") + 1, file.getOriginalFilename().length()), null);
            String flieName = storePath.getFullPath().substring(17, storePath.getFullPath().length());
            String filePath = "http://file.malaxiaoyugan.com/group1/M00/00/00/" + flieName;
            return TTBFResultUtil.success( "图片上传成功", filePath);
        } catch (IOException e) {
            return TTBFResultUtil.error("服务器异常");
        }
    }

    /**
     * 多文件上传
     * @param files
     * @return
     */
    @RequestMapping(value = "/filesupload")
    @ResponseBody
    public ResponseVO uploads(MultipartFile[] files) {
        // 上传文件返回的路径集合
        List<String> arrayList = new ArrayList<String>();
        try {
            for (MultipartFile file:files){
                StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file
                                .getSize(),
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf
                                (".") + 1, file.getOriginalFilename().length()), null);
                String flieName = storePath.getFullPath().substring(17, storePath.getFullPath().length());
                String filePath = "http://file.malaxiaoyugan.com/group1/M00/00/00/" + flieName;
                arrayList.add(filePath);
            }
            return TTBFResultUtil.success( "图片上传成功", arrayList);
        } catch (IOException e) {
            return TTBFResultUtil.error("服务器异常");
        }
    }

    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseVO test(MultipartFile file) {
        try {

            Result result = TTBFHttpUtils.post("http://ttbf.malaxiaoyugan.com/yuuki/file/fileupload", file.getInputStream(), file.getName());
            return TTBFResultUtil.success( "图片上传成功", result);
        } catch (IOException e) {
            return TTBFResultUtil.error("服务器异常");
        }
    }
}
