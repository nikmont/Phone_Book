package phonebook;

public class Person implements Comparable<Person>{
    String phone;
    String name;

    public Person(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(Person o) {
        return this.name.toUpperCase().trim().compareTo(o.getName().toUpperCase().trim());
    }
}
