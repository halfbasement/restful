package com.example.restful.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Integer id;

    @Size(min = 2)
    private String name;

    @Past // 과거
    private Date joinDate;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String ssn;


}
