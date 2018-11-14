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

    @FXML
    private void handleNew(){
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    //FileChooser를 열어서 사용자가 가져올 주소록을 선택하게 한다.
    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        //확장자 필터를 보여준다.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        //File Dialog를 보여준다.
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null){
            mainApp.loadPersonDataFromFile(file);
        }
    }

    //현재 열려 있는 파일에 저장,없으면 Save as Dialog를 보여준다.
    @FXML
    private void handleSave(){
        File personFile = mainApp.getPersonFilePath();
        if(personFile != null){
            mainApp.savePersonDataToFile(personFile);
        }else{
            handleSaveAs();
        }
    }

    //FileChooser를 열어서 사용자가 저장할 파일을 선택하게 한다.
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // 확장자 필터를 설정한다.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Save File Dialog를 보여준다.
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // 정확한 확장자를 가져야 한다.
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    //About Dialog를 보여준다.
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Java 수행평가");
        alert.setHeaderText("About");
        alert.setContentText("넘모 잼미꼬");
        alert.showAndWait();
    }

    //애플리케이션을 닫는다.
    @FXML
    private void handleExit(){
        System.exit(0);
    }
}
