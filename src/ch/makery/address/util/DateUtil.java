package ch.makery.address.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//날짜를 제어하는 헬퍼 함수들
public class DateUtil {

    //변환에 사용되는 날짜 패턴,원하는 대로 바꿔도 O
    private static final String DATE_PATTERN = "yyyy.MM.dd";

    //날짜 변환기
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    //LocalDate -> String.
    public static String format(LocalDate date){
        if(date==null){
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    //String -> LocalDate
    public static LocalDate parse(String dateString){
        try {
            return DATE_FORMATTER.parse(dateString,LocalDate::from);
        }catch (DateTimeException e){
            return null;
        }
    }

    //유효한 날짜인지 검사한다.
    public static boolean validDate(String dateString){
        return DateUtil.parse(dateString)!=null;
    }

}
