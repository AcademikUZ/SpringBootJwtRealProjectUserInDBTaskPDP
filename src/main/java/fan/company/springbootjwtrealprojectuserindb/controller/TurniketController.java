package fan.company.springbootjwtrealprojectuserindb.controller;

import fan.company.springbootjwtrealprojectuserindb.entity.Tasks;
import fan.company.springbootjwtrealprojectuserindb.entity.Turniket;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.TaskDto;
import fan.company.springbootjwtrealprojectuserindb.payload.TurniketDto;
import fan.company.springbootjwtrealprojectuserindb.service.TaskService;
import fan.company.springbootjwtrealprojectuserindb.service.TurniketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/turniket")
public class TurniketController {

    @Autowired
    TurniketService service;

    @PostMapping
    public HttpEntity<?> add (@Valid @RequestBody TurniketDto dto){
        ApiResult apiResult = service.add(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @GetMapping("/getByUser/{id}")
    public HttpEntity<?> getByUser (@PathVariable Long id){
        List<Turniket> list = service.getByUserId(id);
        return ResponseEntity.status(!list.isEmpty() ? HttpStatus.OK:HttpStatus.CONFLICT).body(list);
    }

    @GetMapping("/getByTurniketType/{id}")
    public HttpEntity<?> getByTurniketType (@PathVariable Long id){
        List<Turniket> list = service.getByTurniketType(id);
        return ResponseEntity.status(!list.isEmpty() ? HttpStatus.OK:HttpStatus.CONFLICT).body(list);
    }

    @GetMapping("/getByCreatedTime")
    public HttpEntity<?> getByCreatedTime (@RequestBody Timestamp start, @RequestBody Timestamp end){
        List<Turniket> list = service.getByTCreateTime(start, end);
        return ResponseEntity.status(!list.isEmpty() ? HttpStatus.OK:HttpStatus.CONFLICT).body(list);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page) {
        Page<Turniket> all = service.getAll(page);
        return ResponseEntity.status(all.hasContent() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    /**
     * javax.validation ga o'zbekcha habar yuvorish uchun kerak
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }





}
