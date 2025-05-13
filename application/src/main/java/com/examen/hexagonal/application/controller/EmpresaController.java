package com.examen.hexagonal.application.controller;


import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.ports.in.EmpresaServiceIn;
import com.examen.hexagonal.domain.aggregates.request.UpdateEstadoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/empresa/")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaServiceIn serviceIn;

    @PostMapping("/save_open_feign")
    public ResponseEntity<EmpresaDTO> createOpenFeign(@RequestParam("ruc") String ruc){
        return ResponseEntity.ok(serviceIn.createOpenFeignIn(ruc));
    }

    @PostMapping("/save_retrofit")
    public ResponseEntity<EmpresaDTO> createRetrofit(@RequestParam("ruc") String ruc){
        return ResponseEntity.ok(serviceIn.createRetrofitIn(ruc));
    }

    @PostMapping("/save_rest_template")
    public ResponseEntity<EmpresaDTO> createRestTemplate(@RequestParam("ruc") String ruc){
        return ResponseEntity.ok(serviceIn.createRestTemplateIn(ruc));
    }

    @GetMapping("/find/{ruc}")
    public ResponseEntity<EmpresaDTO> findByRuc(@PathVariable("ruc") String ruc){
        return ResponseEntity.ok(serviceIn.findByRucIn(ruc));
    }

    @PutMapping("/update")
    public ResponseEntity<EmpresaDTO> updateEstadoByRuc(@RequestBody UpdateEstadoRequest updateEstadoRequest){
        return ResponseEntity.ok(serviceIn.updateEstadoByRucIn(updateEstadoRequest));
    }
}
