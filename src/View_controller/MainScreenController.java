package View_controller;

import Model.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
/**
 * The <code>MainScreenController</code> class implements the <code>Initializable</code> class and houses all the methods
 * and fields needed to give the application interactivity on the MainScreen application window.
 *
 * @author JamesBundtrock
 */
public class MainScreenController implements Initializable {
    Inventory inv;

    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Part> partTable = new TableView<Part>();
    @FXML
    private TableView<Product> productTable = new TableView<Product>();
    private ObservableList<Part> partInv = FXCollections.observableArrayList();
    private ObservableList<Product> productInv = FXCollections.observableArrayList();
    private ObservableList<Part> partInvSearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInvSearch = FXCollections.observableArrayList();

    /**
     * This is the constructor for this controller.
     *
     * @param inv   The <code>Inventory</code> instance that this controller is manipulating and passes from
     *              one window to the next within our application.
     */
    public MainScreenController(Inventory inv){
        this.inv = inv;
    }

    /**
     * This override on the <code>initialize</code> method generates the <code>partTable</code> and <code>productTable</code>.
     *
     * @param url   The location used to resolve relative paths for the root object, or null if the locations is not known.
     * @param rb    The resources used to localize the root object, or null if the root object was not localized.
     * @see         javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    public void initialize(URL url, ResourceBundle rb){
        createPartTable();
        createProductTable();
    }

    /**
     * Pulls the inventory and populates the <code>partTable</code> with all <code>Part</code> objects.
     */
    private void createPartTable(){
        partInv.setAll(inv.getAllParts());
        partTable.setItems(partInv);
        partTable.refresh();
    }

    /**
     * Pulls the inventory and populates the <code>productTable</code> with all <code>Product</code> objects.
     */
    private void createProductTable(){
        productInv.setAll(getAllProducts());
        productTable.setItems(productInv);
        productTable.refresh();
    }

    /**
     * Gives the exit button the ability to close the application.
     *
     * @param event A mouse click
     */
    @FXML
    public void exitProgram(ActionEvent event){
        Platform.exit();
    }

    /**
     * Clears the <code>partSearchField</code> when clicked on.
     *
     * @param event A mouse click
     */
    @FXML
    void clearTextPart(MouseEvent event){
        partSearchField.clear();
    }

    /**
     * Clears the <code>productSearchField</code> when click on.
     *
     * @param event A mouse click
     */
    @FXML
    void clearTextProduct(MouseEvent event){
        productSearchField.clear();
    }

