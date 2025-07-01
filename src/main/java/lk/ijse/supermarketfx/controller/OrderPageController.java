package lk.ijse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.supermarketfx.Dto.ItemDto;
import lk.ijse.supermarketfx.Dto.OrderDetailsDto;
import lk.ijse.supermarketfx.Dto.OrderDto;
import lk.ijse.supermarketfx.Dto.tm.CartTM;
import lk.ijse.supermarketfx.model.CustomerModel;
import lk.ijse.supermarketfx.model.ItemModel;
import lk.ijse.supermarketfx.model.OrederModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {
    private final OrederModel orederModel = new OrederModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();
    private final ObservableList<CartTM> cartData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();
        String cartQtyString = txtAddToCartQty.getText();

        if (selectedItemId == null) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please select item..!"
            ).show();
            return;
        }

        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please enter valid quantity..!"
            ).show();
            return;
        }

        int cartQty = Integer.parseInt(cartQtyString);
        int itemStockQty = Integer.parseInt(lblItemQty.getText());

        // 10 < 15
        if (itemStockQty < cartQty) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Not enough item quantity..!"
            ).show();
            return;
        }

        String itemName = lblItemName.getText();
        double itemUnitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = itemUnitPrice * cartQty;

        for (CartTM cartTM : cartData) {
            if (cartTM.getItemId().equals(selectedItemId)) {
                // 20 + 10
                int newQty = cartTM.getCartQty() + cartQty;

                if (itemStockQty < newQty) {
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Not enough item quantity..!"
                    ).show();
                    return;
                }

                cartTM.setCartQty(newQty);
                cartTM.setTotal(newQty * itemUnitPrice);

                txtAddToCartQty.setText("");
                tblCart.refresh();

                return;
            }
        }

        Button removeBtn = new Button("Remove");
        CartTM cartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                itemUnitPrice,
                total,
                removeBtn
        );

        removeBtn.setOnAction(action -> {
            cartData.remove(cartTM);
            tblCart.refresh();
        });

        txtAddToCartQty.setText("");
        cartData.add(cartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (tblCart.getItems().isEmpty()) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please add items to cart..!"
            ).show();
            return;
        }

        if (cmbCustomerId.getValue().isEmpty()) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please select customer for place order..!"
            ).show();
            return;
        }

        String selectedCustomerId = cmbCustomerId.getValue();
        String orderId = lblOrderId.getText();
        Date date = Date.valueOf(orderDate.getText());

        ArrayList<OrderDetailsDto> cartList = new ArrayList<>();

        for (CartTM cartTM : cartData) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQty(),
                    cartTM.getUnitPrice()
            );
            cartList.add(orderDetailsDto);
        }

        OrderDto orderDto = new OrderDto(
                orderId,
                selectedCustomerId,
                date,
                cartList
        );

        try {
            boolean isPlaced = orederModel.placeOrder(orderDto);

            if (isPlaced) {
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to place order..!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to place order..!").show();
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
      String selectedId = cmbCustomerId.getSelectionModel().getSelectedItem();
      String name = customerModel.findNameById(selectedId);

      lblCustomerName.setText(name);

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
      String selectedId = cmbItemId.getSelectionModel().getSelectedItem();
     ItemDto itemDto =  itemModel.findById(selectedId);

      if(itemDto != null) {
          lblItemName.setText(itemDto.getName());
          lblItemQty.setText(String.valueOf(itemDto.getQtyOnHand()));
          lblItemPrice.setText(String.valueOf(itemDto.getUnitPrice()));
      }else {
          lblItemName.setText("");
          lblItemQty.setText("");
          lblItemPrice.setText("");
      }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btmRemove"));

        tblCart.setItems(cartData);

        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();
            e.printStackTrace();
        }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(orederModel.getNextId());
       // orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemIds();
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemIdsList = itemModel.getAllItemIds();
        ObservableList<String> itemIds = FXCollections.observableArrayList();
        itemIds.addAll(itemIdsList);
        cmbItemId.setItems(itemIds);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIdsList = customerModel.getAllCustomerIds();
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        customerIds.addAll(customerIdsList);
        cmbCustomerId.setItems(customerIds);


    }
}
