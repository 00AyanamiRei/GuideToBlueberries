package com.ayanami.dao.tests;

import com.ayanami.dao.SizeBlueberryDAO;
import com.ayanami.dao.impl.SizeBlueberryDAOImpl;
import com.ayanami.model.SizeBlueberry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SizeBlueberryDAOTest {
    private SizeBlueberryDAO sizeBlueberryDAO;

    @Before
    public void setUp() {
        // Ініціалізація SizeBlueberryDAO перед кожним тестом
        sizeBlueberryDAO = new SizeBlueberryDAOImpl();
    }

    @Test
    public void testSaveSizeBlueberry() {
        // Arrange
        SizeBlueberry sizeBlueberry = SizeBlueberry.builder()
                .id(1)
                .size("Small")
                .build();

        // Act
        sizeBlueberryDAO.save(sizeBlueberry);

        // Assert
        List<SizeBlueberry> allSizeBlueberries = sizeBlueberryDAO.findAll();
        Assert.assertEquals(1, allSizeBlueberries.size());
        Assert.assertEquals(sizeBlueberry, allSizeBlueberries.get(0));
    }

    @Test
    public void testUpdateSizeBlueberry() {
        // Arrange
        SizeBlueberry sizeBlueberry = SizeBlueberry.builder()
                .id(1)
                .size("Small")
                .build();
        sizeBlueberryDAO.save(sizeBlueberry);

        // Act
        sizeBlueberry.setSize("Extra Large");
        sizeBlueberryDAO.update(sizeBlueberry);

        // Assert
        SizeBlueberry retrievedSizeBlueberry = sizeBlueberryDAO.findById(1);
        Assert.assertEquals("Extra Large", retrievedSizeBlueberry.getSize());
    }

    @Test
    public void testDeleteSizeBlueberry() {
        // Arrange
        SizeBlueberry sizeBlueberry = SizeBlueberry.builder()
                .id(1)
                .size("Small")
                .build();
        sizeBlueberryDAO.save(sizeBlueberry);

        // Act
        sizeBlueberryDAO.delete(sizeBlueberry);

        // Assert
        SizeBlueberry retrievedSizeBlueberry = sizeBlueberryDAO.findById(1);
        Assert.assertNull(retrievedSizeBlueberry);
    }
}
