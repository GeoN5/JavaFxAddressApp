package test.javafx.people.util;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

//LocalDate와 xml간의 변환을 하는 JAXB 어댑터
public class LocalDateAdapter extends XmlAdapter<String,LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
