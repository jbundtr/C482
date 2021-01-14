package View_controller;


import Model.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The <code>AddPartController</code> class implements the <code>Initializable</code> class and houses all the methods
 * and fields needed to give the application interactivity on the AddPart application window.
 *
 * @author JamesBundtrock
 */
public class AddPartController implements Initializable{
    Inventory inv;

    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outSourcedRadioButton;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private TextField companyName;
    @FXML
    private Label companyLabel;

    /**
     * This is the constructor for this controller.
     *
     * @param inv   The <code>Inventory</code> instance that this controller is manipulating and passes from
     *              one window to the next within our application.
     */
    public AddPartController(Inventory inv){
        this.inv = inv;
    }

    /**
     * This override on the <code>initialize</code> method set this application window's fields to default values. However,
     * in respect to the <code>id</code> field, it populates it with an auto-generated <code>partId</code>, since the user
     * is not allowed to manually set this (as it must be unique).
     *
     * @param url   The location used to resolve relative paths for the root object, or null if the locations is not known.
     * @param rb    The resources used to localize the root object, or null if the root object was not localized.
     * @see         javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartIDNumber();
        resetAllFields();
    }

    /**
     * This method sets all non-id <code>TextField</code> instances on the AddPart window to default values.
     */
    private void resetAllFields(){
        name.setText("Part Name");
        stock.setText("# in Stock");
        price.setText("Part Price");
        min.setText("Min Stock");
        max.setText("Max Stock");
        companyLabel.setText("Machine ID");
        companyName.setText("Machine ID");
        inHouseRadioButton.setSelected(true);
    }

    /**
     * This method auto-generates a new <code>id</code> for our prospective <code>Part</code> object. It sets the text
     * of the <code>id</code> <code>TextField</code> and cannot be edited due to the <code>TextField</code> being disabled.
     */
    private void generatePartIDNumber(){
        if(inv.getAllParts().isEmpty()){
            id.setText("1");
        } else {
            int newID = 1;
            for (Part parts : inv.getAllParts()){
                if(parts.getId() >= newID){
                    newID = parts.getId() + 1;
                }
            }
            Integer integerID = newID;
            id.setText(integerID.toString());
        }
    }

    /**
     * This method clears a <code>TextField</code> when clicked on by a mouse.
     *
     * @param event The mouse click
     */
    @FXML
    private void clearTextFieldOnClick(MouseEvent event){
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }

    /**
     * This method allows the InHouse radio button to change the form to reflect the new <code>Part</code> being
     * <code>InHouse</code> rather than <code>Outsourced</code>.
     *
     * @param event A mouse click
     */
    @FXML
    private void selectInHousePart(MouseEvent event){
        companyLabel.setText("Machine ID");
        companyName.setText("Machine ID");
    }

    /**
     * This method allows the Outsourced radio button to change the form to reflect the new <code>Part</code> being
     * <code>Outsourced</code> rather than <code>InHouse</code>.
     *
     * @param event A mouse click
     */
    @FXML
    private void selectOutsourcedPart(MouseEvent event) {
        companyLabel.setText("Company Name");
        companyName.setText("Company Name");
    }

    /**
     * This gives the cancel button on the AddPart window functionality, it returns the user to the MainScreen if ok is
     * clicked on the confirmation window that pops up.
     *
     * @param event A mouse click
     */
    @FXML
    private void cancelAddPart(MouseEvent event){
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
        cancel.setTitle("Cancel");
        cancel.setHeaderText("Would you like to stop adding this part?");
        cancel.setContentText("Click OK to go back to the main screen");
        Optional<ButtonType> choice = cancel.showAndWait();
        if(choice.get() == ButtonType.OK){
            mainScreenReturn(event);
        } else {
            return;
        }
    }

    /**
     * This is the main error reporting method called when known exceptions or logic errors occur. If the error can be
     * traced to a specific <code>TextField</code>, this method will border it in crimson.
     *
     * @param type  the type of error
     * @param field the <code>TextField</code> where the error occurred, or null if no specific <code>TextField</code>
     *              can be determined to be the source of the error
     */
    private void errorAddPart(int type,TextField field){
        fieldErrorTrigger(field);

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("An Error Occurred");
        error.setHeaderText("Adding part failed");
        switch (type) {
            case 1:
                error.setContentText("Part must be either In House or Outsourced");
                break;
            case 2:
                error.setContentText("Missing or invalid Part Name");
                break;
            case 3:
                error.setContentText("Minimum cannot be higher than Maximum");
                break;
            case 4:
                error.setContentText("Stock cannot be lower than Minimum");
                break;
            case 5:
                error.setContentText("Stock cannot be higher than Maximum");
                break;
            case 6:
                error.setContentText("Missing Company Name or Machine ID");
                break;
            case 7:
                error.setContentText("Machine ID must be a whole number!");
                break;
            case 8:
                error.setContentText("Empty fields must be filled in");
                break;
            case 9:
                error.setContentText("Price cannot be negative!");
                break;
            case 10:
                error.setContentText("Invalid or Incorrect Values!");
                break;
            default:
                error.setContentText("Unknown Error");
        }
        error.showAndWait();
    }

    /**
     * This method colors a crimson border around any <code>TextField</code> passed to it. Always called by the
     * <code>errorAddPart</code>, but sometimes passed null values when error cannot be determined to be within a
     * <code>TextField</code>.
     *
     * @param field the <code>TextField</code> to border with crimson, or null if no border is to be changed
     */
    private void fieldErrorTrigger(TextField field){
        if (field != null){
            field.setStyle("-fx-border-color: crimson");
        }
    }

