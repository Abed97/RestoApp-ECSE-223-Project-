/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 18 "main.ump"
public class Seat
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Seat> seatsBySeatNumber = new HashMap<Integer, Seat>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Seat Attributes
  private boolean isAvailable;
  private String customerName;
  private int seatNumber;

  //Seat Associations
  private Table table;
  private Bill bill;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Seat(int aSeatNumber, Table aTable, Bill aBill)
  {
    resetIsAvailable();
    customerName = null;
    if (!setSeatNumber(aSeatNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate seatNumber");
    }
    boolean didAddTable = setTable(aTable);
    if (!didAddTable)
    {
      throw new RuntimeException("Unable to create seat due to table");
    }
    boolean didAddBill = setBill(aBill);
    if (!didAddBill)
    {
      throw new RuntimeException("Unable to create seat due to bill");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsAvailable(boolean aIsAvailable)
  {
    boolean wasSet = false;
    isAvailable = aIsAvailable;
    wasSet = true;
    return wasSet;
  }

  public boolean resetIsAvailable()
  {
    boolean wasReset = false;
    isAvailable = getDefaultIsAvailable();
    wasReset = true;
    return wasReset;
  }

  public boolean setCustomerName(String aCustomerName)
  {
    boolean wasSet = false;
    customerName = aCustomerName;
    wasSet = true;
    return wasSet;
  }

  public boolean setSeatNumber(int aSeatNumber)
  {
    boolean wasSet = false;
    Integer anOldSeatNumber = getSeatNumber();
    if (hasWithSeatNumber(aSeatNumber)) {
      return wasSet;
    }
    seatNumber = aSeatNumber;
    wasSet = true;
    if (anOldSeatNumber != null) {
      seatsBySeatNumber.remove(anOldSeatNumber);
    }
    seatsBySeatNumber.put(aSeatNumber, this);
    return wasSet;
  }

  public boolean getIsAvailable()
  {
    return isAvailable;
  }

  public boolean getDefaultIsAvailable()
  {
    return true;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public int getSeatNumber()
  {
    return seatNumber;
  }

  public static Seat getWithSeatNumber(int aSeatNumber)
  {
    return seatsBySeatNumber.get(aSeatNumber);
  }

  public static boolean hasWithSeatNumber(int aSeatNumber)
  {
    return getWithSeatNumber(aSeatNumber) != null;
  }

  public boolean isIsAvailable()
  {
    return isAvailable;
  }

  public Table getTable()
  {
    return table;
  }

  public Bill getBill()
  {
    return bill;
  }

  public boolean setTable(Table aTable)
  {
    boolean wasSet = false;
    if (aTable == null)
    {
      return wasSet;
    }

    Table existingTable = table;
    table = aTable;
    if (existingTable != null && !existingTable.equals(aTable))
    {
      existingTable.removeSeat(this);
    }
    table.addSeat(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setBill(Bill aBill)
  {
    boolean wasSet = false;
    if (aBill == null)
    {
      return wasSet;
    }

    Bill existingBill = bill;
    bill = aBill;
    if (existingBill != null && !existingBill.equals(aBill))
    {
      existingBill.removeSeat(this);
    }
    bill.addSeat(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    seatsBySeatNumber.remove(getSeatNumber());
    Table placeholderTable = table;
    this.table = null;
    if(placeholderTable != null)
    {
      placeholderTable.removeSeat(this);
    }
    Bill placeholderBill = bill;
    this.bill = null;
    if(placeholderBill != null)
    {
      placeholderBill.removeSeat(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isAvailable" + ":" + getIsAvailable()+ "," +
            "customerName" + ":" + getCustomerName()+ "," +
            "seatNumber" + ":" + getSeatNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "table = "+(getTable()!=null?Integer.toHexString(System.identityHashCode(getTable())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bill = "+(getBill()!=null?Integer.toHexString(System.identityHashCode(getBill())):"null");
  }
}