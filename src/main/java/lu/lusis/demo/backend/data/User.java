package lu.lusis.demo.backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "USER")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String firstname;

    private String registerNumber;

    private LocalDate birthdate;

    private String countryCode;
    public User() {
    }

    public User(String name, String firstname, LocalDate birthdate) {
        this.name = name;
        this.firstname = firstname;
        this.birthdate = birthdate;
    }

    public User(String name, String firstname, String registerNumber, LocalDate birthdate) {
        this.name = name;
        this.firstname = firstname;
        this.registerNumber = registerNumber;
        this.birthdate = birthdate;
    }

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public int hashCode() {
        if (id == 0) {
            return super.hashCode();
        }

        return 31 + id;
    }

    @Override
    public boolean equals(Object other) {
        if (id == 0) {
            // New entities are only equal if the instance if the same
            return super.equals(other);
        }

        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        return id ==((User) other).id;
    }

    @Override
    public String toString() {
        return firstname + " " + name;
    }
}
