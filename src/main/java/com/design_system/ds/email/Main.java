package com.design_system.ds.email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jakarta.mail.Session;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.Transport;



public class Main {

    @FXML
    private Label user_in;
    @FXML
    private Label node_in;
    
    @FXML
    private TextField address;
    @FXML
    private TextField subject;
    @FXML
    private TextArea message;
    @FXML
    private Label emailMsg;
    
            
    public void initializeData(String user, String node) {
        user_in.setText(user);
        node_in.setText(node);
    }
    
    @FXML
    public void enviarEmail() {
        
        //creoun objeto mapper de json
       ObjectMapper mapper = new ObjectMapper();
       
        if (!checkInternet()) {
            emailMsg.setText("No hay conexión a internet.");
            return;
        }

        try {
            //leo el archivo json
            File jsonFile = new File("src\\main\\resources\\json\\config.json");
            JsonNode rootNode = mapper.readTree(jsonFile);

            // Acceder al nodo
            JsonNode connectionNode = rootNode.path(node_in.getText());
            if (connectionNode.isMissingNode()) {
                emailMsg.setText("Configuración no encontrada en el archivo JSON.");
                return;
            }

            // Extraer valores para el login
            String user_email = connectionNode.path("user_email").asText();
            String password_email = connectionNode.path("password_email").asText();
            String host = connectionNode.path("host").asText();
            String port = connectionNode.path("port").asText();

            // Configuración de propiedades de correo
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            // Sesión autenticada
            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(user_email, password_email);
                }
            });

            // Crear y enviar el correo
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(user_email));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address.getText()));
            emailMessage.setSubject(subject.getText());
            emailMessage.setText(message.getText());

            Transport.send(emailMessage);
            emailMsg.setText("Correo enviado exitosamente.");
            
            address.setText("");
            subject.setText("");
            message.setText("");

        } catch (IOException e) {
            emailMsg.setText("Error al leer el archivo JSON: " + e.getMessage());
        } catch (MessagingException e) {
            emailMsg.setText("Error al enviar el correo: " + e.getMessage());
        } catch (NullPointerException e) {
            emailMsg.setText("Error: Archivo JSON o nodos no encontrados.");
        }
    }
    
    public static boolean checkInternet() {
        try {
            InetAddress.getByName("google.com");
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
    
    public void showMsg() {
        emailMsg.setText("");
    }
}
