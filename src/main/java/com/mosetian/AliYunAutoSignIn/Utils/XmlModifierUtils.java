package com.mosetian.AliYunAutoSignIn.Utils;

import cn.hutool.core.util.XmlUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlModifierUtils {

    /**
     * 更新XML文件中指定note属性的节点内容
     *
     * @param noteValue note属性的目标值
     * @param newValue  新的节点文本内容
     * @return 是否成功更新至少一个节点
     */
    public static boolean updateXmlNode(String noteValue, String newValue) {
        try {
            String xmlFilePath = System.getenv("TOKEN_PATH");
            // 从文件读取XML内容
            Document document = XmlUtil.readXML(xmlFilePath);

            // 获取所有"RefreshTokenPath"的节点
            NodeList nodeList = document.getElementsByTagName("RefreshTokenPath");
            boolean updated = false;

            // 遍历节点并更新内容
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                // 检查是否是特定的note
                if (element.getAttribute("note").equals(noteValue)) {
                    // 更新节点内容
                    element.setTextContent(newValue);
                    updated = true; // 标记至少更新了一个节点
                }
            }
            if (updated) {
                // 将更新后的XML文档写回文件
                XmlUtil.toFile(document, xmlFilePath, "UTF-8");
            }
            return updated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}