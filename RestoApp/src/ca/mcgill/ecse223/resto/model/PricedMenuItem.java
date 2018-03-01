/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;

// line 42 "../../../../../RestoPersistence.ump"
public class PricedMenuItem implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PricedMenuItem Associations
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PricedMenuItem(RestoApp aRestoApp)
  {
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create pricedMenuItem due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public RestoApp getRestoApp()
  {
    return restoApp;
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
      existingRestoApp.removePricedMenuItem(this);
    }
    restoApp.addPricedMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removePricedMenuItem(this);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 45 "../../../../../RestoPersistence.ump"
  private static final long serialVersionUID = -4855219931984388104L ;

  
}