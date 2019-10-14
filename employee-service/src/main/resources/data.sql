DROP TABLE IF EXISTS department;

CREATE TABLE department (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
);

INSERT INTO `department` (id, name) VALUES
    (1, 'R&D Department'),
    (2, 'Management and Marketing Department'),
    (3, 'Legal and Accounting Department'),
    (4, 'Maintenance Department');



DROP TABLE IF EXISTS `position`;

CREATE TABLE `position` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
);

INSERT INTO `position` (id, name) VALUES
    (1, 'Manager'),
    (2, 'Engineer'),
    (3, 'Marketer'),
    (4, 'Lawyer'),
    (5, 'Accountant');



DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  position_id INT NOT NULL,
  department_id INT NOT NULL,
  hire_date DATE NOT NULL,
  fire_date DATE NOT NULL,
  FOREIGN KEY (position_id) REFERENCES `position`,
  FOREIGN KEY (department_id) REFERENCES `department`,
);

INSERT INTO employee (name, position_id, department_id, hire_date, fire_date) VALUES
    ('Ivanov Ivan Ivanovich', 2, 1, '2010-01-01', '2010-01-02'),
    ('Petrov Andrey Sergeevich', 1, 2, '2015-05-10', '2015-05-13'),
    ('Sidorov Petr Nikolaevich', 4, 3, '2008-12-31', '2009-01-02');