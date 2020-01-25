package lemubit.academy.la_webclient_tutorial_reactivewebclient;

public class Person {
    public int id;
    public String name;
    public int age;
    public String country;

    public Person() {
    }

    public Person(int id, String name, int age, String country) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                '}';
    }
}
