package com.amdocs.phonedepot.model;

import com.amdocs.phonedepot.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Dhanapal
 */
@Entity
@Data
@Table(name = "Company")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCompany;
    @NotNull(message = "name cannot be empty or null")
    private String name;
    private String urlImage;
    @NotNull(message = "nit code cannot be empty or null")
    private String nitCode;
    @NotNull(message = "region code cannot be empty or null")
    private String region;
    @NotNull(message = "city code cannot be empty or null")
    private String city;
    @NotNull(message = "address code cannot be empty or null")
    private String address;
    private String managerName;
    private Long phone;
    private Status status;
}
