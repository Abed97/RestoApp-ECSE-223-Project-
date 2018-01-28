/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 27 "main.ump"
public class MenuItem
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum MenuEntry { Steak, Fries, Scallops, Wine, Beer }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<MenuEntry, MenuItem> menuitemsByMenuEntry = new HashMap<MenuEntry, MenuItem>();
  private static Map<String, MenuItem> menuitemsByDescription = new HashMap<String, MenuItem>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MenuItem Attributes
  private MenuEntry menuEntry;
  private int price;
  private String description;

  //MenuItem Associations
  private Menu menu;
  private List<OrderedItem> orderedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MenuItem(MenuEntry aMenuEntry, int aPrice, String aDescription, Menu aMenu)
  {
    price = aPrice;
    if (!setMenuEntry(aMenuEntry))
    {
      throw new RuntimeException("Cannot create due to duplicate menuEntry");
    }
    if (!setDescription(aDescription))
    {
      throw new RuntimeException("Cannot create due to duplicate description");
    }
    boolean didAddMenu = setMenu(aMenu);
    if (!didAddMenu)
    {
      throw new RuntimeException("Unable to create menuItem due to menu");
    }
    orderedItems = new ArrayList<OrderedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMenuEntry(MenuEntry aMenuEntry)
  {
    boolean wasSet = false;
    MenuEntry anOldMenuEntry = getMenuEntry();
    if (hasWithMenuEntry(aMenuEntry)) {
      return wasSet;
    }
    menuEntry = aMenuEntry;
    wasSet = true;
    if (anOldMenuEntry != null) {
      menuitemsByMenuEntry.remove(anOldMenuEntry);
    }
    menuitemsByMenuEntry.put(aMenuEntry, this);
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    String anOldDescription = getDescription();
    if (hasWithDescription(aDescription)) {
      return wasSet;
    }
    description = aDescription;
    wasSet = true;
    if (anOldDescription != null) {
      menuitemsByDescription.remove(anOldDescription);
    }
    menuitemsByDescription.put(aDescription, this);
    return wasSet;
  }

  public MenuEntry getMenuEntry()
  {
    return menuEntry;
  }

  public static MenuItem getWithMenuEntry(MenuEntry aMenuEntry)
  {
    return menuitemsByMenuEntry.get(aMenuEntry);
  }

  public static boolean hasWithMenuEntry(MenuEntry aMenuEntry)
  {
    return getWithMenuEntry(aMenuEntry) != null;
  }

  public int getPrice()
  {
    return price;
  }

  public String getDescription()
  {
    return description;
  }

  public static MenuItem getWithDescription(String aDescription)
  {
    return menuitemsByDescription.get(aDescription);
  }

  public static boolean hasWithDescription(String aDescription)
  {
    return getWithDescription(aDescription) != null;
  }

  public Menu getMenu()
  {
    return menu;
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

  public boolean setMenu(Menu aMenu)
  {
    boolean wasSet = false;
    if (aMenu == null)
    {
      return wasSet;
    }

    Menu existingMenu = menu;
    menu = aMenu;
    if (existingMenu != null && !existingMenu.equals(aMenu))
    {
      existingMenu.removeMenuItem(this);
    }
    menu.addMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfOrderedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderedItem addOrderedItem(boolean aIsShared)
  {
    return new OrderedItem(aIsShared, this);
  }

  public boolean addOrderedItem(OrderedItem aOrderedItem)
  {
    boolean wasAdded = false;
    if (orderedItems.contains(aOrderedItem)) { return false; }
    MenuItem existingMenuItem = aOrderedItem.getMenuItem();
    boolean isNewMenuItem = existingMenuItem != null && !this.equals(existingMenuItem);
    if (isNewMenuItem)
    {
      aOrderedItem.setMenuItem(this);
    }
    else
    {
      orderedItems.add(aOrderedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderedItem(OrderedItem aOrderedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderedItem, as it must always have a menuItem
    if (!this.equals(aOrderedItem.getMenuItem()))
    {
      orderedItems.remove(aOrderedItem);
      wasRemoved = true;
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

  public void delete()
  {
    menuitemsByMenuEntry.remove(getMenuEntry());
    menuitemsByDescription.remove(getDescription());
    Menu placeholderMenu = menu;
    this.menu = null;
    if(placeholderMenu != null)
    {
      placeholderMenu.removeMenuItem(this);
    }
    for(int i=orderedItems.size(); i > 0; i--)
    {
      OrderedItem aOrderedItem = orderedItems.get(i - 1);
      aOrderedItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "menuEntry" + "=" + (getMenuEntry() != null ? !getMenuEntry().equals(this)  ? getMenuEntry().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "menu = "+(getMenu()!=null?Integer.toHexString(System.identityHashCode(getMenu())):"null");
  }
}