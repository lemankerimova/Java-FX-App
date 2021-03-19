package az.javafx.dao.impl;

import az.javafx.config.DBConfig;
import az.javafx.dao.TeacherDao;
import az.javafx.model.Student;
import az.javafx.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDao {
    @Override
    public boolean saveTeacher(Teacher teacher) {
        System.out.println(teacher);
        boolean isAdded = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String insertIntoTeacherContactInfo = "INSERT INTO teacher_contact_info (phone,email) VALUES(?,?)";

        String getLastTeacherInfoId = "SELECT MAX(id) id FROM teacher_contact_info";

        String insertIntoTeacher = "INSERT INTO teacher(t_name,surname,age,seria_num,gender,contact_info_id) VALUES(?,?,?,?,?,?)";


        c = DBConfig.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(insertIntoTeacherContactInfo);
                ps.setString(1, teacher.getPhone());
                ps.setString(2, teacher.getEmail());
                ps.execute();

                ps = c.prepareStatement(getLastTeacherInfoId);
                rs = ps.executeQuery();
                int lastStudentInfoId = 0;
                if (rs.next()) {
                    lastStudentInfoId = rs.getInt("id");
                }


                ps = c.prepareStatement(insertIntoTeacher);
                ps.setString(1, teacher.getName());
                ps.setString(2, teacher.getSurname());
                ps.setString(3, teacher.getDOB());
                ps.setString(4, teacher.getSeriaNum());
                ps.setString(5, teacher.getGender());
                ps.setLong(6, lastStudentInfoId);
                ps.execute();
                isAdded = true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    ps.close();
                    c.close();
                } catch (Exception e) {

                }
            }
        }
        return isAdded;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        String sql = "SELECT \n" +
                "    t.id id,\n" +
                "    t.t_name name,\n" +
                "    t.surname surname, \n" +
                "    t.age age,\n" +
                "    t.seria_num seria_num,\n" +
                "    tci.phone phone,\n" +
                "    tci.email\n" +
                "FROM\n" +
                "    teacher t\n" +
                "        LEFT JOIN\n" +
                "    teacher_contact_info tci ON t.contact_info_id = tci.id\n" +
                "WHERE\n" +
                "    t.active = 1;";
        try {
            c = DBConfig.getConnection();
            if(c != null){
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()){
                Teacher teacher = new Teacher();
                    teacher.setId(rs.getLong("id"));
                    teacher.setName(rs.getString("name"));
                    teacher.setSurname(rs.getString("surname"));
                    teacher.setSeriaNum(rs.getNString("seria_num"));
                    teacher.setDOB(rs.getString("age"));
                    teacher.setPhone(rs.getString("phone"));
                    teacher.setEmail(rs.getString("email"));
                    teachers.add(teacher);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                c.close();
                ps.close();
            }catch (SQLException ex){
                ex.getMessage();
            }
        }
        return teachers;
    }

    @Override
    public boolean softDeleteTeacher(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "UPDATE teacher SET active = 0 WHERE id = ?";
        try {
            c = DBConfig.getConnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                ps.execute();
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
                ps.close();
            } catch (SQLException ex) {

            }
            return isDeleted;
        }
    }

    @Override
    public boolean hardDeleteTeacher(Long id) {
            boolean isDeleted = false;
            Connection c = null;
            PreparedStatement ps = null;
            String sql = "DELETE FROM teacher WHERE id = ?";
            try {
                c = DBConfig.getConnection();
                if (c != null) {
                    ps = c.prepareStatement(sql);
                    ps.setLong(1, id);
                    ps.execute();
                    isDeleted = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    c.close();
                    ps.close();
                } catch (SQLException ex) {

                }
            }
            return isDeleted;
    }

    @Override
    public Teacher getTeacherById(Long id) {
       Teacher teacher = new Teacher();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT \n" +
                "    t.id id,\n" +
                "    t.t_name name,\n" +
                "    t.surname surname, \n" +
                "    t.age age,\n" +
                "    t.seria_num seria_num,\n" +
                "    tci.phone phone,\n" +
                "    tci.email\n" +
                "FROM\n" +
                "    teacher t\n" +
                "        LEFT JOIN\n" +
                "    teacher_contact_info sci ON t.contact_info_id = tci.id\n" +
                "WHERE\n" +
                "    t.active = 1 and t.id="+id;

        try {
            c = DBConfig.getConnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    teacher.setId(rs.getLong("id"));
                    teacher.setName(rs.getString("name"));
                    teacher.setSurname(rs.getString("surname"));
                    teacher.setSeriaNum(rs.getNString("seria_num"));
                    teacher.setDOB(rs.getString("age"));
                    teacher.setPhone(rs.getString("phone"));
                    teacher.setEmail(rs.getString("email"));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                c.close();
                ps.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
        return teacher;
    }

    @Override
    public boolean updateTeacherById(Teacher teacher) {
        boolean isUpdated = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String updateTeacher = "UPDATE teacher SET t_name = ?,surname = ?,age = ?,seria_num = ?,gender = ? WHERE id = ?";

        String selectContactInfoById = "SELECT contact_info_id FROM teacher WHERE id="+teacher.getId();

        String updateTeacherContactInfo = "UPDATE teacher_contact_info SET email = ?,phone = ? WHERE id = ?";

        c = DBConfig.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(updateTeacher);
                ps.setString(1, teacher.getName());
                ps.setString(2, teacher.getSurname());
                ps.setString(3, teacher.getDOB());
                ps.setString(4, teacher.getSeriaNum());
                ps.setString(5, teacher.getGender());
                ps.setLong(6, teacher.getId());
                ps.execute();

                ps = c.prepareStatement(selectContactInfoById);
                rs = ps.executeQuery();
                long contactInfoId = 0;
                if (rs.next()){
                    contactInfoId = rs.getLong("contact_info_id");
                }

                ps = c.prepareStatement(updateTeacherContactInfo);
                ps.setString(1,teacher.getEmail());
                ps.setString(2,teacher.getPhone());
                ps.setLong(3,contactInfoId);
                ps.execute();

                isUpdated = true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    ps.close();
                    c.close();
                } catch (Exception e) {

                }
            }
        }
        return isUpdated;
    }
}
