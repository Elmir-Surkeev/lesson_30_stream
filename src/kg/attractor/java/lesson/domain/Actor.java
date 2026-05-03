package kg.attractor.java.lesson.domain;

import java.util.Objects;

public class Actor extends Person {
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(role, actor.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }

    public String getRole() {
        return role;
    }

    public void print() {
        System.out.printf("%s as %s%n", getFullName(), getRole());
    }
}
