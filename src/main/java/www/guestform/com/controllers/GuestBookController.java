package www.guestform.com.controllers;

import www.guestform.com.Dao.GuestBookDAO;
import www.guestform.com.model.GuestBookRecord;

import java.sql.SQLException;
import java.util.List;
public class GuestBookController {

    public static String getRecordsAsHTML() {

        List<GuestBookRecord> records = null;

        try {
            records = GuestBookDAO.getAllGuestBookRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "";

        for (GuestBookRecord record : records) {

            String htmlTemplate =
                        "<div class=record>\n" +
                        "   <div class=user>" + record.getName() + "</div>\n" +
                        "   <div class=\"dotted-line\"></div>\n" +
                        "   <div class=date>" + record.getDate() + "</div>\n" +
                        "   <div class=\"dotted-line\"></div>\n" +
                        "   <div class=content>" + record.getContent() + "</div>" +
                        "</div>";

            result = result + htmlTemplate;
        }
        return result;
    }
}

