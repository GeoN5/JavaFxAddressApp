package test.javafx.people.view;

import javafx.scene.image.Image;
import test.javafx.people.model.Person;
import test.javafx.people.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PersonEditDialogController {
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

    @FXML
    private void initialize(){ }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        this.dialogStage.getIcons().add(new Image("file:src/images/dialog.png"));
    }

    //다이얼로그에서 변경될 연락처를 설정
    public void setPerson(Person person){
        this.person = person;

        genderField.setText(person.getGender());
        nameField.setText(person.getName());
        ageField.setText(Integer.toString(person.getAge()));
        areaField.setText(person.getArea());
        phoneField.setText(person.getPhone());
        birthField.setText(DateUtil.format(person.getBirth()));
    }

    //사용자가 OK를 클릭하면 true,그 외에는 false
    public boolean isOkClicked(){
        return okClicked;
    }

    //사용자가 OK를 클릭하면 호출
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

    //사용자가 cancel을 클릭하면 호출
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    //사용자 입력 검사
    private boolean isInputValid(){
        String errorMessage = "";

        if(genderField.getText() == null || genderField.getText().length() == 0){
            errorMessage += "성별을 입력해주세요!\n";
        }
        if(nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "이름을 입력해주세요!\n";
        }
        if(ageField.getText() == null || ageField.getText().length() == 0){
            errorMessage += "나이를 입력해주세요!\n";
        }else{
            //숫자 타입인지 검사
            try{
                Integer.parseInt(ageField.getText());
            }catch (NumberFormatException e){
                errorMessage += "나이는 정수여야 합니다!\n";
            }
        }

        if(areaField.getText() == null || areaField.getText().length() == 0){
            errorMessage += "지역을 입력해주세요!\n";
        }

        if(phoneField.getText() == null || phoneField.getText().length() == 0){
            errorMessage += "전화번호를 입력해주세요!\n";
        }else{
            if(phoneField.getLength() != 13){
                errorMessage += "010-1234-5678 형식이어야 합니다!\n";
            }
        }

        if(birthField.getText() == null || birthField.getText().length() == 0){
            errorMessage += "생년월일을 입력해주세요!\n";
        }else{
            if(!DateUtil.validDate(birthField.getText())){
                errorMessage += "1234.01.01 형식이어야 합니다!\n";
            }
        }

        if(errorMessage.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("잘못된 입력");
            alert.setHeaderText("올바르게 입력해주세요.");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
