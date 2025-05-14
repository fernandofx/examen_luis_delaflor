package com.examen.hexagonal.application.controller;


import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.aggregates.response.ResponseBase;
import com.examen.hexagonal.domain.ports.in.EmpresaServiceIn;
import com.examen.hexagonal.domain.aggregates.request.UpdateEstadoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/empresa/")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaServiceIn serviceIn;

    @PostMapping("/save_open_feign")
    public ResponseEntity<ResponseBase<EmpresaDTO>>  createOpenFeign(@RequestParam("ruc") String ruc){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.createOpenFeignIn(ruc)));
        return ResponseEntity.ok(responseBase) ;
    }

    @PostMapping("/save_retrofit")
    public ResponseEntity<ResponseBase<EmpresaDTO>> createRetrofit(@RequestParam("ruc") String ruc){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.createRetrofitIn(ruc)));
        return ResponseEntity.ok(responseBase) ;
    }

    @PostMapping("/save_rest_template")
    public ResponseEntity<ResponseBase<EmpresaDTO>> createRestTemplate(@RequestParam("ruc") String ruc){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.createRestTemplateIn(ruc)));
        return ResponseEntity.ok(responseBase) ;

    }

    @GetMapping("/find/{ruc}")
    public ResponseEntity<ResponseBase<EmpresaDTO>>  findByRuc(@PathVariable("ruc") String ruc){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.findByRucIn(ruc)));
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBase<EmpresaDTO>> updateEstadoByRuc(@RequestBody UpdateEstadoRequest updateEstadoRequest){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.updateEstadoByRucIn(updateEstadoRequest)));
        return ResponseEntity.ok(responseBase);
    }

    @DeleteMapping("/delete/{ruc}")
    public ResponseEntity<ResponseBase<EmpresaDTO>>  deleteByRuc(@PathVariable("ruc") String ruc){
        ResponseBase<EmpresaDTO> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.deleteByRucIn(ruc)));
        return ResponseEntity.ok(responseBase);

    }

    @GetMapping("/findAll")
    public ResponseEntity<ResponseBase<List<EmpresaDTO>>>  findAll(){
        ResponseBase<List<EmpresaDTO>> responseBase = new ResponseBase<>("200", "ok", Optional.of(serviceIn.findByAllIn()));
        return ResponseEntity.ok(responseBase);

    }


}
