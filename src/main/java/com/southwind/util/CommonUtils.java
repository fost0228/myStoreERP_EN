package com.southwind.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author jiangH
 * @create 02-05-2023 10:53 AM
 */
public class CommonUtils {
    public static LocalDateTime parseString(String string) {
        String[] split = string.split("/");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i < split.length - 1) {
                if (split[i].length() == 1) {
                    stringBuffer.append("0").append(split[i]).append("-");
                } else {
                    stringBuffer.append(split[i]).append("-");
                }
            } else {
                if (split[i].length() == 1) {
                    stringBuffer.append("0").append(split[i]);
                } else {
                    stringBuffer.append(split[i]);
                }
            }
        }
        stringBuffer.append(" 00:00:00");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(stringBuffer.toString(), dateTimeFormatter);
        System.out.println(date);
        return date;
    }

    public static String createOrderNo(Integer count, Integer orderType) {
        StringBuffer stringBuffer = null;
        switch (orderType) {
            case 1:
                stringBuffer = new StringBuffer("OV");
                break;
            case 2:
                stringBuffer = new StringBuffer("OR");
                break;
            case 3:
                stringBuffer = new StringBuffer("MI");
                break;
            case 4:
                stringBuffer = new StringBuffer("MR");
                break;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String format1 = format.format(new Date());
        stringBuffer.append(format1);
        count++;
        StringBuffer stringBuffer1 = new StringBuffer(count + "");
        while (stringBuffer1.length() < 6) {
            stringBuffer1.insert(0, "0");
        }
        stringBuffer.append(stringBuffer1);
        return stringBuffer.toString();
    }

    public static LocalDateTime parseString2(String string) {
        if (string.contains("T")) {
            int t = string.indexOf("T");
            string = string.substring(0, t);
            StringBuffer stringBuffer = new StringBuffer(string);
            stringBuffer.append(" 00:00:00");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(stringBuffer.toString(), dateTimeFormatter);
            return date;
        } else {
            StringBuffer stringBuffer = new StringBuffer(string);
            stringBuffer.append(" 00:00:00");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(stringBuffer.toString(), dateTimeFormatter);
            return date;
        }

    }


}
