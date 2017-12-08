package me.cloudcat.develop.sql;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * sql脚本生成器
 *
 * @Author: zhenzhong.wang
 * @Time: 2017/11/30 15:07
 */
public class SQLGenerator {

    @Test
    public void appAccess() throws IOException, TemplateException {
        //创建Freemarker配置实例
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("src/main/webapp/templates"));

        //创建数据模型
        Map root = new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        root.put("date", format.format(new Date()));
        // app基本信息
        root.put("appId", "9");
        root.put("appName", "SCRM");
        root.put("evnName", "SaaS");
        root.put("appCode", "hcrm");
        root.put("appDescriptionCn", "社会化客户关系管理平台");
        root.put("appDescriptionEn", "Customer Relationship Management");
        root.put("appUrl", "scrm.hypers.com.cn");
        root.put("appTheme", "#59C2F0");
        // 环境信息

        root.put("envId", "1");
        root.put("envUrl", "account.hypers.com.cn");
        root.put("envAppOrder", "7,4,1,2,3,6");
        //加载模板文件
        Template t1 = cfg.getTemplate("appAccess.ftl");
        //显示生成的数据,//将合并后的数据打印到控制台
        Writer out = new OutputStreamWriter(System.out);
        t1.process(root, out);
        out.flush();
    }
}
