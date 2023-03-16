package com.peopulley.core.base.util;

import com.peopulley.core.common.enums.StatusEnum;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseCommonUtil {

    public static long diffDaysLocalDateTime(LocalDateTime targetDateTime) {
        return targetDateTime != null ? ChronoUnit.DAYS.between(targetDateTime.toLocalDate(), LocalDate.now()) : 0;
    }
    public static long diffDaysLocalDate(LocalDate targetDate) {
        if(targetDate != null) {
            String localDateToString = targetDate.toString();
            localDateToString = localDateToString.concat(" 00:00:00");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime convertDateTime = LocalDateTime.parse(localDateToString, formatter);
            return diffDaysLocalDateTime(convertDateTime);
        }
        return 0L;
    }

    public static String changeYn(String preYn){
        if(preYn.equals(StatusEnum.FLAG_Y.getStatusMsg())){
            return StatusEnum.FLAG_N.getStatusMsg();
        } else {
            return StatusEnum.FLAG_Y.getStatusMsg();
        }
    }

    public static boolean nullObject(Object obj){
        return obj == null;
    }

    public static boolean notNullObject(Object obj){
        return !nullObject(obj);
    }

    public static <E> boolean isEmptyList(Collection<E> itemList) {
        return !isNotEmptyList(itemList);
    }

    public static <E> boolean isNotEmptyList(Collection<E> itemList) {
        return itemList != null && !itemList.isEmpty();
    }

    public static String removeEmoticon(String str) {
        String replaceStr = str;

        if (StringUtils.hasText(replaceStr)) {
            Pattern emo = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
            Matcher emoMatcher = emo.matcher(replaceStr);
            replaceStr = emoMatcher.replaceAll("");
            replaceStr = replaceStr.replaceAll("\\p{So}+", "")
                    .replaceAll("\\p{InEmoticons}+", "")
                    .replaceAll("\\p{InMiscellaneousSymbolsAndPictographs}+", "");
        }
        return replaceStr;
    }

    public static String removeScript(String str){
        String replaceStr = str;

        if (StringUtils.hasText(replaceStr)) {
            String script = "<script>(.*)</script>";

            Pattern pattern = Pattern.compile(script);
            Matcher matcher = pattern.matcher(replaceStr);
            if (matcher.find()) {
                replaceStr = replaceStr.replace(matcher.group(1), "");
            }
        }

        return replaceStr;
    }

    public static String removeScriptAndEmoticon(String str) {
        return removeEmoticon(removeScript(str));
    }

    public static String removeStr(String str) {
        String replaceStr = str;

        if (StringUtils.hasText(replaceStr)) {
            replaceStr = replaceStr
                    .replace("\n", ", ")
                    .replace("\r", ", ")
                    .replace("\n\r", ", ")
                    .replace("\"", "")
                    .replace("'", "")
                    .replace("'", "")
            ;

            String match = "[^\u3131-\u318E\uAC00-\uD7A3xfe0-9a-zA-Z^,\\s]";
            replaceStr = replaceStr.replaceAll(match, "");
        }

        return replaceStr;
    }

    public static String convertFormatHP(String str){
        String convertStr = str;

        if(convertStr == null) return convertStr;
        if(11 == convertStr.trim().length()){
            convertStr = convertStr.substring(0,3) + "-" + convertStr.substring(3,7) + "-" + convertStr.substring(7,11);
        } else if(10 == convertStr.trim().length()) {
            convertStr = convertStr.substring(0,3) + "-" + convertStr.substring(3,6) + "-" + convertStr.substring(6,10);
        } else if(9 == convertStr.trim().length()) {
            convertStr = convertStr.substring(0,2) + "-" + convertStr.substring(2,5) + "-" + convertStr.substring(5,9);
        } else {
            convertStr = "xxx";
        }

        return convertStr;
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

    public static String upperBarcode(String barcode) {
        return StringUtils.hasText(barcode) ? barcode.toUpperCase() : null;
    }

    public static String appendLeft(String src, String apchar, int len) {
        int count = len - src.trim().length();
        for (int i = 0; i < count; i++)
            src = apchar.trim() + src.trim();

        return src;
    }

    public static String getSrhDate(String reqSrhYear, String reqSrhMonth, String type){

        String srhYear = String.valueOf(LocalDate.now().getYear());
        if (StringUtils.hasText(reqSrhYear)) {
            srhYear = reqSrhYear;
        }

        String srhMonth = String.valueOf(LocalDate.now().getMonthValue());
        if (StringUtils.hasText(reqSrhMonth)) {
            srhMonth = reqSrhMonth;
        }

        if("start".equals(type)){
            return srhYear.concat(BaseCommonUtil.appendLeft(srhMonth, "0", 2).concat("01"));
        }

        if("end".equals(type)){
            String lastDay = String.valueOf(LocalDate.of(Integer.parseInt(srhYear)
                    , Integer.parseInt(srhMonth)
                    , 1)
                    .withDayOfMonth(Integer.parseInt(srhMonth))
                    .lengthOfMonth()
            );
            return srhYear.concat(BaseCommonUtil.appendLeft(srhMonth, "0", 2)
                    .concat(BaseCommonUtil.appendLeft(lastDay, "0", 2)));
        }

        return "";
    }

    public static boolean isStageProdProfile(String profile){
        return StringUtils.hasText(profile) && ("stage".equals(profile) || "prod".equals(profile));
    }

    public static boolean isNotStageProdProfile(String profile){
        return StringUtils.hasText(profile) && (!"stage".equals(profile) && !"prod".equals(profile));
    }

    public static long diffHoursLocalDatetime(LocalDateTime targetDatetime){
        return targetDatetime != null ? ChronoUnit.HOURS.between(targetDatetime, LocalDateTime.now()) : 0L;
    }

    public static long diffMinutesLocalDatetime(LocalDateTime targetDatetime){
        return targetDatetime != null ? ChronoUnit.MINUTES.between(targetDatetime, LocalDateTime.now()) : 0L;
    }
}
