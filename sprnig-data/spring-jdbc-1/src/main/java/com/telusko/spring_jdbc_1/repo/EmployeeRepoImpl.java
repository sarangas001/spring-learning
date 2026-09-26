package com.telusko.spring_jdbc_1.repo;

import com.telusko.spring_jdbc_1.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepoImpl implements IEmployeeRepo{

    private static final String SQL_QUERY = "SELECT * FROM employee";
    private ArrayList<Employee> list;
    private Connection connection;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Employee> getEmployeeInfo()
    {
        //Spring + JDBC
        try{
            System.out.println("Implementation of datasource" + dataSource.getClass().getName());
            // register //load --> Connection
            connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            ResultSet rs = pstmt.executeQuery();

            list = new ArrayList<>();

            while (rs.next())
            {
                Employee employee = new Employee();
//                int id = rs.getInt(1);
//                employee.setId(id);

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setCity(rs.getString(3));

                list.add(employee);

            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
