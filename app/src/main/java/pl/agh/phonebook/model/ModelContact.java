package pl.agh.phonebook.model;

public class ModelContact {

        private int id;
        private String name;
        private String email;
        private String phoneNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
}
