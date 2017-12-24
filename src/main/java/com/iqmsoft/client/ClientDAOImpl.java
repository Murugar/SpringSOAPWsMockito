package com.iqmsoft.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientDAOImpl implements ClientDAO {

    @Autowired
    private ClientRepository customerRepository;

    @Override
    public ClientEntity findByVat(String vat) {
        return this.customerRepository.findByVat(vat);
    }

    @Override
    public List<ClientEntity> findByName(String name) {
        return this.customerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public int deleteByVat(String vat) {
        return this.customerRepository.deleteByVat(vat);
    }

    @Override
    public List<ClientEntity> findAll() {
        return (List<ClientEntity>) this.customerRepository.findAll();
    }

    @Override
    public ClientEntity save(ClientEntity ce) {
        return this.customerRepository.save(ce);
    }

}
