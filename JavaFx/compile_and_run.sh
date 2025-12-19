#!/bin/bash
cd /Users/priv/Codes_On_Git/GUI/JavaFx

# Compile with JavaFX on classpath
javac --class-path "target/classes:$HOME/.m2/repository/org/openjfx/javafx-controls/22/javafx-controls-22.jar:$HOME/.m2/repository/org/openjfx/javafx-controls/22/javafx-controls-22-mac-aarch64.jar:$HOME/.m2/repository/org/openjfx/javafx-graphics/22/javafx-graphics-22.jar:$HOME/.m2/repository/org/openjfx/javafx-graphics/22/javafx-graphics-22-mac-aarch64.jar:$HOME/.m2/repository/org/openjfx/javafx-base/22/javafx-base-22.jar:$HOME/.m2/repository/org/openjfx/javafx-base/22/javafx-base-22-mac-aarch64.jar" -d target/classes src/com/mcgill/application/*.java src/com/mcgill/application/controller/*.java src/com/mcgill/application/database/*.java src/com/mcgill/application/model/*.java src/com/mcgill/application/repository/*.java src/com/mcgill/application/service/*.java

echo "Compilation complete. Check target/classes directory"
