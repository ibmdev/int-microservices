package fr.sma.sy.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import java.io.Serializable;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
