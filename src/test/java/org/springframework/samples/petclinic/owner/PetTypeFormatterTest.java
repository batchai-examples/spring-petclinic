package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PetTypeFormatterTest {

    @Mock
    private OwnerRepository owners;

    @InjectMocks
    private PetTypeFormatter petTypeFormatter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrint_HappyPath() throws Exception {
        // Arrange
        PetType petType = new PetType();
        petType.setName("Dog");
        when(owners.findPetTypes()).thenReturn(Arrays.asList(petType));

        // Act
        String result = petTypeFormatter.print(petType, Locale.US);

        // Assert
        assertEquals("Dog", result);
    }

    @Test
    public void testPrint_NegativePath() throws Exception {
        // Arrange
        PetType petType = new PetType();
        petType.setName("Cat");
        when(owners.findPetTypes()).thenReturn(Arrays.asList(petType));

        // Act & Assert
        assertThrows(ParseException.class, () -> petTypeFormatter.print(new PetType(), Locale.US));
    }

    @Test
    public void testParse_HappyPath() throws Exception {
        // Arrange
        Collection<PetType> petTypes = Arrays.asList(new PetType("Dog"), new PetType("Cat"));
        when(owners.findPetTypes()).thenReturn(petTypes);

        // Act
        PetType result = petTypeFormatter.parse("Dog", Locale.US);

        // Assert
        assertEquals("Dog", result.getName());
    }

    @Test
    public void testParse_NegativePath() throws Exception {
        // Arrange
        Collection<PetType> petTypes = Arrays.asList(new PetType("Dog"), new PetType("Cat"));
        when(owners.findPetTypes()).thenReturn(petTypes);

        // Act & Assert
        assertThrows(ParseException.class, () -> petTypeFormatter.parse("Fish", Locale.US));
    }
}
