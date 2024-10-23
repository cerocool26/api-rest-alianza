package com.springboot.alianza.apirest.controllers;

import java.io.IOException;

import static com.springboot.alianza.apirest.utilities.Constantes.FIND_ALL;
import static com.springboot.alianza.apirest.utilities.Constantes.FIND_BY_ID;
import static com.springboot.alianza.apirest.utilities.Constantes.SAVE_CLIENT;
import static com.springboot.alianza.apirest.utilities.Constantes.UPDATE_CLIENT;
import static com.springboot.alianza.apirest.utilities.Constantes.SHARED_KEY;
import static com.springboot.alianza.apirest.utilities.Constantes.EXPORT_CSV;
import static com.springboot.alianza.apirest.utilities.Constantes.EXPORT_CSV_COMPLETE;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.alianza.apirest.models.entity.Client;
import com.springboot.alianza.apirest.services.IClientService;
import com.springboot.alianza.apirest.utilities.ExcelUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api-rest")
public class ClientController {

    private final IClientService clienteService;

    /**
     * Metodo que devuelve todos los clientes
     *
     * @return GeneralResponse
     */
    @GetMapping("/clientes")
    public GeneralResponse<ClientResponse> findAllCustomer() {
        log.info(FIND_ALL);
        return clienteService.findAllCustomer();
    }

    /**
     * Metodo para encontrar un customer por id
     *
     * @param id
     * @return
     */
    @GetMapping("/clientes/{id}")
    public GeneralResponse<ClientResponse> findCustomerById(@PathVariable Long id) {
        log.info(FIND_BY_ID, id);
        return clienteService.findCustomerById(id);
    }

    /**
     * Metodo que registra un cliente
     *
     * @param client
     * @return
     */
    @PostMapping("/clientes")
    public GeneralResponse<ClienteDto> saveCustomer(@Valid @RequestBody Client client) {
        log.info(SAVE_CLIENT, client.getBusinessId());
        return clienteService.saveCustomer(client);
    }

    /**
     * Metodo para actulizar un cliente por id
     *
     * @param client
     * @param id
     * @return
     */
    @PutMapping("/clientes/{id}")
    public GeneralResponse<ClienteDto> updateCustomer(@RequestBody Client client, @PathVariable Long id) {
        log.info(UPDATE_CLIENT, id);
        return clienteService.updateCustomer(client, id);
    }

    /**
     * Metodo para buscar un cliente
     *
     * @param sharedKey
     * @return
     */
    @GetMapping("/clientes/buscar")
    public GeneralResponse<ClientResponse> searchCustomerBySharedKey(@RequestParam("sharedKey") String sharedKey) {
        log.info(SHARED_KEY);
        return clienteService.findCustomerBySharedKey(sharedKey);
    }

    /**
     * Metodo para exportar en un csv todos los clientes
     *
     * @return
     */
    @GetMapping("/clientes/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        log.info(EXPORT_CSV);
        GeneralResponse<ClientResponse> clients = clienteService.findAllCustomer();
        ExcelUtil.exportToExcel(response, clients.getData().getClientes());
        log.info(EXPORT_CSV_COMPLETE);
    }
}
