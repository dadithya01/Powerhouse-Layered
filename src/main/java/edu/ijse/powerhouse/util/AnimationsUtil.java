package edu.ijse.powerhouse.util;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AnimationsUtil {

    public static void AddFancyHoverAnimation(
            Button button,
            String backgroundColor,
            String glowHexColor
    ) {
        String baseStyle = String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 50;",
                backgroundColor
        );

        button.setStyle(baseStyle);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.web(glowHexColor));
        glow.setRadius(15);
        glow.setSpread(0.3);

        button.setOnMouseEntered(e -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);

            button.setStyle(baseStyle);
            button.setEffect(glow);
            scaleUp.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            button.setStyle(baseStyle);
            button.setEffect(null);
            scaleDown.play();
        });
    }

    public static void TypeWriterEffect(Label label, String message, Duration delay) {
        label.setText("");

        Timeline timeline = new Timeline();
        for (int i = 0; i < message.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(delay.multiply(i),
                    e -> label.setText(label.getText() + message.charAt(index))
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public static void AnimateLabelSlideIn(Label label) {
        label.setOpacity(0);
        label.setTranslateX(-50);

        TranslateTransition slide = new TranslateTransition(Duration.millis(2000), label);
        slide.setFromX(-100);
        slide.setToX(0);

        FadeTransition fade = new FadeTransition(Duration.millis(2000), label);
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition parallel = new ParallelTransition(slide, fade);
        parallel.play();
    }
}
