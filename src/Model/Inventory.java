package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * The <code>Inventory</code> contains all the <code>Part</code> and <code>Product</code> objects that are currently
 * in the inventory. Within the class exists one <code>ObservableList</code> per type of object.
 *
 * @author JamesBundtrock
 */
public class Inventory {
    /**
     * Holds all the <code>Part</code> objects within the <code>Inventory</code> class.
     */
    private static ObservableList<Part> allParts;
    /**
     * Holds all the <code>Product</code> objects within the <code>Inventory</code> class.
     */
    private static ObservableList<Product> allProducts;

    /**
     * Constructs an instance of the <code>Inventory</code> class. Initializes the both <code>allProducts</code> and
     * <code>allParts</code> as an <code>ObservableList</code>.
     */
    public Inventory(){
        allProducts = FXCollections.observableArrayList();
        allParts = FXCollections.observableArrayList();
    }

    /**
     * Adds a <code>Part</code> object to the <code>allParts</code> <code>ObservableList</code> within the
     * <code>Inventory</code> class.
     *
     * @param newPart   The <code>Part</code> being added to the <code>Inventory</code>.
     */
    public static void addPart(Part newPart){
        if(newPart != null) {
            allParts.add(newPart);
        }
    }
    /**
     * Adds a <code>Product</code> object to the <code>allProducts</code> <code>ObservableList</code> within the
     * <code>Inventory</code> class.
     *
     * @param newProduct   The <code>Product</code> being added to the <code>Inventory</code>.
     */
    public static void addProduct(Product newProduct){
        if(newProduct != null){
            allProducts.add(newProduct);
        }
    }

    /**
     * Looks up a <code>Part</code> within the <code>Inventory</code> using the <code>partId</code> and returns the
     * <code>Part</code>.
     *
     * @param partId    The <code>partId</code> to be searched for within the <code>Inventory</code>.
     * @return          returns the <code>Part</code> if found, otherwise returns <code>null</code>
     */
    public static Part lookupPart(int partId){
        if(!allParts.isEmpty()){
            for(Part part : allParts){
                if(part.getId() == partId){
                    return part;
                }
            }
        }
        return null;
    }

    /**
     * Looks up a <code>Product</code> within the <code>Inventory</code> using the <code>productId</code> and returns the
     * <code>Product</code>.
     *
     * @param productId The <code>productId</code> to be searched for within the <code>Inventory</code>.
     * @return          returns the <code>Product</code> if found, otherwise returns <code>null</code>
     */
    public static Product lookupProduct(int productId){
        if(!allProducts.isEmpty()){
            for(Product product : allProducts){
                if(product.getId() == productId){
                    return product;
                }
            }
        }
        return null;
    }

