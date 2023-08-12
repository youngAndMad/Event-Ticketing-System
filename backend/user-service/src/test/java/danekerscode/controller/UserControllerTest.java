package danekerscode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.dto.UserDTO;
import danekerscode.model.User;
import danekerscode.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("test valid registration")
    void shouldBeInvalidRegistration() throws Exception {
        var dto = new UserDTO(
                "",
                "",
                "invalid.gmail",
                "pass"
        );

        /*
        path will import from config server
        in test context servlet.context.path=/
        */
        var res = mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        res
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var response = res.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);

        assertEquals("Bad Request", problemDetail.getTitle());
        assertEquals(400, problemDetail.getStatus());
    }


    @Test
    @DisplayName("test invalid registration")
    void shouldBeValidRegistration() throws Exception {
        var dto = new UserDTO(
                "Mike",
                "Tell",
                "mike@gmail.com",
                "tell_pass"
        );

         /*
        path will import from config server
        in test context servlet.context.path=/
        */
        var res = mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        res
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        var userFromResponse = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), User.class);


        assertEquals(dto.email(), userFromResponse.getEmail());
        assertEquals(dto.firstName(), userFromResponse.getFirstName());
        assertEquals(dto.lastName(), userFromResponse.getLastName());

        assertThat(userFromResponse.getId()).isNotNull();

    }

    @Test
    @DisplayName("test valid find by id")
    void shouldBeFoundById() throws Exception {
        var dto = new UserDTO(
                "Mike",
                "Tell",
                "tell@gmail.com",
                "tell_pass"
        );

         /*
        path will import from config server
        in test context servlet.context.path=/
        */
        var registrationResponse = mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        var userFromRegistrationResponse = objectMapper
                .readValue(registrationResponse.andReturn().getResponse().getContentAsString(), User.class);

        assertThat(userFromRegistrationResponse).isInstanceOf(User.class);
        assertThat(userFromRegistrationResponse.getId()).isNotNull();

        var findByIdResponse = mockMvc.perform(
                get("/" + userFromRegistrationResponse.getId())
        );

        findByIdResponse.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        var userFromFindByIdResponse = objectMapper
                .readValue(findByIdResponse.andReturn().getResponse().getContentAsString(), User.class);

        assertThat(userFromFindByIdResponse)
                .isInstanceOf(User.class)
                .isEqualTo(userFromRegistrationResponse);

    }


    @Test
    @DisplayName("test invalid find by id")
    void shouldBeNotFoundById() throws Exception {

        var fakeUserId = 1;

        var findByIdResponse = mockMvc.perform(
                get("/" + fakeUserId)
        );

        findByIdResponse
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var response = findByIdResponse.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);


        assertEquals("Not Found", problemDetail.getTitle());
        assertEquals(404, problemDetail.getStatus());
    }


    @Test
    @DisplayName("test valid user update")
    void shouldBeValidUserUpdate() throws Exception {
        var user = new User();
        user.setFirstName("Terr");
        user.setLastName("Perlasa");
        user.setEmail("terr@gmail.com");
        user.setPassword("password");

        var saved = userRepository.save(user);

        var dto = new UserDTO(
                "new firstname",
                null,
                null,
                "new password"
        );


        var userUpdateResponse = mockMvc.perform(
                put("/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        userUpdateResponse.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        var responseContent = userUpdateResponse.andReturn().getResponse().getContentAsString();
        var updatedUser = objectMapper.readValue(responseContent, User.class);

        assertEquals(dto.firstName(), updatedUser.getFirstName());
        assertEquals(saved.getId(), updatedUser.getId());

        assertNotNull(updatedUser.getLastName());
        assertNotNull(updatedUser.getEmail());

        assertNull(updatedUser.getPassword());
    }

    @Test
    void shouldBeInvalidUserUpdateBecauseUserWillNotFound() throws
            Exception {

        var dto = new UserDTO(
                "new firstname",
                "new lastname",
                "newupdated@gmail.com",
                "new password"
        );

        var fakeUserId = 1L;
        var userUpdateResponse = mockMvc.perform(
                patch("/" + fakeUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        userUpdateResponse.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var problemDetail = objectMapper
                .readValue(userUpdateResponse.andReturn().getResponse().getContentAsString(), ProblemDetail.class);

        assertEquals("Not Found", problemDetail.getTitle());
        assertEquals(404, problemDetail.getStatus());
        assertEquals("user not found", problemDetail.getDetail());
    }


}
