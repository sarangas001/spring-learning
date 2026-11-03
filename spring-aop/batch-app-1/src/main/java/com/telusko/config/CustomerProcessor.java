package com.telusko.config;

import com.telusko.entity.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public @Nullable Customer process(Customer item) throws Exception {
        //logic to process or filter
//        example ->
//        if (item.getCountry() != null &&
//        item.getCountry().equalsIgnoreCase("Sri Lanka"))
//        {
//            item.setFirstName(item.getFirstName().toUpperCase());
//            return item;
//        }
        return item;
    }
}
