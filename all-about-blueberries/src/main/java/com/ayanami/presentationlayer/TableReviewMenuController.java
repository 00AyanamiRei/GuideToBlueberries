package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.model.BlueberryReview;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TableReviewMenuController {
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<BlueberryReview> tableView;
    @FXML
    private TableColumn<BlueberryReview, Integer> idColumn;
    @FXML
    private TableColumn<BlueberryReview, Integer> blueberryIDColumn;
    @FXML
    private TableColumn<BlueberryReview, Integer> userIDColumn;
    @FXML
    private TableColumn<BlueberryReview, String> reviewColumn;
    @FXML
    private TableColumn<BlueberryReview, Integer> ratingColumn;

    private ObservableList<BlueberryReview> blueberryReviews;
    private ExecutorService executorService;

    public void initialize() {
        blueberryReviews = FXCollections.observableArrayList();
        executorService = Executors.newSingleThreadExecutor();

        // Завантаження даних з бази даних
        loadBlueberryReviewsFromDatabase();

        // Налаштування відображення даних в TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        blueberryIDColumn.setCellValueFactory(new PropertyValueFactory<>("blueberryID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("review"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // Встановлення даних в TableView
        tableView.setItems(blueberryReviews);

        // Обробка події введення символу в поле пошуку
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Запуск пошуку в окремому потоці
            executorService.submit(() -> handleSearchReviews());
        });
    }

    private void loadBlueberryReviewsFromDatabase() {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            String query = "SELECT * FROM blueberry_review";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int blueberryID = resultSet.getInt("blueberry_id");
                int userID = resultSet.getInt("user_id");
                String review = resultSet.getString("Review");
                int rating = resultSet.getInt("Rating");

                BlueberryReview blueberryReview = new BlueberryReview(id, blueberryID, userID, review, rating);
                blueberryReviews.add(blueberryReview);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchReviews() {
        String searchTerm = searchTextField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) {
            // Якщо пошуковий терм пустий, показати всі відгуки
            tableView.setItems(blueberryReviews);
        } else {
            // Якщо пошуковий терм не пустий, фільтрувати дані за вказаним критерієм
            Task<ObservableList<BlueberryReview>> searchTask = new Task<ObservableList<BlueberryReview>>() {
                @Override
                protected ObservableList<BlueberryReview> call() throws Exception {
                    ObservableList<BlueberryReview> filteredReviews = FXCollections.observableArrayList();

                    try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
                        String sql = "SELECT * FROM blueberry_review";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);

                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            int blueberryID = resultSet.getInt("blueberry_id");
                            int userID = resultSet.getInt("user_id");
                            String review = resultSet.getString("Review");
                            int rating = resultSet.getInt("Rating");

                            BlueberryReview blueberryReview = BlueberryReview.builder()
                                    .id(id)
                                    .blueberryID(blueberryID)
                                    .userID(userID)
                                    .review(review)
                                    .rating(rating)
                                    .build();

                            // Фільтрація відгуків за вмістом та наявністю потрібного символу
                            if (review.contains(searchTerm)) {
                                filteredReviews.add(blueberryReview);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return filteredReviews;
                }
            };

            searchTask.setOnSucceeded(event -> {
                ObservableList<BlueberryReview> filteredReviews = searchTask.getValue();
                tableView.setItems(filteredReviews);
            });

            Thread searchThread = new Thread(searchTask);
            searchThread.setDaemon(true);
            searchThread.start();
        }
    }
}
