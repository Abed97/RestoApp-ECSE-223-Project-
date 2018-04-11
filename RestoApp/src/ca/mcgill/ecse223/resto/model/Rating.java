/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;

// line 104 "../../../../../RestoApp v2.ump"
public class Rating
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Rating Attributes
  private int stars;

  //Rating Associations
  private OrderItem orderItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Rating(int aStars, OrderItem aOrderItem)
  {
    stars = aStars;
    boolean didAddOrderItem = setOrderItem(aOrderItem);
    if (!didAddOrderItem)
    {
      throw new RuntimeException("Unable to create rating due to orderItem");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStars(int aStars)
  {
    boolean wasSet = false;
    stars = aStars;
    wasSet = true;
    return wasSet;
  }

  public int getStars()
  {
    return stars;
  }

  public OrderItem getOrderItem()
  {
    return orderItem;
  }

  public boolean setOrderItem(OrderItem aOrderItem)
  {
    boolean wasSet = false;
    if (aOrderItem == null)
    {
      return wasSet;
    }

    OrderItem existingOrderItem = orderItem;
    orderItem = aOrderItem;
    if (existingOrderItem != null && !existingOrderItem.equals(aOrderItem))
    {
      existingOrderItem.removeRating(this);
    }
    orderItem.addRating(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    OrderItem placeholderOrderItem = orderItem;
    this.orderItem = null;
    if(placeholderOrderItem != null)
    {
      placeholderOrderItem.removeRating(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "stars" + ":" + getStars()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderItem = "+(getOrderItem()!=null?Integer.toHexString(System.identityHashCode(getOrderItem())):"null");
  }
}