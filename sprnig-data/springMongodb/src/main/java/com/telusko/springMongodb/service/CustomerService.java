package com.telusko.springMongodb.service;

import com.telusko.springMongodb.dto.CustomerDTO;
import com.telusko.springMongodb.model.Customer;
import com.telusko.springMongodb.repo.ICustomerRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{


    private ICustomerRepo repo;

    private CustomerService(ICustomerRepo repo) {
        this.repo = repo;
    }

    @Override
    public String registerCustomerInfo(CustomerDTO dto) {
        Customer cx = new Customer();
//        cx.setName(dto.getName());
//        cx.setCity(dto.getCity());
//        cx.setCusNum(dto.getCusNum());
        BeanUtils.copyProperties(dto, cx);
        Customer c = repo.save(cx);
        return "Customer info stored with id " + c.getId();
    }

    @Override
    public List<Customer> findAll() {
        return repo.findAll();
    }

    @Override
    public String removeDocument(String id) {
        Optional<Customer> optional = repo.findById(id);
        if (optional.isPresent()) {
            repo.deleteById(id);
            return "Customer info deleted with id " + id;
        }
        return "Customer info not in records to delete";
    }
}
