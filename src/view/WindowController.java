package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import view_model.ViewModel;

public class WindowController {

    private StringProperty aileronV, elevatorV;
    private double sceneX, sceneY;
    private double translateX, translateY;
    ViewModel vmodel;

    @FXML
    Slider throttle;
    @FXML
    Slider rudder;
    @FXML
    private Circle joystick;
    @FXML
    private Circle frameCircle;

    @FXML
    private Label aileronLabel;
    @FXML
    private Label elevatorLabel;
    @FXML
    private Label throttleLabel;
    @FXML
    private Label rudderLabel;

    public WindowController(){
        joystick = new Circle();
        throttleLabel = new Label("0");
        rudderLabel = new Label("0");
        aileronLabel = new Label("0");
        elevatorLabel = new Label("0");

        aileronV = new SimpleStringProperty();
        elevatorV = new SimpleStringProperty();
    }

    public void setViewModel(ViewModel vmodel) {
        this.vmodel=vmodel;
        vmodel.throttle.bind(throttle.valueProperty());
        vmodel.rudder.bind(rudder.valueProperty());
        vmodel.aileron.bind(aileronV);
        vmodel.elevator.bind(elevatorV);
        joystick = new Circle();
    }

    @FXML
    private void joystickPressed(MouseEvent mouse) {
        Circle circle = (Circle) mouse.getSource();
        sceneX = mouse.getSceneX();
        sceneY = mouse.getSceneY();
        translateX = circle.getTranslateX();
        translateY = circle.getTranslateY();
    }
    
    private double normalize(double value) {
        return Math.round(((value * 2) - 1) * 100.00) / 100.00;
    }
    
    private void updateViewValues() {
        this.updateViewValues(0.0, 0.0);
    }
    
    private void updateViewValues(double x, double y) {
        aileronLabel.setText("" + x);
        aileronV.set("" + x);
        elevatorLabel.setText("" + y);
        elevatorV.set("" + y);
        /* Update the view */
        vmodel.setJoystickChanges();
    }
    
    @FXML
    private void joystickDragged(MouseEvent mouse) {
        Circle circle = (Circle) mouse.getSource();
        double offsetX = mouse.getSceneX() - sceneX;
        double offsetY = mouse.getSceneY() - sceneY;
        double newTranslateX = translateX + offsetX;
        double newTranslateY = translateY + offsetY;
        double joystickCenterX = this.getCenter("x");
        double joystickCenterY = this.getCenter("y");
        double frameRadius = frameCircle.getRadius();
        double maxX = joystickCenterX + frameRadius;
        double contractionsCenterX = joystickCenterX - frameRadius;
        double maxY = joystickCenterY - frameRadius;
        double contractionsCenterY = joystickCenterY + frameRadius;

        double slant = Math.sqrt(Math.pow(newTranslateX - joystickCenterX, 2) + Math.pow(newTranslateY - joystickCenterY, 2));

        if (slant > frameRadius) {
            double alpha = Math.atan((newTranslateY - joystickCenterY) / (newTranslateX - joystickCenterX));
            if ((newTranslateX - joystickCenterX) < 0) {
                alpha = alpha + Math.PI;
            }
            newTranslateX = Math.cos(alpha) * frameRadius + translateX;
            newTranslateY = Math.sin(alpha) * frameRadius + translateY;
        }
        circle.setTranslateX(newTranslateX);
        circle.setTranslateY(newTranslateY);
        double xrange = (newTranslateX - contractionsCenterX) / (maxX - contractionsCenterX);
        double normalizedX = this.normalize(xrange);

        double yrange = (newTranslateY - contractionsCenterY) / (maxY - contractionsCenterY);
        double normalizedY = this.normalize(yrange);

        updateViewValues(normalizedX, normalizedY);

    }

    @FXML
    private void joystickReleased(MouseEvent mouse) {
        Circle circle = (Circle) mouse.getSource();
        circle.setTranslateX(this.getCenter("x"));
        circle.setTranslateY(this.getCenter("y"));

    }
    
    public void registerDragListeners() {
        rudder.valueProperty().addListener((ChangeListener<Object>) (arg0, arg1, arg2) -> {
            rudderLabel.textProperty().setValue("" + (Math.round((rudder.getValue() * 10.00))) / 10.00);
            vmodel.setRudder();
        });

        throttle.valueProperty().addListener((ChangeListener<Object>) (arg0, arg1, arg2) -> {
            throttleLabel.textProperty().setValue("" + (Math.round((throttle.getValue() * 10.00))) / 10.00);
            vmodel.setThrottle();
        });
    }

    private double getCenter(String target) {
        double circleRadius  = frameCircle.getRadius();
        double joystickRadius  = joystick.getRadius();
        boolean isXScale = target.equals("x");
        double center = isXScale ? frameCircle.getTranslateX() : frameCircle.getTranslateY();
        return isXScale ? center + circleRadius - joystickRadius - 35 : center - circleRadius - joystickRadius - 35;
    }
}