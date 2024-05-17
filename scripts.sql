-- Schema

drop table g_enrollments;
drop table score_grade;
drop table classes;
drop table course_credit;
drop table prerequisites;
drop table courses;
drop table students;
drop table logs;
drop SEQUENCE log_sequence;

create table students (B# char(9) primary key check (B# like 'B%'),
                       first_name varchar2(15) not null, last_name varchar2(15) not null, st_level varchar2(10)
                           check (st_level in ('freshman', 'sophomore', 'junior', 'senior', 'master', 'PhD')),
                       gpa number(3,2) check (gpa between 0 and 4.0), email varchar2(20) unique,
                       bdate date);

create table courses (dept_code varchar2(4), course# number(3)
    check (course# between 100 and 799), title varchar2(20) not null,
                      primary key (dept_code, course#));

create table prerequisites (dept_code varchar2(4) not null,
                            course# number(3) not null, pre_dept_code varchar2(4) not null,
                            pre_course# number(3) not null,
                            primary key (dept_code, course#, pre_dept_code, pre_course#),
                            foreign key (dept_code, course#) references courses on delete cascade,
                            foreign key (pre_dept_code, pre_course#) references courses on delete cascade);

create table course_credit (course# number(3) primary key,
                            check (course# between 100 and 799), credits number(1) check (credits in (3, 4)),
                            check ((course# < 500 and credits = 4) or (course# >= 500 and credits = 3)));

create table classes (classid char(5) primary key check (classid like 'c%'),
                      dept_code varchar2(4) not null, course# number(3) not null,
                      sect# number(2), year number(4), semester varchar2(8)
                          check (semester in ('Spring', 'Fall', 'Summer 1', 'Summer 2', 'Winter')),
                      limit number(3), class_size number(3), room varchar2(10),
                      foreign key (dept_code, course#) references courses on delete cascade,
                      unique(dept_code, course#, sect#, year, semester), check (class_size <= limit),
                      check ((class_size >= 6 and course# >= 500) or class_size >= 10));

create table score_grade (score number(4, 2) primary key,
                          lgrade varchar2(2) check (lgrade in ('A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-','D', 'F', 'I')));

create table g_enrollments (g_B# char(9) references students on delete cascade, classid char(5) references classes on delete cascade,
                            score number(4, 2) references score_grade , primary key (g_B#, classid));

create table logs (log# number(4) primary key,
                   user_name varchar2(10) not null,
                   op_time date not null,
                   table_name varchar2(13) not null,
                   operation varchar2(6) not null,
                   tuple_keyvalue varchar2(20));


-- Data

insert into students values ('B00000001', 'Anne', 'Broder', 'master', 3.7, 'broder@bu.edu', '17-JAN-94');
insert into students values ('B00000002', 'Terry', 'Buttler', 'master', 3.0, 'buttler@bu.edu', '28-MAY-93');
insert into students values ('B00000003', 'Tracy', 'Wang', 'master', 4.0, 'wang@bu.edu','06-AUG-97');
insert into students values ('B00000004', 'Barbara', 'Callan', 'master', 2.5, 'callan@bu.edu', '18-OCT-95');
insert into students values ('B00000005', 'Jack', 'Smith', 'master', 3.2, 'smith@bu.edu', '18-OCT-95');
insert into students values ('B00000006', 'Terry', 'Zillman', 'PhD', 4.0, 'zillman@bu.edu', '15-JUN-92');
insert into students values ('B00000007', 'Becky', 'Lee', 'master', 4.0, 'lee@bu.edu', '12-NOV-96');
insert into students values ('B00000008', 'Tom', 'Baker', 'master', null, 'baker@bu.edu', '23-DEC-97');
insert into students values ('B00000009', 'Ben', 'Liu', 'master', 3.8, 'liu@bu.edu', '18-MAR-96');
insert into students values ('B00000010', 'Sata', 'Patel', 'master', 3.9, 'patel@bu.edu', '12-OCT-94');
insert into students values ('B00000011', 'Art', 'Chang', 'PhD', 4.0, 'chang@bu.edu', '08-JUN-93');
insert into students values ('B00000012', 'Tara', 'Ramesh', 'senior', 3.98, 'ramesh@bu.edu', '29-JUL-98');

insert into courses values ('CS', 432, 'database systems');
insert into courses values ('Math', 314, 'discrete math');
insert into courses values ('CS', 240, 'data structure');
insert into courses values ('CS', 575, 'algorithms');
insert into courses values ('CS', 532, 'database systems');
insert into courses values ('CS', 550, 'operating systems');
insert into courses values ('CS', 536, 'machine learning');

insert into prerequisites values ('CS', '432', 'Math', '314');
insert into prerequisites values ('CS', '432', 'CS', '240');
insert into prerequisites values ('CS', '532', 'CS', '432');
insert into prerequisites values ('CS', '536', 'CS', '532');
insert into prerequisites values ('CS', '575', 'CS', '240');

insert into course_credit values (432, 4);
insert into course_credit values (314, 4);
insert into course_credit values (240, 4);
insert into course_credit values (536, 3);
insert into course_credit values (532, 3);
insert into course_credit values (550, 3);
insert into course_credit values (575, 3);

insert into classes values ('c0001', 'CS', 432, 1, 2021, 'Spring', 13, 12, 'LH 005');
insert into classes values ('c0002', 'Math', 314, 1, 2020, 'Fall', 13, 12, 'LH 009');
insert into classes values ('c0003', 'Math', 314, 2, 2020, 'Fall', 14, 11, 'LH 009');
insert into classes values ('c0004', 'CS', 432, 1, 2020, 'Spring', 13, 13, 'SW 222');
insert into classes values ('c0005', 'CS', 536, 1, 2021, 'Spring', 14, 13, 'LH 003');
insert into classes values ('c0006', 'CS', 532, 1, 2021, 'Spring', 10, 9, 'LH 005');
insert into classes values ('c0007', 'CS', 550, 1, 2021, 'Spring', 11, 11, 'WH 155');


insert into score_grade values (92, 'A');
insert into score_grade values (79.5, 'B');
insert into score_grade values (84, 'B+');
insert into score_grade values (72.8, 'B-');
insert into score_grade values (76, 'B');
insert into score_grade values (65.35, 'C');
insert into score_grade values (94, 'A');
insert into score_grade values (82, 'B+');
insert into score_grade values (88, 'A-');
insert into score_grade values (68, 'C+');

insert into g_enrollments values ('B00000001', 'c0001', 92);
insert into g_enrollments values ('B00000002', 'c0002', 76);
insert into g_enrollments values ('B00000003', 'c0004', 94);
insert into g_enrollments values ('B00000004', 'c0004', 65.35);
insert into g_enrollments values ('B00000004', 'c0005', 82);
insert into g_enrollments values ('B00000005', 'c0006', 79.5);
insert into g_enrollments values ('B00000006', 'c0006', 92);
insert into g_enrollments values ('B00000001', 'c0002', 68);
insert into g_enrollments values ('B00000003', 'c0005', null);
insert into g_enrollments values ('B00000007', 'c0007', 92);
insert into g_enrollments values ('B00000001', 'c0003', 76);
insert into g_enrollments values ('B00000001', 'c0006', 72.8);
insert into g_enrollments values ('B00000001', 'c0004', 94);
insert into g_enrollments values ('B00000001', 'c0005', 76);
insert into g_enrollments values ('B00000003', 'c0001', 84);
insert into g_enrollments values ('B00000005', 'c0001', 76);


-- Package creation
CREATE OR REPLACE PACKAGE student_management_system_pkg AS
    PROCEDURE show_students;
    PROCEDURE show_courses;
    PROCEDURE show_prerequisites;
    PROCEDURE show_course_credit;
    PROCEDURE show_classes;
    PROCEDURE show_score_grade;
    PROCEDURE show_g_enrollments;
    PROCEDURE show_logs;
    PROCEDURE list_students_in_class(p_classid IN classes.classid%TYPE);
    PROCEDURE GET_PREREQUISITES (p_dept_code IN VARCHAR2,p_course_num IN NUMBER);
    PROCEDURE enroll_student(p_B# IN VARCHAR2, p_classid IN VARCHAR2);
    PROCEDURE drop_grad_student_from_class(p_B# students.B#%TYPE,p_classid classes.classid%TYPE);
    PROCEDURE delete_student(p_B# IN students.B#%TYPE);
END student_management_system_pkg;
/

--package body
CREATE OR REPLACE PACKAGE BODY student_management_system_pkg AS
    PROCEDURE show_students IS
    BEGIN
        FOR rec IN (SELECT * FROM students) LOOP
                DBMS_OUTPUT.PUT_LINE('B#: ' || rec.B# || ', First Name: ' || rec.first_name || ', Last Name: ' || rec.last_name || ', Level: ' || rec.st_level || ', GPA: ' || rec.gpa || ', Email: ' || rec.email || ', Birthdate: ' || TO_CHAR(rec.bdate, 'DD-MON-YYYY'));
            END LOOP;
    END show_students;

    PROCEDURE show_courses IS
    BEGIN
        FOR rec IN (SELECT * FROM courses) LOOP
                DBMS_OUTPUT.PUT_LINE('Department Code: ' || rec.dept_code || ', Course#: ' || rec.course# || ', Title: ' || rec.title);
            END LOOP;
    END show_courses;

    PROCEDURE show_prerequisites IS
    BEGIN
        FOR rec IN (SELECT * FROM prerequisites) LOOP
                DBMS_OUTPUT.PUT_LINE('Department Code: ' || rec.dept_code || ', Course#: ' || rec.course# || ', Prerequisite Department Code: ' || rec.pre_dept_code || ', Prerequisite Course#: ' || rec.pre_course#);
            END LOOP;
    END show_prerequisites;

    PROCEDURE show_course_credit IS
    BEGIN
        FOR rec IN (SELECT * FROM course_credit) LOOP
                DBMS_OUTPUT.PUT_LINE('Course#: ' || rec.course# || ', Credits: ' || rec.credits);
            END LOOP;
    END show_course_credit;

    PROCEDURE show_classes IS
    BEGIN
        FOR rec IN (SELECT * FROM classes) LOOP
                DBMS_OUTPUT.PUT_LINE('Class ID: ' || rec.classid || ', Department Code: ' || rec.dept_code || ', Course#: ' || rec.course# || ', Section#: ' || rec.sect# || ', Year: ' || rec.year || ', Semester: ' || rec.semester || ', Limit: ' || rec.limit || ', Class Size: ' || rec.class_size || ', Room: ' || rec.room);
            END LOOP;
    END show_classes;

    PROCEDURE show_score_grade IS
    BEGIN
        FOR rec IN (SELECT * FROM score_grade) LOOP
                DBMS_OUTPUT.PUT_LINE('Score: ' || rec.score || ', Grade: ' || rec.lgrade);
            END LOOP;
    END show_score_grade;

    PROCEDURE show_g_enrollments IS
    BEGIN
        FOR rec IN (SELECT * FROM g_enrollments) LOOP
                DBMS_OUTPUT.PUT_LINE('B#: ' || rec.g_B# || ', Class ID: ' || rec.classid || ', Score: ' || rec.score);
            END LOOP;
    END show_g_enrollments;

    PROCEDURE show_logs IS
    BEGIN
        FOR rec IN (SELECT * FROM logs) LOOP
                DBMS_OUTPUT.PUT_LINE('Log#: ' || rec.log# || ', User Name: ' || rec.user_name || ', Operation Time: ' || TO_CHAR(rec.op_time, 'DD-MON-YYYY HH24:MI:SS') || ', Table Name: ' || rec.table_name || ', Operation: ' || rec.operation || ', Tuple Keyvalue: ' || rec.tuple_keyvalue);
            END LOOP;
    END show_logs;

    PROCEDURE list_students_in_class(p_classid IN classes.classid%TYPE) IS
        v_count NUMBER;
    BEGIN
        -- Check if the provided classid exists in the Classes table
        SELECT COUNT(*) INTO v_count
        FROM classes
        WHERE classid = p_classid;

        -- If the provided classid is invalid, report and exit
        IF v_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
            RETURN;
        END IF;

        -- Fetch and display students in the given class
        FOR rec IN (
            SELECT s.B#, s.first_name, s.last_name
            FROM students s
                     JOIN g_enrollments ge ON s.B# = ge.g_B#
            WHERE ge.classid = p_classid
            ) LOOP
                DBMS_OUTPUT.PUT_LINE('B#: ' || rec.B# || ', Name: ' || rec.first_name || ' ' || rec.last_name);
            END LOOP;
    END list_students_in_class;


    PROCEDURE GET_PREREQUISITES (
        p_dept_code IN VARCHAR2,
        p_course_num IN NUMBER
    ) AS
        v_course_exists NUMBER;
        v_direct_prerequisites SYS_REFCURSOR;
        v_indirect_prerequisites SYS_REFCURSOR;
        v_pre_dept_code courses.dept_code%TYPE;
        v_pre_course_num courses.course#%TYPE;
    BEGIN
        -- Check if the provided course exists
        SELECT COUNT(*)
        INTO v_course_exists
        FROM courses
        WHERE dept_code = p_dept_code AND course# = p_course_num;

        -- If the course does not exist, display an error message
        IF v_course_exists = 0 THEN
            RAISE_APPLICATION_ERROR(-20008,p_dept_code || p_course_num || ' does not exist.');
        ELSE
            -- Direct prerequisite courses
            OPEN v_direct_prerequisites FOR
                SELECT prereq.pre_dept_code, prereq.pre_course#
                FROM prerequisites prereq
                WHERE prereq.dept_code = p_dept_code AND prereq.course# = p_course_num;

            DBMS_OUTPUT.PUT_LINE('Direct Prerequisite Courses for ' || p_dept_code || p_course_num || ':');
            LOOP
                FETCH v_direct_prerequisites INTO v_pre_dept_code, v_pre_course_num;
                EXIT WHEN v_direct_prerequisites%NOTFOUND;
                DBMS_OUTPUT.PUT_LINE(v_pre_dept_code || v_pre_course_num);
            END LOOP;
            CLOSE v_direct_prerequisites;

            -- Indirect prerequisite courses
            OPEN v_indirect_prerequisites FOR
                WITH recursive_prerequisites(dept_code, course#, pre_dept_code, pre_course#) AS (
                    SELECT p.dept_code, p.course#, p.pre_dept_code, p.pre_course#
                    FROM prerequisites p
                    WHERE p.dept_code = p_dept_code AND p.course# = p_course_num
                    UNION ALL
                    SELECT p2.dept_code, p2.course#, p2.pre_dept_code, p2.pre_course#
                    FROM prerequisites p2
                             JOIN recursive_prerequisites rp ON p2.dept_code = rp.pre_dept_code AND p2.course# = rp.pre_course#
                )
                SELECT DISTINCT rp.pre_dept_code, rp.pre_course#
                FROM recursive_prerequisites rp
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM prerequisites p3
                    WHERE p3.dept_code = p_dept_code AND p3.course# = p_course_num
                      AND p3.pre_dept_code = rp.pre_dept_code AND p3.pre_course# = rp.pre_course#
                );

            DBMS_OUTPUT.PUT_LINE('Indirect Prerequisite Courses for ' || p_dept_code || p_course_num || ':');
            LOOP
                FETCH v_indirect_prerequisites INTO v_pre_dept_code, v_pre_course_num;
                EXIT WHEN v_indirect_prerequisites%NOTFOUND;
                DBMS_OUTPUT.PUT_LINE(v_pre_dept_code || v_pre_course_num);
            END LOOP;
            CLOSE v_indirect_prerequisites;
        END IF;
    END GET_PREREQUISITES;

    PROCEDURE enroll_student(p_B# IN VARCHAR2, p_classid IN VARCHAR2) IS
        v_class_limit NUMBER;
        v_class_size NUMBER;
        v_current_semester VARCHAR2(20) := 'Spring'; -- Assuming the current semester is Spring
        v_student_count NUMBER;
        v_prerequisite_satisfied BOOLEAN := TRUE;
        v_pre_dept_code VARCHAR2(4);
        v_pre_course_number NUMBER;
        p_direct_prerequisites SYS_REFCURSOR;
        p_indirect_prerequisites SYS_REFCURSOR;

    BEGIN
        -- Check if the B# is valid and corresponds to a graduate student
        SELECT COUNT(*)
        INTO v_student_count
        FROM students
        WHERE "B#" = p_B# AND st_level IN ('master', 'PhD');

        IF v_student_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20001,'The B# is invalid or does not correspond to a graduate student.');
            RETURN;
        END IF;

        -- Check if the classid is valid
        SELECT COUNT(*)
        INTO v_class_size
        FROM classes
        WHERE classid = p_classid;

        IF v_class_size = 0 THEN
            RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
            RETURN;
        END IF;

        -- Check if the class is offered in the current semester
        SELECT COUNT(*)
        INTO v_class_size
        FROM classes
        WHERE classid = p_classid AND semester = v_current_semester AND year = 2021;

        IF v_class_size = 0 THEN
            RAISE_APPLICATION_ERROR(-20013,'Cannot enroll into a class from a previous semester.');
            RETURN;
        END IF;

        -- Check if the class is already full
        SELECT limit, class_size INTO v_class_limit, v_class_size
        FROM classes WHERE classid = p_classid;

        IF v_class_size >= v_class_limit THEN
            RAISE_APPLICATION_ERROR(-20004,'The class is already full.');
            RETURN;
        END IF;

        -- Check if the student is already in the class
        SELECT COUNT(*)
        INTO v_student_count
        FROM g_enrollments
        WHERE "G_B#" = p_B# AND classid = p_classid;

        IF v_student_count > 0 THEN
            RAISE_APPLICATION_ERROR(-20005,'The student is already in the class.');
            RETURN;
        END IF;

        -- Check if the student is already enrolled in five other classes in the same semester and the same year
        SELECT COUNT(*)
        INTO v_student_count
        FROM g_enrollments ge
                 JOIN classes c ON ge.classid = c.classid
        WHERE ge."G_B#" = p_B# AND c.semester = v_current_semester AND c.year = 2021;

        IF v_student_count >= 5 THEN
            RAISE_APPLICATION_ERROR(-20006,'Students cannot be enrolled in more than five classes in the same semester.');
            RETURN;
        END IF;

        -- Check if the student has completed the required prerequisite courses with at least a grade C
        SELECT dept_code, "COURSE#"
        INTO v_pre_dept_code, v_pre_course_number
        FROM classes
        WHERE classid = p_classid;

        -- Call the GET_PREREQUISITES function to get direct and indirect prerequisites
        student_management_pkg_jdbc.GET_PREREQUISITES(v_pre_dept_code, v_pre_course_number, p_direct_prerequisites, p_indirect_prerequisites);

        -- Check if the student has completed the required prerequisite courses with at least a grade C
        -- Check for direct prerequisites
        LOOP
            FETCH p_direct_prerequisites INTO v_pre_dept_code, v_pre_course_number;
            EXIT WHEN p_direct_prerequisites%NOTFOUND;

            -- Check if the student has completed the direct prerequisite course with at least a grade C
            SELECT COUNT(*)
            INTO v_student_count
            FROM g_enrollments ge
                     JOIN score_grade sg ON ge.score = sg.score
            WHERE "G_B#" = p_B# AND ge.classid IN (
                SELECT classid FROM classes WHERE dept_code = v_pre_dept_code AND "COURSE#" = v_pre_course_number
            ) AND sg.lgrade IN ('C', 'B-', 'B', 'B+', 'A-', 'A');

            IF v_student_count = 0 THEN
                v_prerequisite_satisfied := FALSE;
                EXIT;
            END IF;
        END LOOP;

        -- Check for indirect prerequisites
        IF v_prerequisite_satisfied THEN
            LOOP
                FETCH p_indirect_prerequisites INTO v_pre_dept_code, v_pre_course_number;
                EXIT WHEN p_indirect_prerequisites%NOTFOUND;

                -- Check if the student has completed the indirect prerequisite course with at least a grade C
                SELECT COUNT(*)
                INTO v_student_count
                FROM g_enrollments ge
                         JOIN score_grade sg ON ge.score = sg.score
                WHERE "G_B#" = p_B# AND ge.classid IN (
                    SELECT classid FROM classes WHERE dept_code = v_pre_dept_code AND "COURSE#" = v_pre_course_number
                ) AND sg.lgrade IN ('C', 'B-', 'B', 'B+', 'A-', 'A');

                IF v_student_count = 0 THEN
                    v_prerequisite_satisfied := FALSE;
                    EXIT;
                END IF;
            END LOOP;
        END IF;

        -- If prerequisites are not satisfied, return with an error message
        IF NOT v_prerequisite_satisfied THEN
            RAISE_APPLICATION_ERROR(-20070,'Prerequisite not satisfied.');
            RETURN;
        END IF;

        -- If all conditions are satisfied, enroll the student into the class
        INSERT INTO g_enrollments ("G_B#", classid)
        VALUES (p_B#, p_classid);

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Enrollment successful.');
    END;



    PROCEDURE drop_grad_student_from_class(
        p_B# students.B#%TYPE,
        p_classid classes.classid%TYPE
    ) AS
        v_st_level students.st_level%TYPE;
        v_student_count NUMBER;
        v_class_count NUMBER;
        v_spring_classes_count NUMBER;
    BEGIN
        -- Check if the student exists and is a graduate student
        SELECT COUNT(*)
        INTO v_student_count
        FROM students
        WHERE B# = p_B#;

        IF v_student_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20021,'The specified B# is invalid.');
            RETURN;
        END IF;

        SELECT st_level INTO v_st_level FROM students WHERE B# = p_B#;
        IF v_st_level NOT IN ('master', 'PhD') THEN
            RAISE_APPLICATION_ERROR(-20022,'This is not a graduate student.');
            RETURN;
        END IF;

        -- Check if the class exists
        SELECT COUNT(*)
        INTO v_class_count
        FROM classes
        WHERE classid = p_classid;

        IF v_class_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20000,'The classid is invalid.');
            RETURN;
        END IF;

        -- Check if the student is enrolled in the class
        SELECT COUNT(*)
        INTO v_class_count
        FROM g_enrollments
        WHERE g_B# = p_B# AND classid = p_classid;

        IF v_class_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20023,'The student is not enrolled in the specified class.');
            RETURN;
        END IF;

        -- Check if the class is offered in Spring 2021
        SELECT COUNT(*)
        INTO v_spring_classes_count
        FROM classes
        WHERE classid = p_classid AND semester = 'Spring' AND year = 2021;

        IF v_spring_classes_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20024,'Only enrollment in the current semester can be dropped.');
            RETURN;
        END IF;

        -- Check if the class is the last class for the student in Spring 2021
        SELECT COUNT(*)
        INTO v_spring_classes_count
        FROM g_enrollments ge
                 JOIN classes c ON ge.classid = c.classid
        WHERE ge.g_B# = p_B# AND c.semester = 'Spring' AND c.year = 2021;

        IF v_spring_classes_count = 1 THEN
            RAISE_APPLICATION_ERROR(-20025,'This is the only class for this student in Spring 2021 and cannot be dropped.');
            RETURN;
        END IF;

        -- If all checks passed, delete the enrollment
        DELETE FROM g_enrollments
        WHERE g_B# = p_B# AND classid = p_classid;

        DBMS_OUTPUT.PUT_LINE('Student dropped from class successfully.');
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20090,'An error occurred: ' || SQLERRM);
    END drop_grad_student_from_class;

    PROCEDURE delete_student(p_B# IN students.B#%TYPE) AS
    BEGIN
        DECLARE
            v_count NUMBER;
        BEGIN
            -- Check if the student exists
            SELECT COUNT(*) INTO v_count FROM students WHERE B# = p_B#;

            IF v_count = 0 THEN
                -- Report if the B# is invalid
                RAISE_APPLICATION_ERROR(-20000,'The specified B# is invalid.');
            ELSE
                -- Delete the student
                DELETE FROM students WHERE B# = p_B#;

                -- Delete all tuples in G_Enrollments table involving the student using trigger
                -- This deletion will be handled by the trigger associated with G_Enrollments table
                -- Ensure that the trigger is properly defined to handle cascade deletion

                -- Report successful deletion
                DBMS_OUTPUT.PUT_LINE('Student with B# ' || p_B# || ' deleted successfully.');
            END IF;
        END;
    END delete_student;

END student_management_system_pkg;
/


CREATE SEQUENCE log_sequence
    START WITH 1000
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;



create or replace TRIGGER increment_class_size_trigger
    AFTER INSERT ON g_enrollments
    FOR EACH ROW
DECLARE
    v_class_size NUMBER;
BEGIN
    -- Get the current class_size
    SELECT class_size
    INTO v_class_size
    FROM classes
    WHERE classid = :NEW.classid;

    -- Increment the class_size by 1
    v_class_size := v_class_size + 1;

    -- Update the class_size in the classes table
    UPDATE classes
    SET class_size = v_class_size
    WHERE classid = :NEW.classid;
END;
/


CREATE OR REPLACE TRIGGER decrement_class_size_trigger
    FOR DELETE ON g_enrollments
    COMPOUND TRIGGER
    -- Create a collection to store classids affected by the delete operation
    TYPE classid_table_type IS TABLE OF g_enrollments.classid%TYPE;
    deleted_classids classid_table_type := classid_table_type();

BEFORE EACH ROW IS
BEGIN
    -- Store the classid of the row being deleted
    deleted_classids.EXTEND;
    deleted_classids(deleted_classids.LAST) := :OLD.classid;
END BEFORE EACH ROW;

    AFTER STATEMENT IS
        v_class_size NUMBER;
    BEGIN
        FOR i IN 1..deleted_classids.COUNT LOOP
                -- Get the current class_size
                SELECT class_size
                INTO v_class_size
                FROM classes
                WHERE classid = deleted_classids(i);

                -- Decrement the class_size by 1
                v_class_size := v_class_size - 1;

                -- Update the class_size in the classes table
                UPDATE classes
                SET class_size = v_class_size
                WHERE classid = deleted_classids(i);
            END LOOP;
    END AFTER STATEMENT;
    END decrement_class_size_trigger;
/


CREATE OR REPLACE TRIGGER student_deletion_trigger
    BEFORE DELETE ON students
    FOR EACH ROW
DECLARE
    v_log_id NUMBER;
BEGIN
    SELECT log_sequence.nextval INTO v_log_id FROM dual;
    INSERT INTO logs (log#, user_name, op_time, table_name, operation, tuple_keyvalue)
    VALUES (v_log_id, USER, SYSDATE, 'Students', 'DELETE', :old.B#);
END;
/


CREATE OR REPLACE TRIGGER enrollment_trigger
    AFTER INSERT OR DELETE ON g_enrollments
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO logs (log#, user_name, op_time, table_name, operation, tuple_keyvalue)
        VALUES (log_sequence.NEXTVAL, USER, SYSDATE, 'G_Enrollments', 'INSERT', :new.g_B# || ',' || :new.classid);
    ELSIF DELETING THEN
        INSERT INTO logs (log#, user_name, op_time, table_name, operation, tuple_keyvalue)
        VALUES (log_sequence.NEXTVAL, USER, SYSDATE, 'G_Enrollments', 'DELETE', :old.g_B# || ',' || :old.classid);
    END IF;
END;
/

CREATE OR REPLACE PACKAGE student_management_pkg_jdbc AS
    -- Function to get students
    FUNCTION get_students RETURN SYS_REFCURSOR;

    -- Function to get courses
    FUNCTION get_courses RETURN SYS_REFCURSOR;

    -- Function to get prerequisites
    FUNCTION get_prerequisites RETURN SYS_REFCURSOR;

    -- Function to get course credit
    FUNCTION get_course_credit RETURN SYS_REFCURSOR;

    -- Function to get classes
    FUNCTION get_classes RETURN SYS_REFCURSOR;

    -- Function to get score grade
    FUNCTION get_score_grade RETURN SYS_REFCURSOR;

    -- Function to get G enrollments
    FUNCTION get_g_enrollments RETURN SYS_REFCURSOR;

    -- Function to get logs
    FUNCTION get_logs RETURN SYS_REFCURSOR;

    FUNCTION get_students_in_class(
        p_classid IN classes.classid%TYPE
    ) RETURN SYS_REFCURSOR;

    PROCEDURE GET_PREREQUISITES (
        p_dept_code IN VARCHAR2,
        p_course_num IN NUMBER,
        p_direct_prerequisites OUT SYS_REFCURSOR,
        p_indirect_prerequisites OUT SYS_REFCURSOR);

END student_management_pkg_jdbc;
/


CREATE OR REPLACE PACKAGE BODY student_management_pkg_jdbc  AS
    FUNCTION get_students RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM students;
        RETURN v_cur;
    END get_students;

    FUNCTION get_courses RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM courses;
        RETURN v_cur;
    END get_courses;

    FUNCTION get_prerequisites RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM prerequisites;
        RETURN v_cur;
    END get_prerequisites;

    FUNCTION get_course_credit RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM course_credit;
        RETURN v_cur;
    END get_course_credit;

    FUNCTION get_classes RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM classes;
        RETURN v_cur;
    END get_classes;

    FUNCTION get_score_grade RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM score_grade;
        RETURN v_cur;
    END get_score_grade;

    FUNCTION get_g_enrollments RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM g_enrollments;
        RETURN v_cur;
    END get_g_enrollments;

    FUNCTION get_logs RETURN SYS_REFCURSOR IS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR
            SELECT * FROM logs;
        RETURN v_cur;
    END get_logs;


    FUNCTION get_students_in_class(
        p_classid IN classes.classid%TYPE
    ) RETURN SYS_REFCURSOR IS
        p_students_cur SYS_REFCURSOR;
    BEGIN
        -- Check if the provided classid exists in the Classes table
        DECLARE
            v_count NUMBER;
        BEGIN
            SELECT COUNT(*) INTO v_count
            FROM classes
            WHERE classid = p_classid;

            -- If the provided classid is invalid, raise an exception
            IF v_count = 0 THEN
                RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
            END IF;
        END;

        -- Open the cursor and fetch students in the given class
        OPEN p_students_cur FOR
            SELECT s.B#, s.first_name, s.last_name
            FROM students s
                     JOIN g_enrollments ge ON s.B# = ge.g_B#
            WHERE ge.classid = p_classid;

        -- Return the cursor
        RETURN p_students_cur;
    END get_students_in_class;

    PROCEDURE GET_PREREQUISITES (
        p_dept_code IN VARCHAR2,
        p_course_num IN NUMBER,
        p_direct_prerequisites OUT SYS_REFCURSOR,
        p_indirect_prerequisites OUT SYS_REFCURSOR
    ) AS
    BEGIN
        -- Direct prerequisite courses
        OPEN p_direct_prerequisites FOR
            SELECT prereq.pre_dept_code, prereq.pre_course#
            FROM prerequisites prereq
            WHERE prereq.dept_code = p_dept_code AND prereq.course# = p_course_num;

        -- Indirect prerequisite courses
        OPEN p_indirect_prerequisites FOR
            WITH recursive_prerequisites(dept_code, course#, pre_dept_code, pre_course#) AS (
                SELECT p.dept_code, p.course#, p.pre_dept_code, p.pre_course#
                FROM prerequisites p
                WHERE p.dept_code = p_dept_code AND p.course# = p_course_num
                UNION ALL
                SELECT p2.dept_code, p2.course#, p2.pre_dept_code, p2.pre_course#
                FROM prerequisites p2
                         JOIN recursive_prerequisites rp ON p2.dept_code = rp.pre_dept_code AND p2.course# = rp.pre_course#
            )
            SELECT DISTINCT rp.pre_dept_code, rp.pre_course#
            FROM recursive_prerequisites rp
            WHERE NOT EXISTS (
                SELECT 1
                FROM prerequisites p3
                WHERE p3.dept_code = p_dept_code AND p3.course# = p_course_num
                  AND p3.pre_dept_code = rp.pre_dept_code AND p3.pre_course# = rp.pre_course#
            );
    END GET_PREREQUISITES;
END student_management_pkg_jdbc;
/