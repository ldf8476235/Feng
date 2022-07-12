package com.yyds.feng.vx.crotroller;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VxController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/vx")
    public String login(String signature, String timestamp, String nonce, String echostr){
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }
        // 消息合法
        return echostr;
    }

    @PostMapping("/vx")
    public String handler(HttpServletRequest request) throws Exception {
        //获取消息流,并解析xml
        WxMpXmlMessage message=WxMpXmlMessage.fromXml(request.getInputStream());
        System.out.println(message.toString());
        //消息类型
        String messageType=message.getMsgType();
        System.out.println("消息类型:"+messageType);
        //发送者帐号
        String fromUser=message.getFromUser();
        System.out.println("发送者账号："+fromUser);
        //开发者微信号
        String touser=message.getToUser();
        System.out.println("开发者微信："+touser);
        //文本消息  文本内容
        String text=message.getContent();
        System.out.println("文本消息："+text);

        //获取微信服务器的IP地址
        /*String[] callbackIP = wxMpService.getCallbackIP();
        for(int i=0;i<callbackIP.length;i++){
            System.out.println("IP地址"+i+"："+callbackIP[i]);
        }*/

        /**
         * 文本消息
         */
        if(messageType.equals("text")){
            if (text.equals("上分")) {
                WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                        .TEXT()
                        .toUser(fromUser)
                        .fromUser(touser)
                        .content("您已成功登记")
                        .build();
                String result = texts.toXml();
                System.out.println("响应给用户的消息：" + result);
                return result;
            }
        }

        /**
         * 图片消息
         */
//        if(messageType.equals("image")){
//            //创建file对象
//            File file=new File("lyf.jpg");
//            //上传多媒体文件
//            WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, file);
//            WxMpXmlOutImageMessage images = WxMpXmlOutMessage.IMAGE()
//                    .mediaId(wxMediaUploadResult.getMediaId())//获取上传到微信服务器的临时素材mediaid.
//                    .fromUser(touser)
//                    .toUser(fromUser)
//                    .build();
//            String result = images.toXml();
//            System.out.println("响应给用户的消息："+result);
//            return result;
//        }
        /**
         * 音乐
         */
//        if(messageType.equals("misic")){
//            //创建file对象
//            File file=new File("E:\\music\\qx.mp3");
//            //上传多媒体文件
//            WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.VOICE, file);
//            WxMpXmlOutMusicMessage musics = WxMpXmlOutMessage.MUSIC()
//                    .fromUser(fromUser)
//                    .toUser(touser)
//                    .title("枪声")
//                    .description("最强震撼枪声")
//                    .hqMusicUrl("高质量音乐链接，WIFI环境优先使用该链接播放音乐")
//                    .musicUrl("缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id")
//                    .thumbMediaId(wxMediaUploadResult.getMediaId())
//                    .build();
//            String result = musics.toXml();
//            System.out.println("响应给用户的消息："+result);
//            return result;
//        }
        return null;
    }
//    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        InputStream inputStream = request.getInputStream();
//        SAXReader reader = new SAXReader();
//        Document document = reader.read(inputStream);
//        Element root = document.getRootElement();
//        List<Element> elementList = root.elements();
//        for(Element e : elementList) {
//            map.put(e.getName(), e.getText());
//        }
//        inputStream.close();
//        inputStream = null;
//        return map;
//    }
}
