package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VisitTest {

    private Visit visit;

    @BeforeEach
    public void setUp() {
        visit = new Visit();
    }

    /**
     * Test case to verify that the default constructor initializes the date to the current date.
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals(LocalDate.now(), visit.getDate());
    }

    /**
     * Test case to verify that the getDate method returns the correct date.
     */
    @Test
    public void testGetDate() {
        LocalDate expectedDate = LocalDate.of(2023, 10, 1);
        visit.setDate(expectedDate);
        assertEquals(expectedDate, visit.getDate());
    }

    /**
     * Test case to verify that the setDate method sets the date correctly.
     */
    @Test
    public void testSetDate() {
        LocalDate expectedDate = LocalDate.of(2023, 10, 1);
        visit.setDate(expectedDate);
        assertEquals(expectedDate, visit.getDate());
    }

    /**
     * Test case to verify that the getDescription method returns the correct description.
     */
    @Test
    public void testGetDescription() {
        String expectedDescription = "Check-up";
        visit.setDescription(expectedDescription);
        assertEquals(expectedDescription, visit.getDescription());
    }

    /**
     * Test case to verify that the setDescription method sets the description correctly.
     */
    @Test
    public void testSetDescription() {
        String expectedDescription = "Check-up";
        visit.setDescription(expectedDescription);
        assertEquals(expectedDescription, visit.getDescription());
    }

    /**
     * Test case to verify that the getDescription method throws a NullPointerException when called on a null object.
     */
    @Test
    public void testGetDescriptionNullPointerException() {
        Visit nullVisit = null;
        assertThrows(NullPointerException.class, () -> nullVisit.getDescription());
    }

    /**
     * Test case to verify that the setDate method throws an IllegalArgumentException when passed a null date.
     */
    @Test
    public void testSetDateIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> visit.setDate(null));
    }

    /**
     * Test case to verify that the setDate method throws an IllegalArgumentException when passed an invalid date format.
     */
    @Test
    public void testSetDateInvalidDateFormat() {
        LocalDate expectedDate = LocalDate.of(2023, 10, 1);
        visit.setDate(expectedDate);
        assertThrows(IllegalArgumentException.class, () -> visit.setDate(LocalDate.parse("invalid-date")));
    }

    /**
     * Test case to verify that the Visit object is not null after creation.
     */
    @Test
    public void testVisitNotNull() {
        assertNotNull(visit);
    }
}
