package com.bdqn;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 177
 * @Date 2022/11/6 14:27
 **/
public class TextFreemaker {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("C:\\D\\J363_Zheng\\itripbackend\\itripauth\\src\\main\\resources\\ "));
        Template template = configuration.getTemplate("a.flt");
        Map map = new HashMap();
        map.put("aa","Java 代码设的值");
        template.process(map,new FileWriter("C:\\Users\\ShiYun\\Desktop\\new.html"));
    }
}
