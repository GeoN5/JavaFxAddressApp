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

    //initialize() 이전 호출
    public PersonOverviewController(){

    }

    //컨트롤러 클래스 초기화
    //fxml 파일이 로드되고 나서 자동으로 호출
    @FXML
    private void initialize(){
        //Person 객체의 어떤 필드를 각 열에 사용할지 결정
        //테이블뷰의 두 열을 초기화
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //세부 정보 삭제
        showPersonDetails(null);

        //선택을 감지하고 그 때마다 연락처의 세부 정보 로드
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    //참조를 다시 유지하기 위해 메인 애플리케이션이 호출
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        //테이블에 observable 리스트 데이터 추가
        personTable.setItems(mainApp.getPersonData());
    }

    //세부 정보를 보여주기 위해 모든 텍스트 필드를 채움
    //만일 person객체가 null이면 모든 텍스트 필드 지움
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

    //사용자가 new 버튼을 클릭할 때 호출
    //새로운 연락처 정보를 넣기 위해 다이얼로그를 띄움.
    @FXML
    private void handleNewPerson(){
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonNewDialog(tempPerson);
        if(okClicked){
            mainApp.getPersonData().add(tempPerson);
        }
    }

    //사용자가 edit 버튼을 클릭할 때 호출
    //선택한 연락처 정보를 변경하기 위해 다이얼로그를 띄움.
    @FXML
    private void handleEditPerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPerson != null){
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if(okClicked){
                showPersonDetails(selectedPerson);
            }
        }else{
            //아무것도 선택하지 않았을때
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("선택되지 않음");
            alert.setHeaderText("선택된 사람이 없습니다.");
            alert.setContentText("사람을 선택해주세요.");

            alert.showAndWait();
        }
    }

    //사용자가 삭제 버튼을 누르면 호출
    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0) {
            personTable.getItems().remove(selectedIndex);
        }else{
            //아무것도 선택하지 않았을때
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("선택되지 않음");
            alert.setHeaderText("선택된 사람이 없습니다");
            alert.setContentText("사람을 선택해주세요.");
            alert.showAndWait();
        }
    }

}
