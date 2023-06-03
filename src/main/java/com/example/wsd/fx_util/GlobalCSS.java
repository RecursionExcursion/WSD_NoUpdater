package com.example.wsd.fx_util;

import javafx.scene.Scene;

public class GlobalCSS {

    public static void applyGlobalCSS(Scene scene){
        scene.getStylesheets().add(CssManager.INSTANCE.getCssUrl());
    }

}
