package com.iqmsoft.client;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "customers")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    @Length(max = 255)
    private String name;
    @NotBlank
    @Length(max = 12)
    @Column(unique = true)
    private String vat;
    @NotBlank
    @Length(max = 255)
    private String address;
    @NotBlank
    @Length(max = 16)
    @Pattern(regexp = "[0-9]+")
    private String phone;

    public ClientEntity() {
    }

    public ClientEntity(String name, String vat, String address, String phone) {
        this.name = name;
        this.vat = vat;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if ((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }

        ClientEntity e = (ClientEntity) o;
        return e.getVat().equals(this.vat);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.vat);
        return hash;
    }

}
