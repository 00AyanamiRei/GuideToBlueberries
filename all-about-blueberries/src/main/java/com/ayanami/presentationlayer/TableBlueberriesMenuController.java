package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.model.Blueberry;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TableBlueberriesMenuController {
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Blueberry> tableView;
    @FXML
    private TableColumn<Blueberry, String> nameColumn;
    @FXML
    private TableColumn<Blueberry, String> sizeColumn;
    @FXML
    private TableColumn<Blueberry, String> periodColumn;
    @FXML
    private TableColumn<Blueberry, String> tasteColumn;
    @FXML
    private TableColumn<Blueberry, String> climateColumn;
    @FXML
    private TableColumn<Blueberry, String> landingDistanceColumn;
    @FXML
    private TableColumn<Blueberry, String> pollinationColumn;
    @FXML
    private TableColumn<Blueberry, String> descriptionColumn;
    @FXML
    private TableColumn<Blueberry, String> photoColumn;

    private final Map<String, Image> imageCache = new ConcurrentHashMap<>();

    // Пул потоків для асинхронного завантаження зображень
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private ObservableList<Blueberry> blueberries;

    public void initialize() {
        blueberries = FXCollections.observableArrayList();

        // Завантаження даних з бази даних
        loadBlueberriesFromDatabase();

        // Налаштування відображення даних в TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("sizeBlueberryID"));
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("periodID"));
        tasteColumn.setCellValueFactory(new PropertyValueFactory<>("tasteID"));
        climateColumn.setCellValueFactory(new PropertyValueFactory<>("climateID"));
        landingDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("landingDistance"));
        pollinationColumn.setCellValueFactory(new PropertyValueFactory<>("pollination"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));


        // Встановлення даних в TableView
        tableView.setItems(blueberries);

        searchTextField.setOnKeyReleased(event -> handleSearch());
        setupPhotoColumnCellFactory();
    }

    private void loadBlueberriesFromDatabase() {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT * FROM blueberry";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int sizeBlueberryID = resultSet.getInt("size_blueberry_id");
                int periodID = resultSet.getInt("period_id");
                int tasteID = resultSet.getInt("taste_id");
                int climateID = resultSet.getInt("climate_id");
                String landingDistance = resultSet.getString("landing_distance");
                String pollination = resultSet.getString("pollination");
                String description = resultSet.getString("description");
                String photo = resultSet.getString("photo");

                Blueberry blueberry = Blueberry.builder()
                        .id(id)
                        .name(name)
                        .sizeBlueberryID(sizeBlueberryID)
                        .periodID(periodID)
                        .tasteID(tasteID)
                        .climateID(climateID)
                        .landingDistance(landingDistance)
                        .pollination(pollination)
                        .description(description)
                        .photo(photo)
                        .build();

                blueberries.add(blueberry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchTextField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) {
            // Якщо пошуковий терм пустий, показати всі записи
            tableView.setItems(blueberries);
        } else {
            // Якщо пошуковий терм не пустий, фільтрувати дані за вказаним критерієм
            Task<ObservableList<Blueberry>> searchTask = new Task<ObservableList<Blueberry>>() {
                @Override
                protected ObservableList<Blueberry> call() throws Exception {
                    ObservableList<Blueberry> filteredBlueberries = FXCollections.observableArrayList();

                    try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
                        String sql = "SELECT * FROM blueberry";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);

                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            int sizeBlueberryID = resultSet.getInt("size_blueberry_id");
                            int periodID = resultSet.getInt("period_id");
                            int tasteID = resultSet.getInt("taste_id");
                            int climateID = resultSet.getInt("climate_id");
                            String landingDistance = resultSet.getString("landing_distance");
                            String pollination = resultSet.getString("pollination");
                            String description = resultSet.getString("description");
                            String photo = resultSet.getString("photo");

                            Blueberry blueberry = Blueberry.builder()
                                    .id(id)
                                    .name(name)
                                    .sizeBlueberryID(sizeBlueberryID)
                                    .periodID(periodID)
                                    .tasteID(tasteID)
                                    .climateID(climateID)
                                    .landingDistance(landingDistance)
                                    .pollination(pollination)
                                    .description(description)
                                    .photo(photo)
                                    .build();

                            // Фільтрація записів за назвою і наявністю потрібного символу
                            if (name.contains(searchTerm)) {
                                filteredBlueberries.add(blueberry);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return filteredBlueberries;
                }
            };

            searchTask.setOnSucceeded(event -> {
                ObservableList<Blueberry> filteredBlueberries = searchTask.getValue();
                tableView.setItems(filteredBlueberries);
            });

            Thread searchThread = new Thread(searchTask);
            searchThread.setDaemon(true);
            searchThread.start();
        }
    }

    private void setupPhotoColumnCellFactory() {
        photoColumn.setCellFactory(column -> {
            TableCell<Blueberry, String> cell = new TableCell<Blueberry, String>() {
                private final ImageView imageView = new ImageView();
                private final String defaultImagePath = "https://media.istockphoto.com/id/1349283598/photo/blueberries-in-basket-on-wooden-table.jpg?s=2048x2048&w=is&k=20&c=0H30gcAd59eB0h_vwXeEvH1BOwPqqMp5hpZGqKoXZHM=";

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);

                    if (empty || imagePath == null) {
                        // Якщо шлях до зображення порожній або нульовий, встановлюємо шлях до за замовчуванням
                        imagePath = defaultImagePath;
                    }

                    // Очищення графічного вмісту комірки
                    setGraphic(null);

                    if (imagePath != null) {
                        // Перевірка наявності зображення у кеші
                        Image cachedImage = imageCache.get(imagePath);

                        if (cachedImage != null) {
                            // Використання завантаженого зображення з кешу
                            imageView.setImage(cachedImage);
                            setGraphic(imageView);
                        } else {
                            // Асинхронне завантаження зображення
                            loadAndSetImage(imagePath);
                        }
                    }
                }

                private void loadAndSetImage(String imagePath) {
                    Task<Image> imageLoadingTask = new Task<Image>() {
                        @Override
                        protected Image call() throws Exception {
                            return new Image(imagePath, 150, 300, true, true);
                        }
                    };

                    // Оновлення графічного вмісту комірки після завершення завантаження зображення
                    imageLoadingTask.setOnSucceeded(event -> {
                        Image loadedImage = imageLoadingTask.getValue();

                        if (loadedImage != null) {
                            // Додавання завантаженого зображення до кешу
                            imageCache.put(imagePath, loadedImage);
                            imageView.setImage(loadedImage);
                            setGraphic(imageView);
                        }
                    });

                    // Обробка помилок при завантаженні зображення
                    imageLoadingTask.setOnFailed(event -> {
                        // Обробка помилки (за бажанням)
                    });

                    // Запуск завдання завантаження зображення у пулі потоків
                    executorService.submit(imageLoadingTask);
                }
            };

            return cell;
        });
    }

    // Код для закриття пулу потоків при завершенні програми
    private void shutdownExecutorService() {
        executorService.shutdown();
    }
}