package test.javafx.people;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import test.javafx.people.model.Person;
import test.javafx.people.model.PersonListWrapper;
import test.javafx.people.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    //유저 정보에 대한 observable 리스트
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp(){ }

    //연락처에 대한 observable 리스트 반환한다.
    public ObservableList<Person> getPersonData(){
        return  personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Java 수행평가(사용자 개인정보 관리)");
        this.primaryStage.getIcons().add(new Image("file:src/images/main.png"));

        initRootLayout();
        showPersonOverview();
    }

    //상위 레이아웃 초기화
    private void initRootLayout() {
        try {
            // fxml 파일에서 상위 레이아웃을 가져옴
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // 스테이지에 씬 설정
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //컨트롤러한테 자기 자신 넘김
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save 파일 로드
        File file = getPersonFilePath();
        if(file != null){
            loadPersonDataFromFile(file);
        }
    }

    //상위 레이아웃 안에 세부 정보를 보여준다.
    private void showPersonOverview() {
        try {
            // 세부 정보를 가져옴
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // rootLayout안에 setting
            rootLayout.setCenter(personOverview);

            //컨트롤러한테 자기 자신 넘김
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //메인 스테이지 반환
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //세부 정보 수정 다이얼로그 띄움
    //사용자가 OK를 클릭하면 파라미터 person 객체에 저장 후 true 반환
    public boolean showPersonEditDialog(Person person){
        try{
            //fxml 파일을 로드
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            //다이얼로그 스테이지를 만든다.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("정보 변경");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //컨트롤러 설정
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            //다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //유저 추가 다이얼로그 띄움
    //사용자가 OK를 클릭하면 파라미터 person 객체에 내용 저장 후 true 반환
    public boolean showPersonNewDialog(Person person){
        try{
            //fxml 파일을 로드
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonNewDialog.fxml"));
            AnchorPane page = loader.load();

            //다이얼로그 스테이지를 만든다.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("사용자 추가");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //컨트롤러 설정
            PersonNewDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            //다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //(key,value) OS 특정 레지스트리에 저장
    public void setPersonFilePath(File file){
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if(file!=null){
            prefs.put("filePath",file.getPath());
            primaryStage.setTitle("사용자 개인정보 관리 - "+file.getName());
        }else{
            prefs.remove("filePath");
            primaryStage.setTitle("사용자 개인정보 관리");
        }
    }

    //OS 특정 레지스트리로부터 읽고 파일을 찾지 못하면 null 반환
    public File getPersonFilePath(){
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath",null);
        if(filePath!=null){
            return new File(filePath);
        }else{
            return null;
        }
    }

    //유저 정보를 지정한 파일에 저장
    public void savePersonDataToFile(File file){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            //연락처 데이터를 감싼다.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);
            //마샬링 후 xml을 파일에 저장
            marshaller.marshal(wrapper,file);
            //파일 경로를 레지스트리에 저장한다.
            setPersonFilePath(file);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("에러");
            alert.setHeaderText("데이터 저장 실패.");
            alert.setContentText("데이터를 파일에 저장할 수 없습니다.\n" + file.getPath());
            alert.showAndWait();
        }
    }

    //파일로부터 유저 정보를 가져온다.
    public void loadPersonDataFromFile(File file){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            //java객체 -> xml 역마샬링
            PersonListWrapper wrapper = (PersonListWrapper)unmarshaller.unmarshal(file);
            personData.clear();
            personData.addAll(wrapper.getPersons());
            //파일 경로를 레지스트리에 저장한다.
            setPersonFilePath(file);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("에러");
            alert.setHeaderText("데이터 저장 실패.");
            alert.setContentText("데이터를 파일에 저장할 수 없습니다.\n" + file.getPath());
            alert.showAndWait();
        }
    }

    //생일 차트 로드
    public void showBirthdayChart(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayChart.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("생일 차트");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:src/images/birthday.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BirthdayChartController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //성별 차트 로드
    public void showGenderChart(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GenderChart.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("성별 차트");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:src/images/gender.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            GenderChartController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
