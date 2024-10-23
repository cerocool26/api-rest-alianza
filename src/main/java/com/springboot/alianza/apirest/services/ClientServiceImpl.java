package com.springboot.alianza.apirest.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClientResponse;
import com.springboot.alianza.apirest.exception.GeneralException;
import com.springboot.alianza.apirest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.alianza.apirest.models.repository.IClientRepository;

import lombok.extern.slf4j.Slf4j;

import com.springboot.alianza.apirest.models.entity.Client;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clienteRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public GeneralResponse<ClientResponse> findAllCustomer() {
        log.info("Llamando al método findAll()");
        ClientResponse clientResponse = new ClientResponse(clienteRepository.findAll().stream()
                .map(cliente -> mapper.map(cliente, ClienteDto.class)).collect(Collectors.toList()));
        return new GeneralResponse<>(clientResponse, HttpStatus.OK.getReasonPhrase());
    }

    @Override
    @Transactional
    public GeneralResponse<ClienteDto> saveCustomer(Client client) {
        log.info("Guardando cliente: {}", client);
        Client clientGuardado = clienteRepository.save(client);
        ClienteDto clienteDto = mapper.map(clientGuardado, ClienteDto.class);
        return new GeneralResponse<>(clienteDto, HttpStatus.CREATED.getReasonPhrase());
    }

    @Override
    public GeneralResponse<ClientResponse> findCustomerById(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Client client = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        ClienteDto clienteDto = mapper.map(client, ClienteDto.class);
        ClientResponse clientResponse = new ClientResponse(Collections.singletonList(clienteDto));

        return new GeneralResponse<>(clientResponse, HttpStatus.OK.getReasonPhrase());
    }

    @Override
    public GeneralResponse<ClienteDto> updateCustomer(Client client, Long id) {
        log.info("Actualizando cliente con ID: {}", id);
        try {
            log.info("Buscar el cliente por su ID");
            GeneralResponse<ClientResponse> clienteResponse = findCustomerById(id);
            ClientResponse clientResponse = clienteResponse.getData();
            log.info("Verificar si se encontró el cliente");
            if (clientResponse == null || clientResponse.getClientes().isEmpty()) {
                throw new NotFoundException("Cliente no encontrado");
            }
            log.info("Actualizar los datos del cliente");
            ClienteDto clienteDto = clientResponse.getClientes().get(0);
            clienteDto.setSharedKey(client.getSharedKey());
            clienteDto.setBusinessId(client.getBusinessId());
            clienteDto.setEmail(client.getEmail());
            clienteDto.setPhone(client.getPhone());
            clienteDto.setDataAdd(client.getDataAdd());
            GeneralResponse<ClienteDto> clienteUpdated = saveCustomer(mapper.map(clienteDto, Client.class));
            log.info("Cliente actualizado correctamente: {}", clienteUpdated);

            return new GeneralResponse<>(clienteDto, HttpStatus.CREATED.getReasonPhrase());

        } catch (DataAccessException e) {
            log.error("Error al actualizar el cliente en la base de datos", e);
            throw new GeneralException("Error al actualizar el cliente en la base de datos");
        }
    }


    @Override
    public GeneralResponse<ClientResponse> findCustomerBySharedKey(String sharedKey) {
        log.info("Llamando al método findBySharedKeyContainingIgnoreCase()");
        List<Client> clients = clienteRepository.findBySharedKeyContainingIgnoreCase(sharedKey);
        List<ClienteDto> clienteDtos = clients.stream().map(cliente -> mapper.map(cliente, ClienteDto.class))
                .collect(Collectors.toList());
        ClientResponse clientResponse = new ClientResponse(clienteDtos);

        return new GeneralResponse<>(clientResponse, HttpStatus.OK.getReasonPhrase());
    }

}