    /**
     * Adds functionality to the partSearchButton and allows it to search for <code>Part</code> objects.
     * Populates the <code>partTable</code> based off of a search of the inventory for the text inside the
     * <code>partSearchField</code> <code>TextField</code>. First, it looks for and adds any partial or full matches based
     * on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole number, for
     * a full match based on the <code>int</code> value of <code>id</code>. If no matches are found,
     * an error message pops up to tell the user, and the table is filled with all part objects within the inventory.
     *
     * @param event A mouse click
     */
    @FXML
    private void searchPartInv(MouseEvent event){
        if(!partSearchField.getText().trim().isEmpty()){
            partInvSearch.clear();
            partInvSearch.addAll(inv.lookupPart(partSearchField.getText().trim()));
            if(partSearchField.getText().trim().matches("^-?\\d+$")) {
                if (partInvSearch.isEmpty()) {
                    partInvSearch.add(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())));
                } else {
                    if (!partInvSearch.contains(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())))) {
                        partInvSearch.add(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())));
                    }
                }
            }
            partTable.setItems(partInvSearch);
            if(partInvSearch.isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Parts");
                error.setContentText("A part with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
                partTable.setItems(getAllParts());
            }
        }
        else {
            partTable.setItems(getAllParts());
        }
        partTable.refresh();
    }

    /**
     * Adds functionality to the productSearchButton and allows it to search for <code>Product</code> objects.
     * Populates the <code>productTable</code> based off of a search of the inventory for the text inside the
     * <code>productSearchField</code> <code>TextField</code>. First, it looks for and adds any partial or full matches based
     * on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole number, for
     * a full match based on the <code>int</code> value of <code>id</code>. If no matches are found,
     * an error message pops up to tell the user, and the table is filled with all product objects within the inventory.
     *
     * @param event A mouse click
     */
    @FXML
    private void searchProductInv(MouseEvent event){
        if(!productSearchField.getText().trim().isEmpty()){
            productInvSearch.clear();
            productInvSearch.addAll(inv.lookupProduct(productSearchField.getText().trim()));
            if(productSearchField.getText().trim().matches("^-?\\d+$")) {
                if (productInvSearch.isEmpty()) {
                    productInvSearch.add(inv.lookupProduct(Integer.parseInt(productSearchField.getText().trim())));
                } else {
                    if (!productInvSearch.contains(inv.lookupProduct(Integer.parseInt(productSearchField.getText().trim()))))
                        productInvSearch.add(inv.lookupProduct(Integer.parseInt(productSearchField.getText().trim())));
                }
            }
            productTable.setItems(productInvSearch);
            if(productInvSearch.isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Products");
                error.setContentText("A product with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
                productTable.setItems(getAllProducts());
            }
        }
        else {
            productTable.setItems(getAllProducts());
        }
        productTable.refresh();
    }

    /**
     * Adds functionality to the <code>partSearchField</code> and allows it to search for <code>Part</code> objects when
     * the enter key is pressed. Populates the <code>partTable</code> based off of a search of the inventory for the text
     * inside the <code>partSearchField</code> <code>TextField</code>. First, it looks for and adds any partial or full
     * matches based on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole
     * number, for a full match based on the <code>int</code> value of <code>id</code>. If no matches are
     * found, an error message pops up to tell the user, and the table is filled with all part objects within the inventory.
     *
     * @param event An enter key press ideally
     */
    @FXML
    void onEnterKeyPart(ActionEvent event){
        if(!partSearchField.getText().trim().isEmpty()){
            partInvSearch.clear();
            partInvSearch.addAll(inv.lookupPart(partSearchField.getText().trim()));
            if(partSearchField.getText().trim().matches("^-?\\d+$")) {
                if (partInvSearch.isEmpty()) {
                    partInvSearch.add(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())));
                } else {
                    if (!partInvSearch.contains(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())))) {
                        partInvSearch.add(inv.lookupPart(Integer.parseInt(partSearchField.getText().trim())));
                    }
                }
            }
            partTable.setItems(partInvSearch);
            if(partInvSearch.isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Parts");
                error.setContentText("A part with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
                partTable.setItems(getAllParts());
            }
        }
        else {
            partTable.setItems(getAllParts());
        }
        partTable.refresh();
    }

    /**
     * Adds functionality to the <code>productSearchField</code> and allows it to search for <code>Product</code> objects when
     * the enter key is pressed. Populates the <code>productTable</code> based off of a search of the inventory for the text
     * inside the <code>productSearchField</code> <code>TextField</code>. First, it looks for and adds any partial or full
     * matches based on the <code>String</code> <code>name</code>. Then it searches once more, if the input is a whole
     * number, for a full match based on the <code>int</code> value of <code>id</code>. If no matches are
     * found, an error message pops up to tell the user, and the table is filled with all product objects within the inventory.
     *
     * @param event An enter key press ideally
     */
    @FXML
    void onEnterKeyProduct(ActionEvent event){
        if(!productSearchField.getText().trim().isEmpty()){
            productInvSearch.clear();
            productInvSearch.addAll(inv.lookupProduct(productSearchField.getText().trim()));
            if(productSearchField.getText().trim().matches("^-?\\d+$")) {
                if (productInvSearch.isEmpty()) {
                    productInvSearch.add(inv.lookupProduct(Integer.parseInt(productSearchField.getText().trim())));
                } else {
                    if (!productInvSearch.contains(inv.lookupProduct(Integer.parseInt(productSearchField.getText().trim()))))
                        ;
                }
            }
            productTable.setItems(productInvSearch);
            if(productInvSearch.isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No Matching Products");
                error.setContentText("A product with that ID or Name cannot be found!");
                Optional<ButtonType> choice = error.showAndWait();
                productTable.setItems(getAllProducts());
            }
        }
        else {
            productTable.setItems(getAllProducts());
        }
        productTable.refresh();
    }

    /**
     * Opens the AddPart screen within the application. Gives the addPartButton functionality.
     *
     * @param event A mouse click
     */
    @FXML
    private void addPart(MouseEvent event){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/AddPart.fxml"));
            AddPartController controller = new AddPartController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Opens the AddProduct screen within the application. Gives the addProductButton functionality.
     *
     * @param event A mouse click
     */
    @FXML
    private void addProduct(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/AddProduct.fxml"));
            AddProductController controller = new AddProductController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * If a <code>Part</code> object is selected within the partTable, this method opens the ModifyPart screen and passes
     * to its controller the selected part. This gives the modifyPartButton functionality. If no part is selected/highlighted,
     * an error message alerts the user that no part is selected to be modified.
     *
     * This little bit of code... I kept messing it up. I kept incorrectly leaving off the package name in the
     * getResource() bit of code. Or, once, I incorrectly pointed it to the View_controller package, to much frustration.
     *
     * Also, one piece of nasty oversight: I had a method within my Add Part, Modify Part, Add Product, and Modify Product
     * screens that would display an error whenever you clicked the id field. This is before I realized that I was
     * supposed to straight up disable the TextField. I liked my method better, but I knew the requirements were very
     * clear, so I removed that bit of code from those controllers and deleted the method calls in the fxml files.
     * That is, except for one! The reason "Parent root = loader.load()" never properly worked, and why my modify Part
     * button was useless... was because I had left a method call onMouseClick to a method that didn't exist within
     * the ModifyPartController class. I had a heck of time debugging that because it never threw any errors! Not at
     * compile-time or run-time. Had to use the good ol print method. Had a print method between each line with a number,
     * and when the number didn't print... the line just before it obviously broke. So that's how I fixed that!
     *
     * @param event A mouse click
     */
    @FXML
    private void modifyPart(MouseEvent event){
        try {
            Part selected = partTable.getSelectionModel().getSelectedItem();
            if (partInv.isEmpty()){
                errorWindow(1);
                return;
            } else if (selected == null){
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/ModifyPart.fxml"));
                ModifyPartController controller = new ModifyPartController(inv, selected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {

        }
    }

    /**
     * If a <code>Product</code> object is selected within the productTable, this method opens the ModifyProduct screen
     * and passes to its controller the selected product. This gives the modifyProductButton functionality.
     * If no product is selected/highlighted, an error message alerts the user that no product is selected to be modified.
     *
     * @param event A mouse click
     */
    @FXML
    private void modifyProduct(MouseEvent event){
        try {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (productInv.isEmpty()){
                errorWindow(1);
                return;
            } else if (selected == null){
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, selected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e){

        }
    }

    /**
     * If a <code>Part</code> object is selected within the partTable, this method deletes the part from the inventory.
     * This gives the deletePartButton functionality. If no part is selected/highlighted,
     * an error message alerts the user that no part is selected to be deleted. Before deletion, the user must confirm
     * their choice through a confirmation window before the part is deleted from the inventory.
     *
     * I would, if I were to add some features, I would implement a check and then warn the user from deleting parts
     * that are associated to Products. This seems like a weird thing to not forbid the users from doing, as it
     * really breaks the inventory. As it stands, I could delete a part from the inventory, but it is still technically
     * associated with a product still within my inventory. Then, if I deleted the part from my product via the modify
     * products window, it could potentially add the part back into the inventory inadvertently. This seems... bad.
     * Unless a company had a specific reason for allowing this behavior, I would limit or removal a user's ability to
     * do this.
     *
     * @param event A mouse click
     */
    @FXML
    private void deletePart(MouseEvent event) {
        Part removedPart = partTable.getSelectionModel().getSelectedItem();
        if(partInv.isEmpty()){
            errorWindow(1);
            return;
        } else if (!partInv.isEmpty() && removedPart == null){
            errorWindow(2);
            return;
        } else {
            boolean areYouSure = areYouSureWindow(removedPart.getName());
            if (!areYouSure){
                return;
            }
            inv.deletePart(removedPart);
            partInv.remove(removedPart);
            partTable.setItems(partInv);
            partTable.refresh();
        }
    }

    /**
     * If a <code>Product</code> object is selected within the productTable, this method deletes the product from the inventory.
     * This gives the deleteProductButton functionality. If no product is selected/highlighted,
     * an error message alerts the user that no product is selected to be deleted. If the product has associated parts,
     * a confirmation window forces the user to confirm that they do wish to delete the part, despite parts being
     * associated with it. If the product has no associated parts, the user must still confirm deletion before deletion
     * from the inventory will actually take effect.
     *
     * @param event A mouse click
     */
    @FXML
    private void deleteProduct(MouseEvent event){
        Product removedProduct = productTable.getSelectionModel().getSelectedItem();
        if (productInv.isEmpty()){
            errorWindow(1);
            return;
        } else if(removedProduct == null){
            errorWindow(2);
            return;
        } else if(!removedProduct.getAllAssociatedParts().isEmpty()) {
            boolean areYouSure = areYouSureWindow(removedProduct.getName());
            if (!areYouSure) {
                return;
            } else {
                boolean ignore = errorAlertWindow(removedProduct.getName());
                if(!ignore){
                    return;
                } else {
                    inv.deleteProduct(removedProduct);
                    productInv.remove(removedProduct);
                    productTable.setItems(productInv);
                    productTable.refresh();
                }
            }
            return;
        } else {
            boolean areYouSure = areYouSureWindow(removedProduct.getName());
            if (!areYouSure) {
                return;
            } else {
                inv.deleteProduct(removedProduct);
                productInv.remove(removedProduct);
                productTable.setItems(productInv);
                productTable.refresh();
            }
        }
    }

    /**
     * Throws error messages if the modify or delete buttons are clicked if their respective inventories are empty, or
     * if there is nothing selected within their respective TableViews.
     *
     * @param type  The error type to be shown to the user
     */
    private void errorWindow(int type){
        if (type == 1){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Missing Inventory!");
            error.setContentText("There's nothing in your inventory to do that!");
            error.showAndWait();
        }
        if (type == 2){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Nothing Selected!");
            error.setContentText("You cannot do that without selecting something first!");
            error.showAndWait();
        }
    }

    /**
     * Confirmation windows when the user wishes to delete a <code>Part</code> or <code>Product</code>.
     *
     * @param name  The name of the object being removed from the inventory
     * @return      Returns true if the user selects ok, returns false if the user selects cancel
     */
    private boolean areYouSureWindow(String name){
        Alert sure = new Alert(Alert.AlertType.CONFIRMATION);
        sure.setTitle("Delete From Inventory");
        sure.setHeaderText("Please confirm that you wish to delete: " + name);
        sure.setContentText("Click OK to proceed");

        Optional<ButtonType> choice = sure.showAndWait();
        if (choice.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * An alert when the user attempts to delete a <code>Product</code> with associated <code>Part</code> objects.
     *
     * @param name  the name of the <code>Product</code> to be deleted
     * @return      returns true if the user clicks ok, returns false if the user clicks cancel
     */
    private boolean errorAlertWindow(String name){
        Alert error = new Alert(Alert.AlertType.CONFIRMATION);
        error.setTitle("Error");
        error.setHeaderText("Unexpected Behavior! " + name + " still has associated parts!");
        error.setContentText("If you wish to delete this product anyway, click OK");

        Optional<ButtonType> choice = error.showAndWait();
        if(choice.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}
