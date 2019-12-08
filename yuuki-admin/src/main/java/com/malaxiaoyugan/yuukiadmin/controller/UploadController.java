package com.malaxiaoyugan.yuukiadmin.controller;



import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.malaxiaoyugan.yuukiadmin.utils.Result;
import com.malaxiaoyugan.yuukiadmin.utils.TTBFHttpUtils;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.QiniuUtils;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.FroalaResponse;
import com.malaxiaoyugan.yuukicore.vo.QiniuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
     * 获取上传凭证
     * @param type 凭证类型
     * @return
     */
    @RequestMapping(value = "/gettoken",method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getToken(@RequestParam int type) {
        try {
            QiniuVo qiniuUpToken = QiniuUtils.getQiniuUpToken(type);
            return TTBFResultUtil.success( "七牛凭证成功", qiniuUpToken);
        } catch (Exception e) {
            return TTBFResultUtil.error("服务器异常");
        }
    }

    /**
     * 单个文件上传七牛云
     * @param file
     * @return
     */
    @RequestMapping(value = "/fileupload",method = RequestMethod.POST)
    @ResponseBody
    public FroalaResponse uploadQiNiu(MultipartFile file) {
        FroalaResponse froalaResponse = new FroalaResponse();
        try {
            String s = QiniuUtils.putInputStrem(file);

            froalaResponse.setLink(s);
            return froalaResponse;
        } catch (Exception e) {
            //return TTBFResultUtil.error("服务器异常");
            return froalaResponse;
        }
    }

    /**
     * 单个文件上传(上传服务器文件 弃用)
     * @param file
     * @return
     */
    @RequestMapping(value = "/fileupload.bak")
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
     * 多文件上传(上传服务器文件 弃用)
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
