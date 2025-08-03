package com.project.vehicleregistry.controller;

import com.project.vehicleregistry.BaseCompTest;
import com.project.vehicleregistry.controller.response.VehicleBrandResponse;
import com.project.vehicleregistry.controller.response.VehicleDecadeResponse;
import com.project.vehicleregistry.controller.response.VehicleResponse;
import com.project.vehicleregistry.repository.VehicleRepository;
import com.project.vehicleregistry.service.VehicleService;
import factory.VehicleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehicleControllerTest extends BaseCompTest {

    private static final String BASE_URL = "/api/veiculos";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VehicleService service;
    @Autowired
    private VehicleRepository repository;


    @Test
    @DisplayName("Deve registrar um veiculo")
    public void shouldRegistryVehicle() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequest(
                "Car",
                "black",
                "HONDA",
                2014,
                "Economical and comfortable car",
                true);

        var body = objectMapper.writeValueAsString(vehicleHonda);


        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andExpect(status().isCreated());
        var vehicle = repository.findById(1L).get();

        Assertions.assertEquals(vehicleHonda.vehicleType(), vehicle.getVehicleType());
        Assertions.assertEquals(vehicleHonda.brand(), vehicle.getBrand());
        Assertions.assertEquals(vehicleHonda.year(), vehicle.getvYear());
        Assertions.assertEquals(vehicleHonda.description(), vehicle.getDescription());
    }

    @Test
    @DisplayName("Deve validar se a brand foi inserida corretamente")
    public void shouldValidateWrongBrand() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequest(
                "Car",
                "black",
                "HONDAaaaaa",
                2014,
                "Economical and comfortable car",
                true);

        var body = objectMapper.writeValueAsString(vehicleHonda);


        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Deve retornar todos os veiculos com sucesso")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldListAllVehicle() throws Exception {

        var mvcResult = mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));


        Assertions.assertEquals(3, body.size());
        Assertions.assertEquals("Car", body.get(0).vehicleType());
        Assertions.assertEquals("HONDA", body.get(0).brand());
        Assertions.assertEquals(2014, body.get(0).year());
        Assertions.assertEquals("A wonderful car", body.get(0).description());
        // Red
        Assertions.assertEquals("Car", body.get(1).vehicleType());
        Assertions.assertEquals("HONDA", body.get(1).brand());
        Assertions.assertEquals("red", body.get(1).color());
        Assertions.assertEquals(2015, body.get(1).year());
        Assertions.assertEquals("A wonderful car", body.get(1).description());
    }

    @Test
    @DisplayName("Deve retornar um veiculo pelo seu ID")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldFindVehicleById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        var body = objectMapper.readValue(contentAsString, VehicleResponse.class);

        Assertions.assertEquals("Car", body.vehicleType());
        Assertions.assertEquals("HONDA", body.brand());
        Assertions.assertEquals(2014, body.year());
        Assertions.assertEquals("A wonderful car", body.description());
    }

    @Test
    @DisplayName("Deve retorna uma lista de veiculos por marca, ano e cor")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByBrandYearAndColor() throws Exception {
        var mvcResult = mockMvc.perform(get(BASE_URL + "?marca=HONDA&ano=2014&cor=black")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals(1, body.size());
        Assertions.assertEquals("Car", body.get(0).vehicleType());
        Assertions.assertEquals("HONDA", body.get(0).brand());
        Assertions.assertEquals("black", body.get(0).color());
        Assertions.assertEquals(2014, body.get(0).year());
        Assertions.assertEquals("A wonderful car", body.get(0).description());

    }

    @Test
    @DisplayName("Deve retorna uma lista de veiculos por marca")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByBrand() throws Exception {
        var mvcResult = mockMvc.perform(get(BASE_URL + "?marca=HONDA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals(3, body.size());
    }

    @Test
    @DisplayName("Deve retorna uma lista de veiculos por ano")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByYear() throws Exception {
        var mvcResult = mockMvc.perform(get(BASE_URL + "?ano=2014")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals(2014, body.get(0).year());
    }

    @Test
    @DisplayName("Deve retorna uma lista de veiculos por cor")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByColor() throws Exception {
        var mvcResult = mockMvc.perform(get(BASE_URL + "?cor=black")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals("black", body.get(0).color());
    }

    @Test
    @DisplayName("Deve retorna uma lista de veiculos por cor e ano")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByColorAndYear() throws Exception {
        var mvcResult = mockMvc.perform(get(BASE_URL + "?ano=2015&cor=red")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals("red", body.get(0).color());
        Assertions.assertEquals(2015, body.get(0).year());
    }

    @Test
    @DisplayName("Deve retornar uma lista de todos os veiculos que estão disponiveis")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListVehiclesNotSold() throws Exception {

        /*MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/not-sold")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk()).andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals(false, body.get(0).sold());*/
        System.out.println(LocalDate.now().toEpochDay());

    }

    @Test
    @DisplayName("Deve retornar uma lista de carros registrados dentro de uma semana")
    @Sql("/db/sql/reset.sql")
    public void shouldReturnListByWeek() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicle(
                "Car",
                "black",
                "HONDA",
                2020,
                "Economical and comfortable car",
                true);
        vehicleHonda.setCreatedAt(LocalDate.now().minusDays(3));

        repository.save(vehicleHonda);

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/by-last-week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk()).andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleResponse.class));

        Assertions.assertEquals(1, body.size());
        Assertions.assertEquals(2020, body.get(0).year());
    }

    @Test
    @DisplayName("Deve retornar uma lista de carros pela decada de fabricação")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnListByDecade() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/by-decade")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk()).andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleDecadeResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleDecadeResponse.class));

        Assertions.assertEquals(2020, body.get(0).decade());
        Assertions.assertEquals(3, body.get(0).quantity());
    }

    @Test
    @DisplayName("Deve retornar uma lista de veiculos pela marca")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldReturnGroupedByBrand() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/by-brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();

        List<VehicleBrandResponse> body = objectMapper.readValue(contentAsString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleBrandResponse.class));

        Assertions.assertEquals("HONDA", body.get(0).brand());
        Assertions.assertEquals(3, body.get(0).quantity());
    }

    @Test
    @DisplayName("Deve atualizar o veiculo")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldUpdateVehicle() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequest(
                "TRUCK",
                "black",
                "VOLKSWAGEN",
                2016,
                "Economical and comfortable car",
                true);
        var body = objectMapper.writeValueAsString(vehicleHonda);

        mockMvc.perform(put(BASE_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        var vehicle = repository.findById(100L).get();

        Assertions.assertEquals(vehicleHonda.vehicleType(), vehicle.getVehicleType());
        Assertions.assertEquals(vehicleHonda.brand(), vehicle.getBrand());
        Assertions.assertEquals(vehicleHonda.year(), vehicle.getvYear());
        Assertions.assertEquals(vehicleHonda.description(), vehicle.getDescription());

    }

    @Test
    @DisplayName("Deve retornar exceção se o id estiver incorreto")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldNotUpdateVehicleWithWrongId() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequest(
                "TRUCK",
                "black",
                "VOLKSWAGEN",
                2016,
                "Economical and comfortable car",
                true);
        var body = objectMapper.writeValueAsString(vehicleHonda);

        mockMvc.perform(put(BASE_URL + "/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar alguns campos especificos")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldUpdateSomeFieldFromVehicle() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequestPatch(
                "TRUCK",
                "black",
                "VOLKSWAGEN",
                null,
                null,
                true);

        var body = objectMapper.writeValueAsString(vehicleHonda);

        mockMvc.perform(patch(BASE_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
        var vehicle = repository.findById(100L).get();

        Assertions.assertEquals(vehicleHonda.brand(), vehicle.getBrand());
        Assertions.assertEquals(2014, vehicle.getvYear());
    }

    @Test
    @DisplayName("Deve retornar exceção se inserido o Brand incorretamente")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldNotUpdateSomeFieldWithWrongBrand() throws Exception {
        var vehicleHonda = VehicleFactory.createVehicleRequestPatch(
                "TRUCK",
                "black",
                "VOLKSWAGENNN",
                null,
                null,
                true);

        var body = objectMapper.writeValueAsString(vehicleHonda);

        mockMvc.perform(patch(BASE_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve deletar um veiculo pelo ID")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldDeleteVehicleById() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar exceção se o id não for localizado")
    @Sql({"/db/sql/reset.sql", "/db/sql/insert_into_vehicle.sql"})
    public void shouldNotDeleteVehicleByWrongId() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/17")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}