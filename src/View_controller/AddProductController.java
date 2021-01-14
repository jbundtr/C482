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

import static Model.Inventory.getAllParts;
/**
 * The <code>AddProductController</code> class implements the <code>Initializable</code> class and houses all the methods
 * and fields needed to give the application interactivity on the AddProduct application window.
 *
 * @author JamesBundtrock
 */
public class AddProductController implements Initializable{
    Inventory inv;

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
    private TextField searchForPart;
    @FXML
    private TableView<Part> partSearchingTable = new TableView<Part>();
    @FXML
    private TableView<Part> associatedPartTable = new TableView<Part>();
    private ObservableList<Part> partInv = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * This is the constructor for this controller.
     *
     * @param inv   The <code>Inventory</code> instance that this controller is manipulating and passes from
     *              one window to the next within our application.
     */
    public AddProductController(Inventory inv){
        this.inv = inv;
    }

    /**
     * This override on the <code>initialize</code> method set this application window's fields to default values. However,
     * in respect to the <code>id</code> field, it populates it with an auto-generated <code>partId</code>, since the user
     * is not allowed to manually set this (as it must be unique). It also generates the <code>partSearchingTable</code>.
     *
     * @param url   The location used to resolve relative paths for the root object, or null if the locations is not known.
     * @param rb    The resources used to localize the root object, or null if the root object was not localized.
     * @see         javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generateProductIDNumber();
        generateSearchTable();
    }

    /**
     * This method auto-generates a new <code>id</code> for our prospective <code>Product</code> object. It sets the text
     * of the <code>id</code> <code>TextField</code> and cannot be edited due to the <code>TextField</code> being disabled.
     */
    private void generateProductIDNumber(){
        if(inv.getAllProducts().isEmpty()){
            id.setText("1");
        } else {
            int newID = 1;
            for (Product products : inv.getAllProducts()){
                if(products.getId() >= newID){
                    newID = products.getId()+1;
                }
            }
            Integer integerID = newID;
            id.setText(integerID.toString());
        }
    }

    /**
     * Pulls the inventory and populates the <code>partSearchingTable</code> with all available <code>Part</code> objects.
     */
    private void generateSearchTable(){
        partInv.setAll(inv.getAllParts());
        partSearchingTable.setItems(partInv);
        partSearchingTable.refresh();
    }

    /**
     * This is the main error reporting method called when known exceptions or logic errors occur. If the error can be
     * traced to a specific <code>TextField</code>, this method will border it in crimson.
     *
     * @param type  the type of error
     * @param field the <code>TextField</code> where the error occurred, or null if no specific <code>TextField</code>
     *              can be determined to be the source of the error
     */
    private void errorAddProduct(int type,TextField field){
        fieldErrorTrigger(field);

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("An Error Occurred");
        error.setHeaderText("Adding product failed");
        switch (type) {
            case 1:
                error.setContentText("Part already associated with this product!");
                break;
            case 2:
                error.setContentText("Product name is missing!");
                break;
            case 3:
                error.setContentText("Product minimum cannot be higher than its maximum");
                break;
            case 4:
                error.setContentText("Product stock cannot be lower than minimum");
                break;
            case 5:
                error.setContentText("Product stock cannot be higher than maximum");
                break;
            case 6:
                error.setContentText("Current price set lower than cost of associated parts!");
                break;
            case 7:
                error.setContentText("Product must have associated parts!");
                break;
            case 8:
                error.setContentText("Field missing or invalid!");
                break;
            case 9:
                error.setContentText("Price of product cannot be less than zero!");
                break;
            case 10:
                error.setContentText("Values incompatible or invalid with field type!");
                break;
            default:
                error.setContentText("");
        }
        error.showAndWait();
    }

