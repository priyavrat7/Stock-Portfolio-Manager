#!/bin/bash
cd /Users/priv/Codes_On_Git/GUI/JavaFx
java --add-modules javafx.controls,javafx.fxml \
     --add-opens javafx.controls/javafx.scene.control=ALL-UNNAMED \
     --add-opens javafx.base/javafx.beans=ALL-UNNAMED \
     -classpath target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout) \
     com.mcgill.application.Main
