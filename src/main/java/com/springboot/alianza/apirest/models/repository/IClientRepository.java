package com.springboot.alianza.apirest.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.alianza.apirest.models.entity.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long>{
	
	List<Client> findBySharedKeyContainingIgnoreCase(String sharedKey);

}
