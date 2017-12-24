package com.iqmsoft.endpoint;

import ws.namespace.customerservice.CustomerException;
import ws.namespace.customerservice.datatypes.ArrayOfCustomer;
import ws.namespace.customerservice.datatypes.Customer;
import ws.namespace.customerservice.general.AddCustomerRequest;
import ws.namespace.customerservice.general.AddCustomerReturn;
import ws.namespace.customerservice.general.CustomerRequest;
import ws.namespace.customerservice.general.CustomerReturn;
import ws.namespace.customerservice.general.CustomersRequest;
import ws.namespace.customerservice.general.CustomersReturn;
import ws.namespace.customerservice.general.DeleteCustomerRequest;
import ws.namespace.customerservice.general.DeleteCustomerReturn;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqmsoft.client.ClientDAOImpl;
import com.iqmsoft.client.ClientEntity;

@Component
public class ClientController {

    @Autowired
    private ClientDAOImpl customerDao;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerReturn getCustomerByVAT(CustomerRequest customerRequest) throws CustomerException {

        CustomerReturn cr = new CustomerReturn();
        String vat = customerRequest.getVAT();

        boolean validVat = true;
    
        
        if (!validVat) {
            cr.setSuccess(false);
            cr.setErrorCode(100);//invalid vat
        } else {
            ClientEntity ce = customerDao.findByVat(vat);

            if (ce != null) {
                Customer c = this.convertToDto(ce);
                cr.setSuccess(true);
                cr.setCustomerResult(c);
            } else {
                cr.setSuccess(false);
                cr.setErrorCode(101);//user not found
            }
        }

        return cr;
    }

    public CustomersReturn getCustomersByName(CustomersRequest customersRequest) {

        String name = customersRequest.getName();
        List<ClientEntity> ceList = customerDao.findByName(name);
        CustomersReturn cr = new CustomersReturn();
        ArrayOfCustomer ac = new ArrayOfCustomer();
        for (ClientEntity ce : ceList) {
            Customer c = this.convertToDto(ce);
            ac.getCustomer().add(c);
        }

        cr.setSuccess(true);
        cr.setCustomersResult(ac);

        return cr;
    }

    public CustomersReturn getCustomers() {

        List<ClientEntity> ceList = (List<ClientEntity>) customerDao.findAll();
        CustomersReturn cr = new CustomersReturn();
        ArrayOfCustomer ac = new ArrayOfCustomer();
        for (ClientEntity ce : ceList) {
            Customer c = this.convertToDto(ce);
            ac.getCustomer().add(c);
        }

        cr.setSuccess(true);
        cr.setCustomersResult(ac);

        return cr;
    }

    AddCustomerReturn addCustomer(AddCustomerRequest addCustomerRequest) {

        Customer c = addCustomerRequest.getCustomer();
        ClientEntity ce = this.convertToEntity(c);
        AddCustomerReturn acr = new AddCustomerReturn();
        try {
            acr.setSuccess(true);
            ClientEntity res = this.customerDao.save(ce);
        } catch (Exception e) {
            //save was not possible due to validations (unique vat constraint, for example)
            acr.setSuccess(false);
            acr.setErrorCode(102);
            acr.setErrorMessage(e.getMessage());//this error message must be adapted to a friendly message
        }

        return acr;
    }

    DeleteCustomerReturn deleteCustomer(DeleteCustomerRequest deleteCustomerRequest) {

        String vat = deleteCustomerRequest.getVAT();
        int affectedRows = this.customerDao.deleteByVat(vat);
        DeleteCustomerReturn dcr = new DeleteCustomerReturn();

        if (affectedRows > 1) {
            dcr.setSuccess(true);
        } else {
            dcr.setSuccess(false);
            dcr.setErrorCode(103);
        }

        return dcr;
    }

    private Customer convertToDto(ClientEntity customerEntity) {
        Customer customer = modelMapper.map(customerEntity, Customer.class);
        return customer;
    }

    private ClientEntity convertToEntity(Customer customer) {
        ClientEntity customerEntity = modelMapper.map(customer, ClientEntity.class);
        return customerEntity;
    }
}
