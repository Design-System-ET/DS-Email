package com.design_system.ds.email;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Main {

    @FXML
    private Label user_in;
    @FXML
    private Label node_in;

    public void initializeData(String user, String node) {
        user_in.setText(user);
        node_in.setText(node);
    }

 
}
