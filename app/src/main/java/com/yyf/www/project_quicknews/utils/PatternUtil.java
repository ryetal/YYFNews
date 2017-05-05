package com.yyf.www.project_quicknews.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class PatternUtil {

    /**
     * 手机号码
     *
     * @return
     */
    public static boolean isPhoneNumberCorrect(String input) {

        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * 密码：以字母开头，长度在6~16之间，只能包含字符、数字和下划线。
     *
     * @return
     */
    public static boolean isPwdCorrect(String input) {

        String regex = "^[a-zA-Z]\\w{5,15}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }


}
