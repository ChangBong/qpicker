package com.peopulley.rest.common.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";

    public static final String TOKEN_TYPE = "Bearer";

    public static final String[] SECURITY_PERMIT_ALL = {
            "/member/signup", "/member/login", "/member/invalidate", "/member/verify/**"
    };

    public static final String[] SECURITY_EXCLUDE_URLS = {
            "/member/invalidate", "/api/member/invalidate"
    };

    public static final String[] SECURITY_WEB_IGNORE_URLS = {
            "/v2/api-docs", "/swagger-resources/**",
            "/swagger-ui.html", "/webjars/**", "/swagger/**",
            "/", "/csrf"
    };

    public static final String DEVICE_HOME_PATH = File.separator.concat("home").concat(File.separator).concat("centos");

    public static final String XLS_ROOT_PATH = DEVICE_HOME_PATH.concat(File.separator).concat("excel");
    public static final String APPLICATION_ROOT_PATH = DEVICE_HOME_PATH.concat(File.separator).concat("application");
    public static final String APPLICATION_AWS_ROOT_PATH = "application";

    public static final String APPLICATION_UPLOAD_PATH = APPLICATION_ROOT_PATH.concat(File.separator).concat("upload");
    public static final String APPLICATION_AWS_UPLOAD_PATH = APPLICATION_AWS_ROOT_PATH.concat("/upload");
    public static final String XLS_UPLOAD_PATH = XLS_ROOT_PATH.concat(File.separator).concat("upload");
    public static final String XLS_DOWNLOAD_PATH = XLS_ROOT_PATH.concat(File.separator).concat("download");
    public static final String XLS_TEMPLATE_PATH = XLS_ROOT_PATH.concat(File.separator).concat("template");
    public static final String XLS_TMP_PATH = XLS_ROOT_PATH.concat(File.separator).concat("tmp");

    public static final String FILE_DOWNLOAD_VIEW = "fileDownloadView";
    public static final String FILE_DOWNLOAD_AWS_VIEW = "fileDownloadAWSView";
    public static final String EXCEL_DOWNLOAD_VIEW = "excelDownloadView";
    public static final HashMap<String, String> ENUM_INNER_CLASS = setEnumInnerClass();

    private static HashMap<String, String> setEnumInnerClass(){

        HashMap<String, String> enumInnerClassMap = new HashMap<>();

        List<Class> enumClassList = new ArrayList<>();

//        enumClassList.add(BondEnum.class);

        for (Class enumClass : enumClassList) {
            for(Class innerClass : enumClass.getClasses()){
                enumInnerClassMap.put(innerClass.getSimpleName().toLowerCase(), innerClass.getName());
            }
        }
        return enumInnerClassMap;
    }
}