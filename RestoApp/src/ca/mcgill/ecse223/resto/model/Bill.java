/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 56 "main.ump"
public class Bill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bill Attributes
  private int subtotal;

  //Bill Associations
  private List<OrderedItem> orderedItems;
  private List<Seat> seats;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bill()
  {
    subtotal = 0;
    orderedItems = new ArrayList<OrderedItem>();
    seats = new ArrayList<Seat>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSubtotal(int aSubtotal)
  {
    boolean wasSet = false;
    subtotal = aSubtotal;
    wasSet = true;
    return wasSet;
  }

  public int getSubtotal()
  {
    return subtotal;
  }

  public OrderedItem getOrderedItem(int index)
  {
    OrderedItem aOrderedItem = orderedItems.get(index);
    return aOrderedItem;
  }

  public List<OrderedItem> getOrderedItems()
  {
    List<OrderedItem> newOrderedItems = Collections.unmodifiableList(orderedItems);
    return newOrderedItems;
  }

  public int numberOfOrderedItems()
  {
    int number = orderedItems.size();
    return number;
  }

  public boolean hasOrderedItems()
  {
    boolean has = orderedItems.size() > 0;
    return has;
  }

  public int indexOfOrderedItem(OrderedItem aOrderedItem)
  {
    int index = orderedItems.indexOf(aOrderedItem);
    return index;
  }

  public Seat getSeat(int index)
  {
    Seat aSeat = seats.get(index);
    return aSeat;
  }

  public List<Seat> getSeats()
  {
    List<Seat> newSeats = Collections.unmodifiableList(seats);
    return newSeats;
  }

  public int numberOfSeats()
  {
    int number = seats.size();
    return number;
  }

  public boolean hasSeats()
  {
    boolean has = seats.size() > 0;
    return has;
  }

  public int indexOfSeat(Seat aSeat)
  {
    int index = seats.indexOf(aSeat);
    return index;
  }

  public static int minimumNumberOfOrderedItems()
  {
    return 0;
  }

  public boolean addOrderedItem(OrderedItem aOrderedItem)
  {
    boolean wasAdded = false;
    if (orderedItems.contains(aOrderedItem)) { return false; }
    orderedItems.add(aOrderedItem);
    if (aOrderedItem.indexOfBill(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrderedItem.addBill(this);
      if (!wasAdded)
      {
        orderedItems.remove(aOrderedItem);
      }
    }
    return wasAdded;
  }

  public boolean removeOrderedItem(OrderedItem aOrderedItem)
  {
    boolean wasRemoved = false;
    if (!orderedItems.contains(aOrderedItem))
    {
      return wasRemoved;
    }

    int oldIndex = orderedItems.indexOf(aOrderedItem);
    orderedItems.remove(oldIndex);
    if (aOrderedItem.indexOfBill(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrderedItem.removeBill(this);
      if (!wasRemoved)
      {
        orderedItems.add(oldIndex,aOrderedItem);
      }
    }
    return wasRemoved;
  }

  public boolean addOrderedItemAt(OrderedItem aOrderedItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderedItem(aOrderedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderedItems()) { index = numberOfOrderedItems() - 1; }
      orderedItems.remove(aOrderedItem);
      orderedItems.add(index, aOrderedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderedItemAt(OrderedItem aOrderedItem, int index)
  {
    boolean wasAdded = false;
    if(orderedItems.contains(aOrderedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderedItems()) { index = numberOfOrderedItems() - 1; }
      orderedItems.remove(aOrderedItem);
      orderedItems.add(index, aOrderedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderedItemAt(aOrderedItem, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSeats()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Seat addSeat(int aSeatNumber, Table aTable)
  {
    return new Seat(aSeatNumber, aTable, this);
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    Bill existingBill = aSeat.getBill();
    boolean isNewBill = existingBill != null && !this.equals(existingBill);
    if (isNewBill)
    {
      aSeat.setBill(this);
    }
    else
    {
      seats.add(aSeat);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSeat(Seat aSeat)
  {
    boolean wasRemoved = false;
    //Unable to remove aSeat, as it must always have a bill
    if (!this.equals(aSeat.getBill()))
    {
      seats.remove(aSeat);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSeatAt(Seat aSeat, int index)
  {  
    boolean wasAdded = false;
    if(addSeat(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeatAt(Seat aSeat, int index)
  {
    boolean wasAdded = false;
    if(seats.contains(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeatAt(aSeat, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<OrderedItem> copyOfOrderedItems = new ArrayList<OrderedItem>(orderedItems);
    orderedItems.clear();
    for(OrderedItem aOrderedItem : copyOfOrderedItems)
    {
      aOrderedItem.removeBill(this);
    }
    for(int i=seats.size(); i > 0; i--)
    {
      Seat aSeat = seats.get(i - 1);
      aSeat.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "subtotal" + ":" + getSubtotal()+ "]";
  }
}