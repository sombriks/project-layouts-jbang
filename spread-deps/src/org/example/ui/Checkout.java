//DEPS org.openjfx:javafx-controls:21.0.9
//DEPS org.openjfx:javafx-fxml:21.0.8
//DEPS io.github.mkpaz:atlantafx-base:2.0.1

package org.example.ui;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.service.Orders;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class Checkout extends Application {

    private final Orders orders = new Orders();

    // language=FXML
    private static final String FXML_TEMPLATE = """
            <?xml version="1.0" encoding="UTF-8"?>
            <?import javafx.geometry.*?>
            <?import javafx.scene.control.*?>
            <?import javafx.scene.layout.*?>
            <HBox spacing="16" prefWidth="950" prefHeight="550"
                  xmlns="http://javafx.com"
                  xmlns:fx="http://javafx.com">
                <padding>
                    <Insets top="16" right="16" bottom="16" left="16"/>
                </padding>
                <VBox spacing="8" HBox.hgrow="ALWAYS" prefWidth="280">
                    <Label text="Pedidos (Orders)" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
                    <TextField fx:id="txtBuscarOrder" promptText="Filtrar por cliente..."/>
                    <ListView fx:id="listOrders" VBox.vgrow="ALWAYS"/>
                </VBox>
                <VBox spacing="12" HBox.hgrow="ALWAYS" prefWidth="340">
                    <VBox spacing="8">
                        <Label text="Detalhes do Pedido" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
                        <Label text="Cliente:"/>
                        <TextField fx:id="txtClient" promptText="Nome do cliente"/>
                        <HBox spacing="8">
                            <Button fx:id="btnNovoOrder" text="Novo" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                            <Button fx:id="btnSalvarOrder" text="Salvar" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                            <Button fx:id="btnExcluirOrder" text="Excluir" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                        </HBox>
                    </VBox>
                    <Separator/>
                    <VBox spacing="8" VBox.vgrow="ALWAYS">
                        <Label text="Itens do Pedido" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
                        <ListView fx:id="listItems" VBox.vgrow="ALWAYS"/>
                    </VBox>
                </VBox>
                <VBox spacing="8" HBox.hgrow="ALWAYS" prefWidth="280">
                    <Label text="Detalhes do Item" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
                    <Label text="Descrição:"/>
                    <TextField fx:id="txtItemDescription" promptText="Descrição do item"/>
                    <Label text="Quantidade:"/>
                    <TextField fx:id="txtItemQuantity" promptText="0"/>
                    <HBox spacing="8">
                        <Button fx:id="btnNovoItem" text="Novo" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                        <Button fx:id="btnSalvarItem" text="Salvar" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                        <Button fx:id="btnExcluirItem" text="Excluir" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                    </HBox>
                </VBox>
            </HBox>
            """;

    @FXML
    private void initialize() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        try (var stream = new ByteArrayInputStream(FXML_TEMPLATE.getBytes(StandardCharsets.UTF_8))) {
            HBox root = loader.load(stream);
            stage.setTitle("JavaFX + JBang example");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public static void main(String... args) {
        launch(args);
    }
}
