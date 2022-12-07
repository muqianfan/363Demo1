package com.bdqn;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 177
 * @Date 2022/11/6 15:32
 **/
public class TextFreemaker2 {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("C:\\D\\J363_Zheng\\itripbackend\\itripauth\\src\\main\\resources\\ "));
        Template template = configuration.getTemplate("b.flt");
        List<Userpojo> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Userpojo pojo = new Userpojo();
            pojo.setId(i);
            pojo.setName("姓名:"+i);
            list.add(pojo);
        }
        Map map = new HashMap();
        map.put("ll",list);
        template.process(map,new FileWriter("C:\\Users\\ShiYun\\Desktop\\new.txt"));
    }
}
