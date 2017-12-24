package com.iqmsoft;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iqmsoft.client.ClientDAOImpl;
import com.iqmsoft.client.ClientEntity;
import com.iqmsoft.client.ClientRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDAOTest {

    @Mock
    private ClientRepository customerRepository;

    @InjectMocks
    private ClientDAOImpl customerDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByNif() {
        ClientEntity ce = new ClientEntity("Nome", "259717423", "Morada", "917762535");
        when(customerRepository.findByVat("259717423")).thenReturn(ce);
        ClientEntity result = customerDAO.findByVat("259717423");
        assertEquals("Nome", result.getName());
        assertEquals("259717423", result.getVat());
        assertEquals("Morada", result.getAddress());
        assertEquals("917762535", result.getPhone());
    }

    @Test
    public void testFindByEmptyNif() {
        //caso de insucesso
    }

    @Test
    public void findByNome() {
        List<ClientEntity> cList = new ArrayList<>();
        List<ClientEntity> expectedCList = new ArrayList<>();
        cList.add(new ClientEntity("Nome1", "259717423", "Morada1", "917762535"));
        cList.add(new ClientEntity("Nome2", "259717424", "Morada2", "917762537"));
        cList.add(new ClientEntity("Nome3", "259717425", "Morada3", "917762538"));
        expectedCList.add(new ClientEntity("Nome1", "259717423", "Morada1", "917762535"));
        when(customerRepository.findByNameContainingIgnoreCase("Nome1")).thenReturn(expectedCList);

        List<ClientEntity> result = customerDAO.findByName("Nome1");
        assertEquals(1, result.size());
    }

    @Test
    public void findAll() {
        List<ClientEntity> cList = new ArrayList<>();
        cList.add(new ClientEntity("Nome1", "259717423", "Morada1", "917762535"));
        cList.add(new ClientEntity("Nome2", "259717424", "Morada2", "917762537"));
        cList.add(new ClientEntity("Nome3", "259717425", "Morada3", "917762538"));
        when(customerRepository.findAll()).thenReturn(cList);

        List<ClientEntity> result = customerDAO.findAll();
        assertEquals(3, result.size());
    }

    @Test
    public void save() {
        ClientEntity ce = new ClientEntity("Nome", "259717423", "Morada", "917762535");
        when(customerRepository.save(ce)).thenReturn(ce);
        ClientEntity result = customerDAO.save(ce);
        assertEquals("Nome", result.getName());
        assertEquals("259717423", result.getVat());
        assertEquals("Morada", result.getAddress());
        assertEquals("917762535", result.getPhone());
    }

    @Test
    public void deleteByVat() {
        ClientEntity ce = new ClientEntity("Nome", "259717423", "Morada", "917762535");
        customerDAO.deleteByVat("259717423");
        verify(customerRepository, times(1)).deleteByVat("259717423");
    }
}
