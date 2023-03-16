package com.peopulley.rest.io.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.peopulley.rest.common.constants.PropertiesValue;
import com.peopulley.rest.io.file.dto.FileUploadDto;
import com.peopulley.rest.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@RequiredArgsConstructor
@Component("fileUpload")
@Slf4j
public class FileUpload {

    private final AmazonS3 amazonS3;

    public void uploadFile(MultipartRequest request) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //AWS S3 파일 삭제
    //physical_path is unique key
    public void deleteAttachFileAWS(String key) {
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(PropertiesValue.s3Bucket, key));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public <T> Object fileUpload(FileUploadDto<T> fileUploadDto){
        if(fileUploadDto == null
                || fileUploadDto.getMultipartFile() == null
                || fileUploadDto.getMultipartFile().getName() == null
                || fileUploadDto.getResultObj() == null
        ){
            log.error("FileUpload fileUpload :::::: MultipartFile is NULL");
            log.error(fileUploadDto.toString());
            return null;
        }else{
            try{
                Map<String, String> resultMap = uploadAttachFile(fileUploadDto.getMultipartFile(), fileUploadDto.getFilePath(), fileUploadDto.getMemberSeq(), fileUploadDto.getPrefixFileName());

                if(fileUploadDto.getResultObj() instanceof Map){
                    return resultMap;
                }else if(fileUploadDto.getResultObj() instanceof Object){
                    Class clazz = fileUploadDto.getResultObj().getClass();
                    Object o = clazz.newInstance();

                    Set set = resultMap.entrySet();
                    Iterator iterator = set.iterator();

                    while (iterator.hasNext()){
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                        String getKeyName = entry.getKey();
                        String getKeyValue = entry.getValue();
                        for(Field field : clazz.getDeclaredFields()) {
                            field.setAccessible(true);
                            if(getKeyName.equals(field.getName())){
                                clazz.getMethod("set".concat(StringUtils.capitalize(field.getName())), String.class)
                                        .invoke(o, getKeyValue);
                            }
                        }
                    }
                    return o;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
        20220-08-22 공통 파일 업로드 작업
        복수 파일 업로드 Object
        Request : FileUploadDto<Object>
                ex) Request Object
                    FileUploadDto<SystemNoticeAttachDto> fileUploadDto = FileUploadDto.<SystemNoticeAttachDto>builder()
                                                                            .multipartFile(multipartFile)
                                                                            .filePath(String filePath)
                                                                            .memberSeq(getMemberSeq)
                                                                            .resultObj(new SystemNoticeAttachDto())
                                                                            .build();
                ex) Request Map
                    FileUploadDto<Map<String, String>> fileUploadMap = FileUploadDto.<Map<String, String>>builder()
                                                                            .multipartFile(multipartFile)
                                                                            .filePath(String filePath)
                                                                            .memberSeq(getMemberSeq)
                                                                            .resultObj(new HashMap<String,String>())
                                                                            .build();
        Response : List<Object or Map>
    */
    public <T> List<T> multipleFileUpload(FileUploadDto<T> fileUploadDto){
        if(fileUploadDto.getMultipartFiles().length == 0
                || fileUploadDto.getResultObj() == null
        ){
            log.error("FileUpload.java multipleFileUpload :::::: File Length 0");
            log.error(fileUploadDto.toString());
            return null;
        }

        List<T> resultObjectList = new ArrayList<>();

        for(MultipartFile multipartFile : fileUploadDto.getMultipartFiles()){
            if(multipartFile == null){
                log.error("FileUpload multipleFileUpload :::::: MultipartFile is NULL");
                log.error(fileUploadDto.toString());
                return null;
            }

            resultObjectList.add((T) fileUpload(FileUploadDto.builder()
                    .resultObj(fileUploadDto.getResultObj())
                    .multipartFile(multipartFile)
                    .memberSeq(fileUploadDto.getMemberSeq())
                    .filePath(fileUploadDto.getFilePath())
                    .build()));
        }
        return resultObjectList;
    }

    /*
        BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive) : true
            - Local FileUpload
        BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive) : False
            - AWS FileUpload
        return : Map
     */
    private Map<String, String> uploadAttachFile(MultipartFile mFile, String filePath, long memberSeq, String prefixFileName) {
        if (mFile != null
                && StringUtils.hasText(mFile.getName())) {

            try (InputStream inputStream = mFile.getInputStream()) {
                filePath = filePath.concat("/").concat(DateUtil.getTodayYYYYMMDD()).concat("/");

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(mFile.getContentType());
                objectMetadata.setContentLength(mFile.getSize());

                String originalFileName = mFile.getOriginalFilename();
                Long fileSize = mFile.getSize();
                String tmpFileName = DateUtil.getCurrentDateTimeMilli();

                if(!StringUtils.isEmpty(prefixFileName)){
                    tmpFileName = prefixFileName.concat("_".concat(tmpFileName));
                }
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                String uploadFile = tmpFileName.concat("_".concat(String.valueOf(memberSeq))).concat(".").concat(fileExtension);

               /* if (BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive)) {
//                    filePath = "C:".concat(File.separator).concat(filePath);
                    uploadFile = filePath.concat(uploadFile);
                    //파일경로 셋팅
                    File dir = new File("C:".concat(File.separator).concat(filePath));

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File attachFile = new File("C:".concat(File.separator).concat(uploadFile));
                    mFile.transferTo(attachFile);
                }else{
                    uploadFile = filePath.concat(uploadFile);
                    amazonS3.putObject(new PutObjectRequest(
                                    PropertiesValue.s3Bucket, uploadFile, inputStream, objectMetadata
                            ).withCannedAcl(CannedAccessControlList.PublicRead)
                    );
                }*/
                /*
                * Local 저장 안하고 바로 S3로 전달
                * */
                uploadFile = filePath.concat(uploadFile);
                amazonS3.putObject(new PutObjectRequest(
                                PropertiesValue.s3Bucket, uploadFile, inputStream, objectMetadata
                        ).withCannedAcl(CannedAccessControlList.PublicRead)
                );
                
                String domainUrl = amazonS3.getUrl(PropertiesValue.s3Bucket, null).toString();
                Map<String, String> attachFileMap = new HashMap<>();
                attachFileMap.put("attachExt", fileExtension);
                attachFileMap.put("attachName", originalFileName.replace(".".concat(fileExtension), ""));
                attachFileMap.put("physicalPath", uploadFile);
                attachFileMap.put("logicalPath", amazonS3.getUrl(PropertiesValue.s3Bucket, uploadFile).toString());
                attachFileMap.put("domainUrl", domainUrl.substring(0, domainUrl.length()-1));
                attachFileMap.put("browserPath", uploadFile);
                attachFileMap.put("attachSize", fileSize.toString());
                attachFileMap.put("name", mFile.getName());
                return attachFileMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //URL 이미지 다운로드
    /*public Map<String, String> externalImageUpload(String url, String filePath, long memberSeq) {
        InputStream is = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = null;
        Map<String, String> attachFileMap = new HashMap<>();

        try{
            //URL 검증.
            if(!CommonUtil.validateUrl(url)){
                log.error("FileUpload externalImageUpload :::::: URL IS FAIL");
                log.error(url);
                return null;
            }

            Document doc = Jsoup.connect(url).header("Member-Agent" , "Mozilla/5.0").get();
            String imagePath = doc.select("meta[property=og:image]").attr("content").toString();

            //네이버 같은경우 iframe로 블로그가 나오는 경우가 있어서 imagePath가없으면 iframe을 한번더 검색한다.
            if(imagePath == null || "".equals(imagePath)){
                if(url.contains("blog.naver.com")){
                    Elements iframe = doc.select("iframe#mainFrame");
                    String mainFrameUrl = "https://blog.naver.com" + iframe.attr("src");
                    doc = Jsoup.connect(mainFrameUrl).header("Member-Agent" , "Mozilla/5.0").get();
                    imagePath = doc.select("meta[property=og:image]").attr("content").toString();
                }
            }

            if(imagePath != null){
                URL imageUrl = new URL(imagePath);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                int len = conn.getContentLength();
                is = conn.getInputStream();

                byte[] buf = new byte[1024];
                while( (len = is.read( buf )) != -1 ) {
                    byteArrayOutputStream.write(buf, 0, len);
                }

                filePath = filePath.concat(File.separator).concat(DateUtil.getTodayYYYYMMDD()).concat(File.separator);

                byte[] fileArray = byteArrayOutputStream.toByteArray();
                String originalFileName = DateUtil.getCurrentDateTimeMilli();
                String fileExtension = "jpeg";
                String uploadFile = originalFileName.concat("_".concat(String.valueOf(memberSeq))).concat(".").concat(fileExtension);

                if (BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive)) {
                    uploadFile = filePath.concat(uploadFile);
                    //파일경로 셋팅
                    File dir = new File("C:".concat(File.separator).concat(filePath));

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(new File("C:".concat(File.separator).concat(uploadFile)));
                    byteArrayOutputStream.writeTo(fileOutputStream);
                }else{
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(uploadFile));
                    objectMetadata.setContentLength(fileArray.length);

                    amazonS3.putObject(new PutObjectRequest(
                                    PropertiesValue.s3Bucket, uploadFile, new ByteArrayInputStream(fileArray), objectMetadata
                            ).withCannedAcl(CannedAccessControlList.PublicRead)
                    );
                }

                String domainUrl = amazonS3.getUrl(PropertiesValue.s3Bucket, null).toString();
                attachFileMap.put("attachExt", fileExtension);
                attachFileMap.put("attachName", originalFileName);
                attachFileMap.put("physicalPath", uploadFile);
                attachFileMap.put("logicalPath", amazonS3.getUrl(PropertiesValue.s3Bucket, uploadFile).toString());
                attachFileMap.put("domainUrl", domainUrl.substring(0, domainUrl.length()-1));
                attachFileMap.put("subLogicalPath", uploadFile);
                attachFileMap.put("attachSize", Integer.toString(fileArray.length));
                attachFileMap.put("name", uploadFile.replace(".".concat(fileExtension), ""));
            }
        }catch (Exception e) {
            e.printStackTrace();
            attachFileMap = null;
        }finally {
            try{
                if(is != null){
                    is.close();
                }
                if(byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return attachFileMap;
        }
    }*/


}










