package ar.com.codoacodo.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.com.codoacodo.entity.Orador;
import ar.com.codoacodo.repository.MySqlOradorRepository;
import ar.com.codoacodo.repository.OradorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class NuevoOradorController extends HttpServlet {
@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response)
	throws ServletException , IOException {
		//obtener json desde el front
		String json=request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		//convertir de String json a Obj java con Jackson2
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		OradorRequest oradorRequest = mapper.readValue(json,OradorRequest.class);
		
		//grabar en DB
		OradorRepository repository = new MySqlOradorRepository();		
		Orador orador = new Orador(oradorRequest.getNombre(), 
				oradorRequest.getApellido(), 
				oradorRequest.getMail(), 
				oradorRequest.getTema(), 
				LocalDateTime.now());
		
		repository.save(orador);
		
		//convierto ahora Objeto java a String
				//enviar por medio de response al frontend
				response.getWriter().print(mapper.writeValueAsString(orador));
		
}
}
