package com.developer.employeemanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.developer.employeemanagement.controller.EmployeeController;
import com.developer.employeemanagement.entity.EmployeeEntity;
import com.developer.employeemanagement.service.EmployeeService;

public class EmployeeManagementApplicationTests{

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);

        when(employeeService.saveEmployee(any(EmployeeEntity.class))).thenReturn(employee);

        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(employeeService, times(1)).saveEmployee(any(EmployeeEntity.class));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);

        when(employeeService.updateEmployee(any(EmployeeEntity.class))).thenReturn(employee);

        mockMvc.perform(put("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(employeeService, times(1)).updateEmployee(any(EmployeeEntity.class));
    }

    @Test
    public void testFindOneEmployee() throws Exception {
        Long id = 1L;
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(id);

        when(employeeService.findById(id)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(employeeService, times(1)).findById(id);
    }

    @Test
    public void testFindAllEmployees() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);

        when(employeeService.findAllEmployee()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(employeeService, times(1)).findAllEmployee();
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/employee/{id}", id))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(id);
    }
}