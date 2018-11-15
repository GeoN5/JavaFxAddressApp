package test.javafx.people.model;

import javafx.beans.property.*;
import test.javafx.people.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

//프로그램에서 사용되는 실제 데이터 및 데이터 조작 로직을 처리하는 부분
//Model은 프로그램에서 사용되는 실제 데이터이며 불러오거나 업데이트 하는 로직이 추가

//세부 정보 모델 클래스
public class Person {
    private StringProperty gender;
    private StringProperty name;
    private IntegerProperty age;
    private StringProperty area;
    private StringProperty phone;
    private ObjectProperty<LocalDate> birth;

    public Person(){
        this(null,null,1,null,null,LocalDate.of(1234,1,1));
    }

    //데이터를 초기화하는 생성자
    public Person(String gender, String name,int age,String area,String phone,LocalDate birth){
        this.gender = new SimpleStringProperty(gender);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.area = new SimpleStringProperty(area);
        this.phone = new SimpleStringProperty(phone);
        this.birth = new SimpleObjectProperty<>(birth);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
    public String getGender() {
        return gender.get();
    }
    public StringProperty genderProperty() {
        return gender;
    }


    public void setName(String name) {
        this.name.set(name);
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }


    public void setAge(int age) {
        this.age.set(age);
    }
    public int getAge() {
        return age.get();
    }
    public IntegerProperty ageProperty() {
        return age;
    }


    public void setArea(String area) {
        this.area.set(area);
    }
    public String getArea() {
        return area.get();
    }
    public StringProperty areaProperty() {
        return area;
    }


    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public String getPhone() {
        return phone.get();
    }
    public StringProperty phoneProperty() {
        return phone;
    }


    public void setBirth(LocalDate birth) {
        this.birth.set(birth);
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirth() {
        return birth.get();
    }
    public ObjectProperty<LocalDate> birthProperty() {
        return birth;
    }

}

