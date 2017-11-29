package PrototypeSA;
import java.io.*;
import java.time.*;
import java.util.ArrayList;

/**
 * Created by Christian on 11/20/2017
 */
public class Room implements java.io.Serializable
{
    private String roomNo;
    private String roomName;
    private int capacity;
    private ArrayList<Availability> availabilities;
    private ArrayList<Request> requests;

    public Room(String roomNo, String roomName, int capacity) {
        this.roomNo = roomNo;
        this.roomName = roomName;
        this.capacity = capacity;

        save();
    }

    public String getRoomNo()
    {
        return roomNo;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
        save();
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        save();
    }

    public void save(){

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/Rooms/room " + roomNo + ".roo");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in request");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //For addmins to add timeslots to book
    public Availability addAvailability(ZonedDateTime start, ZonedDateTime end)
    {
        if(start.compareTo(end) != -1)
            if(!checkForOverlap(start, end))
            {
                availabilities.add(new Availability(start, end));
                save();
                return availabilities.get(availabilities.size() - 1);
            }
            else throw new IllegalArgumentException("This overlaps with an existing sloth");
        else
            throw new IllegalArgumentException("The start time cannot be after the end time");
    }

    //For admins to remove timeslots to book
    public Availability removeAvailability(ZonedDateTime start, ZonedDateTime end)
    {
        for(int i = 0; i < availabilities.size(); i++)
        {
            if(availabilities.get(i).getStartTime() == start && availabilities.get(i).getEndTime() == end)
            {
                Availability temp = availabilities.remove(i);
                save();
                return temp;
            }
        }
        throw new IllegalArgumentException("This availability does not exist");
    }

    //To check whether an availability slot overlaps with another
    private boolean checkForOverlap(ZonedDateTime start, ZonedDateTime end)
    {
        for(int i = 0; i < availabilities.size(); i++)
            if((start.compareTo(availabilities.get(i).getStartTime()) >= 0 && start.compareTo(availabilities.get(i).getEndTime()) <= 0)
                    || (end.compareTo(availabilities.get(i).getStartTime()) >= 0 && end.compareTo(availabilities.get(i).getEndTime()) <= 0))
                return true;
        return false;
    }


//    public void pullRequest() {
//
//        try {
//            FileInputStream fileIn = new FileInputStream("/tmp/rooms.roo");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            //this = (Room) in.readObject();
//            in.close();
//            fileIn.close();
//        }   catch (IOException i) {
//            i.printStackTrace();
//            return;
//        } catch (ClassNotFoundException c) {
//            System.out.println("Request class not found");
//            c.printStackTrace();
//            return;
//        }
//
//    }

    private class Availability implements java.io.Serializable
    {
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;

        public Availability(ZonedDateTime start, ZonedDateTime end)
        {
            startTime = start;
            endTime = end;
        }

        public ZonedDateTime getEndTime()
        {
            return endTime;
        }

        public ZonedDateTime getStartTime()
        {
            return startTime;
        }

        public void setEndTime(ZonedDateTime endTime)
        {
            this.endTime = endTime;
        }

        public void setStartTime(ZonedDateTime startTime)
        {
            this.startTime = startTime;
        }
    }
}