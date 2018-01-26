package ca.mcgill.ecse223.resto.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 1 "RestoApp.ump"
public class Restaurant
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Restaurant theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Restaurant Attributes
  private int numTables;

  //Restaurant Associations
  private List<Table> tables;
  private Menu menu;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private Restaurant()
  {
    numTables = 0;
    tables = new ArrayList<Table>();
  }

  public static Restaurant getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new Restaurant();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumTables(int aNumTables)
  {
    boolean wasSet = false;
    numTables = aNumTables;
    wasSet = true;
    return wasSet;
  }

  public int getNumTables()
  {
    return numTables;
  }

  public Table getTable(int index)
  {
    Table aTable = tables.get(index);
    return aTable;
  }

  public List<Table> getTables()
  {
    List<Table> newTables = Collections.unmodifiableList(tables);
    return newTables;
  }

  public int numberOfTables()
  {
    int number = tables.size();
    return number;
  }

  public boolean hasTables()
  {
    boolean has = tables.size() > 0;
    return has;
  }

  public int indexOfTable(Table aTable)
  {
    int index = tables.indexOf(aTable);
    return index;
  }

  public Menu getMenu()
  {
    return menu;
  }

  public boolean hasMenu()
  {
    boolean has = menu != null;
    return has;
  }

  public static int minimumNumberOfTables()
  {
    return 0;
  }

  public boolean addTable(Table aTable)
  {
    boolean wasAdded = false;
    if (tables.contains(aTable)) { return false; }
    Restaurant existingRestaurant = aTable.getRestaurant();
    if (existingRestaurant == null)
    {
      aTable.setRestaurant(this);
    }
    else if (!this.equals(existingRestaurant))
    {
      existingRestaurant.removeTable(aTable);
      addTable(aTable);
    }
    else
    {
      tables.add(aTable);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTable(Table aTable)
  {
    boolean wasRemoved = false;
    if (tables.contains(aTable))
    {
      tables.remove(aTable);
      aTable.setRestaurant(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTableAt(Table aTable, int index)
  {  
    boolean wasAdded = false;
    if(addTable(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTableAt(Table aTable, int index)
  {
    boolean wasAdded = false;
    if(tables.contains(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTableAt(aTable, index);
    }
    return wasAdded;
  }

  public boolean setMenu(Menu aNewMenu)
  {
    boolean wasSet = false;
    if (menu != null && !menu.equals(aNewMenu) && equals(menu.getRestaurant()))
    {
      //Unable to setMenu, as existing menu would become an orphan
      return wasSet;
    }

    menu = aNewMenu;
    Restaurant anOldRestaurant = aNewMenu != null ? aNewMenu.getRestaurant() : null;

    if (!this.equals(anOldRestaurant))
    {
      if (anOldRestaurant != null)
      {
        anOldRestaurant.menu = null;
      }
      if (menu != null)
      {
        menu.setRestaurant(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !tables.isEmpty() )
    {
      tables.get(0).setRestaurant(null);
    }
    Menu existingMenu = menu;
    menu = null;
    if (existingMenu != null)
    {
      existingMenu.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "numTables" + ":" + getNumTables()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "menu = "+(getMenu()!=null?Integer.toHexString(System.identityHashCode(getMenu())):"null");
  }
}