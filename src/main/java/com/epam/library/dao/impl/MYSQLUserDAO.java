package com.epam.library.dao.impl;

import com.epam.library.constant.UserListFilterName;
import com.epam.library.dao.UserDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.*;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MYSQLUserDAO implements UserDAO {
    private static final Logger logger = Logger.getLogger(MYSQLUserDAO.class.getName());

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
    private static final String LOCK = "lock";
    private static final String DEFAULT_IMAGE_USER = "images/users/default_image_user.jpg";

    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String SELECT_USER = "SELECT * FROM users WHERE id=?";
    private static final String GET_MAX_ID_USER = "SELECT MAX(id) FROM users";
    private static final String REGISTRATION_USER = "INSERT INTO users (id, roles_id, nickname, password, email, last_name, first_name, father_name, birthday, gender, passport, address, phone, image, `lock`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
    private static final String AUTHORIZATION_USER = "SELECT * FROM users WHERE email=?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    private static final String UPDATE_USER_WITH_PASSWORD = "UPDATE users SET roles_id=?, nickname=?, email=?, password=?, last_name=?, first_name=?, father_name=?, birthday=?, gender=?, passport=?, address=?, phone=?, image=? WHERE id=?";
    private static final String UPDATE_LOCK = "UPDATE users SET `lock`= CASE WHEN IFNULL(`lock`,0)=0 THEN 1 ELSE 0 END WHERE id=?";
    private static final String UPDATE_USER = "UPDATE users SET roles_id=?, nickname=?, email=?, last_name=?, first_name=?, father_name=?, birthday=?, gender=?, passport=?, address=?, phone=?, image=? WHERE id=?";

    private static final String USER_FILTER_QUERY = "SELECT * FROM users";
    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String LAST_NAME_FILTER = "last_name LIKE ?";
    private static final String EMAIL_FILTER = "email LIKE ?";
    private static final String NICKNAME_FILTER = "nickname LIKE ?";
    private static final String PERCENT = "%";
    private static final String WHERE = " WHERE ";

    private static final Map<String, String> filterValues = Map.of(
            UserListFilterName.LAST_NAME, LAST_NAME_FILTER,
            UserListFilterName.EMAIL, EMAIL_FILTER,
            UserListFilterName.NICKNAME, NICKNAME_FILTER
    );

    private static final Map<String, String> sortValue = Map.of(
            UserListFilterName.LAST_NAME_ASCENDING, "last_name ASC",
            UserListFilterName.LAST_NAME_DESCENDING, "last_name DESC",
            UserListFilterName.ROLE_ASCENDING, "roles_id DESC, last_name ASC",
            UserListFilterName.ROLE_DESCENDING, "roles_id ASC, last_name ASC"
    );

    private String query;
    private static final String PAGES_COUNT = "pages_count";
    private static final String GET_PAGES_COUNT = "SELECT COUNT(*) AS pages_count FROM ( %s ) query";
    private static final String COLON = ": ";
    private static final String PAGE_LIMIT = " LIMIT %s, 10";

    public MYSQLUserDAO() {}

    @Override
    public int registration(UserInfo user, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_USER);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(MAX_ID_USER) + 1);
            }

            preparedStatement = connection.prepareStatement(REGISTRATION_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, Role.READER.getId());
            preparedStatement.setString(3, user.getNickname());
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            preparedStatement.setString(4, hashed);
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getFirstName());
            preparedStatement.setString(8, user.getFatherName());
            preparedStatement.setDate(9, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(10, user.getGender());
            preparedStatement.setString(11, user.getPassport());
            preparedStatement.setString(12, user.getAddress());
            preparedStatement.setString(13, user.getPhone());
            preparedStatement.setString(14, DEFAULT_IMAGE_USER);
            preparedStatement.executeUpdate();
            return user.getId();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public Integer authentication(String email, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer userID = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(AUTHORIZATION_USER);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && BCrypt.checkpw(password, resultSet.getString(PASSWORD))) {
                userID = resultSet.getInt(ID);
            }
            return userID;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public boolean checkEmail(int userID, String email) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (userID == 0 || userID != resultSet.getInt(ID)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public boolean checkPassword(int userID, String currentPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !BCrypt.checkpw(currentPassword, resultSet.getString(PASSWORD))) {
                return false;
            }
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public boolean checkUserLock(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(LOCK);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public void lockUser(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_LOCK);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public void updateUser(UserInfo user, String currentPassword, String newPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_USER_WITH_PASSWORD);
            preparedStatement.setInt(1, user.getRole().getId());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getEmail());
            String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            preparedStatement.setString(4, hashed);
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getFirstName());
            preparedStatement.setString(7, user.getFatherName());
            preparedStatement.setDate(8, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(9, user.getGender());
            preparedStatement.setString(10, user.getPassport());
            preparedStatement.setString(11, user.getAddress());
            preparedStatement.setString(12, user.getPhone());
            preparedStatement.setString(13, user.getImageURL());
            preparedStatement.setInt(14, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public void updateUser(UserInfo user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setInt(1, user.getRole().getId());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getFatherName());
            preparedStatement.setDate(7, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(8, user.getGender().toString());
            preparedStatement.setString(9, user.getPassport());
            preparedStatement.setString(10, user.getAddress());
            preparedStatement.setString(11, user.getPhone());
            preparedStatement.setString(12, user.getImageURL());
            preparedStatement.setInt(13, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public boolean updatePassword(int userID, String currentPassword, String newPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
                String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                preparedStatement.setString(1, hashed);
                preparedStatement.setInt(2, userID);
                preparedStatement.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public User getUser(int userID) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
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
                user.setLock(resultSet.getBoolean(LOCK));
            }
            return user;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<User> getUsersByFilter(Map<String, Object> filters, int page) throws DAOException {
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
                        query.append(filterValues.get(filterName));
                        query.append(AND);
                    }
                }
                query.setLength(query.length() - 4);
            }
            query.append(ORDER_BY);
            query.append(sortValue.get(filters.get(UserListFilterName.SORT)));

            page = 10*page-10;
            String limit = String.format(PAGE_LIMIT, page);
            query.append(limit);

            preparedStatement = connection.prepareStatement(query.toString());
            List<String> filterNames = new ArrayList<>(filters.keySet());
            for (int i = 0; i < filterNames.size() - 1; i++) {
                preparedStatement.setString(i + 1, PERCENT + filters.get(filterNames.get(i)) + PERCENT);
            }

            this.query = preparedStatement.toString();
            this.query = this.query.substring(this.query.indexOf(COLON) + 2);
            this.query = this.query.substring(0, this.query.length() - limit.length());

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
                user.setLock(resultSet.getBoolean(LOCK));
                users.add(user);
            }
            return users;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public int getPagesCount() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(String.format(GET_PAGES_COUNT, query));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int pagesCount = resultSet.getInt(PAGES_COUNT);

            return pagesCount % 10 == 0 && pagesCount != 0 ? pagesCount/10 : pagesCount/10+1;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public SessionUser getSessionUser(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SessionUser sessionUser = new SessionUser();

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_USER);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sessionUser.setId(resultSet.getInt(ID));
                sessionUser.setRole(Role.getRole(resultSet.getInt(ROLES_ID)));
                sessionUser.setNickname(resultSet.getString(NICKNAME));
            }
            return sessionUser;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }
}
