
package www.guestform.com.model;

    public class GuestBookRecord {

        private String name;
        private String date;
        private String content;

        public GuestBookRecord(String name, String date, String content) {
            this.name = name;
            this.date = date;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getContent() {
            return content;
        }
    }


