package com.amdocs.phonedepot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Dhanapal
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTax;
    @NotNull(message = "value cannot be empty or null")
    private double value;
}
