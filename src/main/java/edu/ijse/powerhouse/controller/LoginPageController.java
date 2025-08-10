package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.db.DBConnection;
import edu.ijse.powerhouse.util.AnimationsUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Pane mainCont;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblLogin;

    @FXML
    private AnchorPane ancMainPage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // animateLabelBlink();
        // animateLabelSlideIn();
        // animateLabelZoomIn();
        AnimationsUtil.TypeWriterEffect(lblLogin, "Log in . . .", Duration.millis(150));
        // addHoverAnimation(btnLogin);
        AnimationsUtil.AddFancyHoverAnimation(btnLogin, "#2f3640", "#353b48");
    }

    @FXML
    void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String inputUserName = txtUserName.getText();
        String inputPassword = txtPassword.getText();

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT user_Type_Id FROM Users  WHERE Username = ? AND password  = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, inputUserName);
            stmt.setString(2, inputPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (Objects.equals(inputUserName, "owner")) {
                    ancMainPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardOA.fxml"));
                    ancMainPage.getChildren().add(load);
                } else if (Objects.equals(inputUserName, "admin")) {
                    ancMainPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardOA.fxml"));
                    ancMainPage.getChildren().add(load);
                } else if (Objects.equals(inputUserName, "trainer")) {
                    ancMainPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardT.fxml"));
                    ancMainPage.getChildren().add(load);
                }
            } else {

                Image image = new Image(
                        getClass().getResourceAsStream("/images/florkofcows_icons-removebg-preview.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Failed");
                alert.setContentText("Invalid username or password.");
                alert.setGraphic(imageView);
                alert.show();
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Image image = new Image(
                    getClass().getResourceAsStream("/images/Screenshot_from_2025-06-10_17-10-53-removebg-preview.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(120);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong! Don't worry its not you--its us.");
            alert.setGraphic(imageView);
            alert.setTitle("Something wrong");
            alert.show();
        }
    }

    // private void animateLabelZoomIn() {
    // String loginText = lblLogin.getText();
    // lblLogin.setText(loginText);
    //
    // lblLogin.setScaleX(0.5);
    // lblLogin.setScaleY(0.5);
    // lblLogin.setOpacity(0);
    //
    // ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500),
    // lblLogin);
    // scaleTransition.setFromX(0.5);
    // scaleTransition.setFromY(0.5);
    // scaleTransition.setToX(1);
    // scaleTransition.setToY(1);
    //
    // FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500),
    // lblLogin);
    // fadeTransition.setFromValue(0);
    // fadeTransition.setToValue(1);
    //
    // ParallelTransition parallelTransition = new
    // ParallelTransition(scaleTransition, fadeTransition);
    // parallelTransition.play();
    // }

    // private void animateLabelSlideIn() {
    // String loginText = lblLogin.getText();
    // lblLogin.setText(loginText);
    // lblLogin.setOpacity(0);
    // lblLogin.setTranslateX(-50);
    //
    // TranslateTransition slide = new TranslateTransition(Duration.millis(2000),
    // lblLogin);
    // slide.setFromX(-100);
    // slide.setToX(0);
    //
    // FadeTransition fade = new FadeTransition(Duration.millis(2000), lblLogin);
    // fade.setFromValue(0);
    // fade.setToValue(1);
    //
    // ParallelTransition parallel = new ParallelTransition(slide, fade);
    // parallel.play();
    // }

    // private void animateLabelBlink() {
    // String loginText = lblLogin.getText();
    // lblLogin.setText(loginText);
    //
    // FadeTransition blink = new FadeTransition(Duration.millis(200), lblLogin);
    // blink.setFromValue(1.0);
    // blink.setToValue(0.0);
    // blink.setCycleCount(10);
    // blink.setAutoReverse(true);
    //
    // blink.play();
    // }

    // private void typewriterEffect(Label label, String message, Duration delay) {
    // label.setText("");
    //
    // Timeline timeline = new Timeline();
    // for (int i = 0; i < message.length(); i++) {
    // final int index = i;
    // KeyFrame keyFrame = new KeyFrame(delay.multiply(i),
    // e -> label.setText(label.getText() + message.charAt(index))
    // );
    // timeline.getKeyFrames().add(keyFrame);
    // }
    // timeline.play();
    // }

    // private void addHoverAnimation(Button button) {
    // button.setOnMouseEntered(e -> {
    // ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
    // st.setToX(1.1); // Zoom in
    // st.setToY(1.1);
    // st.play();
    // });
    //
    // button.setOnMouseExited(e -> {
    // ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
    // st.setToX(1.0); // Return to normal
    // st.setToY(1.0);
    // st.play();
    // });
    // }

    // public void addFancyHoverAnimation(Button button) {
    // button.setStyle("-fx-background-color: #2f3640; -fx-text-fill: white;
    // -fx-background-radius: 50;");
    // DropShadow glow = new DropShadow();
    // glow.setColor(Color.LIGHTBLUE);
    // glow.setRadius(15);
    // glow.setSpread(0.3);
    //
    // button.setOnMouseEntered(e -> {
    // ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
    // scaleUp.setToX(1.1);
    // scaleUp.setToY(1.1);
    //
    // button.setStyle("-fx-background-color: #2f3640; -fx-text-fill: white;
    // -fx-background-radius: 50;");
    //
    // button.setEffect(glow);
    // scaleUp.play();
    // });
    //
    // button.setOnMouseExited(e -> {
    // ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200),
    // button);
    // scaleDown.setToX(1.0);
    // scaleDown.setToY(1.0);
    //
    // button.setStyle("-fx-background-color: #2f3640; -fx-text-fill: white;
    // -fx-background-radius: 50;");
    // button.setEffect(null);
    // scaleDown.play();
    // });
    // }

    public void btnForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        mainCont.getChildren().clear();
        Pane load = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
        mainCont.getChildren().add(load);
    }

}
