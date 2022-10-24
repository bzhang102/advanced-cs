package brandon.dl.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class DriverLicense {
    private SimpleStringProperty dlNumber;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty middleName;
    private SimpleStringProperty gender;
    private SimpleStringProperty address;
    private SimpleStringProperty vision;
    private SimpleStringProperty pictureLocation;

    private SimpleObjectProperty<LocalDate> dob;
    private SimpleObjectProperty<LocalDate> expiration;

    private SimpleIntegerProperty points;

    private SimpleBooleanProperty suspended;
    private SimpleBooleanProperty underAge;
    private SimpleBooleanProperty expired;
    private SimpleBooleanProperty donor;

    public DriverLicense() {
        dlNumber = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        middleName = new SimpleStringProperty();
        gender = new SimpleStringProperty();
        address = new SimpleStringProperty();
        vision = new SimpleStringProperty();
        pictureLocation = new SimpleStringProperty();

        dob = new SimpleObjectProperty<LocalDate>();
        expiration = new SimpleObjectProperty<LocalDate>();

        points = new SimpleIntegerProperty();

        suspended = new SimpleBooleanProperty();
        underAge = new SimpleBooleanProperty();
        expired = new SimpleBooleanProperty();
        donor = new SimpleBooleanProperty();
    }

    public String getDlNumber() {
        return dlNumber.get();
    }

    public SimpleStringProperty dlNumberProperty() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber.set(dlNumber);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getVision() {
        return vision.get();
    }

    public SimpleStringProperty visionProperty() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision.set(vision);
    }

    public String getPictureLocation() {
        return pictureLocation.get();
    }

    public SimpleStringProperty pictureLocationProperty() {
        return pictureLocation;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation.set(pictureLocation);
    }

    public LocalDate getDob() {
        return dob.get();
    }

    public SimpleObjectProperty<LocalDate> dobProperty() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob.set(dob);

        if (dob.isBefore(LocalDate.now().minusYears(18))) {
            setUnderAge(false);
        } else {
            setUnderAge(true);
        }
    }

    public LocalDate getExpiration() {
        return expiration.get();
    }

    public SimpleObjectProperty<LocalDate> expirationProperty() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration.set(expiration);

        if (expiration.isBefore(LocalDate.now())) {
            setExpired(true);
        } else {
            setExpired(false);
        }
    }

    public int getPoints() {
        return points.get();
    }

    public SimpleIntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        if (points >= 15) {
            setSuspended(true);
        } else {
            setSuspended(false);
        }

        this.points.set(points);
    }

    public boolean isSuspended() {
        return suspended.get();
    }

    public SimpleBooleanProperty suspendedProperty() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended.set(suspended);
    }

    public boolean isUnderAge() {
        return underAge.get();
    }

    public SimpleBooleanProperty underAgeProperty() {
        return underAge;
    }

    public void setUnderAge(boolean underAge) {
        this.underAge.set(underAge);
    }

    public boolean isExpired() {
        return expired.get();
    }

    public SimpleBooleanProperty expiredProperty() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired.set(expired);
    }

    public boolean isDonor() {
        return donor.get();
    }

    public SimpleBooleanProperty donorProperty() {
        return donor;
    }

    public void setDonor(boolean donor) {
        this.donor.set(donor);
    }

    @Override
    public String toString() {
        return "DriverLicense{" +
                "dlNumber=" + dlNumber +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", points = " + points +
                '}';
    }
}
