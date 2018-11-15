package test.javafx.people.util;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

//LocalDate와 xml간의 변환을 하는 JAXB 어댑터
public class LocalDateAdapter extends XmlAdapter<String,LocalDate> {

    @Override
    public String marshal(LocalDate v){
        return v.toString();
    }

    @Override
    public LocalDate unmarshal(String v){
        return LocalDate.parse(v);
    }

}
