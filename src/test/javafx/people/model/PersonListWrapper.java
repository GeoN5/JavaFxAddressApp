package test.javafx.people.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//JAXB로 XML로 저장
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
