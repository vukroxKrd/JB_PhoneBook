package phonebook;

import java.io.Serializable;
import java.util.Objects;

public class Addressat implements Comparable<Addressat>, Serializable {
    private String number;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Addressat o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addressat addressat = (Addressat) o;
        return this.name.equals(addressat.getName()) &&
                this.number.equals(addressat.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public String toString() {
        return "Addressat{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
