package com.peopulley.rest.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValue {

    public static String profilesActive;
    public static String s3Bucket;

    @Value("${spring.profiles.active}")
    public void setProfilesActive(String value) {
        profilesActive = value;
    }

    @Value("${cloud.aws.s3.bucket}")
    public void setS3Bucket(String value) {
        s3Bucket = value;
    }

}