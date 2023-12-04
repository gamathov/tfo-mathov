package ar.com.codoacodo.repository;

import java.time.LocalDateTime;
import java.util.List;

import ar.com.codoacodo.entity.Orador;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		OradorRepository repository = new MySqlOradorRepository();
		//Orador orador = repository.getById(1L);
		List<Orador> orador = repository.getAll();
		System.out.println(orador);	

	}

}
