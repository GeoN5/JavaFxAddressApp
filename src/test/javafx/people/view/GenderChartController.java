package test.javafx.people.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import test.javafx.people.model.Person;

import java.util.List;

public class GenderChartController {

    @FXML
    private BarChart<String,Integer> barChart;
    @FXML //수평축
    private CategoryAxis xAxis;

    private ObservableList<String> genders = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        genders.add("남자");
        genders.add("여자");
        xAxis.setCategories(genders);
    }

    public void setPersonData(List<Person> persons){
        int[] genderCounter = new int[2];
        for(Person p : persons){
            if(p.getGender().equals("남자")){
                genderCounter[0]++;
            }else{
                genderCounter[1]++;
            }
        }

        XYChart.Series<String,Integer> series = new XYChart.Series<>();

        for(int i=0; i < genderCounter.length; i++){
            series.getData().add(new XYChart.Data<>(genders.get(i),genderCounter[i]));
        }
        barChart.getData().add(series);
    }
}
