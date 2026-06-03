package com.trabajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trabajo.model.Traslado;
import com.trabajo.service.TrasladoService;

@RestController
@RequestMapping("api/v1/Traslado")
public class TrasladoController {

@Autowired
    private TrasladoService trasladoService;

public TrasladoController(TrasladoService trasladoService) {
        this.trasladoService = trasladoService;
    }


    @GetMapping("/{id}/aprobar")
        public ResponseEntity<Traslado> aprobar(@PathVariable Long id) {
            return ResponseEntity.ok(trasladoService.aprobar(id));
        }

    @GetMapping("/{id}/rechazar")
        public ResponseEntity<Traslado> rechazar(
                @PathVariable Long id,
                @RequestParam String motivo) {

            return ResponseEntity.ok(trasladoService.rechazar(id, motivo));
        }

    @GetMapping("/{id}/cancelar")
    public ResponseEntity<Traslado> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(trasladoService.cancelar(id));
    }
}






