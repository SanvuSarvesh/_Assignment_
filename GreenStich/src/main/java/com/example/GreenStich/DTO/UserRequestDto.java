package com.example.GreenStich.DTO;

import jakarta.persistence.Access;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    String uname;
    String password;
    String email;
}
