package com.example.wsd.fx_nodes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class HBoxFactory {

    public static HBox createHbox(Node... nodes) {
        HBox newHbox = new HBox(nodes);
        newHbox.alignmentProperty().setValue(Pos.CENTER);
        newHbox.setSpacing(20);
        return newHbox;
    }
}
