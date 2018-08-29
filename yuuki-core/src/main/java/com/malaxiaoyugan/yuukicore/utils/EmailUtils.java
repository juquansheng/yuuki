package com.malaxiaoyugan.yuukicore.utils;


import com.malaxiaoyugan.yuukicore.entity.MailParam;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URLDecoder;
import java.util.Properties;

public class EmailUtils {



    /**
     *发送邮件
     * @param subject
     * @param to
     * @param text
     * @param mailParamVo
     * @return
     */
    public static boolean sendEmalil(String subject, String to,String text, MailParam mailParamVo){
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailParamVo.getSmtpHost());
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        // 置true可以在控制台（console)上看到发送邮件的过程
        session.setDebug(true);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailParamVo.getFromAddr()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            //message.setContent(content, "text/html;charset=utf-8");


            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(text);
            multipart.addBodyPart(contentPart);
            /*// 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();

            String path = URLDecoder.decode(EmailUtils.class.getClassLoader().
                    getResource("file/" + fileName).getPath(), "UTF-8").
                    replace("!","").replace("cec-0.0.1-SNAPSHOT.jar","cec-0.0.1-SNAPSHOT");
            path = path.substring(6,path.length());

            //DataSource source = new FileDataSource(URLDecoder.decode(EmailUtils.class.getClassLoader().getResource("file/"+ fileName).getPath(), "UTF-8"));
            DataSource source = new FileDataSource(path);
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            messageBodyPart.setFileName(MimeUtility.encodeText(fileName));*/

            //multipart.addBodyPart(messageBodyPart);

            // 将multipart对象放到message中
            message.setContent(multipart,"text/html;charset=utf-8");
            // 保存邮件
            message.saveChanges();

            Transport transport = session.getTransport();
            transport.connect(mailParamVo.getSmtpHost(), mailParamVo.getUserName(), mailParamVo.getPassWord());
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败...");
        }

        return true;
    }
}
