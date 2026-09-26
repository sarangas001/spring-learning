package repo;

import Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EmployeeRepo2 implements IEmployeeRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    private String sql = "INSERT INTO employee VALUES (4, 'Aseka', 'NuwaraEliya')";

    private String sql = "INSERT INTO employee VALUES (?,?,?)";


    public void input(Employee emp)
    {

        jdbcTemplate.update(sql, emp.getId(), emp.getName(), emp.getCity());
    }

    @Override
    public List<Employee> getEmployeeInfo() {



        return null;
    }
}
