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
 * The <code>ModifyPartController</code> class implements the <code>Initializable</code> class and houses all the methods
 * and fields needed to give the application interactivity on the ModifyPart application window.
 *
 * @author JamesBundtrock
 */
public class ModifyPartController implements Initializable{
    Inventory inv;
    Part part;

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
    @FXML
    private Button modifyPartSaveChangesButton;

    /**
     * This is the constructor for this controller.
     *
     * @param inv   The <code>Inventory</code> instance that this controller is manipulating and passes from
     *              one window to the next within our application.
     * @param part  the <code>Part</code> object to be modified
     */
    public ModifyPartController(Inventory inv, Part part){
        this.inv = inv;
        this.part = part;
    }

    /**
     * This override on the <code>initialize</code> method sets this application window's fields to the <code>part</code>
     * instance values. It does this by calling the <code>generatePartData</code> method.
     *
     * @param url   The location used to resolve relative paths for the root object, or null if the locations is not known.
     * @param rb    The resources used to localize the root object, or null if the root object was not localized.
     * @see         javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Here?");
        generatePartData();
    }

    /**
     * Sets the field values to that of the passed <code>Part</code> object, <code>part</code>. It also automatically
     * sets the forms to show the type of the <code>Part</code> object, either <code>InHouse</code> or <code>Outsourced</code>.
     */
    private void generatePartData(){
        if (part instanceof InHouse){

            InHouse partInstance = (InHouse) part;
            inHouseRadioButton.setSelected(true);
            companyLabel.setText("Machine ID");
            this.name.setText(partInstance.getName());
            this.id.setText(Integer.toString(partInstance.getId()));
            this.stock.setText(Integer.toString(partInstance.getStock()));
            this.price.setText(Double.toString(partInstance.getPrice()));
            this.min.setText(Integer.toString(partInstance.getMin()));
            this.max.setText(Integer.toString(partInstance.getMax()));
            this.companyName.setText(Integer.toString(partInstance.getMachineID()));

        }

        else if (part instanceof Outsourced){

            Outsourced partInstance = (Outsourced) part;
            outSourcedRadioButton.setSelected(true);
            companyLabel.setText("Company Name");
            this.name.setText(partInstance.getName());
            this.id.setText(Integer.toString(partInstance.getId()));
            this.stock.setText(Integer.toString(partInstance.getStock()));
            this.price.setText(Double.toString(partInstance.getPrice()));
            this.min.setText(Integer.toString(partInstance.getMin()));
            this.max.setText(Integer.toString(partInstance.getMax()));
            this.companyName.setText(partInstance.getCompanyName());

        }
    }

    /**
     * Changes the fields to reflect that the <code>Part</code> is of the <code>InHouse</code> varietal.
     *
     * @param event A mouse click that triggered this method call
     */
    @FXML
    private void selectInHousePart(MouseEvent event){
        companyLabel.setText("Machine ID");
    }

    /**
     * Changes the fields to reflect that the <code>Part</code> is of the <code>Outsourced</code> varietal.
     *
     * @param event A mouse click that triggered this method
     */
    @FXML
    private void selectOutsourcedPart(MouseEvent event) {
        companyLabel.setText("Company Name");
    }

    /**
     * Cancels the modification of the <code>Part</code> object. This is tied to a button that is clicked on the screen.
     * This method also makes you confirm that you want to cancel the change before changing the screens.
     *
     * @param event A mouse click that triggered this method
     */
    @FXML
    private void cancelModifyPart(MouseEvent event){
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
        cancel.setTitle("Cancel");
        cancel.setHeaderText("Would you like to cancel changes to this part?");
        cancel.setContentText("Click OK to go back to the main screen");
        Optional<ButtonType> choice = cancel.showAndWait();
        if(choice.get() == ButtonType.OK){
            mainScreenReturn(event);
        }
    }

    /**
     * This is the main error reporting method called when known exceptions or logic errors occur. If the error can be
     * traced to a specific <code>TextField</code>, this method will border it in crimson.
     *
     * Originally, when coding this, I incorrectly formatted the switch block; I had curly brackets on each case, except
     * the default, so every single error threw the default error case. It bewildered me for a moment, as there should
     * be no way for the default case to be triggered (bar something being incorrect, which it was).
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
                error.setContentText("Minimum cannot be lower than Maximum");
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
                error.setContentText("Machine ID must be a number");
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
     * This method gives the save button on the ModifyPart window functionality, as it checks the <code>TextField</code> values
     * for logical and type errors. Any errors found call the <code>errorAddPart</code> method. This method also clears
     * any crimson borders prior to calling the <code>errorAddPart</code> method, so that any new errors are easier to
     * visually see to the user. If the <code>TextField</code> values pass validation and logic checks, the <code>Part</code>
     * is modified, and the user is put back to the MainScreen.
     *
     * @param event A mouse click
     */
    @FXML
    private void saveChangesToPart(MouseEvent event){
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
                updateInHousePartValues();
            } else if(outSourcedRadioButton.isSelected()){
                updateOutsourcedPartValues();
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
     * object and uses the <code>updatePart</code> method to find and overwrite the original part.
     */
    private void updateInHousePartValues(){
        inv.updatePart(new InHouse(
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
     * object and uses the <code>updatePart</code> method to find and overwrite the original part.
     */
    private void updateOutsourcedPartValues(){
        inv.updatePart(new Outsourced(
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
            if (field.getText().trim().isEmpty() || field.getText().trim() == null){
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
