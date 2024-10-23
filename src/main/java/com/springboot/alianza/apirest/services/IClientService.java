package com.springboot.alianza.apirest.services;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClientResponse;
import com.springboot.alianza.apirest.models.entity.Client;

public interface IClientService {

    /**
     * Metodo que devuelve todos los clientes
     *
     * @return GeneralResponse
     */
    GeneralResponse<ClientResponse> findAllCustomer();

    /**
     * Metodo que registra un cliente
     *
     * @param client
     * @return
     */
    GeneralResponse<ClienteDto> saveCustomer(Client client);

    /**
     * Metodo para encontrar un customer por id
     *
     * @param id
     * @return
     */
    GeneralResponse<ClientResponse> findCustomerById(Long id);

    /**
     * Metodo para actulizar un cliente por id
     *
     * @param client
     * @param id
     * @return
     */
    GeneralResponse<ClienteDto> updateCustomer(Client client, Long id);

    /**
     * Metodo para buscar un cliente
     *
     * @param sharedKey
     * @return
     */
    GeneralResponse<ClientResponse> findCustomerBySharedKey(String sharedKey);

}
