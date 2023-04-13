package com.amdocs.phonedepot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.amdocs.phonedepot.enumeration.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dhanapal
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PayMode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPayMode;
    @NotNull(message = "name cannot be empty or null")
    @Column(length = 30)
    private String name;
    private Status status;
}
