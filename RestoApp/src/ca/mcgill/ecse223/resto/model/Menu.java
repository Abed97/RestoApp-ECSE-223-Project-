/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;

// line 69 "../../../../../RestoPersistence.ump"
public class Menu implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Menu Associations
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Menu(RestoApp aRestoApp)
  {
    if (aRestoApp == null || aRestoApp.getMenu() != null)
    {
      throw new RuntimeException("Unable to create Menu due to aRestoApp");
    }
    restoApp = aRestoApp;
  }

  public Menu()
  {
    restoApp = new RestoApp(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public void delete()
  {
    RestoApp existingRestoApp = restoApp;
    restoApp = null;
    if (existingRestoApp != null)
    {
      existingRestoApp.delete();
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 72 "../../../../../RestoPersistence.ump"
  private static final long serialVersionUID = -7403802774454467836L ;

  
}