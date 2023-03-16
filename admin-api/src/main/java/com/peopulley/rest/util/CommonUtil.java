package com.peopulley.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

public class CommonUtil {

    public static <E> List<E> entityToDto(Class<?> clazz, List<E> dataList, String methodName) {

        List<E> dtoList = new ArrayList<>();

        try {
            Object obj = clazz.newInstance();

            dtoList = (List<E>) Optional.ofNullable(dataList)
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(items -> {
                        try {
                            return clazz.getMethod(methodName, items.getClass()).invoke(obj, items);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new ArrayList<E>();
                        }
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtoList;
    }

    public static <E> boolean isEmptyList(Collection<E> itemList) {
        return !isNotEmptyList(itemList);
    }

    public static <E> boolean isNotEmptyList(Collection<E> itemList) {
        return itemList != null && !itemList.isEmpty();
    }

    public static boolean nullObject(Object obj) {
        return obj == null;
    }

    public static boolean notNullObject(Object obj) {
        return !nullObject(obj);
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String getCmnBarcode(String inBarcode) {
        int barcodeSize = inBarcode.length();
        String outBarcode = "";

        // 1. 바코드의 뒷자리 8자리를 구한다.
        String digit8 = inBarcode.substring(barcodeSize - 8, barcodeSize);
        // 2. 마지막 문자제거 ( s20은 바코드를 스캔하면 마지막에 문자하나가 추가된다. )
        String digitFirst7 = digit8.substring(0, digit8.length() - 1);

        // 3. 7자리가 숫자인지 확인

        if (!isNumeric(digitFirst7)) {
            // 8자리중 뒤에서 7자리 구한다. ( s10은 s20과 다르게 바코드를 찍으면 문자가 추가되지 않는다. )
            String digitLast7 = digit8.substring(1, digit8.length());
            if (!isNumeric(digitLast7)) {
                // 애플 (문자숫자조합) // 전체 바코드 중 뒤에서 4자리가 공통
                outBarcode = inBarcode.substring(barcodeSize - 4, barcodeSize);
            } else {
                // S10을 포함한 삼성  //
                outBarcode = inBarcode.substring(0, 8);
            }
        } else {
            // 바코드 전체가 숫자면 LG ( 현재는 velvet-KT 만 확인함 )
            if (isNumeric(inBarcode)) {
                outBarcode = inBarcode.substring(0, 7);
            } else {
                // S20 //
                String uniqBarcode = inBarcode.substring(
                        barcodeSize - 8,
                        barcodeSize
                );
                outBarcode = inBarcode.replace(uniqBarcode, "");
            }
        }
        return outBarcode;
    }

    //일련번호에서 자급제 바코드 추출. 자급제 판단은 불가하며, 앞 5자리 DB 조회해서 검증
    public static String getUnLockCmnBarcode(String inBarcode) {

        if (StringUtils.hasText(inBarcode)
                && inBarcode.length() > 5) {
            inBarcode = inBarcode.substring(0, 5);
        }

        return inBarcode;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String appendLeft(String src, String apchar, int len) {
        int count = len - src.trim().length();
        for (int i = 0; i < count; i++)
            src = apchar.trim() + src.trim();

        return src;
    }

    public static void parseJsonToDto(JsonNode jsonNode, Object obj) throws Exception {

        Class cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            if(jsonNode.get(field.getName()) != null){
                cls.getMethod("set".concat(StringUtils.capitalize(field.getName())), String.class)
                        .invoke(obj, jsonNode.get(field.getName()).textValue());
            }
        }
    }

    /**
     * maskingName
     * @param str, idx
     * @return String
     * str : 변경할 문자
     * idx : 1(첫번째), 2(가운데), 3(마지막) 그룹을 스트링으로 "123" 전체
     */
    public static String maskingName(String str, String idx) {
        //이름 : 홍*동
        //휴대폰 : 010-82**-**28
        //주민번호 : 810127-1******
        //주소 : 경기도 시흥시 ***
        //생년월일 : **년 **월 **일
        String replaceString = str;

        if(str != null) {
            String pattern = "";

            if (str.length() == 2) {
                pattern = "^(.)(.+)$";
            } else {
                pattern = "^(.)(.+)(.)$";
            }

            if("4".equals(idx)){    //01011****11
                pattern = "^(\\d{3})?(\\d{1,2})\\d{3}?\\d(\\d{2})$";
            }else if("5".equals(idx)) {    //11**
                pattern = "^(\\d{1,2})";
            }else if("6".equals(idx)) {    //**11
                pattern = "^?\\d(\\d{2})$";
            }

            Matcher matcher = Pattern.compile(pattern).matcher(str);
            replaceString = "";

            if("4".equals(idx)) {
                if (matcher.find()) {
                    replaceString = matcher.group(1) + matcher.group(2) + "****" + matcher.group(3);
                }
            }else if("5".equals(idx)) {
                if (matcher.find()) {
                    replaceString = matcher.group(1) + "**";
                }
            }else if("6".equals(idx)) {
                if (matcher.find()) {
                    replaceString = "**" + matcher.group(1);
                }
            }else if("7".equals(idx)) {
                if(str.length() > 6){
                    replaceString = str.substring(0, 6).concat(maskingMatcher(
                            Pattern.compile(pattern).matcher(str.replace(str.substring(0, 6), "")),"23"));
                }
            }else {
                replaceString = maskingMatcher(matcher, idx);
            }
        }

        return replaceString;
    }

    private static String maskingMatcher(Matcher matcher, String idx) {

        String replaceString = "";

        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String replaceTarget = matcher.group(i);
                if (idx.contains(String.valueOf(i))) {
                    char[] c = new char[replaceTarget.length()];
                    Arrays.fill(c, '*');

                    replaceString = replaceString + String.valueOf(c);
                } else {
                    replaceString = replaceString + replaceTarget;
                }
            }
        }

        return replaceString;
    }

    public static String cropString(String str, int length){
        if(StringUtils.hasText(str)){
            if(str.length() > length){
                str = str.substring(0, length).concat("...");
            }
        }
        return str;
    }

    public static String cropFirstLine(String str) {
        if(StringUtils.hasText(str)){
            str = str.split("\r")[0];
            str = str.split("\n")[0];
            str = str.split("\n\r")[0];
        }
        return str;
    }

    public static String isNullReturnStr(String str){
        if(!StringUtils.hasText(str)) {
            return "";
        }else{
            return str;
        }
    }

    public static String isNullReturnStr(String str, String return_str){
        if(!StringUtils.hasText(str)) {
            return return_str;
        }else{
            return str;
        }
    }

    public static LocalDate calcDeadLineDate(LocalDate startDate, int days){

        if(startDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            days++;
        }

        while (days > 1){
            startDate = startDate.plusDays(1);
            if(startDate.getDayOfWeek() == DayOfWeek.SUNDAY){
                days++;
            }
            days--;
        }

        return startDate;
    }

    public static String getRequestURIString() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
    }
}

















