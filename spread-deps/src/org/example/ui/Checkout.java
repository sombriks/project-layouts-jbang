//DEPS org.openjfx:javafx-controls:21.0.9
//DEPS org.openjfx:javafx-fxml:21.0.8
//DEPS io.github.mkpaz:atlantafx-base:2.0.1

package org.example.ui;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.model.Item;
import org.example.model.Order;
import org.example.service.Orders;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class Checkout extends Application {

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
                            <Button fx:id="btnLimparOrder" text="Limpar" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
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
                        <Button fx:id="btnLimparItem" text="Limpar" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                        <Button fx:id="btnSalvarItem" text="Salvar" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                        <Button fx:id="btnExcluirItem" text="Excluir" maxWidth="Infinity" HBox.hgrow="ALWAYS"/>
                    </HBox>
                </VBox>
            </HBox>
            """;

    private final Orders orders = new Orders();

    private final ObservableList<Order> masterOrders = FXCollections.observableArrayList();
    private final ObjectProperty<Order> currentOrder = new SimpleObjectProperty<>(novoOrderVazio());
    private final ObjectProperty<Item> currentItem = new SimpleObjectProperty<>(novoItemVazio());
    private Order savedOrder = novoOrderVazio();

    @FXML
    private TextField txtBuscarOrder;
    @FXML
    private ListView<Order> listOrders;
    @FXML
    private TextField txtClient;
    @FXML
    private Button btnLimparOrder;
    @FXML
    private Button btnSalvarOrder;
    @FXML
    private Button btnExcluirOrder;
    @FXML
    private ListView<Item> listItems;
    @FXML
    private TextField txtItemDescription;
    @FXML
    private TextField txtItemQuantity;
    @FXML
    private Button btnLimparItem;
    @FXML
    private Button btnSalvarItem;
    @FXML
    private Button btnExcluirItem;

    @FXML
    private void initialize() {
        configurarCelulas();
        configurarFluxoReativo();
        configurarAcoesOrder();
        configurarAcoesItem();

        masterOrders.setAll(orders.list(""));
    }

    private void configurarCelulas() {
        listOrders.setCellFactory(criarCelulas(
                order -> order.client() + " ("
                        + (order.items() == null ? 0 : order.items().size()) + " itens)"
        ));
        listItems.setCellFactory(criarCelulas(
                item -> item.quantity() + "x " + item.description()
        ));
    }

    private <T> Callback<ListView<T>, ListCell<T>> criarCelulas(Function<T, String> formatar) {
        return lv -> new ListCell<>() {
            @Override
            protected void updateItem(T valor, boolean empty) {
                super.updateItem(valor, empty);
                setText(empty || valor == null ? null : formatar.apply(valor));
            }
        };
    }

    private void configurarFluxoReativo() {
        FilteredList<Order> filteredOrders = new FilteredList<>(masterOrders);
        filteredOrders.predicateProperty().bind(
                txtBuscarOrder.textProperty().map(this::criarFiltroCliente)
        );
        listOrders.setItems(filteredOrders);
        listOrders.getSelectionModel().selectedItemProperty().subscribe(selecionado -> {
            if (selecionado != null) {
                savedOrder = selecionado;
                currentOrder.set(selecionado);
                prepararNovoItem();
            }
        });
        currentOrder.map(Order::client)
                .orElse("")
                .subscribe(txtClient::setText);
        listItems.itemsProperty().bind(
                currentOrder
                        .map(order -> order.items() == null ? List.<Item>of() : List.copyOf(order.items()))
                        .map(FXCollections::observableArrayList)
                        .orElse(FXCollections.emptyObservableList()));
        listItems.getSelectionModel().selectedItemProperty().subscribe(selecionado -> {
            if (selecionado != null) {
                currentItem.set(selecionado);
            }
        });
        currentItem.map(Item::description)
                .orElse("")
                .subscribe(txtItemDescription::setText);
        currentItem.map(item -> String.valueOf(item.quantity()))
                .subscribe(txtItemQuantity::setText);
    }

    private void configurarAcoesOrder() {
        btnSalvarOrder.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> txtClient.getText() == null || txtClient.getText().isBlank(),
                        txtClient.textProperty()
                )
        );
        btnExcluirOrder.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> savedOrder == null || !masterOrders.contains(savedOrder),
                        currentOrder, masterOrders
                )
        );
        btnLimparOrder.setOnAction(e -> prepararNovoOrder());
        btnSalvarOrder.setOnAction(e -> {
            if (savedOrder != null && masterOrders.contains(savedOrder)) {
                orders.remove(savedOrder);
            }
            Set<Item> itensAtuais = new HashSet<>(listItems.getItems());
            Order salvo = new Order(txtClient.getText().trim(), itensAtuais);
            orders.save(salvo);
            savedOrder = salvo;

            txtBuscarOrder.setText("");
            masterOrders.setAll(orders.list(""));
            prepararNovoOrder();
        });
        btnExcluirOrder.setOnAction(e -> {
            if (savedOrder != null && masterOrders.contains(savedOrder)) {
                orders.remove(savedOrder);
                masterOrders.setAll(orders.list(""));
                prepararNovoOrder();
            }
        });
    }

    private void configurarAcoesItem() {
        btnSalvarItem.disableProperty().bind(
                Bindings.createBooleanBinding(
                        this::itemInvalido,
                        txtItemDescription.textProperty(),
                        txtItemQuantity.textProperty()
                )
        );
        btnExcluirItem.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> currentItem.get() == null
                                || listItems.getItems() == null
                                || !listItems.getItems().contains(currentItem.get()),
                        currentItem,
                        listItems.itemsProperty()
                )
        );
        btnLimparItem.setOnAction(e -> prepararNovoItem());
        btnSalvarItem.setOnAction(e -> {
            if (itemInvalido()) {
                return;
            }
            Item antigo = currentItem.get();
            Set<Item> itens = new HashSet<>(listItems.getItems());
            if (antigo != null) {
                itens.remove(antigo);
            }
            Item salvo = new Item(
                    txtItemDescription.getText().trim(),
                    Integer.parseInt(txtItemQuantity.getText().trim())
            );
            itens.add(salvo);
            atualizarItensDoPedido(itens);
            prepararNovoItem();
        });
        btnExcluirItem.setOnAction(e -> {
            Item alvo = currentItem.get();
            if (alvo != null && listItems.getItems().contains(alvo)) {
                Set<Item> itens = new HashSet<>(listItems.getItems());
                itens.remove(alvo);
                atualizarItensDoPedido(itens);
                prepararNovoItem();
            }
        });
    }

    private boolean itemInvalido() {
        String descricao = txtItemDescription.getText();
        String quantidade = txtItemQuantity.getText();
        if (descricao == null || descricao.isBlank() || quantidade == null || quantidade.isBlank()) {
            return true;
        }
        try {
            return Integer.parseInt(quantidade.trim()) < 1;
        } catch (NumberFormatException excecao) {
            return true;
        }
    }

    private void atualizarItensDoPedido(Set<Item> itens) {
        currentOrder.set(new Order(
                txtClient.getText().trim(),
                itens
        ));
    }

    private void prepararNovoOrder() {
        listOrders.getSelectionModel().clearSelection();
        savedOrder = novoOrderVazio();
        currentOrder.set(novoOrderVazio());
        txtClient.clear();
        prepararNovoItem();
        txtClient.requestFocus();
    }

    private void prepararNovoItem() {
        listItems.getSelectionModel().clearSelection();
        currentItem.set(novoItemVazio());
        txtItemDescription.clear();
        txtItemQuantity.clear();
        txtItemDescription.requestFocus();
    }

    private Order novoOrderVazio() {
        return new Order("", Set.of());
    }

    private Item novoItemVazio() {
        return new Item("", 0);
    }

    private Predicate<Order> criarFiltroCliente(String query) {
        return order -> query == null || query.isBlank()
                || (order.client() != null && order.client().toLowerCase().contains(query.toLowerCase()));
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
