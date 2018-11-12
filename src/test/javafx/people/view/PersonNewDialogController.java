package test.javafx.people.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import test.javafx.people.model.Person;
import test.javafx.people.util.DateUtil;

public class PersonNewDialogController {
    @FXML
    private TextField genderField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField birthField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    //컨트롤러 클래스 초기화
    //fxml 로드 후 자동 호출.
    @FXML
    private void initialize(){
    }

    //이 다이얼로그의 스테이지를 설정한다.
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        this.dialogStage.getIcons().add(new Image("file:src/images/dialog.png"));
    }

    //사람을 추가한다.
    public void setPerson(Person person){
        this.person = person;

        genderField.setPromptText("ex) 남자");
        nameField.setPromptText("ex) 홍길동");
        ageField.setPromptText("ex) 20");
        areaField.setPromptText("ex) 서울");
        phoneField.setPromptText("ex) 010-1234-5678");
        birthField.setPromptText("ex) yyyy.mm.dd");
    }

    //사용자가 OK를 클릭하면 true,그 외에는 false
    public boolean isOkClicked(){
        return okClicked;
    }

    //사용자가 OK를 클릭하면 호출된다.
    @FXML
    private void handleOk(){
        if(isInputValid()){
            person.setGender(genderField.getText());
            person.setName(nameField.getText());
            person.setAge(Integer.parseInt(ageField.getText()));
            person.setArea(areaField.getText());
            person.setPhone(phoneField.getText());
            person.setBirth(DateUtil.parse(birthField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    //사용자가 cancel을 클릭하면 호출된다.
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    //텍스트 필드로 사용자 입력을 검사한다.
    private boolean isInputValid(){
        String errorMessage = "";

        if(genderField.getText() == null || genderField.getText().length() == 0){
            errorMessage += "No valid gender!\n";
        }
        if(nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "No valid name!\n";
        }
        if(ageField.getText() == null || ageField.getText().length() == 0){
            errorMessage += "No valid age!\n";
        }else{
            //나이를 int 타입으로 변환한다.
            try{
                Integer.parseInt(ageField.getText());
            }catch (NumberFormatException e){
                errorMessage += "No valid age (must be an integer)!\n";
            }
        }

        if(areaField.getText() == null || areaField.getText().length() == 0){
            errorMessage += "No valid area!\n";
        }

        if(phoneField.getText() == null || phoneField.getText().length() == 0){
            errorMessage += "No valid phone!\n";
        }else{
            if(phoneField.getLength() != 13){
                errorMessage += "No valid phone. Please use '-'!\n";
            }
        }

        if(birthField.getText() == null || birthField.getText().length() == 0){
            errorMessage += "No valid birth!\n";
        }else{
            if(!DateUtil.validDate(birthField.getText())){
                errorMessage += "No valid birth. Use the format yyyy.mm.dd!\n";
            }
        }

        if(errorMessage.length() == 0){
            return true;
        }else{
            //오류 메세지를 보여준다.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
