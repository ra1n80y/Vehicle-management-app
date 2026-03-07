package com.godfrey.vehicle;

import com.godfrey.vehicle.controller.VehicleController;
import com.godfrey.vehicle.model.Vehicle;
import com.godfrey.vehicle.service.IVehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IVehicleService service;

    @Test
    void shouldReturnAllVehicles() throws Exception {

        List<Vehicle> vehicles = List.of(
                new Vehicle(1L, "Toyota", "Corolla", "Sedan", 2022),
                new Vehicle(2L, "Ford", "Ranger", "Truck", 2021)
        );

        when(service.fetchAllVehicles()).thenReturn(vehicles);

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Toyota"));
    }
}