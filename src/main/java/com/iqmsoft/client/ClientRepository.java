package com.iqmsoft.client;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {

    ClientEntity findByVat(String vat);

    List<ClientEntity> findByNameContainingIgnoreCase(String name);

    @Transactional
    @Modifying
    int deleteByVat(String vat);
}
