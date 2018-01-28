/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 50 "main.ump"
public class OrderedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderedItem Attributes
  private boolean isShared;

  //OrderedItem Associations
  private MenuItem menuItem;
  private List<Bill> bills;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderedItem(boolean aIsShared, MenuItem aMenuItem)
  {
    isShared = aIsShared;
    boolean didAddMenuItem = setMenuItem(aMenuItem);
    if (!didAddMenuItem)
    {
      throw new RuntimeException("Unable to create orderedItem due to menuItem");
    }
    bills = new ArrayList<Bill>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsShared(boolean aIsShared)
  {
    boolean wasSet = false;
    isShared = aIsShared;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsShared()
  {
    return isShared;
  }

  public boolean isIsShared()
  {
    return isShared;
  }

  public MenuItem getMenuItem()
  {
    return menuItem;
  }

  public Bill getBill(int index)
  {
    Bill aBill = bills.get(index);
    return aBill;
  }

  public List<Bill> getBills()
  {
    List<Bill> newBills = Collections.unmodifiableList(bills);
    return newBills;
  }

  public int numberOfBills()
  {
    int number = bills.size();
    return number;
  }

  public boolean hasBills()
  {
    boolean has = bills.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bills.indexOf(aBill);
    return index;
  }

  public boolean setMenuItem(MenuItem aMenuItem)
  {
    boolean wasSet = false;
    if (aMenuItem == null)
    {
      return wasSet;
    }

    MenuItem existingMenuItem = menuItem;
    menuItem = aMenuItem;
    if (existingMenuItem != null && !existingMenuItem.equals(aMenuItem))
    {
      existingMenuItem.removeOrderedItem(this);
    }
    menuItem.addOrderedItem(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfBills()
  {
    return 0;
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bills.contains(aBill)) { return false; }
    bills.add(aBill);
    if (aBill.indexOfOrderedItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBill.addOrderedItem(this);
      if (!wasAdded)
      {
        bills.remove(aBill);
      }
    }
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    if (!bills.contains(aBill))
    {
      return wasRemoved;
    }

    int oldIndex = bills.indexOf(aBill);
    bills.remove(oldIndex);
    if (aBill.indexOfOrderedItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBill.removeOrderedItem(this);
      if (!wasRemoved)
      {
        bills.add(oldIndex,aBill);
      }
    }
    return wasRemoved;
  }

  public boolean addBillAt(Bill aBill, int index)
  {  
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bills.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillAt(aBill, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    MenuItem placeholderMenuItem = menuItem;
    this.menuItem = null;
    if(placeholderMenuItem != null)
    {
      placeholderMenuItem.removeOrderedItem(this);
    }
    ArrayList<Bill> copyOfBills = new ArrayList<Bill>(bills);
    bills.clear();
    for(Bill aBill : copyOfBills)
    {
      aBill.removeOrderedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isShared" + ":" + getIsShared()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "menuItem = "+(getMenuItem()!=null?Integer.toHexString(System.identityHashCode(getMenuItem())):"null");
  }
}