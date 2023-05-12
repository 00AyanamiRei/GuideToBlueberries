package com.ayanami.dao.tests;

import com.ayanami.dao.RipeningPeriodDAO;
import com.ayanami.dao.impl.RipeningPeriodDAOImpl;
import com.ayanami.model.RipeningPeriod;
import org.junit.jupiter.api.*;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RipeningPeriodDAOTest {
    private RipeningPeriodDAO ripeningPeriodDAO;

    @BeforeAll
    public void setup() {
        // Ініціалізувати DAO перед початком всіх тестів
        ripeningPeriodDAO = new RipeningPeriodDAOImpl();
    }

    @Test
    public void testSave() {
        RipeningPeriod ripeningPeriod = RipeningPeriod.builder()
                .id(1)
                .deadline(new Date())
                .build();

        ripeningPeriodDAO.save(ripeningPeriod);

        List<RipeningPeriod> ripeningPeriods = ripeningPeriodDAO.findAll();
        Assertions.assertEquals(1, ripeningPeriods.size());
    }

    @Test
    public void testUpdate() {
        RipeningPeriod ripeningPeriod = RipeningPeriod.builder()
                .id(1)
                .deadline(new Date())
                .build();

        ripeningPeriodDAO.save(ripeningPeriod);

        ripeningPeriod.setDeadline(new Date());
        ripeningPeriodDAO.update(ripeningPeriod);

        RipeningPeriod updatedRipeningPeriod = ripeningPeriodDAO.findById(1);
        Assertions.assertEquals(ripeningPeriod.getDeadline(), updatedRipeningPeriod.getDeadline());
    }

    @Test
    public void testDelete() {
        RipeningPeriod ripeningPeriod = RipeningPeriod.builder()
                .id(1)
                .deadline(new Date())
                .build();

        ripeningPeriodDAO.save(ripeningPeriod);

        ripeningPeriodDAO.delete(ripeningPeriod);

        List<RipeningPeriod> ripeningPeriods = ripeningPeriodDAO.findAll();
        Assertions.assertTrue(ripeningPeriods.isEmpty());
    }
}
