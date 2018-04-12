/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.io.ObjectStreamClass;

// line 78 "../../../../../RestoPersistence.ump"
// line 109 "../../../../../RestoApp v2.ump"
public class Rating implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Rating Attributes
  private long serialVersionUID;
  private int stars;

  //Rating Associations
  private OrderItem orderItem;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Rating(int aStars, OrderItem aOrderItem, RestoApp aRestoApp)
  {
    serialVersionUID = ObjectStreamClass.lookup(this.getClass()).getSerialVersionUID();
    stars = aStars;
    boolean didAddOrderItem = setOrderItem(aOrderItem);
    if (!didAddOrderItem)
    {
      throw new RuntimeException("Unable to create rating due to orderItem");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create rating due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSerialVersionUID(long aSerialVersionUID)
  {
    boolean wasSet = false;
    serialVersionUID = aSerialVersionUID;
    wasSet = true;
    return wasSet;
  }

  public boolean setStars(int aStars)
  {
    boolean wasSet = false;
    stars = aStars;
    wasSet = true;
    return wasSet;
  }

  public long getSerialVersionUID()
  {
    return serialVersionUID;
  }

  public int getStars()
  {
    return stars;
  }

  public OrderItem getOrderItem()
  {
    return orderItem;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public boolean setOrderItem(OrderItem aNewOrderItem)
  {
    boolean wasSet = false;
    if (aNewOrderItem == null)
    {
      //Unable to setOrderItem to null, as rating must always be associated to a orderItem
      return wasSet;
    }
    
    Rating existingRating = aNewOrderItem.getRating();
    if (existingRating != null && !equals(existingRating))
    {
      //Unable to setOrderItem, the current orderItem already has a rating, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    OrderItem anOldOrderItem = orderItem;
    orderItem = aNewOrderItem;
    orderItem.setRating(this);

    if (anOldOrderItem != null)
    {
      anOldOrderItem.setRating(null);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeRating(this);
    }
    restoApp.addRating(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    OrderItem existingOrderItem = orderItem;
    orderItem = null;
    if (existingOrderItem != null)
    {
      existingOrderItem.setRating(null);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeRating(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "serialVersionUID" + ":" + getSerialVersionUID()+ "," +
            "stars" + ":" + getStars()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderItem = "+(getOrderItem()!=null?Integer.toHexString(System.identityHashCode(getOrderItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }
}