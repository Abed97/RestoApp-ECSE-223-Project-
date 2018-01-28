/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 8 "main.ump"
public class Table
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextTableNum = 1;
  private static Map<String, Table> tablesByLocation = new HashMap<String, Table>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Table Attributes
  private String location;
  private int totalNbSeats;
  private int availableSeats;
  private boolean isAvailable;

  //Autounique Attributes
  private int tableNum;

  //Table Associations
  private List<Seat> seats;
  private Restaurant restaurant;
  private List<Reservation> reservations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table(String aLocation, int aTotalNbSeats)
  {
    totalNbSeats = aTotalNbSeats;
    resetAvailableSeats();
    resetIsAvailable();
    tableNum = nextTableNum++;
    if (!setLocation(aLocation))
    {
      throw new RuntimeException("Cannot create due to duplicate location");
    }
    seats = new ArrayList<Seat>();
    reservations = new ArrayList<Reservation>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLocation(String aLocation)
  {
    boolean wasSet = false;
    String anOldLocation = getLocation();
    if (hasWithLocation(aLocation)) {
      return wasSet;
    }
    location = aLocation;
    wasSet = true;
    if (anOldLocation != null) {
      tablesByLocation.remove(anOldLocation);
    }
    tablesByLocation.put(aLocation, this);
    return wasSet;
  }

  public boolean setAvailableSeats(int aAvailableSeats)
  {
    boolean wasSet = false;
    availableSeats = aAvailableSeats;
    wasSet = true;
    return wasSet;
  }

  public boolean resetAvailableSeats()
  {
    boolean wasReset = false;
    availableSeats = getDefaultAvailableSeats();
    wasReset = true;
    return wasReset;
  }

  public boolean setIsAvailable(boolean aIsAvailable)
  {
    boolean wasSet = false;
    isAvailable = aIsAvailable;
    wasSet = true;
    return wasSet;
  }

  public boolean resetIsAvailable()
  {
    boolean wasReset = false;
    isAvailable = getDefaultIsAvailable();
    wasReset = true;
    return wasReset;
  }

  public String getLocation()
  {
    return location;
  }

  public static Table getWithLocation(String aLocation)
  {
    return tablesByLocation.get(aLocation);
  }

  public static boolean hasWithLocation(String aLocation)
  {
    return getWithLocation(aLocation) != null;
  }

  public int getTotalNbSeats()
  {
    return totalNbSeats;
  }

  public int getAvailableSeats()
  {
    return availableSeats;
  }

  public int getDefaultAvailableSeats()
  {
    return totalNbSeats;
  }

  public boolean getIsAvailable()
  {
    return isAvailable;
  }

  public boolean getDefaultIsAvailable()
  {
    return true;
  }

  public int getTableNum()
  {
    return tableNum;
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

  public Restaurant getRestaurant()
  {
    return restaurant;
  }

  public boolean hasRestaurant()
  {
    boolean has = restaurant != null;
    return has;
  }

  public Reservation getReservation(int index)
  {
    Reservation aReservation = reservations.get(index);
    return aReservation;
  }

  public List<Reservation> getReservations()
  {
    List<Reservation> newReservations = Collections.unmodifiableList(reservations);
    return newReservations;
  }

  public int numberOfReservations()
  {
    int number = reservations.size();
    return number;
  }

  public boolean hasReservations()
  {
    boolean has = reservations.size() > 0;
    return has;
  }

  public int indexOfReservation(Reservation aReservation)
  {
    int index = reservations.indexOf(aReservation);
    return index;
  }

  public static int minimumNumberOfSeats()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Seat addSeat(int aSeatNumber, Bill aBill)
  {
    return new Seat(aSeatNumber, this, aBill);
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    Table existingTable = aSeat.getTable();
    boolean isNewTable = existingTable != null && !this.equals(existingTable);
    if (isNewTable)
    {
      aSeat.setTable(this);
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
    //Unable to remove aSeat, as it must always have a table
    if (!this.equals(aSeat.getTable()))
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

  public boolean setRestaurant(Restaurant aRestaurant)
  {
    boolean wasSet = false;
    Restaurant existingRestaurant = restaurant;
    restaurant = aRestaurant;
    if (existingRestaurant != null && !existingRestaurant.equals(aRestaurant))
    {
      existingRestaurant.removeTable(this);
    }
    if (aRestaurant != null)
    {
      aRestaurant.addTable(this);
    }
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfReservations()
  {
    return 0;
  }

  public boolean addReservation(Reservation aReservation)
  {
    boolean wasAdded = false;
    if (reservations.contains(aReservation)) { return false; }
    reservations.add(aReservation);
    if (aReservation.indexOfTable(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReservation.addTable(this);
      if (!wasAdded)
      {
        reservations.remove(aReservation);
      }
    }
    return wasAdded;
  }

  public boolean removeReservation(Reservation aReservation)
  {
    boolean wasRemoved = false;
    if (!reservations.contains(aReservation))
    {
      return wasRemoved;
    }

    int oldIndex = reservations.indexOf(aReservation);
    reservations.remove(oldIndex);
    if (aReservation.indexOfTable(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReservation.removeTable(this);
      if (!wasRemoved)
      {
        reservations.add(oldIndex,aReservation);
      }
    }
    return wasRemoved;
  }

  public boolean addReservationAt(Reservation aReservation, int index)
  {  
    boolean wasAdded = false;
    if(addReservation(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReservationAt(Reservation aReservation, int index)
  {
    boolean wasAdded = false;
    if(reservations.contains(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReservationAt(aReservation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    tablesByLocation.remove(getLocation());
    while (seats.size() > 0)
    {
      Seat aSeat = seats.get(seats.size() - 1);
      aSeat.delete();
      seats.remove(aSeat);
    }
    
    if (restaurant != null)
    {
      Restaurant placeholderRestaurant = restaurant;
      this.restaurant = null;
      placeholderRestaurant.removeTable(this);
    }
    ArrayList<Reservation> copyOfReservations = new ArrayList<Reservation>(reservations);
    reservations.clear();
    for(Reservation aReservation : copyOfReservations)
    {
      aReservation.removeTable(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "tableNum" + ":" + getTableNum()+ "," +
            "location" + ":" + getLocation()+ "," +
            "totalNbSeats" + ":" + getTotalNbSeats()+ "," +
            "availableSeats" + ":" + getAvailableSeats()+ "," +
            "isAvailable" + ":" + getIsAvailable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restaurant = "+(getRestaurant()!=null?Integer.toHexString(System.identityHashCode(getRestaurant())):"null");
  }
}