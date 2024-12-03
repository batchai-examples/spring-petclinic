package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetTypeTest {

    private PetType petType;

    @BeforeEach
    public void setUp() {
        petType = new PetType();
    }

    /**
     * Test to verify that the default constructor initializes a PetType object.
     */
    @Test
    public void testDefaultConstructor() {
        assertNotNull(petType);
    }

    /**
     * Test to verify that the setName method sets the name of the PetType.
     */
    @Test
    public void testSetName() {
        petType.setName("Cat");
        assertEquals("Cat", petType.getName());
    }

    /**
     * Test to verify that the getName method returns the correct name of the PetType.
     */
    @Test
    public void testGetName() {
        petType.setName("Dog");
        assertEquals("Dog", petType.getName());
    }

    /**
     * Test to verify that the setName method throws IllegalArgumentException when null is passed as argument.
     */
    @Test
    public void testSetNameWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            petType.setName(null);
        });
    }

    /**
     * Test to verify that the setName method throws IllegalArgumentException when an empty string is passed as argument.
     */
    @Test
    public void testSetNameWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            petType.setName("");
        });
    }

    /**
     * Test to verify that the toString method returns a non-empty string representation of the PetType.
     */
    @Test
    public void testToString() {
        petType.setName("Hamster");
        assertTrue(petType.toString().length() > 0);
    }
}
