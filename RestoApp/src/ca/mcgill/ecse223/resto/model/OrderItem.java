/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 45 "../../../../../RestoPersistence.ump"
// line 77 "../../../../../RestoApp v2.ump"
public class OrderItem implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderItem Attributes
  private int quantity;

  //OrderItem Associations
  private PricedMenuItem pricedMenuItem;
  private List<Seat> seats;
  private Order order;
  private List<Rating> ratings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderItem(int aQuantity, PricedMenuItem aPricedMenuItem, Order aOrder, Seat... allSeats)
  {
    quantity = aQuantity;
    boolean didAddPricedMenuItem = setPricedMenuItem(aPricedMenuItem);
    if (!didAddPricedMenuItem)
    {
      throw new RuntimeException("Unable to create orderItem due to pricedMenuItem");
    }
    seats = new ArrayList<Seat>();
    boolean didAddSeats = setSeats(allSeats);
    if (!didAddSeats)
    {
      throw new RuntimeException("Unable to create OrderItem, must have at least 1 seats");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create orderItem due to order");
    }
    ratings = new ArrayList<Rating>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public PricedMenuItem getPricedMenuItem()
  {
    return pricedMenuItem;
  }

  public Seat getSeat(int index)
  {
    Seat aSeat = seats.get(index);
    return aSeat;
  }

  /**
   * only from order.tables.seats
   */
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

  public Order getOrder()
  {
    return order;
  }

  public Rating getRating(int index)
  {
    Rating aRating = ratings.get(index);
    return aRating;
  }

  public List<Rating> getRatings()
  {
    List<Rating> newRatings = Collections.unmodifiableList(ratings);
    return newRatings;
  }

  public int numberOfRatings()
  {
    int number = ratings.size();
    return number;
  }

  public boolean hasRatings()
  {
    boolean has = ratings.size() > 0;
    return has;
  }

  public int indexOfRating(Rating aRating)
  {
    int index = ratings.indexOf(aRating);
    return index;
  }

  public boolean setPricedMenuItem(PricedMenuItem aPricedMenuItem)
  {
    boolean wasSet = false;
    if (aPricedMenuItem == null)
    {
      return wasSet;
    }

    PricedMenuItem existingPricedMenuItem = pricedMenuItem;
    pricedMenuItem = aPricedMenuItem;
    if (existingPricedMenuItem != null && !existingPricedMenuItem.equals(aPricedMenuItem))
    {
      existingPricedMenuItem.removeOrderItem(this);
    }
    pricedMenuItem.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }

  public boolean isNumberOfSeatsValid()
  {
    boolean isValid = numberOfSeats() >= minimumNumberOfSeats();
    return isValid;
  }

  public static int minimumNumberOfSeats()
  {
    return 1;
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    seats.add(aSeat);
    if (aSeat.indexOfOrderItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSeat.addOrderItem(this);
      if (!wasAdded)
      {
        seats.remove(aSeat);
      }
    }
    return wasAdded;
  }

  public boolean removeSeat(Seat aSeat)
  {
    boolean wasRemoved = false;
    if (!seats.contains(aSeat))
    {
      return wasRemoved;
    }

    if (numberOfSeats() <= minimumNumberOfSeats())
    {
      return wasRemoved;
    }

    int oldIndex = seats.indexOf(aSeat);
    seats.remove(oldIndex);
    if (aSeat.indexOfOrderItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSeat.removeOrderItem(this);
      if (!wasRemoved)
      {
        seats.add(oldIndex,aSeat);
      }
    }
    return wasRemoved;
  }

  public boolean setSeats(Seat... newSeats)
  {
    boolean wasSet = false;
    ArrayList<Seat> verifiedSeats = new ArrayList<Seat>();
    for (Seat aSeat : newSeats)
    {
      if (verifiedSeats.contains(aSeat))
      {
        continue;
      }
      verifiedSeats.add(aSeat);
    }

    if (verifiedSeats.size() != newSeats.length || verifiedSeats.size() < minimumNumberOfSeats())
    {
      return wasSet;
    }

    ArrayList<Seat> oldSeats = new ArrayList<Seat>(seats);
    seats.clear();
    for (Seat aNewSeat : verifiedSeats)
    {
      seats.add(aNewSeat);
      if (oldSeats.contains(aNewSeat))
      {
        oldSeats.remove(aNewSeat);
      }
      else
      {
        aNewSeat.addOrderItem(this);
      }
    }

    for (Seat anOldSeat : oldSeats)
    {
      anOldSeat.removeOrderItem(this);
    }
    wasSet = true;
    return wasSet;
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

  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeOrderItem(this);
    }
    order.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfRatings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Rating addRating(int aStars)
  {
    return new Rating(aStars, this);
  }

  public boolean addRating(Rating aRating)
  {
    boolean wasAdded = false;
    if (ratings.contains(aRating)) { return false; }
    OrderItem existingOrderItem = aRating.getOrderItem();
    boolean isNewOrderItem = existingOrderItem != null && !this.equals(existingOrderItem);
    if (isNewOrderItem)
    {
      aRating.setOrderItem(this);
    }
    else
    {
      ratings.add(aRating);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRating(Rating aRating)
  {
    boolean wasRemoved = false;
    //Unable to remove aRating, as it must always have a orderItem
    if (!this.equals(aRating.getOrderItem()))
    {
      ratings.remove(aRating);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addRatingAt(Rating aRating, int index)
  {  
    boolean wasAdded = false;
    if(addRating(aRating))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRatings()) { index = numberOfRatings() - 1; }
      ratings.remove(aRating);
      ratings.add(index, aRating);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRatingAt(Rating aRating, int index)
  {
    boolean wasAdded = false;
    if(ratings.contains(aRating))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRatings()) { index = numberOfRatings() - 1; }
      ratings.remove(aRating);
      ratings.add(index, aRating);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRatingAt(aRating, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    PricedMenuItem placeholderPricedMenuItem = pricedMenuItem;
    this.pricedMenuItem = null;
    if(placeholderPricedMenuItem != null)
    {
      placeholderPricedMenuItem.removeOrderItem(this);
    }
    ArrayList<Seat> copyOfSeats = new ArrayList<Seat>(seats);
    seats.clear();
    for(Seat aSeat : copyOfSeats)
    {
      aSeat.removeOrderItem(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeOrderItem(this);
    }
    for(int i=ratings.size(); i > 0; i--)
    {
      Rating aRating = ratings.get(i - 1);
      aRating.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pricedMenuItem = "+(getPricedMenuItem()!=null?Integer.toHexString(System.identityHashCode(getPricedMenuItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 48 "../../../../../RestoPersistence.ump"
  private static final long serialVersionUID = 2045406856025012133L ;

  
}