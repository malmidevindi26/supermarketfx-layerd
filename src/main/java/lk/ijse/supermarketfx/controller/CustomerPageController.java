package lk.ijse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.supermarketfx.Dto.CustomerDto;
import lk.ijse.supermarketfx.Dto.tm.CustomerTM;
import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.model.CustomerModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class CustomerPageController implements Initializable {
    public Label lblID;
    public TextField txtName;
    public TextField txtNIC;
    public TextField txtMail;
    public TextField txtPhone;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

   private final CustomerModel customerModel = new CustomerModel();

    public TableView <CustomerTM> tblCustomer;
    public TableColumn <CustomerTM, String> colId;
    public TableColumn <CustomerTM, String> colName;
    public TableColumn <CustomerTM, String> colNic;
    public TableColumn <CustomerTM, String> colEmail;
    public TableColumn <CustomerTM, String> colPhone;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         //C001
        //lblID.setText("C001");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
//            loadNextId();
//            loadTableData();
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();


        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//       ArrayList<CustomerDto> customerDtoArrayList =  customerModel.getAllCustomers();
//        ObservableList<CustomerTM> list = FXCollections.observableArrayList();
//
//        for(CustomerDto customerDto : customerDtoArrayList){
//            CustomerTM customerTM = new CustomerTM(
//                    customerDto.getCustomerID(), customerDto.getName(),customerDto.getNic(),
//                    customerDto.getEmail(), customerDto.getPhone()
//            );
//            list.add(customerTM);
//        }
//       tblCustomer.setItems(list);

        tblCustomer.setItems((FXCollections.observableArrayList(
                customerModel.getAllCustomers().stream().map(customerDto ->
                        new CustomerTM(
                                customerDto.getCustomerID(),
                                customerDto.getName(),
                                customerDto.getNic(),
                                customerDto.getEmail(),
                                customerDto.getPhone()
                        )).toList()
        )));

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = customerModel.getNextId();
        lblID.setText(nextId);
    }

    public void btnCustmerSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
          String customerID = lblID.getText();
          String name = txtName.getText();
          String nic = txtNIC.getText();
          String mail = txtMail.getText();
          String phone = txtPhone.getText();

          boolean isValidName = name .matches(namePattern);
          boolean isValidNic = nic.matches(nicPattern);
          boolean isValidMail = mail.matches(emailPattern);
          boolean isValidPhone = phone.matches(phonePattern);

          txtName.setStyle(txtName.getStyle() + ";-fx-border-color: yellow");
          txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: yellow");
          txtMail.setStyle(txtMail.getStyle() + ";-fx-border-color: yellow");
          txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: yellow");

          if(!isValidName){
              txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red");
          }
          if(!isValidNic){
              txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: red");
          }
          if(!isValidMail){
              txtMail.setStyle(txtMail.getStyle() + ";-fx-border-color: red");
          }
          if(!isValidPhone){
              txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red");
          }

        CustomerDto customerDto = new CustomerDto(customerID, name, nic, mail, phone);

if (isValidName&&isValidNic&&isValidMail&&isValidPhone){
    try {
        boolean isSave = customerModel.saveCustomer(customerDto);
        if (isSave) {
//
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "customer save successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "customer save failed").show();
        }
    }catch (SQLException e){
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Fail to save customer").show();
    }
}

//       try {
//           boolean isSave = customerModel.saveCustomer(customerDto);
//           if (isSave) {
//               loadNextId();
//               loadTableData();
//
//               txtName.setText("");
//               txtNIC.setText("");
//               txtMail.setText("");
//               txtPhone.setText("");
//               new Alert(Alert.AlertType.INFORMATION, "customer save successfully").show();
//           }else {
//               new Alert(Alert.AlertType.ERROR, "customer save failed").show();
//           }
//       }catch (SQLException e){
//          e.printStackTrace();
//           new Alert(Alert.AlertType.ERROR, "Fail to save customer").show();
//       }
    }

private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);

        txtName.setText("");
        txtNIC.setText("");
        txtMail.setText("");
        txtPhone.setText("");


}


    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM selectedItem =  tblCustomer.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            lblID.setText(selectedItem.getId());
            txtName.setText(selectedItem.getName());
            txtNIC.setText(selectedItem.getNic());
            txtMail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }
    }



    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this customer?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String customerID = lblID.getText();
                boolean isDelete = customerModel.deleteCustomer(customerID);
                if(isDelete){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "customer delete successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "customer delete failed").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
                e.printStackTrace();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerID = lblID.getText();
        String name = txtName.getText();
        String nic = txtNIC.getText();
        String mail = txtMail.getText();
        String phone = txtPhone.getText();

        boolean isValidName = name .matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidMail = mail.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: yellow");
        txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: yellow");
        txtMail.setStyle(txtMail.getStyle() + ";-fx-border-color: yellow");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: yellow");

        if(isValidName){
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red");
        }
        if(isValidNic){
            txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: red");
        }
        if(isValidMail){
            txtMail.setStyle(txtMail.getStyle() + ";-fx-border-color: red");
        }
        if(isValidPhone){
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red");
        }

        CustomerDto customerDto = new CustomerDto(customerID, name, nic, mail, phone);



        if (isValidName&&isValidNic&&isValidMail&&isValidPhone){
            try {
                boolean isUpdate = customerModel.updateCustomer(customerDto);
                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "customer update successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "customer update failed").show();
                }
            }catch (SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnGetCustomerOnAction(ActionEvent actionEvent) {
        try {
          JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/Report/Customers.jrxml")

            );
          Connection connection =  DBConnection.getInstance().getConnection();

          Map<String, Object> parameters = new HashMap<>();

          parameters.put("P_date", LocalDate.now().toString());

         JasperPrint jasperPrint =  JasperFillManager.fillReport(
                    jasperReport,
                    parameters, // parameters nttnm  null denna
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSendMailOnAction(ActionEvent actionEvent) {
        CustomerTM selectedId = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerModel == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SendMail.fxml"));
            Parent load = fxmlLoader.load();

            SendMailController controller = fxmlLoader.getController();
            controller.setCustomerEmail(selectedId.getEmail());

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Mail");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window window = txtPhone.getScene().getWindow();
            stage.initOwner(window);

            stage.showAndWait();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerTM selectedId = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerModel == null) {
            return;
        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/Report/customerDetails.jrxml")
            );
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_customer_id", selectedId.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
