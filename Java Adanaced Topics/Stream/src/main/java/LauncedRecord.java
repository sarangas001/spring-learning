
//class Employee
//{
//    private Integer id;
//    private String name;
//
//    public Employee()
//    {
//
//    }
//
//    public Employee(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Employee{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
//}

record Employee(Integer id,  String name)
{

    Employee()
    {
        this(0, "");
    }
}

public class LauncedRecord
{
    public static void main(String[] args) {
        Employee e1 = new Employee(1, "John");
        Employee e2  = new Employee(2, "Jane");


        e1.id();

        System.out.println(e1.equals(e2));


    }
}
