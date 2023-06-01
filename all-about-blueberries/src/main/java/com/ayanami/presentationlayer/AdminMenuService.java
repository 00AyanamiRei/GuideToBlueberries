package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.model.Blueberry;
import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminMenuService {
    @FXML
    private Button deleteButton;
    @FXML
    private TextField idTextField;
    @FXML
    private ChoiceBox<String> ChooseBox;
    @FXML
    private FlowPane textFieldContainer;
    @FXML
    private Button sendButton;
    @FXML
    private TextField searchTextField;

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    @FXML
    private void initialize() {
        // Створити мапу, яка містить відповідні DAO-класи для кожної таблиці
//        Map<String, Object> daoMap = new HashMap<>();
//        daoMap.put("users", new UserDAOImpl());
//        daoMap.put("suitable_climate", new ClimateDAOImpl());
//        daoMap.put("size_blueberry", new SizeBlueberryDAOImpl());
//        daoMap.put("ripening_period", new RipeningPeriodDAOImpl());
//        daoMap.put("blueberry_taste", new BlueberryTasteDAOImpl());
//        daoMap.put("blueberry_review", new BlueberryReviewDAOImpl());
//        daoMap.put("blueberry", new BlueberryDAOImpl());


        // Зчитуємо таблиці з бази даних та заповнюємо ChoiceBox
        readTablesFromDatabase();
        sendButton.setOnAction(this::sendButtonClicked);
        deleteButton.setOnAction(this::deleteButtonClicked);
        // Додаємо обробник події до ChoiceBox
        ChooseBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Викликається при зміні вибраного елемента
                loadTableData(newValue);
                clearTextFields();
                try (Connection connection = ConnectionPool.getDataSource().getConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + newValue);
                    ResultSetMetaData resMetaData = resultSet.getMetaData();
                    int columnCount = resMetaData.getColumnCount();
                    createDynamicTextFields(columnCount, resMetaData);
                } catch (SQLException e) {
                    e.printStackTrace();
                    MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося отримати метадані про цю таблицю");
                }
            }
        });
    }
    /**
     * Reads tables from the database and populates the ChoiceBox with table names.
     */

    private void readTablesFromDatabase() {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement prepStatement = connection.prepareStatement("SHOW TABLES")) {

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                ChooseBox.getItems().add(tableName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося отримати таблиці з бази даних");
        }
    }
    /**
     * Displays an error message in an Alert dialog.
     *
     * @param title   the title of the error dialog
     * @param message the error message to display
     */
    private void showErrorMessage(String title, String message) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle(title);
        alertError.setContentText(message);
        alertError.showAndWait();
    }
    @FXML
    private List<TextField> textFields = new ArrayList<>();
    @FXML
    private TableView<ObservableList<Object>> tableView; // Змінено тип TableView
    /**
     * Loads data from the specified table into a JavaFX TableView.
     *
     * @param tableName The name of the table to load data from.
     */
    private void loadTableData(String tableName) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            // Виконуємо запит до бази даних
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            // Отримуємо метадані про стовпці
            ResultSetMetaData resMetaData = resultSet.getMetaData();
            int columnCount = resMetaData.getColumnCount();

            // Очищаємо TableView
            tableView.getColumns().clear();

            // Створюємо стовпці для TableView з назвами таблиць
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(resMetaData.getColumnName(columnIndex));
                final int columnIndexFinal = columnIndex;
                column.setCellValueFactory(cellData -> {
                    ObservableList<Object> row = cellData.getValue();
                    if (row != null && row.size() >= columnIndexFinal) {
                        return new SimpleObjectProperty<>(row.get(columnIndexFinal - 1));
                    }
                    return null;
                });
                tableView.getColumns().add(column);
            }

            // Створюємо список для збереження даних
            ObservableList<ObservableList<Object>> data = FXCollections.observableArrayList();

            // Додаємо дані до списка
            while (resultSet.next()) {
                ObservableList<Object> row = FXCollections.observableArrayList();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    row.add(resultSet.getString(columnIndex));
                }
                data.add(row);
            }

            // Встановлюємо дані у TableView
            tableView.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося отримати таблиці з бази даних");
        }
    }
    /**
     * Handles the button click event for the sendButton.
     * Retrieves data from text fields, adds it to the database, and updates the TableView.
     *
     * @param event The action event triggered by the sendButton.
     */
    @FXML
    public void sendButtonClicked(ActionEvent event) {
        String selectedTable = ChooseBox.getValue();

        if (selectedTable != null) {
            // Отримати дані з текстових полів
            List<String> inputValues = new ArrayList<>();
            for (TextField textField : textFields) {
                inputValues.add(textField.getText());
            }
            // Відправити дані на сервер
            addDataToDb(selectedTable, inputValues);

            // Оновити TableView
            loadTableData(selectedTable);
        } else {
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Оберіть таблицю");
        }
    }
    /**
     * Adds the specified data values to the specified database table.
     *
     * @param tableName    The name of the table to add data to.
     * @param inputValues  The list of input values to be added.
     */
    private void addDataToDb(String tableName, List<String> inputValues) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            // Формуємо запит для вставки даних
            List<String> promptTexts = textFields.stream().map(TextInputControl::getPromptText).collect(
                    Collectors.toList());

            // Перевіряємо наявність порожніх полів
            boolean hasEmptyField = inputValues.stream().anyMatch(String::isEmpty);
            if (hasEmptyField) {
                // Якщо є порожні поля, можна показати повідомлення або виконати додаткові дії
                MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Заповніть усі поля перед збереженням даних");
                return;
            }

            String query = "INSERT INTO " + tableName + "(" + String.join(", ", promptTexts) + ")"
                    + " VALUES ('" + String.join("', '", inputValues) + "')";
            statement.executeUpdate(query);
            LOGGER.info("Data insert: {}", query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося додати дані в базу даних");
            LOGGER.error("Error data insert: {}", e.getMessage());
        }
    }
    /**
     * Handles the button click event for the deleteButton.
     * Deletes a record from the specified table based on the given id.
     *
     * @param event The action event triggered by the deleteButton.
     */
    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        String selectedTable = ChooseBox.getValue();
        String id = idTextField.getText();

        if (selectedTable != null && !id.isEmpty()) {
            deleteDataFromTable(selectedTable, id);
        } else {
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Оберіть таблицю та введіть id");
        }
    }
    /**
     * Deletes a record from the specified table based on the given id.
     *
     * @param tableName The name of the table to delete data from.
     * @param id        The id of the record to be deleted.
     */
    private void deleteDataFromTable(String tableName, String id) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            String deleteQuery = "DELETE FROM " + tableName + " WHERE id = " + id;

            int rowsAffected = statement.executeUpdate(deleteQuery);

            if (rowsAffected > 0) {
                // Видалення пройшло успішно, оновлюємо дані в TableView
                loadTableData(tableName);
                LOGGER.info("Data delete: {}", deleteQuery.toString());
            } else {
                MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Запис за вказаним id не вказано");
                LOGGER.error("No found data: {}", deleteQuery.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося видалити дані з тиблиці");
            LOGGER.error("Error Data delete: {}", e.getMessage());
        }
    }
    /**
     * Clears the text fields and sets their prompt text to their current text values.
     */
    private void clearTextFields() {
        for (TextField textField : textFields) {
            textField.clear();
            textField.setPromptText(textField.getText());
        }
    }
    /**
     * Creates dynamic text fields based on the number of columns in the table and sets their prompt text.
     *
     * @param columnCount The number of columns in the table.
     * @param metaData    The ResultSetMetaData containing metadata about the columns.
     * @throws SQLException If an SQL exception occurs.
     */
    private void createDynamicTextFields(int columnCount, ResultSetMetaData metaData) throws SQLException {
        // Очищаємо список з попередніми текстовими полями
        textFields.clear();

        // Очищаємо дочірні елементи контейнера для текстових полів, наприклад, VBox або GridPane
        // Наприклад, якщо ви використовуєте VBox з id "textFieldContainer":
        textFieldContainer.getChildren().clear();
        textFieldContainer.setHgap(10);
        textFieldContainer.setVgap(10);


        // Створюємо та додаємо нові текстові поля до контейнера
        for (int i = 0; i < columnCount; i++) {
            TextField textField = new TextField();
            if(!metaData.getColumnName(i + 1).equals("id")) {
                textField.setPromptText(metaData.getColumnName(i + 1)); // Встановлюємо назву поля
                textField.setId("textField" + i);
                textFields.add(textField);
                textFieldContainer.getChildren().add(textField);
            }
        }
    }
    /**
     * Handles the button click event for the updateButton.
     * Retrieves data from text fields, updates the corresponding record in the database, and updates the TableView.
     */
    @FXML
    public void updateButtonClicked() {
        String selectedTable = ChooseBox.getValue();
        String id = idTextField.getText();

        if (selectedTable != null && !id.isEmpty()) {
            // Отримати дані з текстових полів
            List<String> inputValues = new ArrayList<>();
            for (TextField textField : textFields) {
                inputValues.add(textField.getText());
            }

            updateDataOnDb(selectedTable, id, inputValues);

            loadTableData(selectedTable);
        } else {
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Оберіть таблицю та введіть номер id");
        }
    }
    /**
     * Updates the specified record in the specified table with the given input values.
     *
     * @param tableName   The name of the table to update.
     * @param id          The id of the record to be updated.
     * @param inputValues The list of input values to update the record with.
     */
    private void updateDataOnDb(String tableName, String id, List<String> inputValues) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            // Перевірка наявності порожніх полів
            boolean hasEmptyField = inputValues.stream().anyMatch(String::isEmpty);
            if (hasEmptyField) {
                MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Заповніть усі поля перед оновленням");
                return;
            }

            // Формуємо запит для оновлення даних
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE ").append(tableName).append(" SET ");

            // Згенерувати частину запиту для оновлення значень
            for (int i = 0; i < inputValues.size(); i++) {
                String columnValue = inputValues.get(i);
                String columnName = textFields.get(i).getPromptText();
                queryBuilder.append(columnName).append(" = '").append(columnValue).append("', ");
            }
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Видаляємо останню кому

            queryBuilder.append(" WHERE id = ").append(id); // Додаємо умову WHERE для конкретного запису

            statement.executeUpdate(queryBuilder.toString());
            LOGGER.info("Data update: {}", queryBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            MessageBoxUtils.showWarning(null, "Помилка!", "Діалогове вікно попередження","Не вдалося оновити дані в базі даних");
            LOGGER.error("Error update data: {}", e.getMessage());
        }
    }
}