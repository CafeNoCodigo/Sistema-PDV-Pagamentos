TO CREATE RUNTIME FILES
jlink.exe ^ ----CHANGE WITH YOUR JLINK FOLDER DESTINATION----
--module-path "%JAVA_HOME%\jmods;C:\javafx-jmods-21.0.7" ^
--add-modules java.base,java.sql,java.naming,java.desktop,java.logging,java.xml,javafx.controls,javafx.fxml,javafx.graphics,javafx.swing ^
--output C:\MinhaApp\runtime ^
--strip-debug --compress=2 --no-header-files --no-man-pages

TO CREATE EXE FILE
jpackage.exe ^ ----CHANGE WITH YOUR JPACKAGE FOLDER DESTINATION----
--name FPS_COMMERCE ^
--app-version 1.0 ^
--type exe ^
--input "C:\MinhaApp" ^
--main-jar FPS_COMMERCE.jar ^
--main-class com.minhaloja.sistema_pagamento.App ^
--runtime-image "C:\MinhaApp\runtime" ^
--icon "C:\MinhaApp\carinho.ico" ^
--dest "C:\MinhaApp\dist" ^
--win-shortcut ^
--win-menu ^
--win-dir-chooser
