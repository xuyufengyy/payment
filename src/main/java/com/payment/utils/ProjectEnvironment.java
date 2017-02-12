package com.payment.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 6/27/2016.
 */
public class ProjectEnvironment {

    private static Logger logger = LoggerFactory.getLogger(ProjectEnvironment.class);

    //环境类型
    private static String type;

    public static void init(String[] args){
        //解析参数
        type = "";
        for(String arg : args){
            String regex = ".*spring\\.profiles\\.active.*";
            if(arg.matches(regex) == true){
                type = "-" + arg.split("=")[1];
                logger.info("analysis args successfully！");
                break;
            }
        }
    }

    public static String getPropertiesFileName(String baseName){
        return baseName + type + ".properties";
    }
}
