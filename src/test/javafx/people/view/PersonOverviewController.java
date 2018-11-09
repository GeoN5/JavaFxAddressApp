package test.javafx.people.view;

import test.javafx.people.MainApp;
import test.javafx.people.model.Person;
import test.javafx.people.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person,String> genderColumn;
    @FXML
    private TableColumn<Person,String> nameColumn;

    @FXML
    private Label genderLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label areaLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label birthLabel;

    //메인 에플리케이션 참조
    private MainApp mainApp;

    //생성자 initialize()메소드 이전에 호출된다.
    public PersonOverviewController(){

    }

    //컨트롤러 클래스를 초기화한다.
    //fxml 파일이 로드되고 나서 자동으로 호출된다.
    @FXML
    private void initialize(){
        //Person 객체의 어떤 필드를 각 열에 사용할지 결정
        //연락처 테이블의 두 열을 초기화한다.
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //연락처 정보를 지운다.
        showPersonDetails(null);

        //선택을 감지하고 그 때마다 연락처의 자세한 정보를 알려준다.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    //참조를 다시 유지하기 위해 메인 애플리케이션이 호출한다.
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        //테이블에 observable 리스트 데이터를 추가한다.
        personTable.setItems(mainApp.getPersonData());
    }

    //연락처의 자세한 정보를 보여주기 위해 모든 텍스트 필드를 채운다.
    //만일 person객체가 null이면 모든 텍스트 필드가 지워진다.
    private void showPersonDetails(Person person){
        if(person!=null){
            //person 객체로 label에 정보를 채운다.
            genderLabel.setText(person.getGender());
            nameLabel.setText(person.getName());
            ageLabel.setText(Integer.toString(person.getAge()));
            areaLabel.setText(person.getArea());
            phoneLabel.setText(person.getPhone());
            birthLabel.setText(DateUtil.format(person.getBirth()));
        }else{
            //person이 null이면 모든 텍스트를 지운다.
            genderLabel.setText("");
            nameLabel.setText("");
            ageLabel.setText("");
            areaLabel.setText("");
            phoneLabel.setText("");
            birthLabel.setText("");
        }
    }

    //사용자가 new 버튼을 클릭할 때 호출된다.
    //새로운 연락처 정보를 넣기 위해 다이얼로그를 연다.
    @FXML
    private void handleNewPerson(){
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonNewDialog(tempPerson);
        if(okClicked){
            mainApp.getPersonData().add(tempPerson);
        }
    }

    //사용자가 edit 버튼을 클릭할 때 호출된다.
    //선택한 연락처 정보를 변경하기 위해 다이얼로그를 연다.
    @FXML
    private void handleEditPerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPerson != null){
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if(okClicked){
                showPersonDetails(selectedPerson);
            }
        }else{
            //아무것도 선택하지 않았다.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    //사용자가 삭제 버튼을 누르면 호출된다.
    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0) {
            personTable.getItems().remove(selectedIndex);
        }else{
            //아무것도 선택하지 않았다.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table");
            alert.showAndWait();
        }
    }

}
