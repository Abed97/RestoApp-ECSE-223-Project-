package ca.mcgill.ecse223.resto.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 24 "RestoApp.ump"
public class Menu
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Menu Associations
  private List<MenuItem> menuItems;
  private Restaurant restaurant;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Menu(Restaurant aRestaurant)
  {
    menuItems = new ArrayList<MenuItem>();
    boolean didAddRestaurant = setRestaurant(aRestaurant);
    if (!didAddRestaurant)
    {
      throw new RuntimeException("Unable to create menu due to restaurant");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public MenuItem getMenuItem(int index)
  {
    MenuItem aMenuItem = menuItems.get(index);
    return aMenuItem;
  }

  public List<MenuItem> getMenuItems()
  {
    List<MenuItem> newMenuItems = Collections.unmodifiableList(menuItems);
    return newMenuItems;
  }

  public int numberOfMenuItems()
  {
    int number = menuItems.size();
    return number;
  }

  public boolean hasMenuItems()
  {
    boolean has = menuItems.size() > 0;
    return has;
  }

  public int indexOfMenuItem(MenuItem aMenuItem)
  {
    int index = menuItems.indexOf(aMenuItem);
    return index;
  }

  public Restaurant getRestaurant()
  {
    return restaurant;
  }

  public static int minimumNumberOfMenuItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addMenuItem(MenuItem aMenuItem)
  {
    boolean wasAdded = false;
    if (menuItems.contains(aMenuItem)) { return false; }
    Menu existingMenu = aMenuItem.getMenu();
    boolean isNewMenu = existingMenu != null && !this.equals(existingMenu);
    if (isNewMenu)
    {
      aMenuItem.setMenu(this);
    }
    else
    {
      menuItems.add(aMenuItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMenuItem(MenuItem aMenuItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aMenuItem, as it must always have a menu
    if (!this.equals(aMenuItem.getMenu()))
    {
      menuItems.remove(aMenuItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addMenuItemAt(MenuItem aMenuItem, int index)
  {  
    boolean wasAdded = false;
    if(addMenuItem(aMenuItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuItems()) { index = numberOfMenuItems() - 1; }
      menuItems.remove(aMenuItem);
      menuItems.add(index, aMenuItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMenuItemAt(MenuItem aMenuItem, int index)
  {
    boolean wasAdded = false;
    if(menuItems.contains(aMenuItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuItems()) { index = numberOfMenuItems() - 1; }
      menuItems.remove(aMenuItem);
      menuItems.add(index, aMenuItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMenuItemAt(aMenuItem, index);
    }
    return wasAdded;
  }

  public boolean setRestaurant(Restaurant aNewRestaurant)
  {
    boolean wasSet = false;
    if (aNewRestaurant == null)
    {
      //Unable to setRestaurant to null, as menu must always be associated to a restaurant
      return wasSet;
    }
    
    Menu existingMenu = aNewRestaurant.getMenu();
    if (existingMenu != null && !equals(existingMenu))
    {
      //Unable to setRestaurant, the current restaurant already has a menu, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Restaurant anOldRestaurant = restaurant;
    restaurant = aNewRestaurant;
    restaurant.setMenu(this);

    if (anOldRestaurant != null)
    {
      anOldRestaurant.setMenu(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=menuItems.size(); i > 0; i--)
    {
      MenuItem aMenuItem = menuItems.get(i - 1);
      aMenuItem.delete();
    }
    Restaurant existingRestaurant = restaurant;
    restaurant = null;
    if (existingRestaurant != null)
    {
      existingRestaurant.setMenu(null);
    }
  }

}