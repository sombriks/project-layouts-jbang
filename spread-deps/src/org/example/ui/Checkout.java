//DEPS org.openjfx:javafx-controls:21.0.9
//DEPS org.openjfx:javafx-fxml:21.0.8
//DEPS io.github.mkpaz:atlantafx-base:2.0.1

package org.example.ui;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import atlantafx.base.theme.PrimerDark;

public class Checkout extends Application {

    // language=FXML
    private static final String FXML_TEMPLATE = """
        <?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.geometry.Insets?>
        <?import javafx.scene.control.Button?>
        <?import javafx.scene.control.Label?>
        <?import javafx.scene.control.TextField?>
        <?import javafx.scene.layout.VBox?>

        <VBox alignment="CENTER" spacing="12" prefWidth="340" prefHeight="220"
              xmlns="http://javafx.com"
              xmlns:fx="http://javafx.com">
            <padding>
                <Insets top="20" right="20" bottom="20" left="20"/>
            </padding>
            <TextField fx:id="campoNome" promptText="Digite seu nome..." />
            <Label fx:id="saudacao" />
            <Button fx:id="botaoContar" text="Incrementar" onAction="#aoClicar" />
        </VBox>
        """;

    private final StringProperty nome = new SimpleStringProperty("");
    private final IntegerProperty cliques = new SimpleIntegerProperty(0);

    @FXML private TextField campoNome;
    @FXML private Label saudacao;
    @FXML private Button botaoContar;

    @FXML
    private void aoClicar() {
        cliques.set(cliques.get() + 1);
    }

    @FXML
    public void initialize() {
        campoNome.textProperty().bindBidirectional(nome);
        saudacao.textProperty().bind(nome.map(n -> n.isBlank() ? "Olá, visitante!" : "Olá, " + n + "!"));
        botaoContar.textProperty().bind(cliques.map(c -> "Cliques: " + c));
        botaoContar.disableProperty().bind(cliques.greaterThanOrEqualTo(5));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);

        try (var stream = new ByteArrayInputStream(FXML_TEMPLATE.getBytes(StandardCharsets.UTF_8))) {
            VBox root = loader.load(stream);
            stage.setTitle("JavaFX Single-File + this");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public static void main(String... args) {
        launch(args);
    }
}
