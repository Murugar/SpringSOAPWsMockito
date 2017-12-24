package com.iqmsoft.client;

import java.util.List;

public interface ClientDAO {
    ClientEntity findByVat(String vat);
    List<ClientEntity> findByName(String name);
    List<ClientEntity> findAll();
    ClientEntity save(ClientEntity ce);
    int deleteByVat(String vat);
}
