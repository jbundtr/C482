package Model;

/**
 * The <code>Outsourced</code> class extends the abstract <code>Part</code> class. This class is used to denote the
 * <code>Part</code> is purchased, rather than created in house.
 *
 * @author JamesBundtrock
 */
public class Outsourced extends Part {
    /**
     * Holds the <code>companyName</code> of the <code>Outsourced</code> <code>Part</code>.
     */
    private String companyName;
    /**
     * Creates an instance of the <code>Outsourced</code> class, which extends the <code>Part</code> class.
     *
     * @param id            The unique id of the <code>Outsourced</code> <code>Part</code> created.
     * @param name          The name of the <code>Outsourced</code> <code>Part</code> created.
     * @param price         The price of the <code>Outsourced</code> <code>Part</code> created.
     * @param stock         The current stock of the <code>Outsourced</code> <code>Part</code> created.
     * @param min           The minimum stock allowed for the <code>Outsourced</code> <code>Part</code> created.
     * @param max           The maximum stock allowed for the <code>Outsourced</code> <code>Part</code> created.
     * @param companyName   The name of the company who makes this <code>Part</code>, specific to the <code>Outsourced</code> <code>Part</code> class.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the value of the name of the company who makes this <code>Part</code>.
     *
     * @param companyName   the name of the company who makes this <code>Part</code>
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the value of the name of the company who makes this <code>Part</code>.
     *
     * @return  The company's name who created this <code>Part</code>
     */
    public String getCompanyName() {
        return companyName;
    }

}
