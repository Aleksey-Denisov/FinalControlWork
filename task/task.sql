-- В подключенном MySQL репозитории создать базу данных “Друзья человека”
create database Human_friends;
use Human_friends;
--  Создать таблицы с иерархией из диаграммы в БД
create table Class_Animals(
id int not null auto_increment,
class_name varchar(20) not null,
primary key(id)
);
insert into Class_Animals (class_name) values ("Домашние"),("Вьючные");
create table Class_homeAnimals(
id int not null auto_increment,
gen_name varchar(20) not null,
class_id int not null,
primary key (id),
foreign key (class_id) references Class_Animals (id) on delete cascade on update cascade
);
insert into Class_homeAnimals (gen_name, class_id) values 
('Кошки',1), ('Собаки',1), ('Хомяки',1);
create table Class_packAnimals(
id int not null auto_increment,
gen_name varchar(20) not null,
class_id int not null,
primary key (id),
foreign key (class_id) references Class_Animals (id) on delete cascade on update cascade
);
insert into Class_packAnimals (gen_name, class_id) values 
('Лошади',2), ('Ослы',2), ('Верблюды',2);
-- Заполнить низкоуровневые таблицы именами(животных), командами
-- которые они выполняют и датами рождения
CREATE TABLE cats(       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_homeAnimals (id) on delete cascade on update cascade
);
INSERT INTO cats (name_animal, bday, commands, gen_id)
VALUES ('Пушок', '2011-01-01', "тыгыдык", 1),
('Барсик', '2016-01-01', "прыгнуть", 1),  
('Луна', '2017-01-01', "голос", 1); 
CREATE TABLE dogs(       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_homeAnimals (id) on delete cascade on update cascade
);
INSERT INTO dogs (name_animal, bday, commands, gen_id)
VALUES ('Дик', '2020-01-01', "сидеть", 2),
('Матильда', '2021-06-12', "лежать", 2),  
('Шарик', '2018-05-01', "лапу", 2), 
('Марс', '2021-05-10', "место", 2);
CREATE TABLE hamsters(       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_homeAnimals (id) on delete cascade on update cascade
);
INSERT INTO hamsters (name_animal, bday, commands, gen_id)
VALUES ('Малой', '2020-10-12', "прятаться", 3),
('Медведь', '2021-03-12', "бегать в колесе", 3),  
('Ниндзя', '2022-07-11', "играться", 3), 
('Бурый', '2022-05-10', "просить еду", 3);
CREATE TABLE horses (       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_packAnimals (id) on delete cascade on update cascade
);
INSERT INTO horses (name_animal, bday, commands, gen_id)
VALUES ('Гром', '2020-01-12', "хоп, рысь, шагом, вперед, стоп", 1),
('Закат', '2017-03-12', "хоп, рысь, шагом, вперед, стоп", 1),  
('Байкал', '2016-07-12', "хоп, рысь, шагом, вперед, стоп", 1), 
('Молния', '2020-11-10', "хоп, рысь, шагом, вперед, стоп", 1);
CREATE TABLE donkeys (       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_packAnimals (id) on delete cascade on update cascade
);
INSERT INTO donkeys (name_animal, bday, commands, gen_id)
VALUES ('Первый', '2019-04-10', "вперед, назад, бегом, стоп", 2),
('Второй', '2020-03-12', "вперед, назад, бегом, стоп", 2),  
('Третий', '2021-07-12', "вперед, назад, бегом, стоп", 2), 
('Четвертый', '2022-12-10', "вперед, назад, бегом, стоп", 2);
CREATE TABLE camels (       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
commands varchar(50) not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_packAnimals (id) on delete cascade on update cascade
);
INSERT INTO camels (name_animal, bday, commands, gen_id)
VALUES ('Горбатый', '2022-04-10', 'вернись', 3),
('Самец', '2019-03-12', "остановись", 3),  
('Сифон', '2015-07-12', "повернись", 3), 
('Борода', '2022-12-10', "улыбнись", 3);
-- Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой
-- питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу
SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;
SELECT name_animal, bday, commands FROM horses
UNION SELECT name_animal, bday, commands FROM donkeys;
-- Создать новую таблицу “молодые животные” в которую попадут все
-- животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
-- до месяца подсчитать возраст животных в новой таблице
drop table animals;
CREATE TEMPORARY TABLE animals AS 
SELECT *, 'Лошади' as gen_name FROM horses
UNION SELECT *, 'Ослы' AS gen_name FROM donkeys
UNION SELECT *, 'Собаки' AS gen_name FROM dogs
UNION SELECT *, 'Кошки' AS gen_name FROM cats
UNION SELECT *, 'Хомяки' AS gen_name FROM hamsters;
CREATE TABLE yang_animal AS
SELECT name_animal, bday, commands, gen_name, TIMESTAMPDIFF(MONTH, bday, CURDATE()) AS Age_in_month
FROM animals WHERE bday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
SELECT * FROM yang_animal;
-- Объединить все таблицы в одну, при этом сохраняя поля, указывающие на
-- прошлую принадлежность к старым таблицам.
SELECT h.name_animal, h.bday, h.commands, pa.gen_name, ya.Age_in_month 
FROM horses h
LEFT JOIN yang_animal ya ON ya.name_animal = h.name_animal
LEFT JOIN Class_packAnimals pa ON pa.id = h.gen_id
UNION 
SELECT d.name_animal, d.bday, d.commands, pa.gen_name, ya.Age_in_month 
FROM donkeys d 
LEFT JOIN yang_animal ya ON ya.name_animal = d.name_animal
LEFT JOIN Class_packAnimals pa ON pa.id = d.gen_id
UNION
SELECT c.name_animal, c.bday, c.commands, ha.gen_name, ya.Age_in_month 
FROM cats c
LEFT JOIN yang_animal ya ON ya.name_animal = c.name_animal
LEFT JOIN Class_homeAnimals ha ON ha.id = c.gen_id
UNION
SELECT d.name_animal, d.bday, d.commands, ha.gen_name, ya.Age_in_month 
FROM dogs d
LEFT JOIN yang_animal ya ON ya.name_animal = d.name_animal
LEFT JOIN Class_homeAnimals ha ON ha.id = d.gen_id
UNION
SELECT hm.name_animal, hm.bday, hm.commands, ha.gen_name, ya.Age_in_month 
FROM hamsters hm
LEFT JOIN yang_animal ya ON ya.name_animal = hm.name_animal
LEFT JOIN Class_homeAnimals ha ON ha.id = hm.gen_id;

-- Дополнительно для работы с приложением
CREATE TABLE pet_list(       
id int not null auto_increment,
name_animal varchar(20) not null, 
bday date not null,
gen_id int,
primary key (id),
foreign key (gen_id) references Class_homeAnimals (id) on delete cascade on update cascade
);
create table gen_list(
id int not null auto_increment,
gen_name varchar(20) not null,
primary key (id)
);
insert into gen_list (gen_name) values ("Cat"),("Dog"),("Hamster");
create table comands(
id int not null auto_increment,
id_animal int not null,
command_name varchar(20) not null,
primary key (id),
foreign key (id_animal) references pet_list (id) on delete cascade on update cascade
);
