package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import javafx.event.ActionEvent;

public class NewStartupViewController {

    private StartUp startUp;

  public void init(StartUp startUp){
      this.startUp = startUp;
  }

    public void buttonAction(ActionEvent actionEvent) {
        System.out.println(startUp.getName());
    }
}
