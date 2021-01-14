package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The <code>InventoryProgram</code> class extends the <code>Application</code> class. It calls the main method to
 * launch the program.
 *
 * @author JamesBundtrock
 */
public class InventoryProgram extends Application {
    /**
     * This is my main method, which simply calls the <code>launch</code> method for our application.
     *
     * @param args  an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overrides the default abstract <code>start</code> method from the Application class. It also creates an instance
     * of our <code>Inventory</code> class, a necessary parameter for the <code>MainScreenController</code> class.
     * Additionally, it calls the <code>addTestData</code> method, so that our <code>MainScreenController</code> class
     * has some data built in to play around with (this is unnecessary, but included for ease of testing/debugging).
     *
     * @param stage         Sets a stage for the method to build on and show
     * @throws Exception    Throws an exception for a catch method to report if an exception occurs
     */
    @Override
    public void start(Stage stage) throws Exception{
        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_files/MainScreen.fxml"));
        View_controller.MainScreenController controller = new View_controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     *  A simple method for inserting some initial parts into the inventory since the inventory isn't persistent.
     *  None of the information is meaningful, just various, made up parts and products.
     *
     * @param inv Populates the instance of Inventory with pre-made data for testing purposes
     *  */
    void addTestData(Inventory inv){
        //Creating some InHouse Parts
        Part x1 = new InHouse(1,"Part x1",4.99,16,10,200,101);
        Part x2 = new InHouse(2,"Part x2",3.99,8,5,100,102);
        Part y = new InHouse(3,"Part y",6.99,11,5,150,103);
        //Adding those InHouse Parts into our inventory
        inv.addPart(x1);
        inv.addPart(x2);
        inv.addPart(y);
        //Creating and Adding at the same time
        inv.addPart(new InHouse(4,"Part x3",2.99,12,3,125,104));
        inv.addPart(new InHouse(5,"Part x5",1.99,8,2,99,105));
        //Creating *Outsourced* Parts this time
        Part i1 = new Outsourced(6,"Part i1",1.50,8,2,50,"Widget Inc.");
        Part j = new Outsourced(7,"Part j",1.75,5,1,45,"Widget Inc.");
        Part k = new Outsourced(8,"Part k",0.96,12,3,65,"Big Store");
        //Adding them into the inventory
        inv.addPart(i1);
        inv.addPart(j);
        inv.addPart(k);
        //Creating and Adding outsourced parts at the same time
        inv.addPart(new Outsourced(9,"Part i2",4.56,22,1,34,"Big Store"));
        inv.addPart(new Outsourced(10,"Part m",3.21,19,6,324,"Everything Big Box"));
        //Creating Some Products
        Product prod1 = new Product(1,"Product Alpha",10.99,2,1,5);
        //Associating some parts to the product
        prod1.addAssociatedPart(x1);
        prod1.addAssociatedPart(x2);
        //Adding the product to the inventory
        inv.addProduct(prod1);
        //Doing the same thing here
        Product prod2 = new Product(2,"Product Beta",34.99,1,0,12);
        prod2.addAssociatedPart(y);
        prod2.addAssociatedPart(j);
        prod2.deleteAssociatedPart(j);
        inv.addProduct(prod2);
        Product prod3 = new Product(3,"Product Gamma",27.45,9,5,86);
        prod3.addAssociatedPart(k);
        prod3.addAssociatedPart(i1);
        inv.addProduct(prod3);
        //Here, we're creating Products without associated parts and immediately entering them into the inventory
        inv.addProduct(new Product(4,"Product Zeta",56.93,12,3,15));
        inv.addProduct(new Product(5,"Product Omega",23.45,34,20,100));

    }

}
