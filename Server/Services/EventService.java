package Services;

import java.util.List;

import DataAccess.DatabaseException;
import DataAccess.EventDao;
import DataAccess.Transaction;
import Model.*;
import RequestResult.EventResult;
import RequestResult.EventsResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class EventService {

    /**
     * Communicates with the appropriate DAOs to get an event based on an eventID.
     * @param eventID
     * @return
     */
    public Event getEvent(String eventID, String username) throws DatabaseException{
        Transaction transaction = new Transaction();

        try{
            transaction.openConnection();
            EventDao ed = transaction.getEd();
            Event e =  ed.getEvent(eventID);
            if(!e.getDescendant().equals(username)){

                throw new DatabaseException("You don't have permission to access it");
            }
            transaction.closeConnection(true);
            return e;
        } catch (DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        }


    }

    public EventsResult getEvents(AuthToken token) throws DatabaseException{
        Transaction transaction = new Transaction();
        try {
            EventsResult er = new EventsResult();
            transaction.openConnection();
            EventDao ed = transaction.getEd();
            List<Event> events;
            events = ed.getEvents(token);
            er.setEvents(events);
            transaction.closeConnection(true);
            return er;
        }catch (DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        }



    }

}
