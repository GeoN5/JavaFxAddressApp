package test.javafx.people.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import test.javafx.people.MainApp;

import java.io.File;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    //메뉴 New 클릭하면 새창 로드
    @FXML
    private void handleNew(){
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    //사용자가 가져올 파일을 선택
    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        //확장자 필터를 보여줌
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        //File Dialog를 보여준다.
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null){
            mainApp.loadPersonDataFromFile(file);
        }
    }

    //현재 열려 있는 파일에 저장,null이면 Save as Dialog를 띄움
    @FXML
    private void handleSave(){
        File personFile = mainApp.getPersonFilePath();
        if(personFile != null){
            mainApp.savePersonDataToFile(personFile);
        }else{
            handleSaveAs();
        }
    }

    //다른 이름으로 저장
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // 확장자 필터를 보여줌
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        // Save File Dialog를 보여준다.
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // xml 확장자 처리
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    //에플리케이션 종료
    @FXML
    private void handleExit(){
        System.exit(0);
    }

    //생일 차트
    @FXML
    private void handleShowBirthdayChart(){
        mainApp.showBirthdayChart();
    }

    //성별 차트
    @FXML
    private void handleShowGenderChart(){
        mainApp.showGenderChart();
    }

    //About Dialog 띄움
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Java 수행평가");
        alert.setHeaderText(null);
        alert.setContentText("넘모 잼미꼬");
        alert.showAndWait();
    }
}
