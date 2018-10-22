package ch.makery.address.view;

import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


//연락처 정보를 변경하는 다이얼로그
public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

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
    }

    //다이얼로그에서 변경될 연락처를 설정한다.
    public void setPerson(Person person){
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    //사용자가 OK를 클릭하면 true,그 외에는 false
    public boolean isOkClicked(){
        return okClicked;
    }

    //사용자가 OK를 클릭하면 호출된다.
    @FXML
    private void handleOk(){
        if(isInputValid()){
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

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

        if(firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "No valid first name!\n";
        }
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "No valid last name!\n";
        }
        if(streetField.getText() == null || streetField.getText().length() == 0){
            errorMessage += "No valid street!\n";
        }
        if(postalCodeField.getText() == null || postalCodeField.getText().length() == 0){
            errorMessage += "No valid postal code!\n";
        }else{
            //우편 번호를 int 타입으로 변환한다.
            try{
                Integer.parseInt(postalCodeField.getText());
            }catch (NumberFormatException e){
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if(cityField.getText() == null || cityField.getText().length() == 0){
            errorMessage += "No valid city!\n";
        }

        if(birthdayField.getText() == null || birthdayField.getText().length() == 0){
            errorMessage += "No valid birthday!\n";
        }else{
            if(!DateUtil.validDate(birthdayField.getText())){
                errorMessage += "No valid birtyday. Use the format dd.mm.yyyy!\n";
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
