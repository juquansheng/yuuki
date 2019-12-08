package com.malaxiaoyugan.yuukicore.utils;


import com.google.gson.Gson;
import com.malaxiaoyugan.yuukicore.aop.TTBFException;
import com.malaxiaoyugan.yuukicore.vo.QiniuVo;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

public class QiniuUtils {

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "T5opFMk3cXvOoewDTi4Ow_ZNN0euAToDJXA432EP";
    private static final String SECRET_KEY = "DVKqaB6H5Ir4fdiS9M3RPfvzBb4U1Zwof5xE6DZp";
    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 要上传的空间
    private static final String BUCKET = "blob";       //存储空间名称
    private static final String DOMAIN= "picture.malaxiaoyugan.com";       //外链域名

    // 要上传的空间
    private static final String VIDEO_BUCKET = "yuuki-video";       //存储空间名称
    private static final String VIDEO_DOMAIN= "video.malaxiaoyugan.com";       //外链域名

    private static final String style = "自定义的图片样式";

    public static String getUpToken() {
        return auth.uploadToken(BUCKET, null, 3600, new StringMap().put("insertOnly", 1));
    }
    /**
     * type 1 图片 2 视频
     * @return
     * @throws Exception
     */
    public static QiniuVo getQiniuUpToken(Integer type){
        QiniuVo qiniuVo = new QiniuVo();
        try {
            StringMap putPolicy = new StringMap();
            putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"user\":\"$(x:user)\",\"link\":\"$(x:url)$(hash)\"}");
           /* putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
            putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
            putPolicy.put("callbackBodyType", "application/json");*/
            long expireSeconds = 3600;
            //生成凭证
            if (type == 1){
                String upToken = auth.uploadToken(BUCKET,null,expireSeconds,putPolicy);
                qiniuVo.setToken(upToken);
                //存入外链默认域名，用于拼接完整的资源外链路径
                qiniuVo.setDomain(DOMAIN);
                qiniuVo.setImgUrl("http://"+DOMAIN+"/");
            }else {
                String upToken = auth.uploadToken(VIDEO_BUCKET,null,expireSeconds,putPolicy);
                qiniuVo.setToken(upToken);
                //存入外链默认域名，用于拼接完整的资源外链路径
                qiniuVo.setDomain(VIDEO_DOMAIN);
                qiniuVo.setImgUrl("http://"+VIDEO_DOMAIN+"/");
            }
            System.out.println("生成七牛云凭证"+qiniuVo.toString());
            return qiniuVo;

        } catch (Exception e) {
            throw new TTBFException(501,"获取凭证失败，"+e.getMessage());
        }
    }





    // 普通上传
    public static String upload(String filePath, String fileName) throws IOException {
        // 创建上传对象
        UploadManager uploadManager = new UploadManager();
        try {
            // 调用put方法上传
            String token = auth.uploadToken(BUCKET);
            if(StringUtils.isEmpty(token)) {
                System.out.println("未获取到token，请重试！");
                return null;
            }
            Response res = uploadManager.put(filePath, fileName, token);
            // 打印返回的信息
            System.out.println(res.bodyString());
            if (res.isOK()) {
                Ret ret = res.jsonToObject(Ret.class);
                //如果不需要对图片进行样式处理，则使用以下方式即可
                return DOMAIN + ret.key;
                //return DOMAIN + ret.key + "?" + style;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                // ignore
            }
        }
        return null;
    }

    //base64方式上传
    public static String put64image(byte[] base64, String key) throws Exception{
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        String url = "http://upload.qiniu.com/putb64/" + len + "/key/"+ UrlSafeBase64.encodeToString(key);

        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();

        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //如果不需要添加图片样式，使用以下方式
        return DOMAIN + key;
        //return DOMAIN + key + "?" + style;
    }

    public static String putInputStrem(MultipartFile file){
        String key = null;
        try {
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager();
            StringMap putPolicy = new StringMap();
            putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"user\":\"$(x:user)\",\"age\",$(x:age)}");
           /* putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
            putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
            putPolicy.put("callbackBodyType", "application/json");*/
            long expireSeconds = 3600;
            String upToken = auth.uploadToken(BUCKET);
            byte[] bytes = file.getBytes();
            try {
                Response response = uploadManager.put(bytes,key,upToken);
                System.out.println("骑牛上传相应"+response);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "http://"+DOMAIN+"/"+putRet.hash;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    throw new TTBFException(901,r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    throw new TTBFException(901,ex2.getMessage());
                }
            }
        } catch (Exception e) {
            //ignore
            throw new TTBFException(901,e.getMessage());
        }
    }



    public static String getUrl(String key){
        if(!StringUtils.isEmpty(key)){
            return auth.privateDownloadUrl("http://"+key);
        }
        return null;
    }

    class Ret {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }

}
