package test.javafx.people.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    //날짜 패턴,원하는 대로 바꿔도 O
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

    //유효성 검사
    public static boolean validDate(String dateString){
        return parse(dateString)!=null;
    }

}
