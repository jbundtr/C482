package Model;

/**
 * The <code>InHouse</code> class extends the abstract <code>Part</code> class. This class is used to denote the
 * <code>Part</code> is created in house and not outsourced.
 *
 * @author JamesBundtrock
 */
public class InHouse extends Part {
    /**
     * Holds the <code>machineID</code> of the <code>InHouse</code> <code>Part</code>.
     */
    private int machineID;

    /**
     * Creates an instance of the <code>InHouse</code> class, which extends the <code>Part</code> class.
     *
     * @param id        The unique id of the <code>InHouse</code> <code>Part</code> created.
     * @param name      The name of the <code>InHouse</code> <code>Part</code> created.
     * @param price     The price of the <code>InHouse</code> <code>Part</code> created.
     * @param stock     The current stock of the <code>InHouse</code> <code>Part</code> created.
     * @param min       The minimum stock allowed for the <code>InHouse</code> <code>Part</code> created.
     * @param max       The maximum stock allowed for the <code>InHouse</code> <code>Part</code> created.
     * @param machineID The machine id, which is specific to the <code>InHouse</code> <code>Part</code> class.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;

    }

    /**
     * Sets the <code>machineID</code> for the <code>InHouse</code> <code>Part</code> object.
     *
     * @param machineID The <code>machineID</code> that will replace the current <code>machineID</code> of the object.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Gets the <code>machineID</code> for the <code>InHouse</code> <code>Part</code> object.
     *
     * @return  returns the <code>machineID</code> of the object
     */
    public int getMachineID() {
        return machineID;
    }

}
