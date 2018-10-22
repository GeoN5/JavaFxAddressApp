package ch.makery.address.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//날짜를 제어하는 헬퍼 함수들
public class DateUtil {

    //변환에 사용되는 날짜 패턴,원하는 대로 바꿔도 O
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    //날짜 변환기
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    //주어진 날짜를 String타입으로 반환한다.
    public static String format(LocalDate date){
        if(date==null){
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    //String을 DATE_PATTERN에 정의한 대로 LocalDate 객체로 변환한다.
    //String이 변환되지 않으면 null을 반환한다.
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