    /**
     * This method gives the save button on the AddPart window functionality, as it checks the <code>TextField</code> values
     * for logical and type errors. Any errors found call the <code>errorAddPart</code> method. This method also clears
     * any crimson borders prior to calling the <code>errorAddPart</code> method, so that any new errors are easier to
     * visually see to the user. If the <code>TextField</code> values pass validation and logic checks, the <code>Part</code>
     * is added, and the user is put back to the MainScreen.
     *
     * @param event A mouse click
     */
    @FXML
    private void saveAddPart(MouseEvent event){
        resetFieldColors();
        boolean errorFound = false;
        TextField[] fieldCheck = {stock,price,min,max};
        if (!inHouseRadioButton.isSelected() && !outSourcedRadioButton.isSelected()){
            errorAddPart(1,null);
        } else {
            for (TextField field : fieldCheck){
                boolean errorValue = valueCheck(field);
                if(errorValue){
                    errorFound = true;
                    break;
                }
                boolean errorType = typeCheck(field);
                if(errorType){
                    errorFound = true;
                    break;
                }
            }
            if (errorFound) {
                return;
            }
            if(name.getText().trim().isEmpty() || name.getText().trim().equalsIgnoreCase("part name")){
                errorAddPart(2, name);
                errorFound = true;
            }
            if(Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())){
                errorAddPart(3,min);
                errorFound = true;
            }
            if(Integer.parseInt(stock.getText().trim()) < Integer.parseInt(min.getText().trim())){
                errorAddPart(4,stock);
                errorFound = true;
            }
            if(Integer.parseInt(stock.getText().trim()) > Integer.parseInt(max.getText().trim())){
                errorAddPart(5,stock);
                errorFound = true;
            }
            if (errorFound) {
                return;
            }
            if(companyName.getText().trim().isEmpty() || companyName.getText().trim().equalsIgnoreCase("company name")){
                errorAddPart(6,companyName);
                return;
            }
            if(inHouseRadioButton.isSelected() && !companyName.getText().trim().matches("^-?\\d+$")){
                errorAddPart(7,companyName);
                return;
            }
            if(inHouseRadioButton.isSelected()){
                addInHousePart();
            } else if(outSourcedRadioButton.isSelected()){
                addOutsourcedPart();
            }

        }
        mainScreenReturn(event);
    }

    /**
     * This method resets the borders to gray.
     */
    private void resetFieldColors(){
        name.setStyle("-fx-border-color: gray");
        stock.setStyle("-fx-border-color: gray");
        min.setStyle("-fx-border-color: gray");
        max.setStyle("-fx-border-color: gray");
        price.setStyle("-fx-border-color: gray");
        companyName.setStyle("-fx-border-color: gray");
    }

    /**
     * This method pulls the values of the already validated <code>TextField</code> objects and parses their text values into their
     * respective data types. Using these parsed values, it creates a new instance of an <code>InHouse</code> <code>Part</code>
     * object and adds it to the <code>Inventory</code> instance, <code>inv</code>.
     */
    private void addInHousePart(){
        inv.addPart(new InHouse(
                Integer.parseInt(id.getText().trim()),
                name.getText().trim(),
                Double.parseDouble(price.getText().trim()),
                Integer.parseInt(stock.getText().trim()),
                Integer.parseInt(min.getText().trim()),
                Integer.parseInt(max.getText().trim()),
                Integer.parseInt(companyName.getText().trim())
                ));
    }
    /**
     * This method pulls the values of the already validated <code>TextField</code> objects and parses their text values into their
     * respective data types. Using these parsed values, it creates a new instance of an <code>Outsourced</code> <code>Part</code>
     * object and adds it to the <code>Inventory</code> instance, <code>inv</code>.
     */
    private void addOutsourcedPart(){
        inv.addPart(new Outsourced(
                Integer.parseInt(id.getText().trim()),
                name.getText().trim(),
                Double.parseDouble(price.getText().trim()),
                Integer.parseInt(stock.getText().trim()),
                Integer.parseInt(min.getText().trim()),
                Integer.parseInt(max.getText().trim()),
                companyName.getText().trim()
        ));
    }

    /**
     * Redirects the user to the MainScreen and changes the controller to the <code>MainScreenController</code>.
     *
     * @param event A mouse click on either the cancel or save buttons
     */
    private void mainScreenReturn(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }

    /**
     * This method checks each passed <code>TextField</code> for validity. The field must not be empty, and the <code>price</code>
     * <code>TextField</code> cannot be less than zero. This also catches exceptions if invalid (such as letters) values
     * are attempted to be parsed as a <code>Double</code>.
     *
     * @param field The <code>TextField</code> being checked
     * @return      The flag is <code>False</code> if no error found, or <code>True</code> if any error is found.
     */
    private boolean valueCheck(TextField field){
        boolean error = false;
        try {
            if (field.getText().trim().isEmpty()){
                errorAddPart(8,field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0){
                errorAddPart(9,field);
                return true;
            }
        } catch (Exception e) {
            error = true;
            errorAddPart(10,field);
            System.out.println(e);
        }
        return error;
    }

    /**
     * This method checks any <code>TextField</code> passed to it for being valid numbers for the data type. For the
     * <code>price</code> field, it checks to see if it is a valid decimal number (or can be correctly parsed into one).
     * For any other field passed to it, it checks to see if it is a valid whole number.
     *
     * @param field The <code>TextField</code> being checked
     * @return      Returns <code>True</code> if an error is found, <code>False</code> if no error is found
     */
    private boolean typeCheck(TextField field){
        if (field == price && !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            errorAddPart(10,field);
            return true;
        }
        if (field != price && !field.getText().trim().matches("\\d*")){
            errorAddPart(10,field);
            return true;
        }
        return false;
    }

}
