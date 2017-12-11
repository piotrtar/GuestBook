package www.guestform.com.Dao;

import www.guestform.com.model.GuestBookRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestBookDAO {

    private static final String getAllRecordsQuery = "SELECT * FROM records ORDER BY date DESC";
    private static final String addNewRecordQuery = "INSERT INTO records (name, date, content)" +
                                                    "VALUES(?, ?, ?);";

    public static List<GuestBookRecord> getAllGuestBookRecords() throws SQLException {

        List<GuestBookRecord> records = new ArrayList<GuestBookRecord>();

        Connection conn = ConnectionProvider.getConnection();

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(getAllRecordsQuery);

        while (rs.next()) {

            String name = rs.getString("name");
            String date = rs.getString("date");
            String content = rs.getString("content");

            GuestBookRecord guestBookRecord = new GuestBookRecord(name, date, content);

            records.add(guestBookRecord);
        }

        return records;
    }

    public static void addNewRecord(GuestBookRecord record) {

        try {
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement ps = conn.prepareStatement(addNewRecordQuery);

            ps.setString(1, record.getName());
            ps.setString(2, record.getDate());
            ps.setString(3, record.getContent());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
