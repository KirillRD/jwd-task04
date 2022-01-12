package com.epam.library.dao.impl;

import com.epam.library.dao.UserDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserListFilterName;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MYSQLUserDAO implements UserDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID_USER = "MAX(id)";
    private static final String ID = "id";
    private static final String ROLES_ID = "roles_id";
    private static final String NICKNAME = "nickname";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String PASSPORT = "passport";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String IMAGE = "image";
    private static final String DEFAULT_IMAGE_USER = "images/users/default_image_user";

    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String SELECT_USER = "SELECT * FROM users WHERE id=?";
    //private static final String GET_RESERVATIONS = "SELECT * AS count_reservations FROM users WHERE reader_id=?";
    //private static final String GET_REVIEWS = "SELECT * AS count_reviews FROM users WHERE reader_id=?";
    //private static final String GET_ISSUANCE = "SELECT * AS count_issuance FROM users WHERE reader_id=?";
    private static final String GET_MAX_ID_USER = "SELECT MAX(id) FROM users";
    private static final String REGISTRATION_USER = "INSERT INTO users (id, roles_id, nickname, password, email, last_name, first_name, father_name, birthday, gender, passport, address, phone, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String AUTHORIZATION_USER = "SELECT * FROM users WHERE email=?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    //private static final String INSERT_USER_ROLE = "INSERT INTO users_roles (users_id, roles_id) VALUES (?,?)";
    //private static final String SELECT_ROLES_BY_USER = "SELECT roles_id FROM users_roles WHERE users_id=?";
    private static final String UPDATE_USER = "UPDATE users SET roles_id=?, nickname=?, email=?, last_name=?, first_name=?, father_name=?, birthday=?, gender=?, passport=?, address=?, phone=?, image=? WHERE id=?";
    //private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    //private static final String DELETE_USER_ROLES = "DELETE FROM users_roles WHERE users_id=?";


    private static final String USER_FILTER_QUERY = "SELECT * FROM users";
    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String LAST_NAME_FILTER = "last_name LIKE ?";
    private static final String EMAIL_FILTER = "email LIKE ?";
    private static final String NICKNAME_FILTER = "nickname LIKE ?";
    private static final String PERCENT = "%";
    private static final String WHERE = " WHERE ";
    private static final Map<String, String> sortValue = Map.of(
            UserListFilterName.LAST_NAME_ASCENDING, "last_name ASC",
            UserListFilterName.LAST_NAME_DESCENDING, "last_name DESC",
            UserListFilterName.ROLE_ASCENDING, "roles_id DESC, last_name ASC",
            UserListFilterName.ROLE_DESCENDING, "roles_id ASC, last_name ASC"
    );

    public MYSQLUserDAO() {}

    @Override
    public User registration(User user, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //TODO logger
                return null;
            }

            preparedStatement = connection.prepareStatement(GET_MAX_ID_USER);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(MAX_ID_USER) + 1);
            }

            preparedStatement = connection.prepareStatement(REGISTRATION_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, Role.READER.getId());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getFirstName());
            preparedStatement.setString(8, user.getFatherName());
            preparedStatement.setDate(9, user.getBirthday());
            preparedStatement.setString(10, user.getGender().toString());
            preparedStatement.setString(11, user.getPassport());
            preparedStatement.setString(12, user.getAddress());
            preparedStatement.setString(13, user.getPhone());
            preparedStatement.setString(14, DEFAULT_IMAGE_USER);
            preparedStatement.executeUpdate();

            user.setRole(Role.READER);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return user;
    }

    @Override
    public User authentication(String email, String password) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(AUTHORIZATION_USER);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && BCrypt.checkpw(password, resultSet.getString(PASSWORD))) {
                user = new User();
                user.setId(resultSet.getInt(ID));
                user.setRole(Role.getRole(resultSet.getInt(ROLES_ID)));
                user.setNickname(resultSet.getString(NICKNAME));
                user.setEmail(email);
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setFatherName(resultSet.getString(FATHER_NAME));
                user.setBirthday(resultSet.getDate(BIRTHDAY));
                user.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                user.setPassport(resultSet.getString(PASSPORT));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setPhone(resultSet.getString(PHONE));
                user.setImageURL(resultSet.getString(IMAGE));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !user.getEmail().equals(resultSet.getString(EMAIL))) {
                //TODO logger
                return false;
            }

            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setInt(1, user.getRole().getId());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getFatherName());
            preparedStatement.setDate(7, user.getBirthday());
            preparedStatement.setString(8, user.getGender().toString());
            preparedStatement.setString(9, user.getPassport());
            preparedStatement.setString(10, user.getAddress());
            preparedStatement.setString(11, user.getPhone());
            preparedStatement.setString(12, user.getImageURL());
            preparedStatement.setInt(13, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }

    @Override
    public boolean updatePassword(int userID, String newPassword, String oldPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && BCrypt.checkpw(oldPassword, resultSet.getString(PASSWORD))) {
                preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
                String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                preparedStatement.setString(1, hashed);
                preparedStatement.setInt(2, userID);
                preparedStatement.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }

    @Override
    public User getUser(int userID) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(ID));
                user.setRole(Role.getRole(resultSet.getInt(ROLES_ID)));
                user.setNickname(resultSet.getString(NICKNAME));
                user.setEmail(resultSet.getString(EMAIL));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setFatherName(resultSet.getString(FATHER_NAME));
                user.setBirthday(resultSet.getDate(BIRTHDAY));
                user.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                user.setPassport(resultSet.getString(PASSPORT));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setPhone(resultSet.getString(PHONE));
                user.setImageURL(resultSet.getString(IMAGE));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return user;
    }

    @Override
    public List<User> getUsersByFilter(Map<String, Object> filters) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<User> users = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            StringBuilder query = new StringBuilder(USER_FILTER_QUERY);
            if (filters.size() > 1) {
                query.append(WHERE);
                for (String filterName : filters.keySet()) {
                    if (!filterName.equals(UserListFilterName.SORT)) {
                        buildQueryByFilter(query, filterName);
                    }
                }
                query.setLength(query.length() - 4);
            }
            query.append(ORDER_BY);
            query.append(sortValue.get(filters.get(UserListFilterName.SORT)));

            preparedStatement = connection.prepareStatement(query.toString());
            List<String> filterNames = new ArrayList<>(filters.keySet());
            for (int i = 0; i < filterNames.size() - 1; i++) {
                preparedStatement.setString(i + 1, PERCENT + filters.get(filterNames.get(i)) + PERCENT);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(ID));
                user.setRole(Role.getRole(resultSet.getInt(ROLES_ID)));
                user.setNickname(resultSet.getString(NICKNAME));
                user.setEmail(resultSet.getString(EMAIL));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setFatherName(resultSet.getString(FATHER_NAME));
                user.setBirthday(resultSet.getDate(BIRTHDAY));
                user.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                user.setPassport(resultSet.getString(PASSPORT));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setPhone(resultSet.getString(PHONE));
                user.setImageURL(resultSet.getString(IMAGE));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return users;
    }

    private void buildQueryByFilter(StringBuilder query, String filterName) {
        switch (filterName) {
            case UserListFilterName.LAST_NAME:
                query.append(LAST_NAME_FILTER);
                break;
            case UserListFilterName.EMAIL:
                query.append(EMAIL_FILTER);
                break;
            case UserListFilterName.NICKNAME:
                query.append(NICKNAME_FILTER);
                break;
        }
        query.append(AND);
    }

    @Override
    public SessionUser getSessionUser(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SessionUser sessionUser = null;
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sessionUser = new SessionUser();
                sessionUser.setId(resultSet.getInt(ID));
                sessionUser.setRole(Role.getRole(resultSet.getInt(ROLES_ID)));
                sessionUser.setNickname(resultSet.getString(NICKNAME));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return sessionUser;
    }
}
