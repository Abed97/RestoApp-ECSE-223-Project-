package ca.mcgill.ecse223.resto.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 58 "RestoApp.ump"
public class OrderedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderedItem Attributes
  private boolean isShared;

  //OrderedItem Associations
  private MenuItem menuItems;
  private List<BillOrder> billOrders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderedItem(boolean aIsShared, MenuItem aMenuItems)
  {
    isShared = aIsShared;
    boolean didAddMenuItems = setMenuItems(aMenuItems);
    if (!didAddMenuItems)
    {
      throw new RuntimeException("Unable to create orderedItem due to menuItems");
    }
    billOrders = new ArrayList<BillOrder>();
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

  public MenuItem getMenuItems()
  {
    return menuItems;
  }

  public BillOrder getBillOrder(int index)
  {
    BillOrder aBillOrder = billOrders.get(index);
    return aBillOrder;
  }

  public List<BillOrder> getBillOrders()
  {
    List<BillOrder> newBillOrders = Collections.unmodifiableList(billOrders);
    return newBillOrders;
  }

  public int numberOfBillOrders()
  {
    int number = billOrders.size();
    return number;
  }

  public boolean hasBillOrders()
  {
    boolean has = billOrders.size() > 0;
    return has;
  }

  public int indexOfBillOrder(BillOrder aBillOrder)
  {
    int index = billOrders.indexOf(aBillOrder);
    return index;
  }

  public boolean setMenuItems(MenuItem aMenuItems)
  {
    boolean wasSet = false;
    if (aMenuItems == null)
    {
      return wasSet;
    }

    MenuItem existingMenuItems = menuItems;
    menuItems = aMenuItems;
    if (existingMenuItems != null && !existingMenuItems.equals(aMenuItems))
    {
      existingMenuItems.removeOrderedItem(this);
    }
    menuItems.addOrderedItem(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfBillOrders()
  {
    return 0;
  }

  public boolean addBillOrder(BillOrder aBillOrder)
  {
    boolean wasAdded = false;
    if (billOrders.contains(aBillOrder)) { return false; }
    billOrders.add(aBillOrder);
    if (aBillOrder.indexOfOrderedItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBillOrder.addOrderedItem(this);
      if (!wasAdded)
      {
        billOrders.remove(aBillOrder);
      }
    }
    return wasAdded;
  }

  public boolean removeBillOrder(BillOrder aBillOrder)
  {
    boolean wasRemoved = false;
    if (!billOrders.contains(aBillOrder))
    {
      return wasRemoved;
    }

    int oldIndex = billOrders.indexOf(aBillOrder);
    billOrders.remove(oldIndex);
    if (aBillOrder.indexOfOrderedItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBillOrder.removeOrderedItem(this);
      if (!wasRemoved)
      {
        billOrders.add(oldIndex,aBillOrder);
      }
    }
    return wasRemoved;
  }

  public boolean addBillOrderAt(BillOrder aBillOrder, int index)
  {  
    boolean wasAdded = false;
    if(addBillOrder(aBillOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBillOrders()) { index = numberOfBillOrders() - 1; }
      billOrders.remove(aBillOrder);
      billOrders.add(index, aBillOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillOrderAt(BillOrder aBillOrder, int index)
  {
    boolean wasAdded = false;
    if(billOrders.contains(aBillOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBillOrders()) { index = numberOfBillOrders() - 1; }
      billOrders.remove(aBillOrder);
      billOrders.add(index, aBillOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillOrderAt(aBillOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    MenuItem placeholderMenuItems = menuItems;
    this.menuItems = null;
    if(placeholderMenuItems != null)
    {
      placeholderMenuItems.removeOrderedItem(this);
    }
    ArrayList<BillOrder> copyOfBillOrders = new ArrayList<BillOrder>(billOrders);
    billOrders.clear();
    for(BillOrder aBillOrder : copyOfBillOrders)
    {
      aBillOrder.removeOrderedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isShared" + ":" + getIsShared()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "menuItems = "+(getMenuItems()!=null?Integer.toHexString(System.identityHashCode(getMenuItems())):"null");
  }
}