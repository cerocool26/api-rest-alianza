package com.springboot.alianza.apirest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClientResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.springboot.alianza.apirest.models.entity.Client;
import com.springboot.alianza.apirest.services.IClientService;

@SpringBootTest
public class ClientControllerTest {
    @Mock
    IClientService clienteService;
    @InjectMocks
    ClientController clientController;

    static final Long ID = 1L;
    List<ClienteDto> clientes = new ArrayList<>();
    ClientResponse clientResponse = new ClientResponse();

    @Test
    void findAllCustomer() {
        clientes.add(new ClienteDto(1L, "acromero", "Andres Romero", "andrescamiloro@gmail.com", "3213107225", new Date()));
        clientResponse.setClientes(clientes);
        when(clienteService.findAllCustomer())
                .thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClientResponse.builder().clientes(clientes).build()));
        GeneralResponse<ClientResponse> response = clientController.findAllCustomer();
        assertNotNull(response);
        assertNotNull(response.getData());
        List<ClienteDto> clientesEnRespuesta = response.getData().getClientes();
        assertNotNull(clientesEnRespuesta);
        assertEquals(2, clientesEnRespuesta.size());
    }

    @Test
    void findCustomerById() throws Exception {
        when(clienteService.findCustomerById(ID))
                .thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClientResponse.builder().clientes(clientes).build()));
        GeneralResponse<ClientResponse> response = clientController.findCustomerById(ID);
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

    @Test
    void saveCustomerTest() throws Exception {
        when(clienteService.saveCustomer(new Client()))
                .thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));
        GeneralResponse<ClienteDto> response = clientController.saveCustomer(new Client());
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

    @Test
    void updateCustomer() throws Exception {
        when(clienteService.updateCustomer(new Client(), ID))
                .thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));
        GeneralResponse<ClienteDto> response = clientController.updateCustomer(new Client(), ID);
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

}
