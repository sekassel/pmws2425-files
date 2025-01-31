package de.uniks.pmws2425.nopm.controller;

import javafx.scene.Parent;

public interface Controller {
    String getTitle();

    void init();

    Parent render();

    void destroy();
}
