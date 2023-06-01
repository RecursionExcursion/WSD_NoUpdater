package com.example.wsd.fx_nodes;

import javafx.scene.control.Button;

public class ButtonFactory {
        public static Button createButton(String name) {
            Button newButton = new Button(name);
            newButton.setPrefWidth(60);
            return newButton;
        }
}
