package ua.prachyk.FirstRestApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import ua.prachyk.FirstRestApp.models.Person;

public class PersonDTO {

    @NotEmpty(message = "not empty")
    @Size(min = 3, max = 30, message = "between 3 and 30")
    private String name;


    @Min(value = 0, message = "Age should be big that 0")
    private int age;

    @Email
    @NotEmpty
    @UniqueElements
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
