package com.vladmihalcea.book.hpjp.hibernate.collection;

import com.vladmihalcea.book.hpjp.util.AbstractTest;
import org.hibernate.annotations.NaturalId;
import org.junit.Test;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * <code>UnidirectionalBag</code> - Unidirectional Bag Test
 *
 * @author Vlad Mihalcea
 */
public class UnidirectionalSetTest extends AbstractTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
                Person.class,
                Phone.class,
        };
    }

    @Test
    public void testLifecycle() {
        doInJPA(entityManager -> {
            Person person = new Person(1L);
            person.getPhones().add(new Phone(1L, "landline", "028-234-9876"));
            person.getPhones().add(new Phone(2L, "mobile", "072-122-9876"));
            entityManager.persist(person);
        });
        doInJPA(entityManager -> {
            Person person = entityManager.find(Person.class, 1L);
            Set<Phone> phones = person.getPhones();
            assertEquals(2, phones.size());
            phones.remove(phones.iterator().next());
            assertEquals(1, phones.size());
        });
        doInJPA(entityManager -> {
            Person person = entityManager.find(Person.class, 1L);
            Set<Phone> phones = person.getPhones();
            assertEquals(1, phones.size());
        });
    }

    @Entity(name = "Person")
    public static class Person {

        @Id
        private Long id;

        public Person() {
        }

        public Person(Long id) {
            this.id = id;
        }

        @OneToMany(cascade = CascadeType.ALL)
        private Set<Phone> phones = new HashSet<>();

        public Set<Phone> getPhones() {
            return phones;
        }
    }

    @Entity(name = "Phone")
    public static class Phone {

        @Id
        private Long id;

        private String type;

        @NaturalId
        private String number;

        public Phone() {
        }

        public Phone(Long id, String type, String number) {
            this.id = id;
            this.type = type;
            this.number = number;
        }

        public Long getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getNumber() {
            return number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Phone phone = (Phone) o;
            return Objects.equals(number, phone.number);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
    }
}