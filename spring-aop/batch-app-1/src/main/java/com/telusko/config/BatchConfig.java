package com.telusko.config;

import com.telusko.entity.Customer;
import com.telusko.repo.ICustomerRepo;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.LineMapper;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Autowired
    private ICustomerRepo repo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private PlatformTransactionManager transactionManager;

    //ItemReader
    @Bean
    public FlatFileItemReader<Customer> itemReader()
    {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>(new FileSystemResource("src/main/resources/customers_1000.csv"),lineMapper());
        reader.setResource(new FileSystemResource("src/main/resources/customers_1000.csv"));
        reader.setName("csv-reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false); // Don't be very strict ,even if some fields are missing
        lineTokenizer.setNames(
                "customerId",
                "firstName",
                "lastName",
                "email",
                "city",
                "state",
                "country",
                "zipcode"
        );

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    //ItemProcessor
    @Bean
    public CustomerProcessor processCxData()
    {
        return new CustomerProcessor();
    }

    //ItemWriter
    @Bean
    public RepositoryItemWriter<Customer> itemWriter()
    {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<Customer>(repo);
        writer.setRepository(repo);
        writer.setMethodName("save");
        return writer;
    }

    //Step
    @Bean
    public Step step()
    {
        return new StepBuilder("Step-1", jobRepo)
                .<Customer, Customer>chunk(100)
                .transactionManager(transactionManager)
                .reader(itemReader())
                .processor(processCxData())
                .writer(itemWriter())
                .build();
    }

    //Job
    @Bean
    public Job job()
    {
        return new JobBuilder("customer-import", jobRepo)
                .start(step())
                .build();
    }
}
