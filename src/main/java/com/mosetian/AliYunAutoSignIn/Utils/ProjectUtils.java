package com.mosetian.AliYunAutoSignIn.Utils;

import java.util.HashMap;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProjectUtils {

    public static HashMap<String, String> getRefreshToken() {
        // 你的XML文件路径
        String xmlFilePath = System.getenv("TOKEN_PATH");

        // 从文件读取XML内容
        String xmlStr = FileUtil.readUtf8String(xmlFilePath);

        // 解析XML字符串
        Document document = XmlUtil.parseXml(xmlStr);
        // 获取所有"RefreshTokenPath"的节点
        NodeList nodeList = document.getElementsByTagName("RefreshTokenPath");
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            // 使用note属性作为key，节点文本内容作为value
            String key = element.getAttribute("note");
            String value = element.getTextContent().trim(); // trim()去除起始和结尾的空白字符
            map.put(key, value);
        }
        return map;
    }

}
