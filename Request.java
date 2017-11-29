package PrototypeSA;
/**
 * Created by Christian on 11/20/2017
 */
import java.time.*;
import java.io.*;
import java.util.ArrayList;

public class Request implements java.io.Serializable
{
    private String requestID;
    private String requester;
    private ArrayList<TimeRange> finalTimes;
    private TimeRange[] times = new TimeRange[3];
    private String requestName;
    private String description;
    private int repeatWeeks;

    public Request(String requestID, String requester, ZonedDateTime[][] ts, String requestName, String description) {
        this.requestID = requestID;
        this.requester = requester;
        this.times[0] = new TimeRange(ts[0][0], ts[0][1]);
        this.times[1] = new TimeRange(ts[1][0], ts[1][1]);
        this.times[2] = new TimeRange(ts[2][0], ts[2][1]);
        this.requestName = requestName;
        this.description = description;

        save();

    }


    public String getRequestID()
    {
        return requestID;
    }

    public String getRequester()
    {
        return requester;
    }

//    public ZonedDateTime[] getFinalTimes()
//    {
//       ZonedDateTime[] finalT = {finalTimes.getStartTime(), finalTimes.getEndTime()};
//        return finalT;
//    }

    public ZonedDateTime getStartTime(int priority)
    {
        if(priority <= 0 || priority > 3)
            throw new IllegalArgumentException();
        else
        return times[priority -1].getStartTime();
    }

    public ZonedDateTime getEndTime(int priority)
    {
        if(priority <= 0 || priority > 3)
            throw new IllegalArgumentException();
        else
            return times[priority -1].getEndTime();
    }

    public String getRequestName()
    {
        return requestName;
    }

    public String getDescription()
    {
        return description;
    }

//    public void setFinalTimes(TimeRange time)
//    {
//        finalTimes = time;
//        save();
//    }

    public void setTime(ZonedDateTime start, ZonedDateTime end, int priority)
    {
        if(priority <=0 || priority > 3)
            throw new IllegalArgumentException();
        else
        {
            times[priority - 1] = new TimeRange(start, end);
            save();
        }
    }

    public void setRequestName(String requestName)
    {
        this.requestName = requestName;
        save();
    }

    public void setDescription(String description)
    {
        this.description = description;
        save();
    }

    public void save(){

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("Requests/request " + requestID + ".rqst");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in request");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }



//    public void pullRequest() {
//
//        try {
//            FileInputStream fileIn = new FileInputStream("/tmp/request.rqst");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            //this = (Request) in.readObject();
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

    private class TimeRange implements java.io.Serializable
    {
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;

        public TimeRange(ZonedDateTime start, ZonedDateTime end)
        {
            startTime = start;
            endTime = end;
        }

        public ZonedDateTime getStartTime()
        {
            return startTime;
        }

        public ZonedDateTime getEndTime()
        {
            return endTime;
        }
    }





}