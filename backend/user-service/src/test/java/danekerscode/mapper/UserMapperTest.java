package danekerscode.mapper;

import danekerscode.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserMapperTest {

    UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void shouldBeValidMappedUser() {
        // given
        var dto = new UserDTO(
                "Mark",
                "Smith",
                "mark@gmail.com",
                "password"
        );

        var expectedMapped = userMapper.toModel(dto);

        assertEquals(dto.firstName() , expectedMapped.getFirstName());
        assertEquals(dto.lastName() , expectedMapped.getLastName());
        assertEquals(dto.email() , expectedMapped.getEmail());
        assertEquals(dto.password() , expectedMapped.getPassword());

        assertNull(expectedMapped.getId());
    }

    @Test
    void mappedUserShouldBeNull() {
        UserDTO dto = null;

        var expectedMapped = userMapper.toModel(dto);

        assertNull(expectedMapped);
    }



}