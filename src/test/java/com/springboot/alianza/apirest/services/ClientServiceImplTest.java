package com.springboot.alianza.apirest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.springboot.alianza.apirest.models.entity.Client;
import com.springboot.alianza.apirest.models.repository.IClientRepository;

@SpringBootTest
public class ClientServiceImplTest {

    @Mock
    private IClientRepository clienteRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private ClientServiceImpl clienteService;
    static final Long ID = 1L;
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setId(1L);
        client.setSharedKey("jvillamizar");
        client.setBusinessId("jerson villamizar");
        client.setEmail("ing.jersonvilla@gmail.com");
        client.setPhone("3154650762");
        client.setDataAdd(new Date());
    }

    @Test
    void findAllCustomerTest() {
        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(clienteRepository.findAll()).thenReturn(clients);

        GeneralResponse<ClientResponse> response = clienteService.findAllCustomer();

        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getClientes()).hasSize(2);
    }

    @Test
    void saveCustomerTest() {
        Client client = new Client();
        when(clienteRepository.save(client)).thenReturn(client);

        GeneralResponse<ClienteDto> response = clienteService.saveCustomer(client);

        assertThat(response).isNotNull();
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findCustomerByIdTest() {
        when(clienteRepository.findById(ID)).thenReturn(Optional.of(client));

        GeneralResponse<ClientResponse> response = clienteService.findCustomerById(ID);

        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getClientes()).hasSize(1);
    }


    @Test
    void updateCustomerTest() {
        when(clienteRepository.findById(ID)).thenReturn(Optional.of(client));
        when(mapper.map(client, ClienteDto.class)).thenReturn(new ClienteDto());
        when(clienteRepository.save(client)).thenReturn(client);

        GeneralResponse<ClienteDto> response = clienteService.updateCustomer(client, ID);

        assertThat(response.getData()).isNotNull();
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
	
}
