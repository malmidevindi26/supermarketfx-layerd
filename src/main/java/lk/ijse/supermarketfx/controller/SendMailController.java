package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {
    public TextField txtRecipt;
    public TextField txtSubject;
    public TextArea txtDepcription;
    @Setter
    private String customerEmail;

    public void btnSendOnAction(ActionEvent actionEvent) {
        if(customerEmail == null || txtSubject == null || txtDepcription == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"All filled fields are empty");
            return;
        }
        String from = "malmidevindi26@gmail.com";
        String password = "xvga lovu enqd miaj";
        String to = customerEmail;

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(txtSubject.getText());
            message.setText(txtDepcription.getText());

            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION,"Mail sent successfully").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Mail sent Failed").show();
        }
    }
}
