package bt.edu.gcit.usermicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "roles")
public class Role {

    // Default constructor
    public Role() {
    }

    // Parameterized constructor
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ID field with auto-generation strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Name field with constraints
    @Column(name = "name", nullable = false, unique = true, length = 40)
    private String name;

    // Description field with constraints
    @Column(name = "description", nullable = false, length = 150)
    private String description;

    // Getters and Setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
