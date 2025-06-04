package org.dam.controller;

import org.dam.dto.PreguntaRequest;
import org.dam.dto.PreguntaResponse;
import org.dam.service.PreguntaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/preguntas")  // ⚠️ IMPORTANTE: diferente de /pregunta
public class PreguntaWebController {

    private final PreguntaService preguntaService;

    public PreguntaWebController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
        System.out.println("✅ PreguntaWebController creado");
    }


    @GetMapping
    public String listarPreguntas(Model model) {
        List<PreguntaResponse> preguntas = preguntaService.obtenerTodas();
        System.out.println("📋 Total preguntas encontradas: " + preguntas.size());

        model.addAttribute("titulo", "Listado de Preguntas");
        model.addAttribute("preguntas", preguntas);
        model.addAttribute("preguntaRequest", new PreguntaRequest("", "", List.of(""), ""));
        return "listado";
    }

}

