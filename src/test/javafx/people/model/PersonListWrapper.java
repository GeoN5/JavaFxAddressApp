package test.javafx.people.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//Person 리스트를 감싸는 헬퍼 클래스
//JAXB로 XML로 저장하는 데 사용된다.
@XmlRootElement(name = "Persons")
public class PersonListWrapper {

    private List<Person> persons;

    @XmlElement(name = "Person")
    public List<Person> getPersons(){
        return persons;
    }

    public void setPersons(List<Person> persons){
        this.persons = persons;
    }

}
