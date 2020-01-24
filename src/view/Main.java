package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SimulatorModel;
import view_model.ViewModel;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage _primaryStage) {
        primaryStage = _primaryStage;
        SimulatorModel simModel = new SimulatorModel();
        ViewModel viewModel = new ViewModel(simModel);
        FXMLLoader fxl=new FXMLLoader();
        
        try {
            BorderPane root = fxl.load(getClass().getResource("Window.fxml").openStream());

            WindowController wcontroller=fxl.getController(); 
            wcontroller.setViewModel(viewModel);

            Scene scene = new Scene(root,1000,400);
            primaryStage.setScene(scene);
            primaryStage.show();
            wcontroller.registerDragListeners();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}