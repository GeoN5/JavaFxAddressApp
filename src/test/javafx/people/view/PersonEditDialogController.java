package test.javafx.people.view;

import javafx.scene.image.Image;
import test.javafx.people.model.Person;
import test.javafx.people.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//연락처 정보를 변경하는 다이얼로그
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

    //다이얼로그에서 변경될 연락처를 설정한다.
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

    //사용자 입력을 검사한다.
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
            //숫자 타입인지 검사
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