    /**
     * This method colors a crimson border around any <code>TextField</code> passed to it. Always called by the
     * <code>errorAddProduct</code>, but sometimes passed null values when error cannot be determined to be within a
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
     * Generates a new <code>partSearchingTable</code> based off of a search of the inventory for the text inside the
     * <code>searchForPart</code> <code>TextField</code>. First, it looks for and adds any partial or full matches based
     * on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole number, for
     * any full matches based on the <code>int</code> value of <code>id</code>.
     * If no matches are found, an error message pops up to tell the user. Also, to help prevent duplicate parts in the
     * associated parts list, the final result is stripped of any parts already within the associated parts list. If the
     * <code>searchForPart</code> <code>TextField</code> is empty (or the parts found all already exist as associated parts),
     * all parts in the inventory not associated with the product are shown.
     *
     * @param event A mouse click
     */
    @FXML
    private void partSearch(MouseEvent event){
        if(!searchForPart.getText().trim().isEmpty()){
            partInv.clear();
            partInv.addAll(inv.lookupPart(searchForPart.getText().trim()));
            if(searchForPart.getText().trim().matches("^-?\\d+$")) {
                if (partInv.isEmpty()) {
                    partInv.add(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())));
                } else {
                    if (!partInv.contains(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())))) {
                        partInv.add(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())));
                    }
                }
            }
            if(partInv.get(0) == null){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Parts");
                error.setContentText("A part with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
            }
        }
        else {
            partInv.setAll(getAllParts());
        }
        partInv.removeAll(associatedPartList);
        if(partInv.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Additional Matching Parts");
            error.setContentText("All matching parts are already associated with this product!");
            Optional<ButtonType> choice = error.showAndWait();
            partInv.setAll(getAllParts());
            partInv.removeAll(associatedPartList);
        }
        partSearchingTable.setItems(partInv);
        partSearchingTable.refresh();
    }

    /**
     * Generates a new <code>partSearchingTable</code> based off of a search of the inventory for the text inside the
     * <code>searchForPart</code> <code>TextField</code>. First, it looks for and adds any partial or full matches based
     * on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole number, for
     * a full match based on the <code>int</code> value of <code>id</code>.
     * If no matches are found, an error message pops up to tell the user. Also, to help prevent duplicate parts in the
     * associated parts list, the final result is stripped of any parts already within the associated parts list. If the
     * <code>searchForPart</code> <code>TextField</code> is empty (or the parts found all already exist as associated parts),
     * all parts in the inventory not associated with the product are shown.
     *
     * @param event An enter key stroke ideally
     */
    @FXML
    private void onEnterKeySearch(ActionEvent event){
        if(!searchForPart.getText().trim().isEmpty()){
            partInv.clear();
            partInv.addAll(inv.lookupPart(searchForPart.getText().trim()));
            if(searchForPart.getText().trim().matches("^-?\\d+$")) {
                if (partInv.isEmpty()) {
                    partInv.add(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())));
                } else {
                    if (!partInv.contains(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())))) {
                        partInv.add(inv.lookupPart(Integer.parseInt(searchForPart.getText().trim())));
                    }
                }
            }
            if(partInv.get(0) == null){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Parts");
                error.setContentText("A part with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
            }
        }
        else {
            partInv.setAll(getAllParts());
        }
        partInv.removeAll(associatedPartList);
        if(partInv.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Additional Matching Parts");
            error.setContentText("All matching parts are already associated with this product!");
            Optional<ButtonType> choice = error.showAndWait();
            partInv.setAll(getAllParts());
            partInv.removeAll(associatedPartList);
        }
        partSearchingTable.setItems(partInv);
        partSearchingTable.refresh();
    }

    /**
     * Adds an associated <code>Part</code> object to this <code>Product</code> object's associated part list.
     *
     * @param event A mouse click
     */
    @FXML
    private void addAssociatedPart(MouseEvent event){
        Part addAssociatedPart = partSearchingTable.getSelectionModel().getSelectedItem();
        boolean itemExistsCheck = false;

        if(addAssociatedPart != null) {
            for (Part parts : associatedPartList) {
                if (addAssociatedPart.getId() == parts.getId()) {
                    errorAddProduct(1, null);
                    itemExistsCheck = true;
                }
            }
            if (!itemExistsCheck) {
                associatedPartList.add(addAssociatedPart);
            }
            partInv.setAll(inv.getAllParts());
            associatedPartTable.setItems(associatedPartList);
            partInv.removeAll(associatedPartList);
            partSearchingTable.setItems(partInv);
            associatedPartTable.refresh();
            partSearchingTable.refresh();
        }
    }

    /**
     * Removes an associated <code>Part</code> object from this <code>Product</code> object's associated part list.
     *
     * @param event A mouse click
     */
    @FXML
    private void removeAssociatedPart(MouseEvent event){
        Part removedAssociatedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        boolean removed = false;
        if (removedAssociatedPart != null){
            boolean confirmed = false;
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Remove Part");
            confirm.setHeaderText("This action will remove " + removedAssociatedPart.getName() + " as an associated part.");
            confirm.setContentText("Click OK to confirm this choice");
            Optional<ButtonType> choice = confirm.showAndWait();
            confirmed = choice.get() == ButtonType.OK;
            if (confirmed){
                partInv.setAll(inv.getAllParts());
                associatedPartList.remove(removedAssociatedPart);
                associatedPartTable.setItems(associatedPartList);
                partInv.removeAll(associatedPartList);
                partSearchingTable.setItems(partInv);
                associatedPartTable.refresh();
                partSearchingTable.refresh();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success!");
                success.setHeaderText("The operation has completed");
                success.setContentText(removedAssociatedPart.getName() + " has been removed as an associated part!");
                success.showAndWait();
            }
        }
    }

    /**
     * Cancels the addition of this <code>Product</code> to the inventory and sends the user back to the Main Screen.
     * Gives the user a confirmation window prior to fully triggering.
     *
     * @param event A mouse click
     */
    @FXML
    private void cancelAddProduct(MouseEvent event){
        boolean cancelled = false;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cancel Add Product");
        confirm.setHeaderText("Would you stop and return to the main screen?");
        confirm.setContentText("Click OK to confirm this choice");
        Optional<ButtonType> choice = confirm.showAndWait();
        cancelled = choice.get() == ButtonType.OK;
        if (cancelled){
            mainScreenReturn(event);
        }
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
    }

    /**
     * This method gives the save button on the AddProduct window functionality, as it checks the <code>TextField</code> values
     * for logical and type errors. Any errors found call the <code>errorAddProduct</code> method. This method also clears
     * any crimson borders prior to calling the <code>errorAddProduct</code> method, so that any new errors are easier to
     * visually see to the user. If the <code>TextField</code> values pass validation and logic checks, and there is at
     * least one associated part, the <code>Product</code> is added, and the user is put back to the MainScreen.
     *
     * @param event A mouse click
     */
    @FXML
    private void addProduct(MouseEvent event){
        resetFieldColors();
        boolean error = false;
        TextField[] fields = {price,min,max,stock};
        double minimumCost = 0.0;
        for (Part parts : associatedPartList){
            minimumCost += parts.getPrice();
        }
        if (name.getText().trim().isEmpty()){
            errorAddProduct(2,name);
            return;
        }
        for (TextField field : fields){
            boolean errorValue = valueCheck(field);
            if (errorValue){
                error = true;
                break;
            }
            boolean errorType = typeCheck(field);
            if (errorType){
                error = true;
                break;
            }
        }
        if (error){
            return;
        }
        if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())){
            errorAddProduct(3,min);
            error = true;
        }
        if (Integer.parseInt(stock.getText().trim()) < Integer.parseInt(min.getText().trim())){
            errorAddProduct(4,min);
            error = true;
        }
        if (Integer.parseInt(stock.getText().trim()) > Integer.parseInt(max.getText().trim())){
            errorAddProduct(5,max);
            error = true;
        }
        if (Double.parseDouble(price.getText().trim()) < minimumCost){
            errorAddProduct(6,price);
            error = true;
        }
        if (associatedPartList.isEmpty()){
            errorAddProduct(7,null);
            error = true;
        }
        if (error) {
            return;
        }
        addPartToInventory();
        mainScreenReturn(event);

    }

    /**
     * This method pulls the values of the already validated <code>TextField</code> objects and parses their text values into their
     * respective data types. Using these parsed values and this associatedPartList, it creates a new instance of a
     * <code>Product</code> object and adds it to the inventory.
     */
    private void addPartToInventory(){
        Product product = new Product(
                Integer.parseInt(id.getText().trim()),
                name.getText().trim(),
                Double.parseDouble(price.getText().trim()),
                Integer.parseInt(stock.getText().trim()),
                Integer.parseInt(min.getText().trim()),
                Integer.parseInt(max.getText().trim()));
        for (Part parts : associatedPartList){
            product.addAssociatedPart(parts);
        }

        inv.addProduct(product);
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
                errorAddProduct(8,field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0){
                errorAddProduct(9,field);
                return true;
            }
        } catch (Exception e) {
            error = true;
            errorAddProduct(10,field);
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
            errorAddProduct(10,field);
            return true;
        }
        if (field != price && !field.getText().trim().matches("\\d*")){
            errorAddProduct(10,field);
            return true;
        }
        return false;
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

}