    /**
     * Returns an <code>ObservableList</code> of <code>Part</code> objects when given a <code>partName</code> to search for.
     * This overloads the <code>lookupPart</code> method, as it uses a <code>String</code> type parameter, rather than <code>int</code>.
     * Purposefully, it does not only populate the <code>ObservableList</code> with exact, full matches; it also populates
     * it with partial matches and purposefully attempts to ignore case (upper vs. lower).
     *
     * @param partName  The partName of the <code>Part</code> to be searched for within the entire inventory.
     * @return          An <code>ObservableList</code> full of all full and partial matches, irrespective of case
     */
    public static ObservableList<Part> lookupPart(String partName){
        if(!allParts.isEmpty()) {
            ObservableList<Part> parts = FXCollections.observableArrayList();
            for (Part part : allParts) {
                if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                    parts.add(part);
                }
            }
            return parts;
        }
        return null;
    }

    /**
     * Returns an <code>ObservableList</code> of <code>Product</code> objects when given a <code>productName</code> to search for.
     * This overloads the <code>lookupProduct</code> method, as it uses a <code>String</code> type parameter, rather than <code>int</code>.
     * Purposefully, it does not only populate the <code>ObservableList</code> with exact, full matches; it also populates
     * it with partial matches and purposefully attempts to ignore case (upper vs. lower).
     * *
     * @param productName   The productName of the <code>Product</code> to be searched for within the entire inventory.
     * @return              An <code>ObservableList</code> full of all full and partial matches, irrespective of case
     */
    public static ObservableList<Product> lookupProduct(String productName){
        if(!allProducts.isEmpty()) {
            ObservableList<Product> products = FXCollections.observableArrayList();
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                    products.add(product);
                }
            }
            return products;
        }
        return null;
    }

    /**
     * Replaces a <code>Part</code> within the inventory with the <code>Part</code> given as a parameter. This is only
     * successful if the <code>partId</code> of the <code>Part</code> passed to this method matches a <code>partId</code>
     * within the inventory of <code>Part</code> objects. Since all <code>partId</code> fields must be unique for each
     * and every <code>Part</code> object, this method replaces the first match, and only the first as there can be no
     * second.
     *
     * @param selectedPart  A new <code>Part</code> object to replace the matching <code>Part</code> within the inventory
     */
    public static void updatePart(Part selectedPart){
        for (Part parts : allParts){
            if (parts.getId() == selectedPart.getId()){
                int i = allParts.indexOf(parts);
                allParts.set(i,selectedPart);
                break;
            }
        }
    }

    /**
     * Replaces a <code>Product</code> within the inventory with the <code>Product</code> given as a parameter. This is only
     * successful if the <code>productId</code> of the <code>Product</code> passed to this method matches a <code>productId</code>
     * within the inventory of <code>Product</code> objects. Since all <code>productId</code> fields must be unique for each
     * and every <code>Product</code> object, this method replaces the first match, and only the first as there can be no
     * second.
     *
     * @param selectedProduct   A new <code>Product</code> object to replace the matching <code>Product</code> within the inventory
     */
    public static void updateProduct(Product selectedProduct){
        for (Product products : allProducts){
            if (products.getId() == selectedProduct.getId()){
                int i = allProducts.indexOf(products);
                allProducts.set(i,selectedProduct);
                break;
            }
        }
    }

    /**
     * Removes this <code>Part</code> from the inventory of <code>Part</code> objects. This is only successful if the
     * <code>partId</code> of the <code>Part</code> passed to this method matches a <code>partId</code> within the
     * inventory of <code>Part</code> objects. Since all <code>partId</code> fields must be unique for each and every
     * <code>Part</code> object, this method replaces the first match, and only the first as there can be no second. If
     * no <code>Part</code> object is found with a matching <code>partId</code>, the method returns false.
     *
     * @param selectedPart  The <code>Part</code> object to be removed from the inventory
     * @return              A check on whether or not a <code>Part</code> object was removed
     */
    public static boolean deletePart(Part selectedPart){
        boolean changed = false;
        for(Part part : allParts){
            if(part.getId() == selectedPart.getId()){
                changed = allParts.remove(part);
                break;
            }
        }

        return changed;
    }

    /**
     * Removes this <code>Product</code> from the inventory of <code>Product</code> objects. This is only successful if
     * the <code>productId</code> of the <code>Product</code> passed to this method matches a <code>productId</code> within
     * the inventory of <code>Product</code> objects. Since all <code>productId</code> fields must be unique for each and
     * every <code>Product</code> object, this method removes the first match, and only the first as there can be no second.
     * If no <code>Product</code> object is found with a matching <code>productId</code>, the method returns false.
     *
     * @param selectedProduct   The <code>Product</code> object to be removed from the inventory
     * @return                  A check on whether or not a <code>Product</code> object was removed
     */
    public static boolean deleteProduct(Product selectedProduct){
        boolean changed = false;
        for(Product product : allProducts){
            if(product.getId() == selectedProduct.getId()){
                changed = allProducts.remove(product);
                break;
            }
        }

        return changed;
    }

    /**
     * Populates and returns an <code>ObservableList</code> with every <code>Part</code> object within the inventory.
     *
     * @return  An <code>ObservableList</code> containing every <code>Part</code> within the inventory
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Populates and returns an <code>ObservableList</code> with every <code>Product</code> object within the inventory.
     *
     * @return  An <code>ObservableList</code> containing every <code>Product</code> within the inventory
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
