package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    private File selectedFile;
    private String filePath;
    @FXML
    private JFXButton btn_select, btn_analyzer;
    @FXML
    private JFXTextField txt_select;
    @FXML
    private Label lbl_green,lbl_name,lbl_engine,lbl_type,lbl_aware;
    @FXML
    private AnchorPane pane_red, pane_green;



    @FXML
    public void selectFile(MouseEvent event) {
        lbl_green.setVisible(false);
        pane_green.toFront();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        selectedFile = fileChooser.showOpenDialog(null);
        filePath = selectedFile.getAbsolutePath();
        txt_select.setText(filePath);
    }

    @FXML
    public void analyze(MouseEvent event){
        lbl_green.setVisible(false);
        pane_green.toFront();
        byte[] inputStream = readFile(filePath);
        String hashVal = hashFile(inputStream);
        System.out.println(hashVal);

        VProfile vProfile = getVProfile(hashVal);
        if (vProfile!= null){
            lbl_name.setText(vProfile.getVirusName());
            lbl_engine.setText(vProfile.getEngine());
            lbl_type.setText(vProfile.getType());
            lbl_aware.setText(Integer.toString(vProfile.getAwareness()));
            pane_red.toFront();
        }else {
            lbl_green.setVisible(true);
            pane_green.toFront();
        }
    }


    public VProfile getVProfile(String hashVal){

        String sql = "SELECT * FROM vx WHERE vxMD5 = "+"\""+hashVal+"\""+" LIMIT 1;";
//        String sql = "SELECT * FROM vx WHERE vxMD5 = "+"\"01b656578e9f00f289d4c560fb8f2ff8\""+" LIMIT 1;";
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            VProfile vProfile = null;

            while (resultSet.next()){
                vProfile = new VProfile();
                vProfile.setVirusName(resultSet.getString("vxVirusName"));
                vProfile.setEngine(resultSet.getString("vxEngine"));
                vProfile.setAwareness(resultSet.getInt("vxAwareness"));
                vProfile.setType(resultSet.getString("vxType"));

            }return vProfile;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
    public static byte[] readFile(String filePath) {
        byte[] content = {};
        try {
            content = FileUtils.readFileToByteArray(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String hashFile(byte[] inputStream){
        String digestString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theDigest = md.digest(inputStream);
            digestString = Hex.encodeHexString(theDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digestString;
    }

}
