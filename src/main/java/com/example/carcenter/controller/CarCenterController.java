package com.example.carcenter.controller;

import com.example.carcenter.persistence.CompanyH2;
import com.example.carcenter.persistence.DepartmentH2;
import com.example.carcenter.persistence.EmployeeH2;
import com.example.carcenter.pojo.*;
import com.example.carcenter.repository.CompanyRepo;
import com.example.carcenter.repository.DepRepo;
import com.example.carcenter.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class CarCenterController {

    @Autowired
    Company company;
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    DepRepo depRepo;
    @Autowired
    EmployeeRepo employeeRepo;

    static Random rnd = new Random();

    @PostMapping("/carCenter")
    public ResponseEntity<String> createCompany(@RequestParam("name") String name) {

        if (name != null && !name.isEmpty() && !name.isBlank()) {
            try {
                company = getCompany(name);

                // Загружаем компанию в H2:
                List<String> strDeps = new ArrayList<>();
                for (Department dep : company.getDepartments()) {
                    strDeps.add(dep.getName());
                }
                CompanyH2 companyH2 = new CompanyH2();
                companyH2.setName(name);
                companyH2.setDepartments(listToStr(strDeps));
                companyRepo.save(companyH2);

                // Загружаем все отделы в H2:
                for (Department dep : company.getDepartments()) {
                    DepartmentH2 depH2 = new DepartmentH2();
                    depH2.setCompanyName(name);
                    depH2.setDepID(dep.getName() + " (" + name + ")");
                    depH2.setName(dep.getName());
                    depH2.setPosition(dep.getPosition());
                    depH2.setEmployeesNum(dep.getEmployeesNum());
                    depH2.setMinSalary(dep.getMinSalary());
                    depH2.setDeltaSalary(dep.getDeltaSalary());
                    depRepo.save(depH2);

                    // Загружаем всех сотрудников в H2:
                    for (Employee employee : dep.getEmployees()) {
                        EmployeeH2 employeeH2 = new EmployeeH2();
                        employeeH2.setLastName(employee.getLastName());
                        employeeH2.setFirstName(employee.getFirstName());
                        employeeH2.setAge(employee.getAge());
                        employeeH2.setSalary(employee.getSalary());
                        employeeH2.setDep(dep.getName());
                        employeeH2.setCompany(name);
                        employeeRepo.save(employeeH2);
                    }

                }

                return new ResponseEntity<>("Your company was added successfully:\n" + printCompanyStructure(company), HttpStatus.ACCEPTED);

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your input name is not valid.");
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your input name is not valid.");
    }

    @GetMapping("/carCenter/all")
    public ResponseEntity<String> getAllCompanies() {
        if (companyRepo.count() != 0) {
            List<CompanyH2> allCompaniesH2 = (List<CompanyH2>) companyRepo.findAll();
            List<EmployeeH2> allEmployeesH2 = (List<EmployeeH2>) employeeRepo.findAll();
            Map<String, List<String>> allCompaniesMap = new HashMap<>();
            for (CompanyH2 companyH2 : allCompaniesH2) {
                allCompaniesMap.put(companyH2.getName(), strToList(companyH2.getDepartments()));
            }
            List<Company> companies = new ArrayList<>();
            List<Department> deps;
            DepartmentH2 depH2;
            Company comp;
            Department dep;
            Employee worker;
            for (var i : allCompaniesMap.entrySet()) {
                comp = new Company();
                comp.setName(i.getKey());
                deps = new ArrayList<>();
                for (String depName : i.getValue()) {
                    dep = new Department();
                    dep.setName(depName);
                    depH2 = depRepo.findById(depName + " (" + i.getKey() + ")").get();
                    dep.setPosition(depH2.getPosition());
                    dep.setEmployeesNum(depH2.getEmployeesNum());
                    dep.setMinSalary(depH2.getMinSalary());
                    dep.setDeltaSalary(depH2.getDeltaSalary());
                    dep.getEmployees().clear();
                    for (EmployeeH2 employeeH2 : allEmployeesH2) {
                        if (i.getKey().equals(employeeH2.getCompany()) && depName.equals(employeeH2.getDep())) {
                            worker = new Employee();
                            worker.setLastName(employeeH2.getLastName());
                            worker.setFirstName(employeeH2.getFirstName());
                            worker.setAge(employeeH2.getAge());
                            worker.setSalary(employeeH2.getSalary());
                            dep.getEmployees().add(worker);
                        }
                    }
                    deps.add(dep);
                }
                comp.setDepartments(deps);
                companies.add(comp);
            }

            return new ResponseEntity<>("The list of all car centers:\n" + printCompanyList(companies), HttpStatus.ACCEPTED);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The database is empty.");
    }

    @DeleteMapping("carCenter/all")
    public ResponseEntity<String> deleteAllCarCenters() {

        if (companyRepo.count() != 0 && depRepo.count() != 0 && employeeRepo.count() != 0) {

            companyRepo.deleteAll();
            depRepo.deleteAll();
            employeeRepo.deleteAll();

            return new ResponseEntity<>("The data of all car centers is deleted successfully.", HttpStatus.ACCEPTED);

        } else {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All car centers data bases are already empty.");

        }
    }

    static Company getCompany(String companyName) {

        int salesmenNum = rnd.nextInt(5) + 12;
        int mechanicsNum = rnd.nextInt(5) + 20;
        int cooksNum = rnd.nextInt(1) + 2;
        int secretariesNum = rnd.nextInt(2) + 3;
        int engineersNum = rnd.nextInt(3) + 5;
        int carwashersNum = rnd.nextInt(4) + 2;
        int accountantsNum = rnd.nextInt(2) + 2;
        int marketersNum =  rnd.nextInt(1) + 2;
        int suppliersNum =  rnd.nextInt(3) + 2;
        int administratorsNum =  rnd.nextInt(4) + 2;
        Department sales = getDep("Sales department", Position.SALESMAN, salesmenNum,0, 30000, 90001);
        Department service = getDep("Service", Position.MECHANIC, mechanicsNum, 1, 50000, 50001);
        Department kitchen = getDep("Canteen", Position.COOK, cooksNum, 0, 70000, 10001);
        Department secretariat = getDep("Secretariat", Position.SECRETARY, secretariesNum, 2, 50000, 1);
        Department carReception = getDep("Car acceptance", Position.ENGINEER, engineersNum, 1, 70000, 50001);
        Department carWash = getDep("Car wash", Position.CARWASHER, carwashersNum, 1, 40000, 5001);
        Department accounting = getDep("Accounting", Position.ACCOUNTANT, accountantsNum, 2, 70000, 10001);
        Department marketing = getDep("Marketing department", Position.MARKETER, marketersNum, 0, 75000, 50001);
        Department purchase = getDep("Purchase department", Position.SUPPLIER, suppliersNum, 0, 60000, 50001);
        Department reception = getDep("Reception", Position.ADMINISTRATOR, administratorsNum, 2, 45000, 5001);
        List<Department> departments = List.of(sales,
                service,
                kitchen,
                secretariat,
                carReception,
                carWash,
                accounting,
                marketing,
                purchase,
                reception);
        Company comp = new Company();
        comp.setName(companyName);
        comp.setDepartments(departments);
        return comp;
    }

    static Department getDep(String name,
                             Position position,
                             int employeesNum,
                             int genders,
                             int minSalary,
                             int deltaSalary) {
        Department dep = new Department();
        List<Employee> employees = new ArrayList<>();
        boolean male = genders != 2;
        dep.setName(name);
        dep.setPosition(position);
        dep.setEmployeesNum(employeesNum);
        dep.setMinSalary(minSalary);
        dep.setDeltaSalary(deltaSalary);
        for (int i = 0; i < employeesNum; i++) {
            if (genders == 0) {
                male = rnd.nextInt(2) == 0;
            }
            employees.add(new Employee(male ? getMaleFirstName() : getFemaleFirstName(),
                    male ? getMaleLastName() : getFemaleLastName(), (rnd.nextInt(deltaSalary) + minSalary)));
        }
        dep.setEmployees(employees);
        return dep;
    }

    static String getMaleFirstName() {

        Random rnd = new Random();
        String[] names = ("""
                Artem  2. Alexander
                          
                                             3. Maxim
                          
                                             4. Daniil
                          
                                             5. Dmitry
                          
                                             6. Ivan
                          
                                             7. Kirill
                          
                                             8. Nikita
                          
                                             9. Michael
                          
                                           10. Egor
                          
                                           11. Matvey
                          
                                           12. Andrey
                          
                                           13. Ilya
                          
                                           14. Alexey
                          
                                           15. Roman
                          
                                           16. Sergey
                          
                                           17. Vladislav
                          
                                           18. Yaroslav
                          
                                           19. Timofey
                          
                                           20. Arseniy
                          
                                           21. Denis
                          
                                           22. Vladimir
                          
                                           23. Pavel
                          
                                           24. Gleb
                          
                                           25. Konstantin
                          
                                           26. Bogdan
                          
                                           27. Evgeniy
                          
                                           28. Nikolay
                          
                                           29. Stepan
                          
                                           30. Zahar""").split("\\PL+");
        return names[rnd.nextInt(names.length)];
    }

    static String getFemaleFirstName() {

        Random rnd = new Random();
        String[] names = ("""
                Sofia
                         
                                            2. Anastasia
                         
                                            3. Daria
                         
                                            4. Maria
                         
                                            5. Anna
                         
                                            6. Victoria
                         
                                            7. Polina
                         
                                            8. Elizabeth
                         
                                            9. Catherine
                         
                                          10. Kseniya
                         
                                          11. Valeria
                         
                                          12. Varvara
                         
                                          13. Alexandra
                         
                                          14. Veronica
                         
                                          15. Arina
                         
                                          16. Alice
                         
                                          17. Alina
                         
                                          18. Milana
                         
                                          19. Margarita
                         
                                          20. Diana
                         
                                          21. Ulyana
                         
                                          22. Alena
                         
                                          23. Angelina + Angelica
                         
                                          24. Christina
                         
                                          25. Yulia
                         
                                          26. Kira
                         
                                          27. Eva
                         
                                          28. Karina
                         
                                          29. Vasilisa
                         
                                          30. Olga""").split("\\PL+");
        return names[rnd.nextInt(names.length)];
    }

    static String getMaleLastName() {

        Random rnd = new Random();
        String[] lastNames = ("Ivanov 1.0000 2 Smirnov 0.7412 3 Kuznetsov 0.7011 4 Popov 0.5334 5 Vasiliev 0.4948 6 Petrov 0.4885 7 Sokolov 0.4666 8 Mikhailov 0.3955 9 Novikov 0.3743 10 Fedorov 0.3662 11 Morozov 0.3639 12 Volkov 0.3636 13 Alekseev 0.3460 14 Lebedev 0.3431 15 Semenov 0.3345 16 Egorov 0.3229 17 Pavlov 0.3226 18 Kozlov 0.3139 19 Stepanov 0.3016 20 Nikolaev 0.3005 21 Orlov 0.2976 22 Andreev 0.2972 23 Makarov 0.2924 24 Nikitin 0.2812 25 Zakharov 0.2755 26 Zaitsev 0.2728 27 Solovyov 0.2712 28 Borisov 0.2710 29 Yakovlev 0.2674 30 Grigoriev 0.2541 31 Romanov 0.2442 32 Vorobyov 0.2371 33 Sergeyev 0.2365 34 Kuzmin 0.2255 35 Frolov 0.2235 36 Aleksandrov 0.2234 37 Dmitriev 0.2171 38 Korolev 0.2083 39 Gusev 0.2075 40 Kiselev 0.2070 41 Ilyin 0.2063 42 Maksimov 0.2059 43 Polyakov 0.2035 44 Sorokin 0.1998 45 Vinogradov 0.1996 46 Kovalev 0.1978 47 Belov 0.1964 48 Medvedev 0.1953 49 Antonov 0.1928 50 Tarasov").split("\\PL+");
        return lastNames[rnd.nextInt(lastNames.length)];
    }

    static String getFemaleLastName() {

        Random rnd = new Random();
        String[] lastNames = ("""
                Orlova;
                                 Lebedeva;
                                 Simonova;
                                 Alexandrova;
                                 Tretyakova;
                                 Lenskaya;
                                 Kamenskaya;
                                 Kozhevnikova;
                                 Denisova;
                                 Andreeva;
                                 Tolmacheva;
                                 Shevchenko;
                                 Panchenko;
                                 Nazarova;
                                 Bezrukova;
                                 Sokolova;
                                 Rodochinskaya;
                                 Volkova;
                                 Kovalevskaya;
                                 Oblomova;
                                 Koroleva;
                                 Volochkova;
                                 Matveeva;
                                 Levchenko;
                                 Leonova;
                                 Kotova;
                                 Bratislavskaya;
                                 Polyakov;
                                 Efimova;
                                 Malysheva;
                                 Tarasova;
                                 Novitskaya;
                                 Novikova;
                                 Istomina;
                                 Ivleva;
                                 Ulyanova;
                                 Romanova;
                                 Gronskaya;
                                 Bondarenko;
                                 Khovanskaya""").split("\\PL+");
        return lastNames[rnd.nextInt(lastNames.length)];
    }

    static String listToStr(List<String> list) {
        StringBuilder str = new StringBuilder();
        for (String i : list) {
            str.append(i).append("&");
        }
        return String.valueOf(str);
    }

    static List<String> strToList(String str) {
        String[] arr = str.split("&");
        return Arrays.stream(arr).toList();
    }

    static String printCompanyStructure(Company company) {

        return company.toString().replace("[", "").replace("]", "");
    }

    static String printCompanyList(List<Company> list) {
        StringBuilder allCompanies = new StringBuilder();
        for (Company i : list) {
            allCompanies.append(printCompanyStructure(i)).append("\n\n");
        }
        return String.valueOf(allCompanies);
    }

}
