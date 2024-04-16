package com.server.server.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document("users")
@Data               // getters, setters
@NoArgsConstructor  // no-argument constructor
@AllArgsConstructor // all-argument constructor
public class User {
    @Id
    private String _id;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
